package andrews.swampier_swamps.config.configs;

import andrews.swampier_swamps.config.util.ConfigHelper;
import andrews.swampier_swamps.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

public class SSCommonConfig
{
    // Fertile Farmland
    public ConfigValueListener<Integer> waterRange;
    public ConfigValueListener<Integer> growthMultiplier;
    // Swamp Gas
    public ConfigValueListener<Boolean> isFurnaceFuel;
    public ConfigValueListener<Integer> burnTime;
    // Frog
    public ConfigValueListener<Boolean> allowWaldo;
    public ConfigValueListener<Double> waldoDamageModifier;
    // Brewing
    public ConfigValueListener<Boolean> alterRecipes;
    // Tree Stuff
    public ConfigValueListener<Integer> growBaldCypressFromSaplings;
    // Lily Pad
    public ConfigValueListener<Integer> shouldLilyPadsGrow;
    public ConfigValueListener<Integer> lilyPadSinkTimeStage1;
    public ConfigValueListener<Integer> lilyPadSinkTimeStage2;
    public ConfigValueListener<Integer> lilyPadSinkTimeStage3;
    public ConfigValueListener<Integer> lilyPadResetTime;

    public SSCommonConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
    {
        builder.comment(" Common Settings for Swampier Swamps")
               .push("Settings");

        builder.push("Fertile Farmland"); // Fertile Farmland Start
        waterRange = subscriber.subscribe(builder
                .comment("""
                        This can be used to adjust how far away from Water, Fertile Farmland\r
                        can turn into Moist Fertile Farmland. Vanilla Farmland uses 4 as its value.""".indent(1)
                ).defineInRange("waterRange", 3, 1, 8));
        growthMultiplier = subscriber.subscribe(builder
                .comment("""
                        The multiplier used to speed up Crop growth.""".indent(1)
                ).defineInRange("growthMultiplier", 6, 1, 10));
        builder.pop(); // Fertile Farmland End

        builder.push("Swamp Gas"); // Swamp Gas Start
        isFurnaceFuel = subscriber.subscribe(builder
                .comment("""
                        This can be used to prevent Swamp Gas from being used as Furnace Fuel.""".indent(1)
                ).define("isFurnaceFuel", true));
        burnTime = subscriber.subscribe(builder
                .comment("""
                        How long the Swamp Gas fuels a Furnace. (Measured in Ticks)""".indent(1)
                ).defineInRange("burnTime", 4000, 10, 60000));
        builder.pop(); // Swamp Gas End

        builder.push("Frog"); // Frog Start
        allowWaldo = subscriber.subscribe(builder
                .comment("""
                        This can be used to prevent Frogs from becoming "killers" when named Swallow Me Waldo""".indent(1)
                ).define("allowWaldo", true));
        waldoDamageModifier = subscriber.subscribe(builder
                .comment("""
                        This value is a Damage multiplier for the "Swallow Me Waldo" Frog, meaning \r
                        the value used will be multiplied with the Waldo Frogs base damage. (10 by Default)\r
                        This can get out of hand quickly, so be careful with it. Unless you want a\r
                        Frog that can swallow a Wither.""".indent(1)
                ).defineInRange("waldoDamageModifier", 2.0D, 1.0D, 100.0D));
        builder.pop(); // Frog End

        builder.push("Brewing"); // Brewing Start
        alterRecipes = subscriber.subscribe(builder
                .comment("""
                        This can be used to disable all changes done to Brewing by Swampier Swamps, in case\r
                        of compatibility issues, or any other reason.\r
                        The changes are:\r
                        * Altered Leaping Potion Recipe to use Frog Legs instead of Rabbit Paws\r
                        + Added Recipe for Luck Potions, crafted with Rabbit Paws""".indent(1)
                ).define("alterRecipes", true));
        builder.pop(); // Brewing End

        builder.push("Tree Stuff"); // Tree Stuff Start
        growBaldCypressFromSaplings = subscriber.subscribe(builder
                .comment("""
                        Used to determine whether Bald Cypress Trees can grow from planting Oak Saplings\r
                        in a 2x2 pattern. (Like Big Jungle Trees)
                        Values:\r
                        0: Disables Bald Cypress growth from Oak Saplings completely\r
                        1: Bald Cypresses only grow from Oak Saplings, in Biomes specified in #can_bald_cypress_grow_in\r
                        2: Enables Bald Cypress growth from Oak Saplings, in all biomes""".indent(1)
                ).defineInRange("growBaldCypressFromSaplings", 2, 0, 2));
        builder.pop(); // Tree Stuff End

        builder.push("Lily Pad");
        shouldLilyPadsGrow = subscriber.subscribe(builder
                .comment("""
                        Whether Lily Pads should grow. To be specific, this makes Small Lily Pads grow into\r
                        Lily Pads, which grow into Large Lily Pads. And lastly Large Lily Pads create Small\r
                        ones around themselves.\r
                        Values:\r
                        0: Disables Lily Pad growth completely\r
                        1: Lily Pads only grow in Biomes specified in #can_lily_pad_grow_in\r
                        2: Enables Lily Pad growth in all biomes""".indent(1)
                ).defineInRange("shouldLilyPadsGrow", 1, 0, 2));

        lilyPadSinkTimeStage1 = subscriber.subscribe(builder
                .comment("""
                        These values control how long it takes for the Lily Pad to advance/reset its sink stage.\r
                        This is measured in ticks.""".indent(1)
                ).defineInRange("lilyPadSinkTimeStage1", 5, 1, 200));
        lilyPadSinkTimeStage2 = subscriber.subscribe(builder
                .defineInRange("lilyPadSinkTimeStage2", 5, 1, 200));
        lilyPadSinkTimeStage3 = subscriber.subscribe(builder
                .defineInRange("lilyPadSinkTimeStage3", 10, 1, 200));
        lilyPadResetTime = subscriber.subscribe(builder
                .defineInRange("lilyPadResetTime", 20, 1, 200));
        builder.pop(); // Lily Pads End

        builder.pop();
    }
}