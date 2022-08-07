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
    
//      if (worldGenLevel.getBlockState(posAtTarget.below()).is(Blocks.WATER) && worldGenLevel.getBlockState(posAtTarget).is(Blocks.AIR));
//      {
        Direction direction = switch (randomSource.nextInt(4))
        {
            default -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.WEST;
            case 3 -> Direction.EAST;
        };
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
//      }
        return placedBigLilyPad;
    }

    private boolean isValidPosition(WorldGenLevel level, BlockPos pos, Direction direction)
    {
        for (int i = 0; i < 4; i++) {
            FluidState fluidState = level.getFluidState(pos.below());
            BlockState blockStateAbove = level.getBlockState(pos);
            if(!(fluidState.is(FluidTags.WATER) && fluidState.getAmount() == FluidState.AMOUNT_FULL && blockStateAbove.is(Blocks.AIR))) {
                return false;
            }
            pos = pos.relative(direction);
            direction = direction.getClockWise();
        }
        return true;
    }
}