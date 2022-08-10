package andrews.swampier_swamps.network.client;

import andrews.swampier_swamps.network.client.util.ClientPacketHandlerClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageClientGasExplosionParticles
{
    public BlockPos pos;

    public MessageClientGasExplosionParticles(BlockPos pos)
    {
        this.pos = pos;
    }

    public void serialize(FriendlyByteBuf buf)
    {
        buf.writeBlockPos(this.pos);
    }

    public static MessageClientGasExplosionParticles deserialize(FriendlyByteBuf buf)
    {
        BlockPos pos = buf.readBlockPos();
        return new MessageClientGasExplosionParticles(pos);
    }

    public static void handle(MessageClientGasExplosionParticles message, Supplier<NetworkEvent.Context> ctx)
    {
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide() == LogicalSide.CLIENT)
        {
            context.enqueueWork(() ->
            {
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClientPacketHandlerClass.handleSpawnGasExplosionParticles(message, ctx));
            });
            context.setPacketHandled(true);
        }
    }
}
