package andrews.swampier_swamps.level.features;

import andrews.swampier_swamps.registry.SSBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallSeagrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;

public class DecayingKelpFeature extends Feature<NoneFeatureConfiguration>
{
    public DecayingKelpFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        boolean placed = false;
        BlockPos blockpos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource rand = context.random();

        int featureYPos = blockpos.getY();
        int placementHeight = featureYPos + 1;
        int placementDepth = featureYPos - 3;
        int radius = 4;
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for(BlockPos pos : BlockPos.betweenClosed(blockpos.offset(-radius, 0, -radius), blockpos.offset(radius, 0, radius)))
        {
            int xPos = pos.getX() - blockpos.getX();
            int zPos = pos.getZ() - blockpos.getZ();
            int distance = xPos * xPos + zPos * zPos;
            if (distance <= radius * radius)
                placed |= this.placeColumn(level, rand, placementHeight, placementDepth, distance, mutablePos.set(pos));
        }
        return placed;
    }

    protected boolean placeColumn(WorldGenLevel level, RandomSource rand, int placementHeight, int placementDepth, int distance, BlockPos.MutableBlockPos mutableBlockPos)
    {
        double sqrtDist = Math.sqrt(distance);
        boolean placed = false;
        for(int height = placementHeight; height > placementDepth; --height)
        {
            mutableBlockPos.setY(height);
            if (level.isStateAtPosition(mutableBlockPos, state -> state.is(Blocks.DIRT) || state.is(Blocks.CLAY) || state.is(Blocks.GRAVEL) || state.is(Blocks.MUD)))
            {
                BlockState state = Blocks.MUD.defaultBlockState();
                if(sqrtDist < 1)
                {
                    // The center Block, so basically the Decaying Kelp
                    state = SSBlocks.DECAYING_KELP.get().defaultBlockState();
                }
                else if(sqrtDist < 2)
                {
                    // The Rim next to the center
                    state = rand.nextInt(3) > 0 ? Blocks.MUDDY_MANGROVE_ROOTS.defaultBlockState() : state;
                }
                else if(sqrtDist < 3.1)
                {
                    // The next Rim, used to place Mud with Roots, but at a lower rate
                    state = rand.nextInt(4) == 0 ? Blocks.MUDDY_MANGROVE_ROOTS.defaultBlockState() : state;
                }
                else if (sqrtDist < 5)
                {
                    // Used to create a better looking fade-in
                    for(int j = 0; j < 3; j++)
                    {
                        int xRimOffset = rand.nextInt(2) - rand.nextInt(2);
                        int zRimOffset = rand.nextInt(2) - rand.nextInt(2);
                        BlockPos fadeInPos = mutableBlockPos.offset(xRimOffset, 0, zRimOffset);
                        BlockState stateAtRim = level.getBlockState(fadeInPos);
                        if(stateAtRim.is(Blocks.GRAVEL) || stateAtRim.is(Blocks.DIRT) || stateAtRim.is(Blocks.CLAY))
                        {
                            level.setBlock(fadeInPos, state, 2);
                            this.markAboveForPostProcessing(level, fadeInPos);
                        }
                    }
                }

                level.setBlock(mutableBlockPos, state, 2);
                this.markAboveForPostProcessing(level, mutableBlockPos);
                // Place Some Plants
                if(level.getBlockState(mutableBlockPos.above()).is(Blocks.WATER))
                {
                    if(rand.nextInt(2) == 0)
                    {
                        int plantToPlace = rand.nextInt(3);
                        if(plantToPlace == 0)
                        {
                            boolean isTall = rand.nextInt(3) == 0;
                            if(isTall)
                                isTall = level.getBlockState(mutableBlockPos.above(2)).is(Blocks.WATER);
                            state = Blocks.KELP.defaultBlockState();
                            level.setBlock(mutableBlockPos.above(), isTall ? Blocks.KELP_PLANT.defaultBlockState() : state, 2);
                            if(isTall)
                                level.setBlock(mutableBlockPos.above(2), state, 2);
                            this.markAboveForPostProcessing(level, mutableBlockPos.above(isTall ? 2 : 1));
                        }
                        else if(plantToPlace == 1 || plantToPlace == 2)
                        {
                            boolean isTall = rand.nextInt(3) == 0;
                            if(isTall)
                                isTall = level.getBlockState(mutableBlockPos.above(2)).is(Blocks.WATER);
                            if(isTall)
                            {
                                level.setBlock(mutableBlockPos.above(), Blocks.TALL_SEAGRASS.defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.LOWER), 2);
                                level.setBlock(mutableBlockPos.above(2), Blocks.TALL_SEAGRASS.defaultBlockState().setValue(TallSeagrassBlock.HALF, DoubleBlockHalf.UPPER), 2);
                                this.markAboveForPostProcessing(level, mutableBlockPos.above(2));
                            }
                            else
                            {
                                level.setBlock(mutableBlockPos.above(), Blocks.SEAGRASS.defaultBlockState(), 2);
                                this.markAboveForPostProcessing(level, mutableBlockPos.above());
                            }
                        }
                    }
                }
                placed = true;
            }
        }
        return placed;
    }
}