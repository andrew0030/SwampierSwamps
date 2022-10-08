package andrews.swampier_swamps.entities.renderer.layers;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.registry.SSFrogVariants;
import andrews.swampier_swamps.util.Reference;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.FrogModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;

public class FrogEyesLayer<T extends Frog> extends RenderLayer<T, FrogModel<T>>
{
    private static final RenderType FROG_EYES_SMALL_BASE = RenderType.entityCutout(new ResourceLocation(Reference.MODID, "textures/entity/frog/eyes/frog_eyes_small.png"));
    private static final RenderType FROG_EYES_LARGE_BASE = RenderType.entityCutout(new ResourceLocation(Reference.MODID, "textures/entity/frog/eyes/frog_eyes_large.png"));
    private static final RenderType FROG_EYES_SMALL = RenderType.eyes(new ResourceLocation(Reference.MODID, "textures/entity/frog/eyes/frog_eyes_small.png"));
    private static final RenderType FROG_EYES_LARGE = RenderType.eyes(new ResourceLocation(Reference.MODID, "textures/entity/frog/eyes/frog_eyes_large.png"));

    public FrogEyesLayer(RenderLayerParent<T, FrogModel<T>> layerParent)
    {
        super(layerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T frog, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch)
    {
        if (frog.hasCustomName())
        {
            if (frog.getCustomName().getString().equals("Swallow Me Waldo") && SSConfigs.commonConfig.allowWaldo)
            {
                VertexConsumer vertexconsumer = buffer.getBuffer(hasBigEyes(frog) ? FROG_EYES_LARGE_BASE : FROG_EYES_SMALL_BASE);
                this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 0.0F, 0.0F, 0.0F, 0.0F);
                vertexconsumer = buffer.getBuffer(hasBigEyes(frog) ? FROG_EYES_LARGE : FROG_EYES_SMALL);
                this.getParentModel().renderToBuffer(poseStack, vertexconsumer, 15728640, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            }
        }
    }

    private boolean hasBigEyes(Frog frog)
    {
        FrogVariant variant = frog.getVariant();
        if(variant.equals(SSFrogVariants.BLUE_VARIANT))
            return true;
        if(variant.equals(SSFrogVariants.CYAN_VARIANT))
            return true;
        if(variant.equals(SSFrogVariants.LIGHT_BLUE_VARIANT))
            return true;
        if(variant.equals(SSFrogVariants.LIME_VARIANT))
            return true;
        if(variant.equals(SSFrogVariants.RED_VARIANT))
            return true;
        if(variant.equals(SSFrogVariants.YELLOW_VARIANT))
            return true;
        return false;
    }
}