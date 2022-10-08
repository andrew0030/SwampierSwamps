package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.particles.DecayBubblesParticle;
import andrews.swampier_swamps.particles.SwampGasParticle;
import andrews.swampier_swamps.util.Reference;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

public class SSParticles
{
    public static final SimpleParticleType DECAY_BUBBLES    = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "decay_bubbles"), FabricParticleTypes.simple());
    public static final SimpleParticleType SWAMP_GAS        = Registry.register(Registry.PARTICLE_TYPE, new ResourceLocation(Reference.MODID, "swamp_gas"), FabricParticleTypes.simple());

    public static void init() {}

    // Note call this on the client only
    public static void registerParticles()
    {
        // We add the custom textures to the texture ATLAS
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(((atlasTexture, registry) -> {
            registry.register(new ResourceLocation(Reference.MODID, "particle/green_flame")); //TODO
        }));

        ParticleFactoryRegistry.getInstance().register(DECAY_BUBBLES, DecayBubblesParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SWAMP_GAS, SwampGasParticle.Factory::new);
    }
}