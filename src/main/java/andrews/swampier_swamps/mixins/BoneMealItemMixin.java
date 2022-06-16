package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.objects.blocks.CattailBlock;
import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import javax.annotation.Nullable;
import java.util.Random;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin
{
    @Unique private static Level level;
    @Unique private static BlockPos pos;

    @ModifyVariable(method = "growWaterPlant", at = @At("STORE"), ordinal = 0)
    private static BlockState inject(BlockState blockstate, ItemStack stack, Level level, BlockPos pos, @Nullable Direction direction)
    {
        Random rand = new Random();
        return level.getBiomeManager().getBiome(pos).is(Biomes.SWAMP) ? (rand.nextInt(3) == 0 ? SSBlocks.CATTAIL.get().defaultBlockState() : blockstate) : blockstate;
    }

    // I would use modifyArgs instead but for some reason its broken when used here???
    @ModifyVariable(method = "growWaterPlant", at = @At("STORE"), ordinal = 1)
    private static BlockPos inject(BlockPos newPos, ItemStack stack, Level originalLevel, BlockPos originalPos, @Nullable Direction direction)
    {
        level = originalLevel;
        return pos = newPos;
    }

    @ModifyArg(method = "growWaterPlant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), index = 1)
    private static BlockState setBlock(BlockState state)
    {
        return CattailBlock.isValidCattailPosition(level, pos) ? state : state.is(SSBlocks.CATTAIL.get()) ? Blocks.SEAGRASS.defaultBlockState() : state;
    }
}