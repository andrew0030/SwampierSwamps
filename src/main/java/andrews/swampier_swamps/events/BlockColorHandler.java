package andrews.swampier_swamps.events;

import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BlockColorHandler
{
    @SubscribeEvent
    public static void registerBlockColor(ColorHandlerEvent.Block event)
    {
        BlockColors blockcolors = event.getBlockColors();
        blockcolors.register((state, level, pos, tintIndex) -> isNotNull(level, pos) ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), SSBlocks.SWAMP_VINE.get());
        blockcolors.register((state, level, pos, tintIndex) -> isNotNull(level, pos) ? 2129968 : 7455580, SSBlocks.SINKING_LILY_PAD.get());
    }

    @SubscribeEvent
    public static void registerBlockColor(ColorHandlerEvent.Item event)
    {
        BlockColors blockcolors = event.getBlockColors();
        ItemColors itemcolors = event.getItemColors();

        itemcolors.register((stack, tintIndex) ->
        {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return blockcolors.getColor(blockstate, null, null, tintIndex);
        }, SSBlocks.SWAMP_VINE.get());
    }

    private static boolean isNotNull(BlockAndTintGetter level, BlockPos pos)
    {
        return level != null && pos != null;
    }
}