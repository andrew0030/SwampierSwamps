package andrews.swampier_swamps;

import andrews.swampier_swamps.config.SSConfig;
import andrews.swampier_swamps.entities.Dragonfly;
import andrews.swampier_swamps.events.CreativeTabEvents;
import andrews.swampier_swamps.registry.*;
import andrews.swampier_swamps.util.PotionRecipeChanger;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class SwampierSwamps implements ModInitializer
{
    public static SSConfig SS_CONFIG;

    @Override
    public void onInitialize()
    {
        AutoConfig.register(SSConfig.class, JanksonConfigSerializer::new);
        SS_CONFIG = AutoConfig.getConfigHolder(SSConfig.class).getConfig();

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

        PotionRecipeChanger.performChanges();
        SSFlammables.registerFlammables();
        CreativeTabEvents.init();
//        No Server Messages, maybe I will need this in the future!
//        SSNetwork.registerNetworkMessages();
    }
}