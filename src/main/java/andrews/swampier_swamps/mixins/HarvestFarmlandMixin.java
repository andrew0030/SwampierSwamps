package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.objects.blocks.FertileFarmlandBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ai.behavior.HarvestFarmland;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HarvestFarmland.class)
public class HarvestFarmlandMixin
{
    @Shadow private BlockPos aboveFarmlandPos;

    @Inject(method = "validPos", at = @At(value = "RETURN"), cancellable = true)
    public void injectValidPos(BlockPos pos, ServerLevel level, CallbackInfoReturnable<Boolean> cir)
    {
        Block farmlandBlock = level.getBlockState(pos.below()).getBlock();
        if(farmlandBlock instanceof FertileFarmlandBlock)
        {
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof CropBlock cropBlock)
                cir.setReturnValue(cropBlock.isMaxAge(state) || state.isAir());
        }
    }

    @Inject(method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;J)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/npc/Villager;hasFarmSeeds()Z"))
    public void injectTick(ServerLevel level, Villager owner, long gameTime, CallbackInfo ci)
    {
        BlockState cropState = level.getBlockState(aboveFarmlandPos);
        Block farmlandBlock = level.getBlockState(aboveFarmlandPos.below()).getBlock();

        if (cropState.isAir() && farmlandBlock instanceof FertileFarmlandBlock && owner.hasFarmSeeds())
        {
            SimpleContainer simplecontainer = owner.getInventory();

            for(int i = 0; i < simplecontainer.getContainerSize(); ++i)
            {
                ItemStack itemstack = simplecontainer.getItem(i);
                boolean plantedSeed = false;
                if (!itemstack.isEmpty())
                {
                    if (itemstack.is(Items.WHEAT_SEEDS))
                    {
                        BlockState wheatState = Blocks.WHEAT.defaultBlockState();
                        level.setBlockAndUpdate(aboveFarmlandPos, wheatState);
                        level.gameEvent(GameEvent.BLOCK_PLACE, aboveFarmlandPos, GameEvent.Context.of(owner, wheatState));
                        plantedSeed = true;
                    }
                    else if (itemstack.is(Items.POTATO))
                    {
                        BlockState potatoState = Blocks.POTATOES.defaultBlockState();
                        level.setBlockAndUpdate(aboveFarmlandPos, potatoState);
                        level.gameEvent(GameEvent.BLOCK_PLACE, aboveFarmlandPos, GameEvent.Context.of(owner, potatoState));
                        plantedSeed = true;
                    }
                    else if (itemstack.is(Items.CARROT))
                    {
                        BlockState carrotState = Blocks.CARROTS.defaultBlockState();
                        level.setBlockAndUpdate(aboveFarmlandPos, carrotState);
                        level.gameEvent(GameEvent.BLOCK_PLACE, aboveFarmlandPos, GameEvent.Context.of(owner, carrotState));
                        plantedSeed = true;
                    }
                    else if (itemstack.is(Items.BEETROOT_SEEDS))
                    {
                        BlockState beetrootState = Blocks.BEETROOTS.defaultBlockState();
                        level.setBlockAndUpdate(aboveFarmlandPos, beetrootState);
                        level.gameEvent(GameEvent.BLOCK_PLACE, aboveFarmlandPos, GameEvent.Context.of(owner, beetrootState));
                        plantedSeed = true;
                    }
                }

                if (plantedSeed)
                {
                    level.playSound(null, aboveFarmlandPos.getX(), aboveFarmlandPos.getY(), aboveFarmlandPos.getZ(), SoundEvents.CROP_PLANTED, SoundSource.BLOCKS, 1.0F, 1.0F);
                    itemstack.shrink(1);
                    if (itemstack.isEmpty())
                        simplecontainer.setItem(i, ItemStack.EMPTY);
                    break;
                }
            }
        }
    }
}