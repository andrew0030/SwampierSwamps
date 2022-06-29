package andrews.swampier_swamps.events;

//@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ToolTipsHelper
{
//    @SubscribeEvent
//    public static void onItemTooltip(ItemTooltipEvent event)
//    {
//        // Retrieve the ItemStack and Item
//        ItemStack itemStack = event.getItemStack();
//
//        // If item stack empty do nothing
//        if (event.getItemStack().isEmpty())
//            return;
//
//        // Tooltip - Tags
//        if (itemStack.getTags().toList().size() > 0)
//        {
//            Component tagsTooltip = Component.literal("Item Tags:").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));
//            Component blockTagsTooltip = Component.literal("Block Tags:").setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY));
//
//            Set<Component> tags = new HashSet<>();
//            Set<Component> blockTags = new HashSet<>();
//
//            for (ResourceLocation tag : itemStack.getTags().map(TagKey::location).toList())
//            {
//                tags.add(Component.literal("    " + tag).withStyle(ChatFormatting.DARK_GRAY));
//                if(itemStack.getItem() instanceof BlockItem blockItem)
//                {
//                    for (ResourceLocation blockTag : blockItem.getBlock().defaultBlockState().getTags().map(TagKey::location).toList())
//                    {
//                        blockTags.add(Component.literal("    " + blockTag).withStyle(ChatFormatting.DARK_GRAY));
//                    }
//                }
//            }
//
////            if(Screen.hasShiftDown())
////            {
//                event.getToolTip().add(tagsTooltip);
//                for (Component tooltip : tags)
//                {
//                    event.getToolTip().add(tooltip);
//                }
//                event.getToolTip().add(blockTagsTooltip);
//                for (Component tooltip : blockTags)
//                {
//                    event.getToolTip().add(tooltip);
//                }
////            }
//        }
//    }
}