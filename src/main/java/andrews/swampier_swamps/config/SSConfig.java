package andrews.swampier_swamps.config;

import andrews.swampier_swamps.config.configs.SSClientConfig;
import andrews.swampier_swamps.config.configs.SSCommonConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "swampier_swamps")
public class SSConfig implements ConfigData
{
    @ConfigEntry.Category("common")
    @ConfigEntry.Gui.TransitiveObject
    public SSCommonConfig SSCommonConfig = new SSCommonConfig();

    @ConfigEntry.Category("client")
    @ConfigEntry.Gui.TransitiveObject
    public SSClientConfig SSClientConfig = new SSClientConfig();
}