package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.level.configs.FrogVariantConfig;
import andrews.swampier_swamps.level.features.*;
import andrews.swampier_swamps.util.Reference;
import com.mojang.realmsclient.util.WorldGenerationInfo;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.RandomPatchFeature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ProbabilityFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class SSFeatures
{
    // Plant Patches
    public static final Feature<ProbabilityFeatureConfiguration> PATCH_CATTAIL           = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "patch_cattail"), new CattailFeature(ProbabilityFeatureConfiguration.CODEC));
    public static final Feature<RandomPatchConfiguration>        PATCH_SMALL_LILY_PAD    = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "patch_small_lily_pad"), new RandomPatchFeature(RandomPatchConfiguration.CODEC));
    public static final Feature<ProbabilityFeatureConfiguration> PATCH_BIG_LILY_PAD      = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "patch_big_lily_pad"), new BigLilyPadFeature(ProbabilityFeatureConfiguration.CODEC));
    // Mud Puddles
    public static final Feature<NoneFeatureConfiguration>        MUD_PUDDLE              = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "mud_puddle"), new MudPuddleFeature(NoneFeatureConfiguration.CODEC));
    // Tree Stuff
    public static final Feature<NoneFeatureConfiguration>        DECAYING_LOG            = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "decaying_log"), new DecayingLogFeature(NoneFeatureConfiguration.CODEC));
    public static final Feature<TreeConfiguration>               BALD_CYPRESS            = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "bald_cypress"), new TreeFeature(TreeConfiguration.CODEC));
    // Decaying Kelp
    public static final Feature<NoneFeatureConfiguration>        DECAYING_KELP           = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "decaying_kelp"), new DecayingKelpFeature(NoneFeatureConfiguration.CODEC));
    // Village Frog
    public static final Feature<FrogVariantConfig>               VILLAGE_FROG            = Registry.register(Registry.FEATURE, new ResourceLocation(Reference.MODID, "village_frog"), new VillageFrogFeature(FrogVariantConfig.CODEC));

    public static void init()
    {
        addFeaturesToWorld();
    }

    private static void addFeaturesToWorld()
    {
        // Bald Cypress
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "bald_cypress")));
        // Decaying Kelp
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "decaying_kelp")));
        // Decaying Log
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "decaying_log")));
        // Mud Puddle
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.LAKES,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "mud_puddle")));
        // Patch Big Lily Pad
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "patch_big_lily_pad")));
        // Patch Cattail
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "patch_cattail")));
        // Patch Small Lily Pad
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "patch_small_lily_pad")));
        // Swamp Vegetation
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.SWAMP),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY, new ResourceLocation(Reference.MODID, "swamp_vegetation")));
    }
}