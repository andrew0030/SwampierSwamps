package andrews.swampier_swamps.level.features;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class DecayingLogFeature extends Feature<NoneFeatureConfiguration>
{
    public DecayingLogFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        boolean placedLog = false;
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource rand = context.random();

        Direction direction =  switch (rand.nextInt(4))
        {
            default -> Direction.NORTH;
            case 1 -> Direction.SOUTH;
            case 2 -> Direction.WEST;
            case 3 -> Direction.EAST;
        };

        if((level.getBlockState(pos).is(Blocks.AIR) || level.getBlockState(pos).is(BlockTags.REPLACEABLE)) && level.getBlockState(pos.below()).isSolid())
        {
            int logSize = rand.nextInt(2) + 4;
            // We make sure there is nothing in the way of the Log
            for (int i = 0; i < logSize; i++)
            {
                BlockPos offsetPos = pos.relative(direction, i);
                if (!(level.getBlockState(offsetPos).is(BlockTags.REPLACEABLE) || level.getBlockState(offsetPos).is(Blocks.AIR)))
                    return false;
            }
            // We make sure this is at least 3 valid blocks under the Log
            int minValidGround = 2;
            for (int i = 0; i < logSize; i++)
            {
                BlockPos offsetPos = pos.relative(direction, i);
                if (!level.getBlockState(offsetPos.below()).isSolid())
                {
                    --minValidGround;
                    if(minValidGround <= 0)
                        return false;
                }
            }

            for (int i = 0; i < logSize; i++)
            {
                BlockPos offsetPos = pos.relative(direction, i);
                level.setBlock(offsetPos, Blocks.OAK_LOG.defaultBlockState().setValue(RotatedPillarBlock.AXIS, direction.getAxis()), 2);
                if (level.getBlockState(offsetPos.above()).isAir() && rand.nextInt(6) == 0)
                    level.setBlock(offsetPos.above(), getLogDecoration(rand), 2);
            }
            placedLog = true;
        }
        return placedLog;
    }

    private BlockState getLogDecoration(RandomSource rand)
    {
        int value = rand.nextInt(3);
        return switch (value)
        {
            default -> Blocks.BROWN_MUSHROOM.defaultBlockState();
            case 1 -> Blocks.RED_MUSHROOM.defaultBlockState();
            case 2 -> Blocks.MOSS_CARPET.defaultBlockState();
        };
    }
}