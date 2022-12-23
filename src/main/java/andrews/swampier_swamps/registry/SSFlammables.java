package andrews.swampier_swamps.registry;

import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;

public class SSFlammables
{
    public static void registerFlammables()
    {
        FlammableBlockRegistry.getDefaultInstance().add(SSBlocks.SWAMP_VINE, 15, 100);
    }
}