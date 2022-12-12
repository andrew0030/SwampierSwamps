package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.objects.items.GasBottleItem;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> FROG_LEG	            = ITEMS.register("frog_leg", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(2).saturationMod(0.3F).meat().build())));
    public static final RegistryObject<Item> COOKED_FROG_LEG	    = ITEMS.register("cooked_frog_leg", () -> new Item(new Item.Properties().food((new FoodProperties.Builder()).nutrition(6).saturationMod(0.6F).meat().build())));
    public static final RegistryObject<Item> GAS_BOTTLE	            = ITEMS.register("gas_bottle", () -> new GasBottleItem(new Item.Properties().stacksTo(1)));
    // Spawn Eggs
    public static final RegistryObject<Item> DRAGONFLY_SPAWN_EGG    = ITEMS.register("dragonfly_spawn_egg", () -> new ForgeSpawnEggItem(SSEntities.DRAGONFLY, 0x2b3f8a, 0x94de2c, new Item.Properties()));
}