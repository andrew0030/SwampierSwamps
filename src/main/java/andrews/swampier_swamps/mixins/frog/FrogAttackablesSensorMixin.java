package andrews.swampier_swamps.mixins.frog;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.FrogAttackablesSensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FrogAttackablesSensor.class)
public abstract class FrogAttackablesSensorMixin
{
    @Shadow protected abstract boolean isUnreachableAttackTarget(LivingEntity frog, LivingEntity target);

    @Inject(method = "isMatchingEntity", at = @At(value = "HEAD"), cancellable = true)
    public void injectIsMatchingEntity(LivingEntity frog, LivingEntity target, CallbackInfoReturnable<Boolean> cir)
    {
        if(frog.hasCustomName()) // We make sure the Frog has a custom name to avoid NullPointers
            if(frog.getCustomName().getString().equals("Swallow Me Waldo")) // We check the name for Waldo
//                if(!frog.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN))
                    if(Sensor.isEntityAttackable(frog, target) && !isUnreachableAttackTarget(frog, target))
                        if(target.closerThan(frog, 10.0D)) // We make all the standard checks while skipping "canEat"
                            cir.setReturnValue(true);
    }
}