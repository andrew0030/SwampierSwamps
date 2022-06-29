package andrews.swampier_swamps.entities.renderer;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.entities.model.DragonflyModel;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class DragonflyRenderer<E extends Dragonfly> extends MobRenderer<E, DragonflyModel<E>>
{
    private final ResourceLocation BLUE_PATH = new ResourceLocation(Reference.MODID, "textures/entity/dragonfly_blue.png");
    private final ResourceLocation RED_PATH = new ResourceLocation(Reference.MODID, "textures/entity/dragonfly_red.png");
    private final ResourceLocation ORANGE_PATH = new ResourceLocation(Reference.MODID, "textures/entity/dragonfly_orange.png");
    private final ResourceLocation GREEN_PATH = new ResourceLocation(Reference.MODID, "textures/entity/dragonfly_green.png");

    public DragonflyRenderer(EntityRendererProvider.Context context)
    {
        super(context, new DragonflyModel<>(context.bakeLayer(DragonflyModel.LAYER)), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(E entity)
    {
        return switch (entity.getDragonflyType())
        {
            default -> BLUE_PATH; // By defaulting to Blue we dont need 1 -> Blue because its redundant
            case 2 -> RED_PATH;
            case 3 -> ORANGE_PATH;
            case 4 -> GREEN_PATH;
        };
    }
}