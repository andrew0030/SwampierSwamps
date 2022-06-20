package andrews.swampier_swamps.events;

import andrews.swampier_swamps.util.Reference;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class ToolTipsHelper
{
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event)
    {
        // Retrieve the ItemStack and Item
        ItemStack itemStack = event.getItemStack();

        // If item stack empty do nothing
        if (event.getItemStack().isEmpty())
            return;

        // Tooltip - Tags
        if (itemStack.getTags().toList().size() > 0)
        {
            Component tagsTooltip = Component.literal("Tags:").setStyle(Style.EMPTY.applyFormat(ChatFormatting.DARK_GRAY));

            Set<Component> tags = new HashSet<>();

            for (ResourceLocation tag : itemStack.getTags().map(TagKey::location).toList())
            {
                tags.add(Component.literal("    " + tag).withStyle(ChatFormatting.DARK_GRAY));
            }

            event.getToolTip().add(tagsTooltip);
            for (Component tooltip : tags)
            {
                event.getToolTip().add(tooltip);
            }
        }
    }
}