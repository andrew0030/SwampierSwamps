package andrews.swampier_swamps.util;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.registry.SSItems;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;

public class PotionRecipeChanger
{
    public static void performChanges()
    {
        // We check the config, if the value we get is true we make our changes.
        if (SwampierSwamps.SS_CONFIG.SSCommonConfig.alterRecipes)
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
            if (mix.from == Potions.AWKWARD && mix.ingredient.getItems()[0].is(Items.RABBIT_FOOT) && mix.to == Potions.LEAPING)
                PotionBrewing.POTION_MIXES.set(i, new PotionBrewing.Mix<>(Potions.AWKWARD, Ingredient.of(SSItems.FROG_LEG), Potions.LEAPING));
        }
    }

    private static void registerRecipes()
    {
        PotionBrewing.addMix(Potions.AWKWARD, Items.RABBIT_FOOT, Potions.LUCK);
    }
}