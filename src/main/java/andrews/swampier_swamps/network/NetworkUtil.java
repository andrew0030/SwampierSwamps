package andrews.swampier_swamps.network;

import andrews.swampier_swamps.network.client.MessageClientGasExplosionParticles;
import andrews.swampier_swamps.network.client.MessageClientSplashParticles;
import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class NetworkUtil
{
    public static void createSplashParticlesAtPos(ServerLevel level, BlockPos pos)
    {
        FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBlockPos(pos);

        for (ServerPlayer player : PlayerLookup.tracking(level, pos))
        {
            ServerPlayNetworking.send(player, MessageClientSplashParticles.PACKET_ID, passedData);
        }
    }

    public static void createGasExplosionParticlesAtPos(ServerLevel level, BlockPos pos)
    {
        FriendlyByteBuf passedData = new FriendlyByteBuf(Unpooled.buffer());
        passedData.writeBlockPos(pos);

        for (ServerPlayer player : PlayerLookup.tracking(level, pos))
        {
            ServerPlayNetworking.send(player, MessageClientGasExplosionParticles.PACKET_ID, passedData);
        }
    }
}