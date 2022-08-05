package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.util.TheUnsafeHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@Mixin(Blocks.class)
public class BlocksMixin
{
    @Shadow @Final public static final Block DRIED_KELP_BLOCK = null;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void postStaticInit(CallbackInfo ci)
    {
        Unsafe theUnsafe = TheUnsafeHelper.getTheUnsafe();
        try
        {
            Field f = BlockBehaviour.class.getDeclaredField("isRandomlyTicking");
            long offset = theUnsafe.objectFieldOffset(f);
            theUnsafe.putBoolean(DRIED_KELP_BLOCK, offset, true);
        }
        catch (NoSuchFieldException e)
        {
            throw new RuntimeException("Could not find Field");
        }
    }
}