package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.features.decorators.MudPuddlePlacer;
import andrews.swampier_swamps.util.Reference;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;

public class SSPlacements
{
    public static final PlacementModifierType<MudPuddlePlacer> MUD_PUDDLE_PLACER = Registry.register(Registry.PLACEMENT_MODIFIERS, new ResourceLocation(Reference.MODID, "mud_puddle_placer"), () -> MudPuddlePlacer.CODEC);

    public static void init() {}
}