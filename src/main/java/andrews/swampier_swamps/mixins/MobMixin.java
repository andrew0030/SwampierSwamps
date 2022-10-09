package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.SwampierSwamps;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mob.class)
public class MobMixin
{
    @ModifyVariable(method = "doHurtTarget", at = @At("STORE"), ordinal = 0)
    public float modifyVarDoHurtTarget(float attackDamage)
    {
        if (((Mob)(Object)this) instanceof Frog frog)
            if(frog.hasCustomName())
                if(frog.getCustomName().getString().equals("Swallow Me Waldo") && SwampierSwamps.SS_CONFIG.SSCommonConfig.allowWaldo)
                {
                    float damageMod = SwampierSwamps.SS_CONFIG.SSCommonConfig.waldoDamageModifier;
                    return attackDamage * damageMod;
                }
        return attackDamage;
    }
}