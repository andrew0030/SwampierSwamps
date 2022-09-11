package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.level.configs.FrogVariantConfig;
import andrews.swampier_swamps.level.features.*;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSFeatures
{
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Reference.MODID);

    // Plant Patches
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> PATCH_CATTAIL           = FEATURES.register("patch_cattail", () -> new CattailFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<RandomPatchConfiguration>>        PATCH_SMALL_LILY_PAD    = FEATURES.register("patch_small_lily_pad", () -> new RandomPatchFeature(RandomPatchConfiguration.CODEC));
    public static final RegistryObject<Feature<ProbabilityFeatureConfiguration>> PATCH_BIG_LILY_PAD      = FEATURES.register("patch_big_lily_pad", () -> new BigLilyPadFeature(ProbabilityFeatureConfiguration.CODEC));
    // Mud Puddles
    public static final RegistryObject<Feature<NoneFeatureConfiguration>>        MUD_PUDDLE              = FEATURES.register("mud_puddle", () -> new MudPuddleFeature(NoneFeatureConfiguration.CODEC));
    // Tree Stuff
    public static final RegistryObject<Feature<NoneFeatureConfiguration>>        DECAYING_LOG            = FEATURES.register("decaying_log", () -> new DecayingLogFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>>               BALD_CYPRESS            = FEATURES.register("bald_cypress", () -> new TreeFeature(TreeConfiguration.CODEC));
    // Decaying Kelp
    public static final RegistryObject<Feature<NoneFeatureConfiguration>>        DECAYING_KELP           = FEATURES.register("decaying_kelp", () -> new DecayingKelpFeature(NoneFeatureConfiguration.CODEC));
    // Village Frog
    public static final RegistryObject<Feature<FrogVariantConfig>>               VILLAGE_FROG            = FEATURES.register("village_frog", () -> new VillageFrogFeature(FrogVariantConfig.CODEC));

}