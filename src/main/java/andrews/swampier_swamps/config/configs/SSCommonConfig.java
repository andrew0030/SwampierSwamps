package andrews.swampier_swamps.config.configs;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "Common")
public class SSCommonConfig implements ConfigData
{
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 8)
    @Comment(value = """
                        This can be used to adjust how far away from Water, Fertile Farmland\r
                        can turn into Moist Fertile Farmland. Vanilla Farmland uses 4 as its value.""")
    public int waterRange = 3;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @Comment(value = """
                        The multiplier used to speed up Crop growth.""")
    public int growthMultiplier = 6;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 10)
    @Comment(value = """
                        This can be used to alter how big the Swamp Gas explosions are.""")
    public int explosionStrength = 6;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
                        Whether the Swamp Gas Cloud will give Living Entities negative effects,\r
                        when they walk through it (for example Effects like Poison).""")
    public boolean givesNegativeEffects = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.Gui.RequiresRestart
    @Comment(value = """
                        This can be used to prevent Swamp Gas from being used as Furnace Fuel.""")
    public boolean isFurnaceFuel = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.Gui.RequiresRestart
    @ConfigEntry.BoundedDiscrete(min = 10, max = 60000)
    @Comment(value = """
                        How long the Swamp Gas fuels a Furnace. (Measured in Ticks)""")
    public int burnTime = 4000;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
                        This can be used to prevent Frogs from becoming "killers" when named Swallow Me Waldo.""")
    public boolean allowWaldo = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 100)
    @Comment(value = """
                        This value is a Damage multiplier for the "Swallow Me Waldo" Frog, meaning\r
                        the value used will be multiplied with the Waldo Frogs base damage. (10 by Default)\r
                        This can get out of hand quickly, so be careful with it. Unless you want a\r
                        Frog that can swallow a Wither.""")
    public int waldoDamageModifier = 2;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.Gui.RequiresRestart
    @Comment(value = """
                        This can be used to disable all changes done to Brewing by Swampier Swamps, in case\r
                        of compatibility issues, or any other reason.\r
                        The changes are:\r
                        * Altered Leaping Potion Recipe to use Frog Legs instead of Rabbit Paws\r
                        + Added Recipe for Luck Potions, crafted with Rabbit Paws""")
    public boolean alterRecipes = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 0, max = 2)
    @Comment(value = """
                        Used to determine whether Bald Cypress Trees can grow from planting Oak Saplings\r
                        in a 2x2 pattern. (Like Big Jungle Trees)
                        Values:\r
                        0: Disables Bald Cypress growth from Oak Saplings completely\r
                        1: Bald Cypresses only grow from Oak Saplings, in Biomes specified in #can_bald_cypress_grow_in\r
                        2: Enables Bald Cypress growth from Oak Saplings, in all biomes""")
    public int growBaldCypressFromSaplings = 2;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
                        This can be used to disable Lily Pad sinking completely.\r
                        Values:\r
                        true: Lily Pads will sink when a big Entity stands on them\r
                        false: Lily Pads wont sink when any Entity stands on them""")
    public boolean doLilyPadsSink = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
                        This is used to determine whether Lily Pads break once they reach their final sink stage.\r
                        Values:\r
                        true: Lily Pads will break\r
                        false: Lily Pads wont break, and instead become collision-less (like dripleafs)""")
    public boolean doLilyPadsBreak = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 0, max = 2)
    @Comment(value = """
                        Whether Lily Pads should grow. To be specific, this makes Small Lily Pads grow into\r
                        Lily Pads, which grow into Large Lily Pads. And lastly Large Lily Pads create Small\r
                        ones around themselves.\r
                        Values:\r
                        0: Disables Lily Pad growth completely\r
                        1: Lily Pads only grow in Biomes specified in #can_lily_pad_grow_in\r
                        2: Enables Lily Pad growth in all biomes""")
    public int shouldLilyPadsGrow = 1;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
    @Comment(value = """
                        These values control how long it takes for the Lily Pad to advance/reset its sink stage.\r
                        This is measured in ticks.""")
    public int lilyPadSinkTimeStage1 = 5;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
    public int lilyPadSinkTimeStage2 = 5;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
    public int lilyPadSinkTimeStage3 = 10;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 200)
    public int lilyPadResetTime = 20;
}