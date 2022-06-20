package andrews.swampier_swamps.network.client;

import andrews.swampier_swamps.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientSplashParticles
{
    public BlockPos pos;
    public boolean isStandardSetUnlocked;
    public boolean isClassicSetUnlocked;
    public boolean isPandorasCreaturesSetUnlocked;

    public MessageClientSplashParticles(BlockPos pos)
    {
        this.pos = pos;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
    }

    public static MessageClientSplashParticles deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        return new MessageClientSplashParticles(pos);
    }

    public static void handle(MessageClientSplashParticles message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleSpawnSplashParticles(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}
