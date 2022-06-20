package andrews.swampier_swamps.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;

public class SwampVineBlock extends VineBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public SwampVineBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(UP, false).setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        FluidState fluidstate = context.getLevel().getFluidState(context.getClickedPos());
        boolean isWaterloggableVine = blockstate.is(this);
        BlockState state = isWaterloggableVine ? blockstate : this.defaultBlockState();

        for(Direction direction : context.getNearestLookingDirections())
        {
            if (direction != Direction.DOWN)
            {
                BooleanProperty booleanproperty = getPropertyForFace(direction);
                boolean flag1 = isWaterloggableVine && blockstate.getValue(booleanproperty);
                if (!flag1 && this.canSupportAtFace(context.getLevel(), context.getClickedPos(), direction))
                    return state.setValue(booleanproperty, true).setValue(WATERLOGGED, fluidstate.getType() == Fluids.WATER);
            }
        }
        return isWaterloggableVine ? state : null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(UP, NORTH, EAST, SOUTH, WEST, WATERLOGGED);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor levelAccessor, BlockPos pos, BlockPos pos1)
    {
        if (state.getValue(WATERLOGGED))
            levelAccessor.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));

        if (direction == Direction.DOWN)
        {
            return state;
        }
        else
        {
            BlockState blockstate = this.getUpdatedState(state, levelAccessor, pos);
            return !this.hasFaces(blockstate) ? Blocks.AIR.defaultBlockState() : blockstate;
        }
    }

    private boolean hasFaces(BlockState state)
    {
        int faces = 0;
        for(BooleanProperty booleanproperty : PROPERTY_BY_DIRECTION.values())
        {
            if (state.getValue(booleanproperty))
                ++faces;
        }
        return faces > 0;
    }

    private BlockState getUpdatedState(BlockState state, BlockGetter getter, BlockPos pos)
    {
        BlockPos blockpos = pos.above();
        if (state.getValue(UP))
            state = state.setValue(UP, isAcceptableNeighbour(getter, blockpos, Direction.DOWN));

        BlockState blockstate = null;
        for(Direction direction : Direction.Plane.HORIZONTAL)
        {
            BooleanProperty booleanproperty = getPropertyForFace(direction);
            if (state.getValue(booleanproperty))
            {
                boolean canSupport = this.canSupportAtFace(getter, pos, direction);
                if (!canSupport)
                {
                    if (blockstate == null)
                        blockstate = getter.getBlockState(blockpos);

                    canSupport = blockstate.is(this) && blockstate.getValue(booleanproperty);
                }

                state = state.setValue(booleanproperty, canSupport);
            }
        }
        return state;
    }

    @Override
    public FluidState getFluidState(BlockState state)
    {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        if (level.random.nextInt(4) == 0 && level.isAreaLoaded(pos, 4))
        {
            Direction direction = Direction.getRandom(rand);
            BlockPos blockpos = pos.above();
            if (direction.getAxis().isHorizontal() && !state.getValue(getPropertyForFace(direction)))
            {
                if (this.canSpread(level, pos)) // We make sure the SwampVine can spread to surrounding blocks
                {
                    BlockPos relativePosInDir = pos.relative(direction);
                    BlockState relativePosState = level.getBlockState(relativePosInDir);
                    if (relativePosState.isAir()) // We make sure the block in the direction we are spreading is Air
                    {
                        Direction clockWiseDir = direction.getClockWise();
                        Direction counterClockWiseDir = direction.getCounterClockWise();
                        boolean canAttachClockWise = state.getValue(getPropertyForFace(clockWiseDir));
                        boolean canAttachCounterClockWise = state.getValue(getPropertyForFace(counterClockWiseDir));
                        BlockPos posRelativeClockWise = relativePosInDir.relative(clockWiseDir);
                        BlockPos posRelativeCounterClockWise = relativePosInDir.relative(counterClockWiseDir);
                        if (canAttachClockWise && isAcceptableNeighbour(level, posRelativeClockWise, clockWiseDir))
                        {
                            level.setBlock(relativePosInDir, this.defaultBlockState().setValue(getPropertyForFace(clockWiseDir), true), 2);
                        }
                        else if (canAttachCounterClockWise && isAcceptableNeighbour(level, posRelativeCounterClockWise, counterClockWiseDir))
                        {
                            level.setBlock(relativePosInDir, this.defaultBlockState().setValue(getPropertyForFace(counterClockWiseDir), true), 2);
                        }
                        else
                        {
                            Direction oppositeDir = direction.getOpposite();
                            if (canAttachClockWise && level.isEmptyBlock(posRelativeClockWise) && isAcceptableNeighbour(level, pos.relative(clockWiseDir), oppositeDir))
                            {
                                level.setBlock(posRelativeClockWise, this.defaultBlockState().setValue(getPropertyForFace(oppositeDir), true), 2);
                            }
                            else if (canAttachCounterClockWise && level.isEmptyBlock(posRelativeCounterClockWise) && isAcceptableNeighbour(level, pos.relative(counterClockWiseDir), oppositeDir))
                            {
                                level.setBlock(posRelativeCounterClockWise, this.defaultBlockState().setValue(getPropertyForFace(oppositeDir), true), 2);
                            }
                            else if ((double)rand.nextFloat() < 0.05D && isAcceptableNeighbour(level, relativePosInDir.above(), Direction.UP))
                            {
                                level.setBlock(relativePosInDir, this.defaultBlockState().setValue(UP, true), 2);
                            }
                        }
                    }
                    else if (isAcceptableNeighbour(level, relativePosInDir, direction))
                    {
                        level.setBlock(pos, state.setValue(getPropertyForFace(direction), true), 2);
                    }
                }
            }
            else
            {
                if (direction == Direction.UP && pos.getY() < level.getMaxBuildHeight() - 1)
                {
                    if (this.canSupportAtFace(level, pos, direction))
                    {
                        level.setBlock(pos, state.setValue(UP, true), 2);
                        return;
                    }

                    if (level.isEmptyBlock(blockpos))
                    {
                        if (!this.canSpread(level, pos))
                            return;

                        BlockState stateForPlacement = state;
                        for(Direction horizontalDir : Direction.Plane.HORIZONTAL)
                        {
                            if (rand.nextBoolean() || !isAcceptableNeighbour(level, blockpos.relative(horizontalDir), horizontalDir))
                                stateForPlacement = stateForPlacement.setValue(getPropertyForFace(horizontalDir), false);
                        }

                        if (this.hasHorizontalConnection(stateForPlacement))
                            level.setBlock(blockpos, stateForPlacement, 2);
                        return;
                    }
                }

                if (pos.getY() > level.getMinBuildHeight())
                {
                    BlockPos posBelow = pos.below();
                    BlockState stateBelow = level.getBlockState(posBelow);
                    FluidState fluidStateBelow = level.getFluidState(posBelow);
                    if (stateBelow.isAir() || (stateBelow.is(Blocks.WATER) && fluidStateBelow.getAmount() == FluidState.AMOUNT_FULL) || stateBelow.is(this))
                    {
                        BlockState vineBelow = (stateBelow.isAir() || stateBelow.is(Blocks.WATER)) ? this.defaultBlockState().setValue(WATERLOGGED, stateBelow.is(Blocks.WATER)) : stateBelow;
                        BlockState stateForPlacement = copyRandomFaces(state, vineBelow, rand);
                        if (vineBelow != stateForPlacement && hasHorizontalConnection(stateForPlacement))
                        {
                            level.setBlock(posBelow, stateForPlacement, 2);
                        }
                    }
                }
            }
        }
    }

    private boolean canSpread(BlockGetter getter, BlockPos pos)
    {
        Iterable<BlockPos> iterable = BlockPos.betweenClosed(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        int iterationsRemaining = 5;

        for(BlockPos blockpos : iterable)
        {
            if (getter.getBlockState(blockpos).is(this))
            {
                --iterationsRemaining;
                if (iterationsRemaining <= 0)
                    return false;
            }
        }
        return true;
    }

    public boolean canSupportAtFace(BlockGetter getter, BlockPos pos, Direction dir)
    {
        if (dir == Direction.DOWN)
        {
            return false;
        }
        else
        {
            BlockPos blockpos = pos.relative(dir);
            if (isAcceptableNeighbour(getter, blockpos, dir))
            {
                return true;
            }
            else if (dir.getAxis() == Direction.Axis.Y)
            {
                return false;
            }
            else
            {
                BooleanProperty directionProperty = PROPERTY_BY_DIRECTION.get(dir);
                BlockState blockstate = getter.getBlockState(pos.above());
                return blockstate.is(this) && blockstate.getValue(directionProperty);
            }
        }
    }
}
