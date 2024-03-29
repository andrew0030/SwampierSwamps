package andrews.swampier_swamps.events;

import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSItems;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreativeTabEvents
{
    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey().equals(CreativeModeTabs.BUILDING_BLOCKS))
        {
            event.accept(SSBlocks.MUD_STAIRS);
            event.accept(SSBlocks.MUD_SLAB);
        }
        else if(event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS))
        {
            event.accept(SSBlocks.DECAYING_KELP);
            event.accept(SSBlocks.FERTILE_FARMLAND);
            event.accept(SSBlocks.WHITE_FROG_LIGHT);
            event.accept(SSBlocks.MAGENTA_FROG_LIGHT);
            event.accept(SSBlocks.LIGHT_BLUE_FROG_LIGHT);
            event.accept(SSBlocks.YELLOW_FROG_LIGHT);
            event.accept(SSBlocks.LIME_FROG_LIGHT);
            event.accept(SSBlocks.PINK_FROG_LIGHT);
            event.accept(SSBlocks.GRAY_FROG_LIGHT);
            event.accept(SSBlocks.LIGHT_GRAY_FROG_LIGHT);
            event.accept(SSBlocks.CYAN_FROG_LIGHT);
            event.accept(SSBlocks.BLUE_FROG_LIGHT);
            event.accept(SSBlocks.BROWN_FROG_LIGHT);
            event.accept(SSBlocks.RED_FROG_LIGHT);
            event.accept(SSBlocks.BLACK_FROG_LIGHT);
            event.accept(SSBlocks.SWAMP_VINE);
            event.accept(SSBlocks.CATTAIL);
            event.accept(SSBlocks.BIG_LILY_PAD);
            event.accept(SSBlocks.SMALL_LILY_PAD);
        }
        else if(event.getTabKey().equals(CreativeModeTabs.FUNCTIONAL_BLOCKS))
        {
            event.accept(SSBlocks.GAS_LAMP);
        }
        else if(event.getTabKey().equals(CreativeModeTabs.REDSTONE_BLOCKS))
        {
            event.accept(SSBlocks.GAS_LAMP);
        }
        else if(event.getTabKey().equals(CreativeModeTabs.FOOD_AND_DRINKS))
        {
            event.accept(SSItems.FROG_LEG);
            event.accept(SSItems.COOKED_FROG_LEG);
        }
        else if(event.getTabKey().equals(CreativeModeTabs.INGREDIENTS))
        {
            event.accept(SSItems.GAS_BOTTLE);
        }
        else if(event.getTabKey().equals(CreativeModeTabs.SPAWN_EGGS))
        {
            event.accept(SSItems.DRAGONFLY_SPAWN_EGG);
        }
    }
}