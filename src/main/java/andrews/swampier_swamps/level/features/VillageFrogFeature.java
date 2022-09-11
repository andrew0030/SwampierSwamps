package andrews.swampier_swamps.level.features;

import andrews.swampier_swamps.level.configs.FrogVariantConfig;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class VillageFrogFeature extends Feature<FrogVariantConfig>
{
    public VillageFrogFeature(Codec<FrogVariantConfig> codec)
    {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<FrogVariantConfig> context)
    {
        BlockPos pos = context.origin().below();
        Frog frog = EntityType.FROG.create(context.level().getLevel());
        frog.finalizeSpawn(context.level(), context.level().getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
        context.config().variant.ifPresent(frog::setVariant);
        frog.moveTo((double)pos.getX() + 0.5D, pos.getY(), (double)pos.getZ() + 0.5D, 0.0F, 0.0F);
        context.level().addFreshEntityWithPassengers(frog);
        return true;
    }
}