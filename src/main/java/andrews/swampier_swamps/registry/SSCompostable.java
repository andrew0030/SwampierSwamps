package andrews.swampier_swamps.registry;

import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;

public class SSCompostable
{
    public static void registerCompostable()
    {
        CompostingChanceRegistry.INSTANCE.add(SSBlocks.SWAMP_VINE.asItem(), 0.5F);
        CompostingChanceRegistry.INSTANCE.add(SSBlocks.CATTAIL.asItem(), 0.5F);
        CompostingChanceRegistry.INSTANCE.add(SSBlocks.BIG_LILY_PAD.asItem(), 0.85F);
        CompostingChanceRegistry.INSTANCE.add(SSBlocks.SMALL_LILY_PAD.asItem(), 0.3F);
        CompostingChanceRegistry.INSTANCE.add(SSBlocks.DECAYING_KELP.asItem(), 0.5F);
    }
}