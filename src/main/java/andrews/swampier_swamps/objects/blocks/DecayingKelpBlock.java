package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.entities.SwampGas;
import andrews.swampier_swamps.registry.SSBlocks;
import andrews.swampier_swamps.registry.SSEntities;
import andrews.swampier_swamps.registry.SSParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

public class DecayingKelpBlock extends Block
{
    public DecayingKelpBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
//        if(level.getGameTime() % (long) 10 == 0L)
//        {
//            if(level.getFluidState(pos.above()).is(FluidTags.WATER))
//            {
//                SwampGas swampGas = SSEntities.SWAMP_GAS.get().create(level);
//                swampGas.moveTo(pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, 0.0F, 0.0F);
//
//                level.addFreshEntity(swampGas);
//            }
//        } // TODO probably use a damn Block Entity
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction toolAction, boolean simulate)
    {
        if(context.getLevel().getBlockState(context.getClickedPos().above()).isAir())
            return toolAction == ToolActions.HOE_TILL ? SSBlocks.FERTILE_FARMLAND.get().defaultBlockState() : state;
        return super.getToolModifiedState(state, context, toolAction, simulate);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource rand)
    {
        if(level.getFluidState(pos.above()).is(FluidTags.WATER))
            if (rand.nextInt(3) == 0)
                for(int i = 0; i < 2; i++)
                    level.addParticle(SSParticles.DECAY_BUBBLES.get(), (double)pos.getX() + rand.nextDouble(), (double)pos.getY() + 1.1D, (double)pos.getZ() + rand.nextDouble(), 0.0D, 1.5D, 0.0D);
    }
}