package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.objects.items.GasBottleItem;
import andrews.swampier_swamps.util.Reference;
import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;

public class SSItems
{
    public static final Item FROG_LEG	            = Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, "frog_leg"), new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build())));
    public static final Item COOKED_FROG_LEG	    = Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, "cooked_frog_leg"), new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build())));
    public static final Item GAS_BOTTLE	            = Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, "gas_bottle"), new GasBottleItem(new Item.Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1).craftRemainder(Items.GLASS_BOTTLE)));
    // Spawn Eggs
    public static final Item DRAGONFLY_SPAWN_EGG    = Registry.register(Registry.ITEM, new ResourceLocation(Reference.MODID, "dragonfly_spawn_egg"), new SpawnEggItem(SSEntities.DRAGONFLY, 0x2b3f8a, 0x94de2c, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

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