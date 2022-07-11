package andrews.swampier_swamps.config;

import andrews.swampier_swamps.config.configs.SSClientConfig;
import andrews.swampier_swamps.config.configs.SSCommonConfig;
import andrews.swampier_swamps.config.util.ConfigHelper;
import andrews.swampier_swamps.util.Reference;
import net.minecraftforge.fml.config.ModConfig;

public class SSConfigs
{
    public static SSClientConfig clientConfig = null;
    public static SSCommonConfig commonConfig = null;

    public static void registerConfigs()
    {
        clientConfig = ConfigHelper.register(ModConfig.Type.CLIENT, SSClientConfig::new, createConfigName("client"));
        commonConfig = ConfigHelper.register(ModConfig.Type.COMMON, SSCommonConfig::new, createConfigName("common"));
    }

    /**
     * Helper method to make registering Config names easier
     */
    private static String createConfigName(String name)
    {
        return Reference.MODID + "-" + name + ".toml";
    }
}