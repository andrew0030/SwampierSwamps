package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.registry.SSFrogVariants;
import andrews.swampier_swamps.registry.SSTags;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
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
        if (holder.is(SSTags.Biomes.WHITE_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.WHITE_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.ORANGE_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(FrogVariant.TEMPERATE);
        }
        else if (holder.is(SSTags.Biomes.MAGENTA_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.MAGENTA_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.LIGHT_BLUE_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.LIGHT_BLUE_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.YELLOW_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.YELLOW_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.LIME_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.LIME_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.PINK_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.PINK_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.GRAY_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.GRAY_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.LIGHT_GRAY_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(FrogVariant.WARM);
        }
        else if (holder.is(SSTags.Biomes.CYAN_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.CYAN_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.PURPLE_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.PURPLE_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.BLUE_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.BLUE_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.BROWN_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.BROWN_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.GREEN_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(FrogVariant.COLD);
        }
        else if (holder.is(SSTags.Biomes.RED_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.RED_VARIANT.get());
        }
        else if (holder.is(SSTags.Biomes.BLACK_FROG_BIOMES))
        {
            ((Frog) (Object) this).setVariant(SSFrogVariants.BLACK_VARIANT.get());
        }
    }
}