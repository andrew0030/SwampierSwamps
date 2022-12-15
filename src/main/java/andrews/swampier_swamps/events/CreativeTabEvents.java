package andrews.swampier_swamps.events;

import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSItems;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.world.item.CreativeModeTabs;

public class CreativeTabEvents
{
    public static void init()
    {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries ->
        {
            entries.accept(SSBlocks.MUD_STAIRS);
            entries.accept(SSBlocks.MUD_SLAB);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries ->
        {
            entries.accept(SSBlocks.DECAYING_KELP);
            entries.accept(SSBlocks.FERTILE_FARMLAND);
            entries.accept(SSBlocks.WHITE_FROG_LIGHT);
            entries.accept(SSBlocks.MAGENTA_FROG_LIGHT);
            entries.accept(SSBlocks.LIGHT_BLUE_FROG_LIGHT);
            entries.accept(SSBlocks.YELLOW_FROG_LIGHT);
            entries.accept(SSBlocks.LIME_FROG_LIGHT);
            entries.accept(SSBlocks.PINK_FROG_LIGHT);
            entries.accept(SSBlocks.GRAY_FROG_LIGHT);
            entries.accept(SSBlocks.LIGHT_GRAY_FROG_LIGHT);
            entries.accept(SSBlocks.CYAN_FROG_LIGHT);
            entries.accept(SSBlocks.BLUE_FROG_LIGHT);
            entries.accept(SSBlocks.BROWN_FROG_LIGHT);
            entries.accept(SSBlocks.RED_FROG_LIGHT);
            entries.accept(SSBlocks.BLACK_FROG_LIGHT);
            entries.accept(SSBlocks.SWAMP_VINE);
            entries.accept(SSBlocks.CATTAIL);
            entries.accept(SSBlocks.BIG_LILY_PAD);
            entries.accept(SSBlocks.SMALL_LILY_PAD);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries ->
        {
            entries.accept(SSBlocks.GAS_LAMP);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries ->
        {
            entries.accept(SSBlocks.GAS_LAMP);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries ->
        {
            entries.accept(SSItems.FROG_LEG);
            entries.accept(SSItems.COOKED_FROG_LEG);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries ->
        {
            entries.accept(SSItems.GAS_BOTTLE);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries ->
        {
            entries.accept(SSItems.DRAGONFLY_SPAWN_EGG);
        });
    }
}