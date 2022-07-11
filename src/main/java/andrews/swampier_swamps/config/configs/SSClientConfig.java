package andrews.swampier_swamps.config.configs;

import andrews.swampier_swamps.config.util.ConfigHelper;
import andrews.swampier_swamps.config.util.ConfigHelper.ConfigValueListener;
import net.minecraftforge.common.ForgeConfigSpec;

public class SSClientConfig
{
    public ConfigValueListener<Boolean> randomizeDragonflySizes;

    public SSClientConfig(ForgeConfigSpec.Builder builder, ConfigHelper.Subscriber subscriber)
    {
        builder.comment(" Client Settings for Swampier Swamps")
                .push("Settings");

        builder.push("Dragonflies"); // Dragonflies Start
        randomizeDragonflySizes = subscriber.subscribe(builder
                .comment("""
                        Toggles whether Dragonflies should be rendered with size variations. This\r
                        setting is purely cosmetic, and does not affect the Hit Boxes.""".indent(1)
                ).define("randomizeDragonflySizes", true));
        builder.pop(); // Dragonflies End

        builder.pop();
    }
}
