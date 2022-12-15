package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.entities.SwampGas;
import andrews.swampier_swamps.events.EntityAttributesHandler;
import andrews.swampier_swamps.util.Reference;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;

public class SSEntities
{
    public static final EntityType<Dragonfly> DRAGONFLY     = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Reference.MODID, "dragonfly"), EntityType.Builder.of(Dragonfly::new, MobCategory.CREATURE).sized(0.5F, 0.25F).clientTrackingRange(10).build(new ResourceLocation(Reference.MODID, "dragonfly").toString()));
    public static final EntityType<SwampGas> SWAMP_GAS      = Registry.register(BuiltInRegistries.ENTITY_TYPE, new ResourceLocation(Reference.MODID, "swamp_gas"), EntityType.Builder.of(SwampGas::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(8).build(new ResourceLocation(Reference.MODID, "swamp_gas").toString()));

    public static void init()
    {
        EntityAttributesHandler.registerEntityAttributes();
        registerSpawnPlacements();
        addEntitiesToWorld();
    }

    private static void registerSpawnPlacements()
    {
        SpawnPlacements.register(DRAGONFLY, SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dragonfly::checkDragonflySpawnRules);
    }

    private static void addEntitiesToWorld()
    {
        // Dragonfly
        BiomeModifications.addSpawn(BiomeSelectors.tag(SSTags.Biomes.HAS_DRAGONFLY), MobCategory.CREATURE, DRAGONFLY, 15, 3, 6);
    }
}