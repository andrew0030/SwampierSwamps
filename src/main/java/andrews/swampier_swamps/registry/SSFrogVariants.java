package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.FrogVariant;

public class SSFrogVariants
{
    public static final FrogVariant WHITE_VARIANT           = createFrogVariant("white_variant", "frog_white");
    // Orange is one of vanillas Frogs.
    public static final FrogVariant MAGENTA_VARIANT         = createFrogVariant("magenta_variant", "frog_magenta");
    public static final FrogVariant LIGHT_BLUE_VARIANT      = createFrogVariant("light_blue_variant", "frog_light_blue");
    public static final FrogVariant YELLOW_VARIANT          = createFrogVariant("yellow_variant", "frog_yellow");
    public static final FrogVariant LIME_VARIANT            = createFrogVariant("lime_variant", "frog_lime");
    public static final FrogVariant PINK_VARIANT            = createFrogVariant("pink_variant", "frog_pink");
    public static final FrogVariant GRAY_VARIANT            = createFrogVariant("gray_variant", "frog_gray");
    // Light Gray is one of vanillas Frogs.
    public static final FrogVariant CYAN_VARIANT            = createFrogVariant("cyan_variant", "frog_cyan");
    public static final FrogVariant PURPLE_VARIANT          = createFrogVariant("purple_variant", "frog_purple");
    public static final FrogVariant BLUE_VARIANT            = createFrogVariant("blue_variant", "frog_blue");
    public static final FrogVariant BROWN_VARIANT           = createFrogVariant("brown_variant", "frog_brown");
    // Green is one of vanillas Frogs.
    public static final FrogVariant RED_VARIANT             = createFrogVariant("red_variant", "frog_red");
    public static final FrogVariant BLACK_VARIANT           = createFrogVariant("black_variant", "frog_black");

    private static FrogVariant createFrogVariant(String name, String texture)
    {
        return Registry.register(BuiltInRegistries.FROG_VARIANT, new ResourceLocation(Reference.MODID, name), new FrogVariant(new ResourceLocation(Reference.MODID, "textures/entity/frog/" + texture + ".png")));
    }

    public static void init() {}
}