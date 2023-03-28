package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.config.SSConfigs;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WaterlilyBlock.class)
public class WaterlilyBlockMixin
{
    @ModifyVariable(method = "<init>", at = @At(value = "HEAD"), index = 1, argsOnly = true)
    private static BlockBehaviour.Properties entityInside(BlockBehaviour.Properties value)
    {
        return value.randomTicks();
    }

    @Inject(method = "entityInside", at = @At(value = "TAIL"))
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, CallbackInfo ci)
    {
        if (level instanceof ServerLevel && entity instanceof LivingEntity)
            if(level.getBlockState(pos).is(((WaterlilyBlock)(Object)this)) && SSConfigs.commonConfig.doLilyPadsSink.get() && !(level.getBlockState(pos.below()).getMaterial() == Material.ICE))
                level.scheduleTick(pos, ((WaterlilyBlock)(Object)this), SSConfigs.commonConfig.lilyPadSinkTimeStage1.get());
    }
}