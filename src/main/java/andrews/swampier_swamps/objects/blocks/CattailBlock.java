package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CattailBlock extends BushBlock
{
    protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    public static final BooleanProperty LOWER = BooleanProperty.create("lower");

    public CattailBlock()
    {
        super(BlockBehaviour.Properties.of(Material.WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, true).setValue(LOWER, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return SHAPE;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state1, boolean p_60519_)
    {
        super.onRemove(state, level, pos, state1, p_60519_);
        if(!state.getValue(BlockStateProperties.WATERLOGGED))
        {
            if(level.getBlockState(pos.above()).is(this))
                level.destroyBlock(pos.above(), false);
            if(level.getBlockState(pos.below()).is(this))
                level.destroyBlock(pos.below(), false);
            if(level.getBlockState(pos.below(2)).is(this))
                level.destroyBlock(pos.below(2), false);
            if(level.getBlockState(pos.below(3)).is(this))
                level.destroyBlock(pos.below(3), false);
        }
        else
        {
            if(level.getBlockState(pos.below()).is(this))
                level.destroyBlock(pos.below(), false);
            if(level.getBlockState(pos.above()).is(this))
                level.destroyBlock(pos.above(), false);
            if(level.getBlockState(pos.above(2)).is(this))
                level.destroyBlock(pos.above(2), false);
            if(level.getBlockState(pos.above(3)).is(this))
                level.destroyBlock(pos.above(3), false);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean p_60570_)
    {
        BlockState blockStateBelow1 = level.getBlockState(pos.below());
        BlockState blockStateAbove1 = level.getBlockState(pos.above());
        BlockState blockStateAbove2 = level.getBlockState(pos.above(2));
        BlockState blockStateAbove3 = level.getBlockState(pos.above(3));

        if (!blockStateBelow1.is(this) && blockStateAbove1.is(Blocks.WATER) && blockStateAbove2.is(Blocks.AIR) && blockStateAbove3.is(Blocks.AIR))
        {
            level.setBlock(pos.above(), this.defaultBlockState().setValue(LOWER, false), 2);                             // Middle Block In Water
            level.setBlock(pos.above(2), this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false), 2); // First Block Above Water
            level.setBlock(pos.above(3), this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(LOWER, false), 2); // Top Block Above Water
        }
        else if (!blockStateBelow1.is(this) && blockStateAbove1.is(Blocks.AIR) && blockStateAbove2.is(Blocks.AIR))
        {
            level.setBlock(pos.above(), this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false), 2); // First Block Above Water
            level.setBlock(pos.above(2), this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false).setValue(LOWER, false), 2); // Top Block Above Water
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        if(isValidCattailPosition(context.getLevel(), context.getClickedPos()))
            return super.getStateForPlacement(context);
        return null;
    }

    /**
     * Gets called inside BoneMealItem to check if the Block can be placed at a randomly determined BlockPos
     * around the area that was BoneMealed
     */
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        if (state.getBlock() == this)
            return this.mayPlaceOn(level.getBlockState(posBelow), level, posBelow) || level.getBlockState(posBelow).is(this);

        return this.mayPlaceOn(level.getBlockState(posBelow), level, posBelow);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition)
    {
        stateDefinition.add(BlockStateProperties.WATERLOGGED).add(LOWER);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter blockGetter, BlockPos pos)
    {
        return state.isFaceSturdy(blockGetter, pos, Direction.UP) && !state.is(Blocks.MAGMA_BLOCK);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos pos, BlockPos pos1)
    {
        BlockState blockstate = super.updateShape(state, direction, state1, levelAccessor, pos, pos1);
        if (!blockstate.isAir())
            levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        return blockstate;
    }

    /**
     * Used to determine if the position we want to place Cattail at, is a valid one
     * @param level The Level
     * @param pos The BlockPosition to check around
     * @return Whether the position is Valid
     */
    public static boolean isValidCattailPosition(Level level, BlockPos pos)
    {
        FluidState fluidstate = level.getFluidState(pos);
        BlockState blockStateBelow1 = level.getBlockState(pos.below());
        BlockState blockStateAbove1 = level.getBlockState(pos.above());
        BlockState blockStateAbove2 = level.getBlockState(pos.above(2));
        BlockState blockStateAbove3 = level.getBlockState(pos.above(3));

        if(fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == FluidState.AMOUNT_FULL)
        {
            return (!blockStateBelow1.is(SSBlocks.CATTAIL) && blockStateAbove1.is(Blocks.WATER) && blockStateAbove2.is(Blocks.AIR) && blockStateAbove3.is(Blocks.AIR)) ||
                   (!blockStateBelow1.is(SSBlocks.CATTAIL) && blockStateAbove1.is(Blocks.AIR) && blockStateAbove2.is(Blocks.AIR));
        }
        return false;
    }
}