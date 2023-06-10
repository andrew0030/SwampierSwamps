package andrews.swampier_swamps.objects.blocks;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.network.NetworkUtil;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Map;

public class SinkingLilyPad extends WaterlilyBlock
{
    public static final IntegerProperty SINK_STAGE = IntegerProperty.create("sink_stage", 0, 2);
    private static final Map<Integer, VoxelShape> AABB_SHAPES = ImmutableMap.of(0, Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D), 1, Block.box(1.0D, -1.5D, 1.0D, 15.0D, 0.0D, 15.0D), 2, Block.box(1.0D, -3.0D, 1.0D, 15.0D, -1.5D, 15.0D));

    public SinkingLilyPad(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SINK_STAGE, 0));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving)
    {
        if(level instanceof ServerLevel)
            if (!(state.getValue(SINK_STAGE) > 0))
                level.scheduleTick(pos, this, SSConfigs.commonConfig.lilyPadSinkTimeStage1.get());
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context)
    {
        int sinkStage = state.getValue(SINK_STAGE);
        VoxelShape shape = AABB_SHAPES.get(sinkStage);
        return SSConfigs.commonConfig.doLilyPadsBreak.get() ? shape : sinkStage == 2 ? Shapes.empty() : shape;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource rand)
    {
        switch (state.getValue(SINK_STAGE))
        {
            case 0:
                // If there are any LivingEntities over the Block we schedule the next tick and set
                // the Block to its next Sinking Stage
                if(getLivingEntitiesAtPos(level, pos) > 0) {
                    setNextSinkStage(level, pos, 1, SSConfigs.commonConfig.lilyPadSinkTimeStage2.get());
                } else { // If there aren't any LivingEntities over the block we revert to default (Lily Pad Block)
                    level.setBlock(pos, Blocks.LILY_PAD.defaultBlockState(), 2);
                }
                break;
            case 1:
                if(getLivingEntitiesAtPos(level, pos) > 0) {
                    setNextSinkStage(level, pos, 2, SSConfigs.commonConfig.lilyPadSinkTimeStage3.get());
                } else { // If there aren't any LivingEntities over the block we revert to default (Lily Pad Block)
                    level.setBlock(pos, this.defaultBlockState().setValue(SINK_STAGE, 0), 2);
                    level.scheduleTick(pos, this, SSConfigs.commonConfig.lilyPadResetTime.get());
                }
                break;
            case 2:
                if(getLivingEntitiesAtPos(level, pos) > 0) {
                    if(SSConfigs.commonConfig.doLilyPadsBreak.get()) {
                        level.destroyBlock(pos, true);
                    } else {
                        level.scheduleTick(pos, this, SSConfigs.commonConfig.lilyPadResetTime.get());
                    }
                } else { // If there aren't any LivingEntities over the block we revert to default (Lily Pad Block)
                    level.setBlock(pos, this.defaultBlockState().setValue(SINK_STAGE, 1), 2);
                    level.scheduleTick(pos, this, SSConfigs.commonConfig.lilyPadResetTime.get());
                }
        }
    }

    private int getLivingEntitiesAtPos(Level level, BlockPos pos)
    {
        int livingEntityCount = 0;
        for(Entity entity : level.getEntities(null, new AABB(pos.getX() + 0.0625D, pos.getY(), pos.getZ() + 0.0625D, pos.getX() + 0.9375, pos.getY() + 1D, pos.getZ() + 0.9375D)))
        {
            if (entity instanceof LivingEntity livingEntity)
                if (livingEntity.getBbHeight() > 1.2F)
                    if(livingEntity.onGround() || livingEntity.isInWater())
                        if(livingEntity.position().y > (double)((float)pos.below().getY() + 0.6875F) || !SSConfigs.commonConfig.doLilyPadsBreak.get())
                            ++livingEntityCount;
        }
        return livingEntityCount;
    }

    private boolean canEntitySink(BlockPos pos, Entity entity)
    {
        return entity.onGround() && entity.position().y > (pos.getY() + 0.6875D);
    }

    private void setNextSinkStage(ServerLevel level, BlockPos pos, int stage, int ticks)
    {
        NetworkUtil.createSplashParticlesAtPos(level, pos);
        level.playSound(null, pos, SoundEvents.PLAYER_SPLASH, SoundSource.BLOCKS, 1.0F, 1.0f);
        level.setBlock(pos, this.defaultBlockState().setValue(SINK_STAGE, stage), 2);
        level.scheduleTick(pos, this, ticks);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder)
    {
        stateBuilder.add(SINK_STAGE);
    }
}
