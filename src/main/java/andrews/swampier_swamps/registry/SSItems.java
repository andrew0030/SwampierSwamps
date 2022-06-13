package andrews.swampier_swamps.registry;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SSItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Reference.MODID);

    public static final RegistryObject<Item> FROG_LEG	        = ITEMS.register("frog_leg", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(Foods.PORKCHOP)));
    public static final RegistryObject<Item> COOKED_FROG_LEG	= ITEMS.register("cooked_frog_leg", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(Foods.PORKCHOP)));
}