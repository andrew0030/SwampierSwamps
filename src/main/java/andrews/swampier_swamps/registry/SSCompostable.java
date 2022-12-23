package andrews.swampier_swamps.registry;

import net.minecraft.world.level.block.ComposterBlock;

public class SSCompostable
{
    public static void registerCompostable()
    {
        ComposterBlock.COMPOSTABLES.put(SSBlocks.SWAMP_VINE.asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(SSBlocks.CATTAIL.asItem(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(SSBlocks.BIG_LILY_PAD.asItem(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(SSBlocks.SMALL_LILY_PAD.asItem(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(SSBlocks.DECAYING_KELP.asItem(), 0.5F);
    }
}