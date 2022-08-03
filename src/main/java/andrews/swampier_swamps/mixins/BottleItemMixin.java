package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.entities.SwampGas;
import andrews.swampier_swamps.registry.SSItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BottleItem.class)
public abstract class BottleItemMixin
{
    @Inject(method = "use", at = @At(value = "HEAD"), cancellable = true)
    public void injectUse(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir)
    {
        List<SwampGas> list = level.getEntitiesOfClass(SwampGas.class, player.getBoundingBox().inflate(2.0D), (entity) -> entity != null && entity.isAlive() && entity.isCould());
        if (!list.isEmpty())
        {
            list.get(0).tickCount += 50; // We increase the age of the SwampGas Entity, effectively reducing its life span
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0F, 1.0F);
            player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
            ItemStack filledBottle = ItemUtils.createFilledResult(player.getItemInHand(hand), player, new ItemStack(SSItems.GAS_BOTTLE.get()), false);
            cir.setReturnValue(InteractionResultHolder.sidedSuccess(filledBottle, level.isClientSide()));
        }
    }
}