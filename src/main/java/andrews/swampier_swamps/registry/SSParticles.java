package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.particles.DecayBubblesParticle;
import andrews.swampier_swamps.particles.SwampGasParticle;
import andrews.swampier_swamps.util.Reference;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class SSParticles
{
    public static final SimpleParticleType DECAY_BUBBLES    = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "decay_bubbles"), FabricParticleTypes.simple());
    public static final SimpleParticleType SWAMP_GAS        = Registry.register(BuiltInRegistries.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "swamp_gas"), FabricParticleTypes.simple());

    public static void init() {}

    public static void registerParticles()
    {
        ParticleFactoryRegistry.getInstance().register(DECAY_BUBBLES, DecayBubblesParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SWAMP_GAS, SwampGasParticle.Factory::new);
    }
}