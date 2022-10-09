package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.util.TheUnsafeHelper;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@Mixin(Blocks.class)
public class BlocksMixin
{
	@Inject(method = "<clinit>", at = @At("TAIL"))
	private static void postStaticInit(CallbackInfo ci)
	{
		Unsafe theUnsafe = TheUnsafeHelper.getTheUnsafe();
		Block block = Blocks.DRIED_KELP_BLOCK;
		Class<?> clazz = Block.class;
		while (clazz != Object.class)
		{
			try
			{
				Field f = clazz.getDeclaredField(ObfuscationReflectionHelper.remapName(INameMappingService.Domain.FIELD, "isRandomlyTicking"));
				long offset = theUnsafe.objectFieldOffset(f);
				// this should always fail
				// but I'm leaving it for the case that there's some JVM where it works
				// this try-catch can be commented if you want tho
				try
				{
					// maybe some JVM is poorly made in such away that this works
					// idk
					f.setAccessible(true);
					f.setBoolean(block, true);
					break;
				} catch (Throwable ignored) {}
				theUnsafe.putBoolean(clazz.cast(block), offset, true);
				break;
			}
			catch (Throwable e)
			{
				if (clazz == clazz.getSuperclass())
					throw new RuntimeException("Could not find Field");
				clazz = clazz.getSuperclass();
			}
		}
	}
}