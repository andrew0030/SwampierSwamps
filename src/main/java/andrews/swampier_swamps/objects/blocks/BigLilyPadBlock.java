package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BigLilyPadBlock extends WaterlilyBlock
{
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty LILY_PAD_PART = IntegerProperty.create("lily_pad_part", 0, 3);

    protected static final VoxelShape AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);

    public BigLilyPadBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(LILY_PAD_PART, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        if (rand.nextInt(25) == 0)
        {
            int configValue = SSConfigs.commonConfig.shouldLilyPadsGrow.get();
            if ((configValue == 1 && level.getBiome(pos).is(SSTags.Biomes.CAN_LILY_PAD_GROW_IN)) || configValue == 2)
            {
                int smallLilyPadLimit = 30;

                for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-5, -1, -5), pos.offset(5, 1, 5)))
                {
                    if (level.getBlockState(blockpos).is(SSBlocks.SMALL_LILY_PAD.get()))
                    {
                        --smallLilyPadLimit;
                        if (smallLilyPadLimit <= 0)
                            return;
                    }
                }

                BlockPos randPosForPlacement = pos.offset(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
                BlockState smallLilyPadState = SSBlocks.SMALL_LILY_PAD.get().defaultBlockState();

                for (int i = 0; i < 4; ++i)
                {
                    if (level.isEmptyBlock(randPosForPlacement) && smallLilyPadState.canSurvive(level, randPosForPlacement))
                        pos = randPosForPlacement;

                    randPosForPlacement = pos.offset(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
                }

                if (level.isEmptyBlock(randPosForPlacement) && smallLilyPadState.canSurvive(level, randPosForPlacement))
                    level.setBlock(randPosForPlacement, smallLilyPadState, 2);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return AABB;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        Direction direction = context.getHorizontalDirection();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        FluidState fluid = level.getFluidState(pos.below());
        BlockPos pos1 = context.getClickedPos().relative(direction);
        FluidState fluid1 = level.getFluidState(pos1.below());
        BlockPos pos2 = context.getClickedPos().relative(direction.getClockWise());
        FluidState fluid2 = level.getFluidState(pos2.below());
        BlockPos pos3 = context.getClickedPos().relative(direction.getClockWise()).relative(direction);
        FluidState fluid3 = level.getFluidState(pos3.below());

        if((((fluid.getType() == Fluids.WATER && fluid.getAmount() == FluidState.AMOUNT_FULL) || level.getBlockState(pos.below()).getMaterial() == Material.ICE) && level.getBlockState(pos).is(Blocks.AIR)) &&
           (((fluid1.getType() == Fluids.WATER && fluid1.getAmount() == FluidState.AMOUNT_FULL) || level.getBlockState(pos1.below()).getMaterial() == Material.ICE) && level.getBlockState(pos1).is(Blocks.AIR)) &&
           (((fluid2.getType() == Fluids.WATER && fluid2.getAmount() == FluidState.AMOUNT_FULL) || level.getBlockState(pos2.below()).getMaterial() == Material.ICE) && level.getBlockState(pos2).is(Blocks.AIR)) &&
           (((fluid3.getType() == Fluids.WATER && fluid3.getAmount() == FluidState.AMOUNT_FULL) || level.getBlockState(pos3.below()).getMaterial() == Material.ICE) && level.getBlockState(pos3).is(Blocks.AIR)))
            return this.defaultBlockState().setValue(FACING, direction);
        return null;
    }

        @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        super.onRemove(state, level, pos, newState, isMoving);
        Direction direction = state.getValue(FACING);
        int type = state.getValue(LILY_PAD_PART);

        if (!level.isClientSide)
        {
            if(type == 0)
            {
                if (level.getBlockState(pos.relative(direction)).is(this) && level.getBlockState(pos.relative(direction)).getValue(LILY_PAD_PART) == 1)
                    level.destroyBlock(pos.relative(direction), false);
                if (level.getBlockState(pos.relative(direction.getClockWise())).is(this) && level.getBlockState(pos.relative(direction.getClockWise())).getValue(LILY_PAD_PART) == 2)
                    level.destroyBlock(pos.relative(direction.getClockWise()), false);
            }
            else if (type == 1)
            {
                if (level.getBlockState(pos.relative(direction.getOpposite())).is(this) && level.getBlockState(pos.relative(direction.getOpposite())).getValue(LILY_PAD_PART) == 0)
                    level.destroyBlock(pos.relative(direction.getOpposite()), false);
                if (level.getBlockState(pos.relative(direction.getClockWise())).is(this) && level.getBlockState(pos.relative(direction.getClockWise())).getValue(LILY_PAD_PART) == 3)
                    level.destroyBlock(pos.relative(direction.getClockWise()), false);
            }
            else if (type == 2)
            {
                if (level.getBlockState(pos.relative(direction)).is(this) && level.getBlockState(pos.relative(direction)).getValue(LILY_PAD_PART) == 3)
                    level.destroyBlock(pos.relative(direction), false);
                if (level.getBlockState(pos.relative(direction.getCounterClockWise())).is(this) && level.getBlockState(pos.relative(direction.getCounterClockWise())).getValue(LILY_PAD_PART) == 0)
                    level.destroyBlock(pos.relative(direction.getCounterClockWise()), false);
            }
            else if (type == 3)
            {
                if (level.getBlockState(pos.relative(direction.getOpposite())).is(this) && level.getBlockState(pos.relative(direction.getOpposite())).getValue(LILY_PAD_PART) == 2)
                    level.destroyBlock(pos.relative(direction.getOpposite()), false);
                if (level.getBlockState(pos.relative(direction.getCounterClockWise())).is(this) && level.getBlockState(pos.relative(direction.getCounterClockWise())).getValue(LILY_PAD_PART) == 1)
                    level.destroyBlock(pos.relative(direction.getCounterClockWise()), false);
            }
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if (!level.isClientSide)
        {
            if (level.getBlockState(pos).getValue(LILY_PAD_PART) == 0)
            {
                Direction direction = state.getValue(FACING);
                level.setBlock(pos.relative(direction), SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(FACING, direction).setValue(LILY_PAD_PART, 1), 2);
                level.setBlock(pos.relative(direction.getClockWise()), SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(FACING, direction).setValue(LILY_PAD_PART, 2), 2);
                level.setBlock(pos.relative(direction.getClockWise()).relative(direction), SSBlocks.BIG_LILY_PAD.get().defaultBlockState().setValue(FACING, direction).setValue(LILY_PAD_PART, 3), 2);
            }
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos)
    {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState fluidstateAbove = level.getFluidState(pos.above());
        return (fluidstate.getType() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstateAbove.getType() == Fluids.EMPTY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FACING, LILY_PAD_PART);
    }
}
