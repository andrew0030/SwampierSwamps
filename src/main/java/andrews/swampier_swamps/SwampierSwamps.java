package andrews.swampier_swamps;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.network.SSNetwork;
import andrews.swampier_swamps.registry.*;
import andrews.swampier_swamps.util.PotionRecipeChanger;
import andrews.swampier_swamps.util.Reference;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = Reference.MODID)
public class SwampierSwamps
{
    public SwampierSwamps()
    {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        SSFrogVariants.FROG_VARIANTS.register(modEventBus);
        SSItems.ITEMS.register(modEventBus);
        SSBlocks.BLOCKS.register(modEventBus);
        SSFeatures.FEATURES.register(modEventBus);
        SSEntities.ENTITIES.register(modEventBus);
        SSParticles.PARTICLES.register(modEventBus);
        SSBlockEntities.BLOCK_ENTITY_TYPES.register(modEventBus);
        SSTrunkPlacers.TRUNK_PLACERS.register(modEventBus);
        SSTreeDecorators.TREE_DECORATORS.register(modEventBus);
        SSPlacements.PLACEMENT_MODIFIER.register(modEventBus);

        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
        {
            modEventBus.addListener(this::setupClient);
            modEventBus.addListener(this::setupParticles);
        });
        modEventBus.addListener(this::setupCommon);

        SSConfigs.registerConfigs();
    }

    void setupCommon(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(() ->
        {
            PotionRecipeChanger.performChanges();
            SSEntities.registerSpawnPlacements();
            SSFlammables.registerFlammables();
            SSCompostable.registerCompostable();
        });
        //Thread Safe Stuff Bellow
        SSNetwork.setupMessages();
    }

    void setupClient(final FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {});
        // Thread Safe Stuff Bellow
    }

    void setupParticles(final RegisterParticleProvidersEvent event)
    {
        SSParticles.registerParticles();
    }
}