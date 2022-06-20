package andrews.swampier_swamps.network.client.util;

import andrews.swampier_swamps.network.client.MessageClientSplashParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientPacketHandlerClass
{
    public static void handleSpawnSplashParticles(MessageClientSplashParticles msg, Supplier<NetworkEvent.Context> ctx)
    {
        RandomSource rand = Minecraft.getInstance().level.random;
        BlockPos pos = msg.pos;
        for(int i = 0; i < 5; i++)
        {
            Minecraft.getInstance().level.addParticle(ParticleTypes.SPLASH, pos.getX() + rand.nextDouble(), pos.getY() + 0.1, pos.getZ() + rand.nextDouble(), 0, 0, 0);
            Minecraft.getInstance().level.addParticle(ParticleTypes.BUBBLE, pos.getX() + rand.nextDouble(), pos.getY() - 0.1, pos.getZ() + rand.nextDouble(), 0, 0, 0);
        }
    }
}
