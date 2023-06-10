package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.entities.SwampGas;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Reference.MODID);

    public static final RegistryObject<EntityType<Dragonfly>> DRAGONFLY     = ENTITIES.register("dragonfly", () -> EntityType.Builder.of(Dragonfly::new, MobCategory.CREATURE).sized(0.5F, 0.25F).clientTrackingRange(10).build(new ResourceLocation(Reference.MODID, "dragonfly").toString()));
    public static final RegistryObject<EntityType<SwampGas>> SWAMP_GAS      = ENTITIES.register("swamp_gas", () -> EntityType.Builder.of(SwampGas::new, MobCategory.MISC).sized(0.3125F, 0.3125F).clientTrackingRange(8).build(new ResourceLocation(Reference.MODID, "swamp_gas").toString()));

    public static void registerSpawnPlacements()
    {
        SpawnPlacements.register(DRAGONFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dragonfly::checkDragonflySpawnRules);
    }
}