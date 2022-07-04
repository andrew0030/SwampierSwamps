package andrews.swampier_swamps.level.features;

import andrews.swampier_swamps.objects.blocks.BigLilyPadBlock;
import andrews.swampier_swamps.registry.SSBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.material.FluidState;

public class BigLilyPadFeature extends Feature<ProbabilityFeatureConfiguration>
{
    public BigLilyPadFeature(Codec<ProbabilityFeatureConfiguration> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ProbabilityFeatureConfiguration> context)
    {
        boolean placedBigLilyPad = false;
        RandomSource randomSource = context.random();
        WorldGenLevel worldGenLevel = context.level();
        BlockPos pos = context.origin();
        ProbabilityFeatureConfiguration probabilityfeatureconfiguration = context.config();
        int xOffset = randomSource.nextInt(10) - randomSource.nextInt(10);
        int zOffset = randomSource.nextInt(10) - randomSource.nextInt(10);
        int heightInWorld = worldGenLevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, pos.getX() + xOffset, pos.getZ() + zOffset);
        BlockPos posAtTarget = new BlockPos(pos.getX() + xOffset, heightInWorld, pos.getZ() + zOffset);

        if (worldGenLevel.getBlockState(posAtTarget.below()).is(Blocks.WATER) && worldGenLevel.getBlockState(posAtTarget).is(Blocks.AIR));
        {
            Direction direction;
            switch (randomSource.nextInt(4))
            {
                default -> direction = Direction.NORTH;
                case 1 -> direction = Direction.SOUTH;
                case 2 -> direction = Direction.WEST;
                case 3 -> direction = Direction.EAST;
            }
            boolean probabilityCheck = randomSource.nextDouble() < (double) probabilityfeatureconfiguration.probability;
            if (isValidPosition(worldGenLevel, posAtTarget, direction) && probabilityCheck)
            {
                BlockState state = SSBlocks.BIG_LILY_PAD.get().defaultBlockState();
                worldGenLevel.setBlock(posAtTarget, state.setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 0), 2);
                worldGenLevel.setBlock(posAtTarget.relative(direction), state.setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 1), 2);
                worldGenLevel.setBlock(posAtTarget.relative(direction.getClockWise()), state.setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 2), 2);
                worldGenLevel.setBlock(posAtTarget.relative(direction).relative(direction.getClockWise()), state.setValue(BigLilyPadBlock.FACING, direction).setValue(BigLilyPadBlock.LILY_PAD_PART, 3), 2);
                placedBigLilyPad = true;
            }
        }
        return placedBigLilyPad;
    }

    private boolean isValidPosition(WorldGenLevel level, BlockPos pos, Direction direction)
    {
        FluidState fluidState = level.getFluidState(pos.below());
        BlockState blockStateAbove = level.getBlockState(pos);
        if(fluidState.is(FluidTags.WATER) && fluidState.getAmount() == FluidState.AMOUNT_FULL && blockStateAbove.is(Blocks.AIR))
        {
            FluidState fluidState1 = level.getFluidState(pos.below().relative(direction));
            BlockState blockStateAbove1 = level.getBlockState(pos.relative(direction));
            if(fluidState1.is(FluidTags.WATER) && fluidState1.getAmount() == FluidState.AMOUNT_FULL && blockStateAbove1.is(Blocks.AIR))
            {
                FluidState fluidState2 = level.getFluidState(pos.below().relative(direction.getClockWise()));
                BlockState blockStateAbove2 = level.getBlockState(pos.relative(direction.getClockWise()));
                if(fluidState2.is(FluidTags.WATER) && fluidState2.getAmount() == FluidState.AMOUNT_FULL && blockStateAbove2.is(Blocks.AIR))
                {
                    FluidState fluidState3 = level.getFluidState(pos.below().relative(direction).relative(direction.getClockWise()));
                    BlockState blockStateAbove3 = level.getBlockState(pos.relative(direction).relative(direction.getClockWise()));
                    return fluidState3.is(FluidTags.WATER) && fluidState3.getAmount() == FluidState.AMOUNT_FULL && blockStateAbove3.is(Blocks.AIR);
                }
            }
        }
        return false;
    }
}