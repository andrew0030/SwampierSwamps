package andrews.swampier_swamps.block_entities;

import andrews.swampier_swamps.entities.SwampGas;
import andrews.swampier_swamps.registry.SSBlockEntities;
import andrews.swampier_swamps.registry.SSEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class DecayingKelpBlockEntity extends BlockEntity
{
    private int ticksSinceLastGas = 0;

    public DecayingKelpBlockEntity(BlockPos pos, BlockState state)
    {
        super(SSBlockEntities.DECAYING_KELP, pos, state);
        Random rand = new Random();
        ticksSinceLastGas = rand.nextInt(6000);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DecayingKelpBlockEntity blockEntity)
    {
        if (!level.isClientSide)
        {
            int ticksSinceLastGas = blockEntity.getTicksSinceLastGas();
            if (ticksSinceLastGas >= 6000)
            {
                if(level.getFluidState(pos.above()).is(FluidTags.WATER))
                {
                    SwampGas swampGas = SSEntities.SWAMP_GAS.create(level);
                    swampGas.moveTo(pos.getX() + 0.5D, pos.getY() + 1.2D, pos.getZ() + 0.5D, 0.0F, 0.0F);
                    level.addFreshEntity(swampGas);
                }
                blockEntity.setTicksSinceLastGas(0);
            }
            blockEntity.increaseTicksSinceLastGas();
        }
    }

    public int getTicksSinceLastGas()
    {
        return this.ticksSinceLastGas;
    }

    public void setTicksSinceLastGas(int ticksSinceLastGas)
    {
        this.ticksSinceLastGas = ticksSinceLastGas;
    }

    public void increaseTicksSinceLastGas()
    {
        this.ticksSinceLastGas++;
    }
}