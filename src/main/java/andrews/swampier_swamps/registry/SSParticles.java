package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSParticles
{
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MODID);

    public static final RegistryObject<SimpleParticleType> DECAY_BUBBLES    = PARTICLES.register("decay_bubbles", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SWAMP_GAS        = PARTICLES.register("swamp_gas", () -> new SimpleParticleType(false));
}