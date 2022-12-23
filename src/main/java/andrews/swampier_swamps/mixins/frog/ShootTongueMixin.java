package andrews.swampier_swamps.mixins.frog;

import andrews.swampier_swamps.SwampierSwamps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.ShootTongue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShootTongue.class)
public class ShootTongueMixin
{
    @Shadow private boolean canPathfindToTarget(Frog frog, LivingEntity target) {return false;}
    @Shadow private void addUnreachableTargetToMemory(Frog frog, LivingEntity target) {}

    @Inject(method = "checkExtraStartConditions(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/animal/frog/Frog;)Z", at = @At(value = "RETURN"), cancellable = true)
    public void injectCheckExtraStartConditions(ServerLevel level, Frog frog, CallbackInfoReturnable<Boolean> cir)
    {
        if(frog.hasCustomName())
        {
            if (frog.getCustomName().getString().equals("Swallow Me Waldo") && SwampierSwamps.SS_CONFIG.SSCommonConfig.allowWaldo)
            {
                if(frog.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isPresent())
                {
                    LivingEntity target = frog.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get();
                    boolean canPathFind = this.canPathfindToTarget(frog, target);
                    if (!canPathFind)
                    {
                        frog.getBrain().eraseMemory(MemoryModuleType.ATTACK_TARGET);
                        addUnreachableTargetToMemory(frog, target);
                    }
                    cir.setReturnValue(canPathFind && frog.getPose() != Pose.CROAKING);
                }
            }
        }
    }
}