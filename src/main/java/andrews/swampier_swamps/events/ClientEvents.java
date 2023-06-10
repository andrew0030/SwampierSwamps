package andrews.swampier_swamps.events;

import andrews.swampier_swamps.entities.model.DragonflyModel;
import andrews.swampier_swamps.entities.renderer.DragonflyRenderer;
import andrews.swampier_swamps.entities.renderer.SwampGasRenderer;
import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSEntities;
import andrews.swampier_swamps.util.Reference;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents
{
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event)
    {
        event.registerLayerDefinition(DragonflyModel.LAYER, DragonflyModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(SSEntities.DRAGONFLY.get(), DragonflyRenderer::new);
        event.registerEntityRenderer(SSEntities.SWAMP_GAS.get(), SwampGasRenderer::new);
    }

    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Block event)
    {
        event.register((state, level, pos, tintIndex) -> isNotNull(level, pos) ? BiomeColors.getAverageFoliageColor(level, pos) : FoliageColor.getDefaultColor(), SSBlocks.SWAMP_VINE.get());
        event.register((state, level, pos, tintIndex) -> isNotNull(level, pos) ? 2129968 : 7455580, SSBlocks.SINKING_LILY_PAD.get(), SSBlocks.BIG_LILY_PAD.get(), SSBlocks.SMALL_LILY_PAD.get());
    }

    @SubscribeEvent
    public static void registerBlockColor(RegisterColorHandlersEvent.Item event)
    {
        BlockColors blockcolors = event.getBlockColors();
        event.register((stack, tintIndex) -> {
            BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().defaultBlockState();
            return blockcolors.getColor(blockstate, null, null, tintIndex);
        }, SSBlocks.SWAMP_VINE.get(), SSBlocks.BIG_LILY_PAD.get(), SSBlocks.SMALL_LILY_PAD.get());
    }

    private static boolean isNotNull(BlockAndTintGetter level, BlockPos pos)
    {
        return level != null && pos != null;
    }
}