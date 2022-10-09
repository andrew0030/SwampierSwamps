package andrews.swampier_swamps.config.configs;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "Client")
public class SSClientConfig implements ConfigData
{
    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = """
                        Toggles whether Dragonflies should be rendered with size variations. This\r
                        setting is purely cosmetic, and does not affect the Hit Boxes.""")
    public boolean randomizeDragonflySizes = true;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @ConfigEntry.BoundedDiscrete(min = 2, max = 32)
    @Comment(value = """
                        This value is used to Randomize the Dragonfly sizes, smaller values result\r
                        in smaller difference in between the Dragonfly sizes.""")
    public int dragonflySizeModifier = 10;
}