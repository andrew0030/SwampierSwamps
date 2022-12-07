package andrews.swampier_swamps.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.ToIntFunction;

public class GasLampBlock extends Block
{
    public static final BooleanProperty HANGING = BlockStateProperties.HANGING;
    protected static final VoxelShape AABB = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D), Block.box(4.0D, 7.0D, 4.0D, 12.0D, 8.0D, 12.0D));
    protected static final VoxelShape HANGING_AABB = Shapes.or(Block.box(5.0D, 1.0D, 5.0D, 11.0D, 10.0D, 11.0D), Block.box(4.0D, 8.0D, 4.0D, 12.0D, 9.0D, 12.0D));
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public GasLampBlock(Properties properties)
    {
        super(properties.lightLevel(litBlockEmission()));
        this.registerDefaultState(this.stateDefinition.any().setValue(HANGING, false).setValue(POWER, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return state.getValue(HANGING) ? HANGING_AABB : AABB;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state)
    {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState state1, LevelAccessor level, BlockPos pos, BlockPos pos1)
    {
        return getConnectedDirection(state).getOpposite() == direction && !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, state1, level, pos, pos1);
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition)
    {
        stateDefinition.add(POWER, HANGING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        for(Direction direction : context.getNearestLookingDirections())
        {
            if (direction.getAxis() == Direction.Axis.Y)
            {
                BlockState blockstate = this.defaultBlockState().setValue(HANGING, direction == Direction.UP);
                if (blockstate.canSurvive(context.getLevel(), context.getClickedPos()))
                {
                    return blockstate.setValue(POWER, context.getLevel().getBestNeighborSignal(context.getClickedPos()));
                }
            }
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        Direction direction = getConnectedDirection(state).getOpposite();
        return Block.canSupportCenter(level, pos.relative(direction), direction.getOpposite());
    }

    private static Direction getConnectedDirection(BlockState state)
    {
        return state.getValue(HANGING) ? Direction.DOWN : Direction.UP;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!level.isClientSide)
        {
            level.setBlock(pos, state.setValue(POWER, level.getBestNeighborSignal(pos)), 2);
        }
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand)
    {
        int signalStrength = state.getValue(POWER);
        for (int i = 0; i < 2; i++)
        {
            double xPos = pos.getX() + 0.4 + (rand.nextDouble() * 0.2);
            double zPos = pos.getZ() + 0.4 + (rand.nextDouble() * 0.2);
            double yPos = pos.getY() + 0.3 + (rand.nextDouble() * 0.15);

            if (signalStrength != 15)
                if (rand.nextInt(1 + signalStrength) == 0)
                    level.addParticle(ParticleTypes.SMALL_FLAME, xPos, yPos, zPos, 0, 0, 0);
        }
    }

    @Override
    public boolean isPathfindable(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType)
    {
        return false;
    }

    private static ToIntFunction<BlockState> litBlockEmission()
    {
        return (state) -> 15 - state.getValue(POWER);
    }
}