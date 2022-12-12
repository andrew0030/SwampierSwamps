package andrews.swampier_swamps.level.configs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.animal.FrogVariant;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Optional;

public class FrogVariantConfig implements FeatureConfiguration
{
    public static final Codec<FrogVariantConfig> CODEC = RecordCodecBuilder.create((configInstance) -> configInstance.group(
            BuiltInRegistries.FROG_VARIANT.byNameCodec().optionalFieldOf("variant").forGetter(config -> config.variant)
    ).apply(configInstance, FrogVariantConfig::new));

    public final Optional<FrogVariant> variant;

    public FrogVariantConfig(Optional<FrogVariant> variant)
    {
        this.variant = variant;
    }
}