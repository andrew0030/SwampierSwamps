package andrews.swampier_swamps.util;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.registry.SSItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.registries.ForgeRegistries;

public class PotionRecipeChanger
{
    public static void performChanges()
    {
        // We check the config, if the value we get is true we make our changes.
        if (SSConfigs.commonConfig.alterRecipes.get())
        {
            PotionRecipeChanger.alterLeapingPotionRecipe();
            PotionRecipeChanger.registerRecipes();
        }
    }

    private static void alterLeapingPotionRecipe()
    {
        for(int i = 0; i < PotionBrewing.POTION_MIXES.size(); i++)
        {
            PotionBrewing.Mix<Potion> mix = PotionBrewing.POTION_MIXES.get(i);
            if (mix.from.get() == Potions.AWKWARD && mix.ingredient.getItems()[0].is(Items.RABBIT_FOOT) && mix.to.get() == Potions.LEAPING)
                PotionBrewing.POTION_MIXES.set(i, new PotionBrewing.Mix<>(ForgeRegistries.POTIONS, Potions.AWKWARD, Ingredient.of(SSItems.FROG_LEG.get()), Potions.LEAPING));
        }
    }

    private static void registerRecipes()
    {
        BrewingRecipeRegistry.addRecipe(new SSBrewingRecipe(Potions.AWKWARD, Items.RABBIT_FOOT, Potions.LUCK));
    }

    // A small helper record to make registering Potions easier
    private record SSBrewingRecipe(Potion input, Item ingredient, Potion output) implements IBrewingRecipe
    {
        @Override
        public boolean isInput(ItemStack input)
        {
            return PotionUtils.getPotion(input) == this.input;
        }

        @Override
        public boolean isIngredient(ItemStack ingredient)
        {
            return ingredient.getItem() == this.ingredient;
        }

        @Override
        public ItemStack getOutput(ItemStack input, ItemStack ingredient)
        {
            if (!this.isInput(input) || !this.isIngredient(ingredient))
            {
                return ItemStack.EMPTY;
            }

            ItemStack itemStack = new ItemStack(input.getItem());
            PotionUtils.setPotion(itemStack, this.output);
            return itemStack;
        }
    }
}