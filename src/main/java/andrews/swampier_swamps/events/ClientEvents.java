package andrews.swampier_swamps.events;

import andrews.swampier_swamps.registry.SSBlocks;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;

public class ClientEvents
{
    public static void registerBlockColors()
    {
        // Blocks
        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> isNotNull(level, pos) ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), SSBlocks.SWAMP_VINE);
        ColorProviderRegistry.BLOCK.register((state, level, pos, tintIndex) -> isNotNull(level, pos) ? 2129968 : 7455580, SSBlocks.SINKING_LILY_PAD, SSBlocks.BIG_LILY_PAD, SSBlocks.SMALL_LILY_PAD);

        // Items
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> FoliageColor.getDefaultColor(), SSBlocks.SWAMP_VINE);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> 7455580, SSBlocks.BIG_LILY_PAD, SSBlocks.SMALL_LILY_PAD);
    }

    private static boolean isNotNull(BlockAndTintGetter level, BlockPos pos)
    {
        return level != null && pos != null;
    }
}