package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSFrogVariants
{
    public static final DeferredRegister<FrogVariant> FROG_VARIANTS = DeferredRegister.create(Registries.FROG_VARIANT, Reference.MODID);

    public static final RegistryObject<FrogVariant> WHITE_VARIANT           = createFrogVariant("white_variant", "frog_white");
    // Orange is one of vanillas Frogs.
    public static final RegistryObject<FrogVariant> MAGENTA_VARIANT         = createFrogVariant("magenta_variant", "frog_magenta");
    public static final RegistryObject<FrogVariant> LIGHT_BLUE_VARIANT      = createFrogVariant("light_blue_variant", "frog_light_blue");
    public static final RegistryObject<FrogVariant> YELLOW_VARIANT          = createFrogVariant("yellow_variant", "frog_yellow");
    public static final RegistryObject<FrogVariant> LIME_VARIANT            = createFrogVariant("lime_variant", "frog_lime");
    public static final RegistryObject<FrogVariant> PINK_VARIANT            = createFrogVariant("pink_variant", "frog_pink");
    public static final RegistryObject<FrogVariant> GRAY_VARIANT            = createFrogVariant("gray_variant", "frog_gray");
    // Light Gray is one of vanillas Frogs.
    public static final RegistryObject<FrogVariant> CYAN_VARIANT            = createFrogVariant("cyan_variant", "frog_cyan");
    public static final RegistryObject<FrogVariant> PURPLE_VARIANT          = createFrogVariant("purple_variant", "frog_purple");
    public static final RegistryObject<FrogVariant> BLUE_VARIANT            = createFrogVariant("blue_variant", "frog_blue");
    public static final RegistryObject<FrogVariant> BROWN_VARIANT           = createFrogVariant("brown_variant", "frog_brown");
    // Green is one of vanillas Frogs.
    public static final RegistryObject<FrogVariant> RED_VARIANT             = createFrogVariant("red_variant", "frog_red");
    public static final RegistryObject<FrogVariant> BLACK_VARIANT           = createFrogVariant("black_variant", "frog_black");

    private static RegistryObject<FrogVariant> createFrogVariant(String name, String texture)
    {
        return FROG_VARIANTS.register(name, () -> new FrogVariant(new ResourceLocation(Reference.MODID, "textures/entity/frog/" + texture + ".png")));
    }
}