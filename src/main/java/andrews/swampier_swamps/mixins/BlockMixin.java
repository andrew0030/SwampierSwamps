package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.registry.SSTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin
{
    @ModifyVariable(method = "fallOn", at = @At(value = "HEAD"), index = 5, argsOnly = true)
    private float fallOn(float f, Level level, BlockState blockState, BlockPos blockPos, Entity entity)
    {
        return ((Block)(Object)this).defaultBlockState().is(SSTags.Blocks.FROGLIGHTS) && !entity.isSuppressingBounce() ? 0 : f;
    }

    @Inject(method = "updateEntityAfterFallOn", at = @At(value = "HEAD"), cancellable = true)
    public void updateEntityAfterFallOn(BlockGetter blockGetter, Entity entity, CallbackInfo ci)
    {
        if(((Block)(Object)this).defaultBlockState().is(SSTags.Blocks.FROGLIGHTS))
        {
            if (!entity.isSuppressingBounce())
            {
                Vec3 vec3 = entity.getDeltaMovement();
                if (vec3.y < 0.0D)
                {
                    double multiplier = entity instanceof LivingEntity ? 0.6D : 0.4D;
                    entity.setDeltaMovement(vec3.x, -vec3.y * multiplier, vec3.z);
                    ci.cancel();
                }
            }
        }
    }
}