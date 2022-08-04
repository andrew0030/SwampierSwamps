package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.FarmlandWaterManager;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

public class FertileFarmlandBlock extends FarmBlock
{
    public FertileFarmlandBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
        if (!level.isClientSide && ForgeHooks.onFarmlandTrample(level, pos, SSBlocks.DECAYING_KELP.get().defaultBlockState(), fallDistance, entity))
        { // Forge: Move logic to Entity#canTrample
            turnToDecayingKelp(state, level, pos);
        }
        entity.causeFallDamage(fallDistance, 1.0F, DamageSource.FALL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? SSBlocks.DECAYING_KELP.get().defaultBlockState() : this.defaultBlockState();
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        if (!state.canSurvive(level, pos))
            turnToDecayingKelp(state, level, pos);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        int moisture = state.getValue(MOISTURE);
        if (!isNearWater(level, pos) && !level.isRainingAt(pos.above()))
        {
            if (moisture > 0)
            {
                level.setBlock(pos, state.setValue(MOISTURE, moisture - 1), 2);
            }
            else if (!isUnderCrops(level, pos))
            {
                turnToDecayingKelp(state, level, pos);
            }
        }
        else if (moisture < 7)
        {
            level.setBlock(pos, state.setValue(MOISTURE, 7), 2);
        }
    }

    public static void turnToDecayingKelp(BlockState state, Level level, BlockPos pos)
    {
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, SSBlocks.DECAYING_KELP.get().defaultBlockState(), level, pos));
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos)
    {
        int waterRange = SSConfigs.commonConfig.waterRange.get();
        BlockState state = level.getBlockState(pos);
        for(BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-waterRange, 0, -waterRange), pos.offset(waterRange, 1, waterRange)))
        {
            if (state.canBeHydrated(level, pos, level.getFluidState(blockpos), blockpos))
                return true;
        }
        return FarmlandWaterManager.hasBlockWaterTicket(level, pos);
    }

    private static boolean isUnderCrops(BlockGetter level, BlockPos pos)
    {
        BlockState plant = level.getBlockState(pos.above());
        BlockState state = level.getBlockState(pos);
        return plant.getBlock() instanceof IPlantable && state.canSustainPlant(level, pos, Direction.UP, (IPlantable)plant.getBlock());
    }
}