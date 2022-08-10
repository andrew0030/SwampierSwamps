package andrews.swampier_swamps.network;

import andrews.swampier_swamps.network.client.MessageClientGasExplosionParticles;
import andrews.swampier_swamps.network.client.MessageClientSplashParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.PacketDistributor;

public class NetworkUtil
{
    public static void createSplashParticlesAtPos(Level level, BlockPos pos)
    {
        SSNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new MessageClientSplashParticles(pos));
    }

    public static void createGasExplosionParticlesAtPos(Level level, BlockPos pos)
    {
        SSNetwork.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> level.getChunkAt(pos)), new MessageClientGasExplosionParticles(pos));
    }
}
