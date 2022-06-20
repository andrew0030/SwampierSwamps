package andrews.swampier_swamps.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterlilyBlock.class)
public class WaterlilyBlockMixin
{
    @Inject(method = "entityInside", at = @At(value = "TAIL"))
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci)
    {
        if (level instanceof ServerLevel && entity instanceof LivingEntity)
            if(level.getBlockState(pos).is(((WaterlilyBlock)(Object)this)))
                level.scheduleTick(pos, ((WaterlilyBlock)(Object)this), 5);
    }
}