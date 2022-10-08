package andrews.swampier_swamps;

import andrews.swampier_swamps.registry.*;
import net.fabricmc.api.ModInitializer;

public class SwampierSwamps implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        SSFrogVariants.init();
        SSItems.init();
        SSBlocks.init();
        SSFeatures.init();
        SSEntities.init();
        SSParticles.init();
        SSBlockEntities.init();
        SSTrunkPlacers.init();
        SSTreeDecorators.init();
        SSPlacements.init();

//        TTCNetwork.registerNetworkMessages();
    }








//    public SwampierSwamps()
//    {
//        DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
//        {
//            modEventBus.addListener(this::setupClient);
//            modEventBus.addListener(this::setupParticles);
//        });
//        modEventBus.addListener(this::setupCommon);
//
//        SSConfigs.registerConfigs();
//    }
//
//    void setupCommon(final FMLCommonSetupEvent event)
//    {
//        event.enqueueWork(() ->
//        {
//            PotionRecipeChanger.performChanges();
//            SSEntities.registerSpawnPlacements();
//            SSFlammables.registerFlammables();
//        });
//        //Thread Safe Stuff Bellow
//        SSNetwork.setupMessages();
//    }
//
//    void setupClient(final FMLClientSetupEvent event)
//    {
//        event.enqueueWork(() -> {});
//        // Thread Safe Stuff Bellow
//    }
}