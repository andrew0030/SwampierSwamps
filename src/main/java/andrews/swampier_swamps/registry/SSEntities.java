package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSEntities
{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Reference.MODID);

    public static final RegistryObject<EntityType<Dragonfly>> DRAGONFLY     = ENTITIES.register("dragonfly", () -> EntityType.Builder.of(Dragonfly::new, MobCategory.CREATURE).sized(0.5F, 0.25F).clientTrackingRange(10).build(new ResourceLocation(Reference.MODID, "dragonfly").toString()));

    public static void registerSpawnPlacements()
    {
        SpawnPlacements.register(DRAGONFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Dragonfly::checkDragonflySpawnRules);
    }
}