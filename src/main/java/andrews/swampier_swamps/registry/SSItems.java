package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.objects.items.GasBottleItem;
import andrews.swampier_swamps.util.Reference;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;

public class SSItems
{
    public static final Item FROG_LEG	            = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, "frog_leg"), new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build())));
    public static final Item COOKED_FROG_LEG	    = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, "cooked_frog_leg"), new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build())));
    public static final Item GAS_BOTTLE	            = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, "gas_bottle"), new GasBottleItem(new Item.Properties().stacksTo(1).craftRemainder(Items.GLASS_BOTTLE)));
    // Spawn Eggs
    public static final Item DRAGONFLY_SPAWN_EGG    = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Reference.MODID, "dragonfly_spawn_egg"), new SpawnEggItem(SSEntities.DRAGONFLY, 0x2b3f8a, 0x94de2c, new Item.Properties()));

    public static void init()
    {
        registerFuels();
        HoeItem.TILLABLES.put(SSBlocks.DECAYING_KELP, Pair.of(HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(SSBlocks.FERTILE_FARMLAND.defaultBlockState())));
    }

    private static void registerFuels()
    {
        if(SwampierSwamps.SS_CONFIG.SSCommonConfig.isFurnaceFuel)
        {
            FuelRegistry registry = FuelRegistry.INSTANCE;
            registry.add(GAS_BOTTLE, SwampierSwamps.SS_CONFIG.SSCommonConfig.burnTime);
        }
    }
}