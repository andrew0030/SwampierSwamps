package andrews.swampier_swamps.network;

import andrews.swampier_swamps.network.client.MessageClientGasExplosionParticles;
import andrews.swampier_swamps.network.client.MessageClientSplashParticles;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class SSNetwork
{
    public static final String NETWORK_PROTOCOL = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Reference.MODID, "net"))
            .networkProtocolVersion(() -> NETWORK_PROTOCOL)
            .clientAcceptedVersions(NETWORK_PROTOCOL::equals)
            .serverAcceptedVersions(NETWORK_PROTOCOL::equals)
            .simpleChannel();

    /**
     * Used to set up all the Messages
     */
    public static void setupMessages()
    {
        int id = -1;
        //Client Messages
        CHANNEL.registerMessage(id++, MessageClientSplashParticles.class, MessageClientSplashParticles::serialize, MessageClientSplashParticles::deserialize, MessageClientSplashParticles::handle);
        CHANNEL.registerMessage(id++, MessageClientGasExplosionParticles.class, MessageClientGasExplosionParticles::serialize, MessageClientGasExplosionParticles::deserialize, MessageClientGasExplosionParticles::handle);
        //Server Messages
    }
}