package andrews.swampier_swamps.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.ToIntFunction;

public class GasLampBlock extends Block
{
    private static final VoxelShape BASE = Block.box(5.0D, 0.0D, 5.0D, 11.0D, 9.0D, 11.0D);
    private static final VoxelShape COVER = Block.box(4.0D, 7.0D, 4.0D, 12.0D, 8.0D, 12.0D);
    private static final VoxelShape AABB = Shapes.or(BASE, COVER);
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public GasLampBlock(Properties properties)
    {
        super(properties.lightLevel(litBlockEmission()));
        this.registerDefaultState(this.stateDefinition.any().setValue(POWER, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        return AABB;
    }

    @Override
    public void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateDefinition)
    {
        stateDefinition.add(POWER);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = this.defaultBlockState();
        boolean hasNeighborSignal = context.getLevel().hasNeighborSignal(context.getClickedPos());
        if(hasNeighborSignal)
        {
            int signalStrength = context.getLevel().getBestNeighborSignal(context.getClickedPos());
            return state.setValue(POWER, signalStrength);
        }
        return state;
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving)
    {
        if (!level.isClientSide)
        {
            boolean hasNeighborSignal = level.hasNeighborSignal(pos);
            if(hasNeighborSignal)
            {
                int signalStrength = level.getBestNeighborSignal(pos);
                level.setBlock(pos, this.defaultBlockState().setValue(POWER, signalStrength), 2);
            }
            else
            {
                level.setBlock(pos, this.defaultBlockState(), 2);
            }
        }
    }

    private static ToIntFunction<BlockState> litBlockEmission()
    {
        return (state) -> 15 - state.getValue(POWER);
    }

}