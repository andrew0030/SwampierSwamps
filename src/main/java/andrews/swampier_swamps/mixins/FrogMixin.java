package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.registry.SSFrogVariants;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Frog.class)
public class FrogMixin
{
    @Inject(method = "finalizeSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/frog/FrogAi;initMemories(Lnet/minecraft/world/entity/animal/frog/Frog;Lnet/minecraft/util/RandomSource;)V"))
    public void finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CompoundTag compound, CallbackInfoReturnable<SpawnGroupData> cir)
    {
        Holder<Biome> holder = serverLevelAccessor.getBiome(((Frog)(Object)this).blockPosition());
        if(holder.is(Biomes.TAIGA))
        {
            //((Frog)(Object)this).setVariant(SSFrogVariants.WHITE_VARIANT.get());
        }
        else if(holder.is(Biomes.DESERT))
        {
            ((Frog)(Object)this).setVariant(SSFrogVariants.RED_VARIANT.get());
        }
    }
}