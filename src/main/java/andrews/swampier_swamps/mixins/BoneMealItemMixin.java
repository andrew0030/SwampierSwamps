package andrews.swampier_swamps.mixins;

import andrews.swampier_swamps.objects.blocks.CattailBlock;
import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Random;

@Mixin(BoneMealItem.class)
public class BoneMealItemMixin
{
    @Unique private static Level level;
    @Unique private static BlockPos pos;

    // I would use modifyArgs instead but for some reason its broken
    @ModifyVariable(method = "growWaterPlant", at = @At("STORE"), ordinal = 1)
    private static BlockPos inject(BlockPos newPos, ItemStack stack, Level originalLevel, BlockPos originalPos, Direction direction)
    {
        level = originalLevel;
        pos = newPos;
        return pos;
    }

    // I would use modifyArgs instead but for some reason its broken
    @ModifyArg(method = "growWaterPlant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"), index = 1)
    private static BlockState setBlock(BlockState state)
    {
        BlockState cattailState = SSBlocks.CATTAIL.defaultBlockState();
        Random rand = new Random();

        if(level.getBiomeManager().getBiome(pos).is(Biomes.SWAMP) && rand.nextInt(3) == 0)
            return CattailBlock.isValidCattailPosition(level, pos) ? cattailState : state;
        return state;
    }
}