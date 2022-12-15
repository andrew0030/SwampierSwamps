package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.features.decorators.MudPuddlePlacer;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class SSPlacements
{
    public static final PlacementModifierType<MudPuddlePlacer> MUD_PUDDLE_PLACER = Registry.register(BuiltInRegistries.PLACEMENT_MODIFIER_TYPE, new ResourceLocation(Reference.MODID, "mud_puddle_placer"), () -> MudPuddlePlacer.CODEC);

    public static void init() {}
}