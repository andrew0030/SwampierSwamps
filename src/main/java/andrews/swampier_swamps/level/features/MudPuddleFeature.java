package andrews.swampier_swamps.level.features;

import andrews.swampier_swamps.registry.SSTags;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class MudPuddleFeature extends Feature<NoneFeatureConfiguration>
{
    public MudPuddleFeature(Codec<NoneFeatureConfiguration> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context)
    {
        BlockPos pos = context.origin();
        WorldGenLevel level = context.level();
        RandomSource rand = context.random();
        // We make sure we at least 4 Blocks above the min build height as we cant generate into Bedrock!
        if (pos.getY() <= level.getMinBuildHeight() + 4)
        {
            return false;
        }
        else
        {
            pos = pos.below(4); // We offset the BlockPos to 4 Blocks bellow
            boolean[] withinRange = new boolean[2048];
            int i = rand.nextInt(4) + 4;

            for(int j = 0; j < i; ++j)
            {
                double xMin = rand.nextDouble() * 6.0D + 3.0D;
                double yMin = rand.nextDouble() * 4.0D + 2.0D;
                double zMin = rand.nextDouble() * 6.0D + 3.0D;
                double xMax = rand.nextDouble() * (16.0D - xMin - 2.0D) + 1.0D + xMin / 2.0D;
                double yMax = rand.nextDouble() * (8.0D - yMin - 4.0D) + 2.0D + yMin / 2.0D;
                double zmax = rand.nextDouble() * (16.0D - zMin - 2.0D) + 1.0D + zMin / 2.0D;

                for(int xOffset = 1; xOffset < 15; ++xOffset)
                {
                    double deltaX = ((double)xOffset - xMax) / (xMin / 2.0D);
                    for(int zOffset = 1; zOffset < 15; ++zOffset)
                    {
                        double deltaZ = ((double)zOffset - zmax) / (zMin / 2.0D);
                        for(int yOffset = 1; yOffset < 7; ++yOffset)
                        {
                            double deltaY = ((double)yOffset - yMax) / (yMin / 2.0D);
                            double range = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
                            if (range < 1.0D)
                                withinRange[(xOffset * 16 + zOffset) * 8 + yOffset] = true;
                        }
                    }
                }
            }

            BlockState waterState = Blocks.WATER.defaultBlockState();
            for(int waterXOffset = 0; waterXOffset < 16; ++waterXOffset)
            {
                for(int waterZOffset = 0; waterZOffset < 16; ++waterZOffset)
                {
                    for(int waterYOffset = 0; waterYOffset < 8; ++waterYOffset)
                    {
                        boolean flag = !withinRange[(waterXOffset * 16 + waterZOffset) * 8 + waterYOffset] && (waterXOffset < 15 && withinRange[((waterXOffset + 1) * 16 + waterZOffset) * 8 + waterYOffset] || waterXOffset > 0 && withinRange[((waterXOffset - 1) * 16 + waterZOffset) * 8 + waterYOffset] || waterZOffset < 15 && withinRange[(waterXOffset * 16 + waterZOffset + 1) * 8 + waterYOffset] || waterZOffset > 0 && withinRange[(waterXOffset * 16 + (waterZOffset - 1)) * 8 + waterYOffset] || waterYOffset < 7 && withinRange[(waterXOffset * 16 + waterZOffset) * 8 + waterYOffset + 1] || waterYOffset > 0 && withinRange[(waterXOffset * 16 + waterZOffset) * 8 + (waterYOffset - 1)]);
                        if (flag) {
                            BlockState state = level.getBlockState(pos.offset(waterXOffset, waterYOffset, waterZOffset));
                            if (waterYOffset >= 4 && state.liquid())
                                return false;

                            if (waterYOffset < 4 && !state.isSolid() && level.getBlockState(pos.offset(waterXOffset, waterYOffset, waterZOffset)) != waterState)
                                return false;
                        }
                    }
                }
            }

            for(int waterXOffset = 0; waterXOffset < 16; ++waterXOffset)
            {
                for(int waterZOffset = 0; waterZOffset < 16; ++waterZOffset)
                {
                    for(int waterYOffset = 0; waterYOffset < 8; ++waterYOffset)
                    {
                        if (withinRange[(waterXOffset * 16 + waterZOffset) * 8 + waterYOffset])
                        {
                            BlockPos posAtTarget = pos.offset(waterXOffset, waterYOffset, waterZOffset);
                            if (this.canReplaceBlock(level.getBlockState(posAtTarget)))
                            {
                                boolean placeAir = waterYOffset >= 4;
                                level.setBlock(posAtTarget, placeAir ? Blocks.AIR.defaultBlockState() : waterState, 2);
                                // If there is a Log (Tree) above, we extend the Tree Logs down
                                if(level.getBlockState(posAtTarget.above()).is(Blocks.OAK_LOG) && level.getBlockState(posAtTarget.above()).getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y)
                                {
                                    BlockPos extraLogsPos = posAtTarget;
                                    while(!level.getBlockState(extraLogsPos).isSolid())
                                    {
                                        level.setBlock(extraLogsPos, Blocks.OAK_LOG.defaultBlockState(), 2);
                                        extraLogsPos = extraLogsPos.below();
                                    }
                                }
                                else
                                {
                                    if (placeAir)
                                    {
                                        level.scheduleTick(posAtTarget, Blocks.AIR, 0);
                                        this.markAboveForPostProcessing(level, posAtTarget);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            BlockState mudState = Blocks.MUD.defaultBlockState();
            BlockState mudRootState = Blocks.MUDDY_MANGROVE_ROOTS.defaultBlockState();
            for(int mudXOffset = 0; mudXOffset < 16; ++mudXOffset)
            {
                for(int mudZOffset = 0; mudZOffset < 16; ++mudZOffset)
                {
                    for(int mudYOffset = 0; mudYOffset < 8; ++mudYOffset)
                    {
                        boolean flag2 = !withinRange[(mudXOffset * 16 + mudZOffset) * 8 + mudYOffset] && (mudXOffset < 15 &&
                                         withinRange[((mudXOffset + 1) * 16 + mudZOffset) * 8 + mudYOffset] || mudXOffset > 0 &&
                                         withinRange[((mudXOffset - 1) * 16 + mudZOffset) * 8 + mudYOffset] || mudZOffset < 15 &&
                                         withinRange[(mudXOffset * 16 + mudZOffset + 1) * 8 + mudYOffset] || mudZOffset > 0 &&
                                         withinRange[(mudXOffset * 16 + (mudZOffset - 1)) * 8 + mudYOffset] || mudYOffset < 7 &&
                                         withinRange[(mudXOffset * 16 + mudZOffset) * 8 + mudYOffset + 1] || mudYOffset > 0 &&
                                         withinRange[(mudXOffset * 16 + mudZOffset) * 8 + (mudYOffset - 1)]);
                        if (flag2 && (mudYOffset < 4 || rand.nextInt(2) != 0))
                        {
                            BlockState stateAtTarget = level.getBlockState(pos.offset(mudXOffset, mudYOffset, mudZOffset));
                            if (stateAtTarget.isSolid() && !stateAtTarget.is(BlockTags.LAVA_POOL_STONE_CANNOT_REPLACE))
                            {
                                // Used to create a better looking fade-in
                                for(int j = 0; j < 5; j++)
                                {
                                    int xRimOffset = rand.nextInt(4) - rand.nextInt(4);
                                    int zRimOffset = rand.nextInt(4) - rand.nextInt(4);
                                    BlockPos fadeInPos = pos.offset(mudXOffset + xRimOffset, mudYOffset, mudZOffset + zRimOffset);
                                    BlockState stateAtRim = level.getBlockState(fadeInPos);
                                    if(stateAtRim.is(SSTags.Blocks.MUD_PUDDLE_CAN_REPLACE))
                                    {
                                        level.setBlock(fadeInPos, rand.nextInt(15) == 0 ? mudRootState : mudState, 2);
                                        this.markAboveForPostProcessing(level, fadeInPos);
                                    }
                                }
                                BlockPos posAtTarget = pos.offset(mudXOffset, mudYOffset, mudZOffset);
                                if(level.getBlockState(posAtTarget).is(SSTags.Blocks.MUD_PUDDLE_CAN_REPLACE))
                                {
                                    level.setBlock(posAtTarget, rand.nextInt(15) == 0 ? mudRootState : mudState, 2);
                                    this.markAboveForPostProcessing(level, posAtTarget);
                                }
                            }
                        }
                    }
                }
            }

            // Handles freezing the top layer of water
            if (waterState.getFluidState().is(FluidTags.WATER))
            {
                for(int freezingXOffset = 0; freezingXOffset < 16; ++freezingXOffset)
                {
                    for(int freezingZOffset = 0; freezingZOffset < 16; ++freezingZOffset)
                    {
                        BlockPos waterSurfacePos = pos.offset(freezingXOffset, 4, freezingZOffset);
                        if (level.getBiome(waterSurfacePos).value().shouldFreeze(level, waterSurfacePos, false) && this.canReplaceBlock(level.getBlockState(waterSurfacePos)))
                            level.setBlock(waterSurfacePos, Blocks.ICE.defaultBlockState(), 2);
                    }
                }
            }

            return true;
        }
    }

    private boolean canReplaceBlock(BlockState state)
    {
        return state.is(SSTags.Blocks.MUD_PUDDLE_CAN_REPLACE);
    }
}