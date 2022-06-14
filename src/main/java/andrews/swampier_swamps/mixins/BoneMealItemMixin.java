package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin
{
    @Unique private static Level level;
    @Unique private static BlockPos pos;

    @Inject(method = "growWaterPlant", at = @At("HEAD"))
    private static void growWaterPlant(ItemStack stack, Level bonemealLevel, BlockPos bonemealPos, Direction direction, CallbackInfoReturnable<Boolean> cir)
    {
        level = bonemealLevel;
        pos = bonemealPos;
    }

    @ModifyVariable(method = "growWaterPlant", at = @At("STORE"), ordinal = 0)
    private static BlockState inject(BlockState blockstate)
    {
        Random rand = new Random();
        return level.getBiomeManager().getBiome(pos).is(Biomes.SWAMP) ? (rand.nextInt(3) == 0 ? SSBlocks.CATTAIL.get().defaultBlockState() : blockstate) : blockstate;
    }
}