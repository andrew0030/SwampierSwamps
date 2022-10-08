package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.features.trunk_placers.BaldCypressTrunkPlacer;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class SSTrunkPlacers
{
    public static final TrunkPlacerType<BaldCypressTrunkPlacer> BALD_CYPRESS_TRUNK_PLACER = Registry.register(Registry.TRUNK_PLACER_TYPES, new ResourceLocation(Reference.MODID, "bald_cypress_trunk_placer"), new TrunkPlacerType<>(BaldCypressTrunkPlacer.CODEC));

    public static void init() {}
}