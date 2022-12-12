package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.features.trunk_placers.BaldCypressTrunkPlacer;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSTrunkPlacers
{
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACERS = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Reference.MODID);

    public static final RegistryObject<TrunkPlacerType<BaldCypressTrunkPlacer>> BALD_CYPRESS_TRUNK_PLACER = TRUNK_PLACERS.register("bald_cypress_trunk_placer", () -> new TrunkPlacerType<>(BaldCypressTrunkPlacer.CODEC));
}
