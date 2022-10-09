package andrews.swampier_swamps.network.client;

import andrews.swampier_swamps.network.client.util.ClientPacketHandlerClass;
import andrews.swampier_swamps.util.Reference;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class MessageClientSplashParticles
{
    public static ResourceLocation PACKET_ID = new ResourceLocation(Reference.MODID, "splash_particles_packet");

    public static void registerPacket()
    {
        ClientPlayNetworking.registerGlobalReceiver(PACKET_ID, (client, handler, buf, responseSender) ->
        {
            BlockPos pos = buf.readBlockPos();

            client.execute(() ->
            {
                if(Minecraft.getInstance().level == null) {
                    return;
                }

                ClientPacketHandlerClass.handleSpawnSplashParticles(pos);
            });
        });
    }
}