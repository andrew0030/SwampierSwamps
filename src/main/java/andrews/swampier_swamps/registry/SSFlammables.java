package andrews.swampier_swamps.registry;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class SSFlammables
{
    public static void registerFlammables()
    {
        FireBlock fireblock = (FireBlock) Blocks.FIRE;
        fireblock.setFlammable(SSBlocks.SWAMP_VINE, 15, 100);
    }
}