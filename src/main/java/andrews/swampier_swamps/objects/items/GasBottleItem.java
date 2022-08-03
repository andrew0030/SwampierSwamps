package andrews.swampier_swamps.objects.items;

import andrews.swampier_swamps.config.SSConfigs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;

public class GasBottleItem extends Item
{

    public GasBottleItem(Properties properties)
    {
        super(properties);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack)
    {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack itemStack)
    {
        return new ItemStack(Items.GLASS_BOTTLE);
    }

    @Override
    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType)
    {
        return SSConfigs.commonConfig.isFurnaceFuel.get() ? SSConfigs.commonConfig.burnTime.get() : -1;
    }
}