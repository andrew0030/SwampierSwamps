package andrews.swampier_swamps.objects.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.function.Supplier;
import java.util.stream.IntStream;

public class MudStairsBlock extends StairBlock
{
    private static final VoxelShape BOTTOM_AABB = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
    private static final VoxelShape TOP_AABB = Block.box(0.0D, 6.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    private static final VoxelShape OCTET_NNN = Block.box(0.0D, 0.0D, 0.0D, 8.0D, 6.0D, 8.0D);
    private static final VoxelShape OCTET_NNP = Block.box(0.0D, 0.0D, 8.0D, 8.0D, 6.0D, 16.0D);
    private static final VoxelShape OCTET_NPN = Block.box(0.0D, 6.0D, 0.0D, 8.0D, 14.0D, 8.0D);
    private static final VoxelShape OCTET_NPP = Block.box(0.0D, 6.0D, 8.0D, 8.0D, 14.0D, 16.0D);
    private static final VoxelShape OCTET_PNN = Block.box(8.0D, 0.0D, 0.0D, 16.0D, 6.0D, 8.0D);
    private static final VoxelShape OCTET_PNP = Block.box(8.0D, 0.0D, 8.0D, 16.0D, 6.0D, 16.0D);
    private static final VoxelShape OCTET_PPN = Block.box(8.0D, 6.0D, 0.0D, 16.0D, 14.0D, 8.0D);
    private static final VoxelShape OCTET_PPP = Block.box(8.0D, 6.0D, 8.0D, 16.0D, 14.0D, 16.0D);

    private static final VoxelShape[] TOP_SHAPES = makeShapes(TOP_AABB, OCTET_NNN, OCTET_PNN, OCTET_NNP, OCTET_PNP);
    private static final VoxelShape[] BOTTOM_SHAPES = makeShapes(BOTTOM_AABB, OCTET_NPN, OCTET_PPN, OCTET_NPP, OCTET_PPP);
    private static final int[] SHAPE_BY_STATE = new int[]{12, 5, 3, 10, 14, 13, 7, 11, 13, 7, 11, 14, 8, 4, 1, 2, 4, 1, 2, 8};

    private static VoxelShape[] makeShapes(VoxelShape shape1, VoxelShape shape2, VoxelShape shape3, VoxelShape shape4, VoxelShape shape5)
    {
        return IntStream.range(0, 16).mapToObj((type) ->
        {
            return makeStairShape(type, shape1, shape2, shape3, shape4, shape5);
        }).toArray(VoxelShape[]::new);
    }

    private static VoxelShape makeStairShape(int type, VoxelShape shape1, VoxelShape shape2, VoxelShape shape3, VoxelShape shape4, VoxelShape shape5) {
        VoxelShape voxelshape = shape1;
        if ((type & 1) != 0)
            voxelshape = Shapes.or(shape1, shape2);
        if ((type & 2) != 0)
            voxelshape = Shapes.or(voxelshape, shape3);
        if ((type & 4) != 0)
            voxelshape = Shapes.or(voxelshape, shape4);
        if ((type & 8) != 0)
            voxelshape = Shapes.or(voxelshape, shape5);
        return voxelshape;
    }

    public MudStairsBlock(BlockState state, Properties properties)
    {
        super(state, properties);
    }

    public MudStairsBlock(Supplier<BlockState> state, Properties properties)
    {
        super(state, properties);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter blockGetter, BlockPos pos, CollisionContext context)
    {
        return (state.getValue(HALF) == Half.TOP ? TOP_SHAPES : BOTTOM_SHAPES)[SHAPE_BY_STATE[this.getShapeIndex(state)]];
    }

    private int getShapeIndex(BlockState state)
    {
        return state.getValue(SHAPE).ordinal() * 4 + state.getValue(FACING).get2DDataValue();
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter blockGetter, BlockPos pos, PathComputationType pathComputationType)
    {
        return false;
    }
}
