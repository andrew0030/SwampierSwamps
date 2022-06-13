package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSFrogVariants
{
    public static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(Registry.FROG_VARIANT_REGISTRY, Reference.MODID);

    public static final RegistryObject<FrogVariant> WHITE_VARIANT   = createFrogVariant("white_variant", "frog_white_variant");
    public static final RegistryObject<FrogVariant> RED_VARIANT     = createFrogVariant("red_variant", "frog_red_variant");

    private static RegistryObject<FrogVariant> createFrogVariant(String name, String texture)
    {
        return FROG_VARIANTS.register(name, () -> new FrogVariant(new ResourceLocation(Reference.MODID, "textures/entity/frog/" + texture + ".png")));
    }
}