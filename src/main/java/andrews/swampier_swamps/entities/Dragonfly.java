package andrews.swampier_swamps.entities;

import andrews.swampier_swamps.registry.SSEntities;
import andrews.swampier_swamps.registry.SSTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

public class Dragonfly extends Animal implements FlyingAnimal
{
    private static final EntityDataAccessor<Integer> DRAGONFLY_TYPE = SynchedEntityData.defineId(Dragonfly.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DRAGONFLY_SCALE = SynchedEntityData.defineId(Dragonfly.class, EntityDataSerializers.INT);

    public Dragonfly(EntityType<? extends Animal> entityType, Level level)
    {
        super(entityType, level);
        this.moveControl = new FlyingMoveControl(this, 20, true);
        this.setPathfindingMalus(BlockPathTypes.DANGER_FIRE, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.WATER_BORDER, 16.0F);
        this.setPathfindingMalus(BlockPathTypes.COCOA, -1.0F);
        this.setPathfindingMalus(BlockPathTypes.FENCE, -1.0F);
    }

    @Override
    protected PathNavigation createNavigation(Level level)
    {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level)
        {
            public boolean isStableDestination(BlockPos pos)
            {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);
        navigation.setCanPassDoors(true);
        return navigation;
    }

    @Override
    public boolean causeFallDamage(float fallDistance, float multiplier, DamageSource source)
    {
        return false;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGround, BlockState state, BlockPos pos) {}

    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new Dragonfly.FlyingWanderGoal(this));
        this.goalSelector.addGoal(2, new Dragonfly.RandomLookAroundOnFloorGoal(this));
        this.goalSelector.addGoal(3, new FloatGoal(this));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag compound)
    {
        spawnData = super.finalizeSpawn(level, difficulty, spawnType, spawnData, compound);
        if(compound != null && compound.contains("Variant", Tag.TAG_INT))
        {
            this.setDragonflyType(compound.getInt("Variant"));
            return spawnData;
        }
        this.setDragonflyType(level.getRandom().nextInt(4) + 1);
        return spawnData;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions size)
    {
        return size.height * 0.5F;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound)
    {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.getDragonflyType());
        compound.putInt("Scale", this.getDragonflyScale());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound)
    {
        super.readAdditionalSaveData(compound);
        this.setDragonflyType(compound.getInt("Variant"));
        this.setDragonflyScale(compound.getInt("Scale"));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(DRAGONFLY_TYPE, 0);
        this.entityData.define(DRAGONFLY_SCALE, 0);
    }

    public static AttributeSupplier.Builder createAttributes()
    {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.FLYING_SPEED, 0.8D).add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    public static boolean checkDragonflySpawnRules(EntityType<? extends Animal> entity, LevelAccessor level, MobSpawnType type, BlockPos pos, RandomSource rand)
    {
        return level.getBlockState(pos.below()).is(SSTags.Blocks.DRAGONFLIES_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, pos);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob)
    {
        return SSEntities.DRAGONFLY.get().create(level);
    }

    @Override
    public boolean canMate(Animal pOtherAnimal)
    {
        return false;
    }

    @Override
    public boolean isFlying()
    {
        return !this.onGround;
    }

    public int getDragonflyType()
    {
        if(this.entityData.get(DRAGONFLY_TYPE) == 0)
        {
            Random rand = new Random();
            this.entityData.set(DRAGONFLY_TYPE, rand.nextInt(4) + 1);
        }
        return this.entityData.get(DRAGONFLY_TYPE);
    }

    public void setDragonflyType(int typeId)
    {
        this.entityData.set(DRAGONFLY_TYPE, typeId);
    }

    public int getDragonflyScale()
    {
        if(this.entityData.get(DRAGONFLY_SCALE) == 0)
        {
            Random rand = new Random();
            this.entityData.set(DRAGONFLY_SCALE, rand.nextInt(5) + 1);
        }
        return this.entityData.get(DRAGONFLY_SCALE);
    }

    public void setDragonflyScale(int typeId)
    {
        this.entityData.set(DRAGONFLY_SCALE, typeId);
    }

    public boolean isMoving()
    {
        return ((this.xOld != this.getX()) && (this.yOld != this.getY()) && (this.zOld != this.getZ()));
    }

    static class FlyingWanderGoal extends Goal
    {
        private final PathfinderMob mob;

        public FlyingWanderGoal(PathfinderMob mob)
        {
            this.setFlags(EnumSet.of(Flag.MOVE));
            this.mob = mob;
        }

        @Override
        public boolean canUse()
        {
            return mob.getNavigation().isDone() && mob.getRandom().nextInt(15) == 0;
        }

        @Override
        public boolean canContinueToUse()
        {
            return mob.getNavigation().isInProgress();
        }

        @Override
        public void start()
        {
            Vec3 vec3 = this.findPos();
            if (vec3 != null)
                mob.getNavigation().moveTo(mob.getNavigation().createPath(BlockPos.containing(vec3), 1), 1.0D);
        }

        @Nullable
        private Vec3 findPos()
        {
            Vec3 viewVector = mob.getViewVector(0.0F);
            Vec3 hoverPos = HoverRandomPos.getPos(mob, 8, 7, viewVector.x, viewVector.z, ((float) Math.PI / 2F), 3, 2);
            return hoverPos != null ? hoverPos : AirAndWaterRandomPos.getPos(mob, 8, 4, -1, viewVector.x, viewVector.z, (float) Math.PI / 2F);
        }
    }

    static class RandomLookAroundOnFloorGoal extends RandomLookAroundGoal
    {
        private final PathfinderMob mob;

        public RandomLookAroundOnFloorGoal(PathfinderMob mob)
        {
            super(mob);
            this.mob = mob;
        }

        @Override
        public boolean canUse()
        {
            return (!this.mob.getBlockStateOn().isAir() && this.mob.getRandom().nextFloat() < 0.04F);
        }
    }
}
