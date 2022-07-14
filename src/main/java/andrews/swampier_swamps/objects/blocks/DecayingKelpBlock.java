package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.registry.SSParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class DecayingKelpBlock extends Block
{
    public DecayingKelpBlock(Properties properties)
    {
        super(properties);
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