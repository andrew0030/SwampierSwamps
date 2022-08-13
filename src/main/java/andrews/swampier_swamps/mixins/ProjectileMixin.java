package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.entities.SwampGas;
import andrews.swampier_swamps.registry.SSEntities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Projectile.class)
public class ProjectileMixin
{
    @Shadow private boolean leftOwner;

    @Inject(method = "canHitEntity", at = @At("HEAD"), cancellable = true)
    public void injectCanHitEntity(Entity target, CallbackInfoReturnable<Boolean> cir)
    {
        if (!target.isSpectator() && target.isAlive() && target instanceof SwampGas)
        {
            Entity entity = ((Projectile)(Object)this).getOwner();
            if(((Projectile)(Object)this).isOnFire())
                if(entity == null || leftOwner || !entity.isPassengerOfSameVehicle(target))
                    cir.setReturnValue(true);
        }
    }
}