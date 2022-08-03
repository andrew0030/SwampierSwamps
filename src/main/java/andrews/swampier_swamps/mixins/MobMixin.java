package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.config.configs.SSCommonConfig;
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
                if(frog.getCustomName().getString().equals("Swallow Me Waldo") && SSConfigs.commonConfig.allowWaldo.get())
                {
                    float damageMod = SSConfigs.commonConfig.waldoDamageModifier.get().floatValue();
                    return attackDamage * damageMod;
                }
        return attackDamage;
    }
}
