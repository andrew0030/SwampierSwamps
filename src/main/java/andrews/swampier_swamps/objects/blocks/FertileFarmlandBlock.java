package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;

public class FertileFarmlandBlock extends FarmBlock
{
    public FertileFarmlandBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
        if (!level.isClientSide && ForgeHooks.onFarmlandTrample(level, pos, Blocks.DIRT.defaultBlockState(), fallDistance, entity))
        { // Forge: Move logic to Entity#canTrample
            level.setBlockAndUpdate(pos, pushEntitiesUp(state, SSBlocks.DECAYING_KELP.get().defaultBlockState(), level, pos));
        }
        entity.causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
    }
}