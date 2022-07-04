package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.features.BigLilyPadFeature;
import andrews.swampier_swamps.level.features.CattailFeature;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MODID);

    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> PATCH_CATTAIL           = FEATURES.register("patch_cattail", () -> new CattailFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<RandomPatchConfiguration>>        PATCH_SMALL_LILY_PAD    = FEATURES.register("patch_small_lily_pad", () -> new RandomPatchFeature(RandomPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> PATCH_BIG_LILY_PAD      = FEATURES.register("patch_big_lily_pad", () -> new BigLilyPadFeature(ProbabilityFeatureConfiguration.CODEC));
}