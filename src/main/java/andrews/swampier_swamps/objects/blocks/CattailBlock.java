package andrews.swampier_swamps.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CattailBlock extends BushBlock
{
    protected static final VoxelShape SHAPE     = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    protected static final VoxelShape SHAPE_TOP = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);

    public CattailBlock()
    {
        super(BlockBehaviour.Properties.of(Material.WATER_PLANT).noCollission().instabreak().sound(SoundType.WET_GRASS));
        this.registerDefaultState(this.stateDefinition.any().setValue(BlockStateProperties.WATERLOGGED, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? SHAPE : SHAPE_TOP;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState state1, boolean p_60519_)
    {
        super.onRemove(state, level, pos, state1, p_60519_);
        if(!state.getValue(BlockStateProperties.WATERLOGGED))
        {
            if(level.getBlockState(pos.below()).is(this))
                level.destroyBlock(pos.below(), true);
            if(level.getBlockState(pos.below(2)).is(this))
                level.destroyBlock(pos.below(2), true);
        }
        else
        {
            if(level.getBlockState(pos.below()).is(this))
                level.destroyBlock(pos.below(), true);
            if(level.getBlockState(pos.above()).is(this))
                level.destroyBlock(pos.above(), true);
            if(level.getBlockState(pos.above(2)).is(this))
                level.destroyBlock(pos.above(2), true);
        }
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState state1, boolean p_60570_)
    {
        FluidState fluidstate = level.getFluidState(pos);
        BlockState blockStateAbove = level.getBlockState(pos.above());

        if(fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8)
        {
            if(blockStateAbove.is(Blocks.WATER) && level.getBlockState(pos.above(2)).is(Blocks.AIR))
            {
                level.setBlock(pos.above(2), this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false), 2); // Top Block (Above Water)
                level.setBlock(pos.above(), this.defaultBlockState(), 2);                                                    // Middle Block (In Water)
            }
            else if(blockStateAbove.is(Blocks.AIR) && !level.getBlockState(pos.below()).is(this))
            {
                level.setBlock(pos.above(), this.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false), 2); // Top Block (Above Water)
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        BlockState blockStateAbove = context.getLevel().getBlockState(context.getClickedPos().above());

        if(fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == 8)
        {
            if(blockStateAbove.is(Blocks.WATER) && context.getLevel().getBlockState(context.getClickedPos().above(2)).is(Blocks.AIR))
            {
                return super.getStateForPlacement(context);                                                                                                  // Bottom Block (In Water)
            }
            else if(blockStateAbove.is(Blocks.AIR) && !context.getLevel().getBlockState(context.getClickedPos().below()).is(this))
            {
                return super.getStateForPlacement(context);                                                                                                  // Bottom Block (In Water)
            }
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        BlockPos posBelow = pos.below();
        if (state.getBlock() == this)
            return level.getBlockState(posBelow).canSustainPlant(level, posBelow, Direction.UP, this) ||
                    level.getBlockState(posBelow).getBlock().equals(this);

        return this.mayPlaceOn(level.getBlockState(posBelow), level, posBelow);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition)
    {
        stateDefinition.add(BlockStateProperties.WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

//    @Override
//    public BlockState updateShape(BlockState p_154530_, Direction p_154531_, BlockState p_154532_, LevelAccessor p_154533_, BlockPos p_154534_, BlockPos p_154535_)
//    {
//        BlockState blockstate = super.updateShape(p_154530_, p_154531_, p_154532_, p_154533_, p_154534_, p_154535_);
//        if (!blockstate.isAir()) {
//            p_154533_.scheduleTick(p_154534_, Fluids.WATER, Fluids.WATER.getTickDelay(p_154533_));
//        }
//
//        return blockstate;
//    }
}