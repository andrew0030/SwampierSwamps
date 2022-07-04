package andrews.swampier_swamps.level.features;

import andrews.swampier_swamps.registry.SSBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;

public class CattailFeature extends Feature<ProbabilityFeatureConfiguration>
{
    public CattailFeature(Codec<ProbabilityFeatureConfiguration> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context)
    {
        boolean placedCattail = false;
        RandomSource randomSource = context.random();
        WorldGenLevel worldGenLevel = context.level();
        BlockPos pos = context.origin();
        ProbabilityFeatureConfiguration probabilityfeatureconfiguration = context.config();
        int xOffset = randomSource.nextInt(6) - randomSource.nextInt(6);
        int zOffset = randomSource.nextInt(6) - randomSource.nextInt(6);
        int heightInWorld = worldGenLevel.getHeight(Heightmap.Types.OCEAN_FLOOR, pos.getX() + xOffset, pos.getZ() + zOffset);
        BlockPos posAtTarget = new BlockPos(pos.getX() + xOffset, heightInWorld, pos.getZ() + zOffset);

        if (worldGenLevel.getBlockState(posAtTarget).is(Blocks.WATER))
        {
            boolean probabilityCheck = randomSource.nextDouble() < (double)probabilityfeatureconfiguration.probability;
            if (isValidCattailPosition(worldGenLevel, posAtTarget) && probabilityCheck)
            {
                BlockState state = SSBlocks.CATTAIL.get().defaultBlockState();
                boolean isTallCattail = worldGenLevel.getBlockState(posAtTarget.above(2)).is(Blocks.AIR) && worldGenLevel.getBlockState(posAtTarget.above()).is(Blocks.WATER);
                if (isTallCattail)
                {
                    worldGenLevel.setBlock(posAtTarget, state, 2);
                    worldGenLevel.setBlock(posAtTarget.above(), state, 2);
                    worldGenLevel.setBlock(posAtTarget.above(2), state.setValue(BlockStateProperties.WATERLOGGED, false), 2);
                }
                else
                {
                    worldGenLevel.setBlock(posAtTarget, state, 2);
                    worldGenLevel.setBlock(posAtTarget.above(), state.setValue(BlockStateProperties.WATERLOGGED, false), 2);
                }
                placedCattail = true;
            }
        }
        return placedCattail;
    }

    private boolean isValidCattailPosition(WorldGenLevel level, BlockPos pos)
    {
        FluidState fluidstate = level.getFluidState(pos);
        BlockState blockStateBelow1 = level.getBlockState(pos.below());
        BlockState blockStateAbove1 = level.getBlockState(pos.above());
        BlockState blockStateAbove2 = level.getBlockState(pos.above(2));

        if(fluidstate.is(FluidTags.WATER) && fluidstate.getAmount() == FluidState.AMOUNT_FULL)
        {
            return (!blockStateBelow1.is(SSBlocks.CATTAIL.get()) && blockStateAbove1.is(Blocks.WATER) && blockStateAbove2.is(Blocks.AIR)) ||
                    (!blockStateBelow1.is(SSBlocks.CATTAIL.get()) && blockStateAbove1.is(Blocks.AIR));
        }
        return false;
    }
}