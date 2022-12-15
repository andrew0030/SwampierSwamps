package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.registry.SSTags;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(AbstractTreeGrower.class)
public class AbstractTreeGrowerMixin
{
    @Inject(method = "growTree", at = @At(value = "HEAD"), cancellable = true)
    public void injectGetConfiguredMegaFeature(ServerLevel level, ChunkGenerator generator, BlockPos pos, BlockState state, RandomSource randomSource, CallbackInfoReturnable<Boolean> cir)
    {
        if (state.is(Blocks.OAK_SAPLING))
        {
            int configValue = SwampierSwamps.SS_CONFIG.SSCommonConfig.growBaldCypressFromSaplings;
            if ((configValue == 1 && level.getBiome(pos).is(SSTags.Biomes.CAN_BALD_CYPRESS_GROW_IN)) || configValue == 2)
            {
                for (int xOffset = 0; xOffset >= -1; --xOffset)
                {
                    for (int zOffset = 0; zOffset >= -1; --zOffset)
                    {
                        Block block = state.getBlock();
                        // We check if the saplings are a 2x2 shape
                        if (level.getBlockState(pos.offset(xOffset, 0, zOffset)).is(block) &&
                                level.getBlockState(pos.offset(xOffset + 1, 0, zOffset)).is(block) &&
                                level.getBlockState(pos.offset(xOffset, 0, zOffset + 1)).is(block) &&
                                level.getBlockState(pos.offset(xOffset + 1, 0, zOffset + 1)).is(block))
                        {
                            Optional<ConfiguredFeature<?, ?>> baldCypressCF = level.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getOptional(new ResourceLocation(Reference.MODID, "bald_cypress"));
                            if (baldCypressCF.isPresent())
                            {
                                BlockState blockstate = Blocks.AIR.defaultBlockState();
                                level.setBlock(pos.offset(xOffset, 0, zOffset), blockstate, 2);
                                level.setBlock(pos.offset(xOffset + 1, 0, zOffset), blockstate, 2);
                                level.setBlock(pos.offset(xOffset, 0, zOffset + 1), blockstate, 2);
                                //level.setBlock(pos.offset(xOffset + 1, 0, zOffset + 1), blockstate, 2); We don't need this as we are using a "+" shape

                                if (baldCypressCF.get().place(level, generator, randomSource, pos.offset(xOffset, 0, zOffset)))
                                {
                                    cir.setReturnValue(true);
                                }
                                else
                                {
                                    level.setBlock(pos.offset(xOffset, 0, zOffset), state, 2);
                                    level.setBlock(pos.offset(xOffset + 1, 0, zOffset), state, 2);
                                    level.setBlock(pos.offset(xOffset, 0, zOffset + 1), state, 2);
                                    //level.setBlock(pos.offset(xOffset + 1, 0, zOffset + 1), state, 2); We don't need this as we are using a "+" shape
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}