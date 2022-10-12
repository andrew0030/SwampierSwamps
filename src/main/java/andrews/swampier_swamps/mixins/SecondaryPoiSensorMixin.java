package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.registry.SSBlocks;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.sensing.SecondaryPoiSensor;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(SecondaryPoiSensor.class)
public class SecondaryPoiSensorMixin
{
    @Unique
    private final List<GlobalPos> fertileFarmlandPosList = Lists.newArrayList();

    @Inject(method = "doTick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;)V", at = @At(value = "HEAD"))
    public void injectDoTick(ServerLevel level, Villager entity, CallbackInfo ci)
    {
        ResourceKey<Level> resourceKey = level.dimension();
        BlockPos pos = entity.blockPosition();

        for(int x = -4; x <= 4; ++x)
        {
            for(int y = -2; y <= 2; ++y)
            {
                for(int z = -4; z <= 4; ++z)
                {
                    BlockPos offsetPos = pos.offset(x, y, z);
                    if(entity.getVillagerData().getProfession().name().equals("farmer"))
                        if(level.getBlockState(offsetPos).getBlock() == SSBlocks.FERTILE_FARMLAND.get())
                            fertileFarmlandPosList.add(GlobalPos.of(resourceKey, offsetPos));
                }
            }
        }
    }

    @ModifyVariable(method = "doTick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;)V", at = @At("STORE"), ordinal = 0)
    public List<GlobalPos> modifyVarDoTick(List<GlobalPos> pos)
    {
        if(!fertileFarmlandPosList.isEmpty())
        {
            pos.addAll(fertileFarmlandPosList);
            fertileFarmlandPosList.clear();
        }
        return pos;
    }
}