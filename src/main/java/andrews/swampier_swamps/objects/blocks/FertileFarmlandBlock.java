package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.SwampierSwamps;
import andrews.swampier_swamps.registry.SSBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class FertileFarmlandBlock extends FarmBlock
{
    public FertileFarmlandBlock(Properties properties)
    {
        super(properties);
    }

    @Override
    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float fallDistance)
    {
        if (!level.isClientSide && level.random.nextFloat() < fallDistance - 0.5f && entity instanceof LivingEntity && (entity instanceof Player || level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) && entity.getBbWidth() * entity.getBbWidth() * entity.getBbHeight() > 0.512f)
        {
            turnToDecayingKelp(state, level, pos);
        }
        entity.causeFallDamage(fallDistance, 1.0f, DamageSource.FALL);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return !this.defaultBlockState().canSurvive(context.getLevel(), context.getClickedPos()) ? SSBlocks.DECAYING_KELP.defaultBlockState() : this.defaultBlockState();
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
        level.setBlockAndUpdate(pos, pushEntitiesUp(state, SSBlocks.DECAYING_KELP.defaultBlockState(), level, pos));
    }

    private static boolean isNearWater(LevelReader level, BlockPos pos)
    {
        int waterRange = SwampierSwamps.SS_CONFIG.SSCommonConfig.waterRange;
        for (BlockPos blockpos : BlockPos.betweenClosed(pos.offset(-waterRange, 0, -waterRange), pos.offset(waterRange, 1, waterRange)))
        {
            if (level.getFluidState(blockpos).is(FluidTags.WATER))
                return true;
        }
        return false;
    }

    private static boolean isUnderCrops(BlockGetter level, BlockPos pos)
    {
        Block block = level.getBlockState(pos.above()).getBlock();
        return block instanceof CropBlock || block instanceof StemBlock || block instanceof AttachedStemBlock;
    }
}