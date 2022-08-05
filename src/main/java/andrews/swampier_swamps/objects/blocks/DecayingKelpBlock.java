package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.block_entities.DecayingKelpBlockEntity;
import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class DecayingKelpBlock extends BaseEntityBlock
{
    public DecayingKelpBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
    {
        if(context.getLevel().getBlockState(context.getClickedPos().above()).isAir())
            return toolAction == ToolActions.HOE_TILL ? SSBlocks.FERTILE_FARMLAND.get().defaultBlockState() : null;
        return null;
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand)
    {
        if(level.getFluidState(pos.above()).is(FluidTags.WATER))
            if (rand.nextInt(3) == 0)
                for(int i = 0; i < 2; i++)
                    level.addParticle(SSParticles.DECAY_BUBBLES.get(), (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 1.1D, (double)pos.getZ() + rand.nextDouble(), 0.0D, 1.5D, 0.0D);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new DecayingKelpBlockEntity(pos, state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state)
    {
        return RenderShape.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType)
    {
        return (level1, pos, state1, blockEntity) -> DecayingKelpBlockEntity.tick(level1, pos, state1, (DecayingKelpBlockEntity) blockEntity);
    }
}