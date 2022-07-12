package andrews.swampier_swamps.level.features.trunk_placers;

import andrews.swampier_swamps.registry.SSTrunkPlacers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class BaldCypressTrunkPlacer extends TrunkPlacer
{
    public static final Codec<BaldCypressTrunkPlacer> CODEC = RecordCodecBuilder.create((instance) -> trunkPlacerParts(instance).apply(instance, BaldCypressTrunkPlacer::new));

    public BaldCypressTrunkPlacer(int baseHeight, int firstRandomHeight, int secondRandomHeight)
    {
        super(baseHeight, firstRandomHeight, secondRandomHeight);
    }

    @Override
    protected TrunkPlacerType<?> type()
    {
        return SSTrunkPlacers.BALD_CYPRESS_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource rand, int height, BlockPos pos, TreeConfiguration config) {
        // Places the dirt Blocks under the Tree
        BlockPos blockpos = pos.below();
        setDirtAt(level, consumer, rand, blockpos, config);
        setDirtAt(level, consumer, rand, blockpos.east(), config);
        setDirtAt(level, consumer, rand, blockpos.south(), config);
        setDirtAt(level, consumer, rand, blockpos.north(), config);
        setDirtAt(level, consumer, rand, blockpos.west(), config);

        List<Integer> lowerBranchHeights = Lists.newArrayList();
        // Places the center Logs
        for(int i = 0; i < height; ++i)
        {
            this.placeLog(level, consumer, rand, pos.above(i), config);
            if((lowerBranchHeights.size() < 3) && (i > 3) && (i < height - 2))
                if(rand.nextInt(2) == 0)
                    lowerBranchHeights.add(pos.above(i).getY());
        }
        if (lowerBranchHeights.isEmpty()) // If the branches list is empty we generate one
            lowerBranchHeights.add(pos.getY() + 4 /* We add 3 so it's above the lower part */ + rand.nextInt(height - 5));

        // The Logs around the center Log
        this.placeLogs(level, consumer, rand, 1, rand.nextInt(2) + 1, pos.north(), config);
        this.placeLogs(level, consumer, rand, 1, rand.nextInt(2) + 1, pos.south(), config);
        this.placeLogs(level, consumer, rand, 1, rand.nextInt(2) + 1, pos.east(), config);
        this.placeLogs(level, consumer, rand, 1, rand.nextInt(2) + 1, pos.west(), config);
        // The Logs in the corners
        this.randomDiagonalLog(level, consumer, rand, pos.below().offset(1, 0, 1), config);
        this.randomDiagonalLog(level, consumer, rand, pos.below().offset(-1, 0, -1), config);
        this.randomDiagonalLog(level, consumer, rand, pos.below().offset(-1, 0, 1), config);
        this.randomDiagonalLog(level, consumer, rand, pos.below().offset(1, 0, -1), config);

        // This list is used to store the places' foliage will be attached to.
        List<FoliagePlacer.FoliageAttachment> list = Lists.newArrayList();
        Direction direction = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
        // The top of the tree
        createBranch(level, consumer, rand, height, pos, config, list, direction, rand.nextInt(3) + 1);
        createBranch(level, consumer, rand, height, pos, config, list, direction.getOpposite(), rand.nextInt(3) + 1);
        // The lower branches
        List<Direction> directions = Lists.newArrayList();
        for (int branchHeight : lowerBranchHeights)
        {
            Direction branchDirection;
            do {
                branchDirection = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
            } while (directions.contains(branchDirection));

            createBranch(level, consumer, rand, height, new BlockPos(pos.getX(), branchHeight - height, pos.getZ()), config, list, branchDirection, rand.nextInt(2) + 2);
            directions.add(branchDirection);
        }

        return list;
    }

    private void placeLogs(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource rand, int height, int heightMod, BlockPos pos, TreeConfiguration config)
    {
        for(int i = 0; i < height + heightMod; ++i)
        {
//            this.placeLog(level, consumer, rand, pos.above(i), config);
            if(TreeFeature.validTreePos(level, pos.above(i)) || level.isStateAtPosition(pos.above(i), state -> state.is(Blocks.OAK_SAPLING)))
                consumer.accept(pos.above(i), config.trunkProvider.getState(rand, pos.above(i)));
        }
        if (rand.nextInt(3) == 0) // Places Mos Carpets over the bottom Logs
            if(level.isStateAtPosition(pos.above(height + heightMod), BlockBehaviour.BlockStateBase::isAir))
                consumer.accept(pos.above(height + heightMod), Blocks.MOSS_CARPET.defaultBlockState());
    }

    private void randomDiagonalLog(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource rand, BlockPos pos, TreeConfiguration config)
    {
        if(rand.nextInt(2) == 1)
        {
            setDirtAt(level, consumer, rand, pos, config);
            //this.placeLog(level, consumer, rand, pos.above(), config); We manually do this in order to replace oak saplings if need be
            if(TreeFeature.validTreePos(level, pos.above()) || level.isStateAtPosition(pos.above(), state -> state.is(Blocks.OAK_SAPLING)))
                consumer.accept(pos.above(), config.trunkProvider.getState(rand, pos.above()));

        }
    }

    private void createBranch(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> consumer, RandomSource rand, int height, BlockPos pos, TreeConfiguration config, List<FoliagePlacer.FoliageAttachment> list, Direction direction, int branchSize)
    {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        OptionalInt optionalInt = OptionalInt.empty();
        int xPos = pos.getX();
        int zPos = pos.getZ();
        int logYOffset = 0;

        for(int heightMod = 0; heightMod < branchSize; ++heightMod)
        {
            int yPos = pos.getY() + height + logYOffset;
            if(rand.nextInt(2) == 0)
                logYOffset++;
            xPos += direction.getStepX();
            zPos += direction.getStepZ();

            if (this.placeLog(level, consumer, rand, mutableBlockPos.set(xPos, yPos, zPos), config))
            {
                // The location above the last placed log in the loop gets added to the foliage placer list
                optionalInt = OptionalInt.of(yPos + 1);
            }
        }

        if (optionalInt.isPresent())
        {
            list.add(new FoliagePlacer.FoliageAttachment(new BlockPos(xPos, optionalInt.getAsInt(), zPos), 1, false));
        }
    }
}