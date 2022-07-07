package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.features.decorators.MudPuddlePlacer;
import andrews.swampier_swamps.util.Reference;
import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SSPlacements
{
    public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, Reference.MODID);

    public static final RegistryObject<PlacementModifierType<MudPuddlePlacer>> MUD_PUDDLE_PLACER = PLACEMENT_MODIFIER.register("mud_puddle_placer", () -> codecHelper(MudPuddlePlacer.CODEC));

    // We need this little helper because the compiler will scream at us if we used () -> directly.
    private static <P extends PlacementModifier> PlacementModifierType<P> codecHelper(Codec<P> codec)
    {
        return () -> codec;
    }
}