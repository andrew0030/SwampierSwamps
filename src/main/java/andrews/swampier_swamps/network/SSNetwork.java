package andrews.swampier_swamps.network;

import andrews.swampier_swamps.network.client.MessageClientGasExplosionParticles;
import andrews.swampier_swamps.network.client.MessageClientSplashParticles;

public class SSNetwork
{
    public static void registerClientNetworkMessages()
    {
        MessageClientSplashParticles.registerPacket();
        MessageClientGasExplosionParticles.registerPacket();
    }

//    public static void registerNetworkMessages() {}
}