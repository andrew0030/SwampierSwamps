package andrews.swampier_swamps.mixins.frog;

import andrews.swampier_swamps.entities.renderer.layers.FrogEyesLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.FrogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FrogRenderer.class)
public class FrogRendererMixin
{
    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void inject(EntityRendererProvider.Context context, CallbackInfo ci)
    {
        ((FrogRenderer)(Object)this).addLayer(new FrogEyesLayer<>(((FrogRenderer)(Object)this)));
    }
}