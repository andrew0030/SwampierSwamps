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

    //public static final RegistryObject<FrogVariant> WHITE_VARIANT           = createFrogVariant("white_variant", "frog_white");
    // Orange is one of vanillas Frogs.
    //magenta
    public static final RegistryObject<FrogVariant> LIGHT_BLUE_VARIANT      = createFrogVariant("light_blue_variant", "frog_light_blue");
    public static final RegistryObject<FrogVariant> YELLOW_VARIANT          = createFrogVariant("yellow_variant", "frog_yellow");
    public static final RegistryObject<FrogVariant> LIME_VARIANT            = createFrogVariant("lime_variant", "frog_lime");
    //pink
    public static final RegistryObject<FrogVariant> GRAY_VARIANT            = createFrogVariant("gray_variant", "frog_gray");
    // Light Gray is one of vanillas Frogs.
    public static final RegistryObject<FrogVariant> CYAN_VARIANT            = createFrogVariant("cyan_variant", "frog_cyan");
    //purple
    public static final RegistryObject<FrogVariant> BLUE_VARIANT            = createFrogVariant("blue_variant", "frog_blue");
    //brown
    // Green is one of vanillas Frogs.
    public static final RegistryObject<FrogVariant> RED_VARIANT             = createFrogVariant("red_variant", "frog_red");
    //black

    private static RegistryObject<FrogVariant> createFrogVariant(String name, String texture)
    {
        return FROG_VARIANTS.register(name, () -> new FrogVariant(new ResourceLocation(Reference.MODID, "textures/entity/frog/" + texture + ".png")));
    }
}