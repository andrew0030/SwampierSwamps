package andrews.swampier_swamps.util;

import andrews.swampier_swamps.registry.SSItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionRecipeChanger
{
    public static void alterLeapingPotionRecipe()
    {
        for(int i = 0; i < PotionBrewing.POTION_MIXES.size(); i++)
        {
            PotionBrewing.Mix<Potion> mix = PotionBrewing.POTION_MIXES.get(i);
            if (mix.from.get() == Potions.AWKWARD && mix.ingredient.getItems()[0].is(Items.RABBIT_FOOT) && mix.to.get() == Potions.LEAPING)
            {
                PotionBrewing.POTION_MIXES.set(i, new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, Potions.AWKWARD, Ingredient.of(SSItems.FROG_LEG.get()), Potions.LEAPING));
            }
        }
    }

    public static void registerRecipes()
    {
        BrewingRecipeRegistry.addRecipe(Ingredient.of(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD)), Ingredient.of(Items.RABBIT_FOOT), PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.LUCK));
    }
}