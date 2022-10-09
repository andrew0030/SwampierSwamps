package andrews.swampier_swamps.objects.items;

import net.minecraft.world.item.Item;

public class GasBottleItem extends Item
{

    public GasBottleItem(Properties properties)
    {
        super(properties);
    }

//    ##################
//     Moved to SSItems
//     BurnTime is handled by FAPI and has remaining item is in Item Properties
//    ##################
//
//    @Override
//    public boolean hasCraftingRemainingItem(ItemStack stack)
//    {
//        return true;
//    }
//
//    @Override
//    public ItemStack getCraftingRemainingItem(ItemStack itemStack)
//    {
//        return new ItemStack(Items.GLASS_BOTTLE);
//    }
//
//    @Override
//    public int getBurnTime(ItemStack itemStack, @Nullable RecipeType<?> recipeType)
//    {
//        return SSConfig.commonConfig.isFurnaceFuel.get() ? SSConfig.commonConfig.burnTime.get() : -1;
//    }
}