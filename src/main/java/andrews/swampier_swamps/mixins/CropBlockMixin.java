package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CropBlock.class)
public class CropBlockMixin
{
    @Inject(method = "mayPlaceOn", at = @At(value = "HEAD"), cancellable = true)
    public void injectMayPlace(BlockState state, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Boolean> cir)
    {
        if (state.is(SSBlocks.FERTILE_FARMLAND))
            cir.setReturnValue(true);
    }

    @Inject(method = "getGrowthSpeed", at = @At(value = "RETURN"), cancellable = true)
    private static void injectGetGrowthSpeed(Block block, BlockGetter level, BlockPos pos, CallbackInfoReturnable<Float> cir)
    {
        if (level.getBlockState(pos.below()).is(SSBlocks.FERTILE_FARMLAND))
            cir.setReturnValue(cir.getReturnValue() * SwampierSwamps.SS_CONFIG.SSCommonConfig.growthMultiplier);
    }
}