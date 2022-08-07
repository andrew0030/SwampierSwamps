package andrews.swampier_swamps.entities;

import andrews.swampier_swamps.config.SSConfigs;
import andrews.swampier_swamps.registry.SSParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SwampGas extends Entity
{
    private static final EntityDataAccessor<Boolean> IS_CLOUD = SynchedEntityData.defineId(SwampGas.class, EntityDataSerializers.BOOLEAN);

    public SwampGas(EntityType<? extends SwampGas> entityType, Level level)
    {
        super(entityType, level);
    }

    @Override
    public void tick()
    {
        super.tick();
        // This makes a Gas Bubble go up if underwater
        if(this.isInWater() && !this.isCould())
        {
            Vec3 vec3 = this.getDeltaMovement();
            double deltaX = this.getX() + vec3.x;
            double deltaY = this.getY() + vec3.y + 0.1D;
            double deltaZ = this.getZ() + vec3.z;
            this.setPos(deltaX, deltaY, deltaZ);
            this.setDeltaMovement(vec3);
        }
        else if(!this.isInWater() && !this.isCould()) // If its no longer in water we turn it into a Gas Cloud.
        {
            this.setIsCould(true);
            this.refreshDimensions(); // Updates the dimensions, note this only refreshes the size, the logic is handled below
        }

        // Spawns the Particles
        if (this.isCould() && random.nextInt(2) == 0)
        {
            for(int i = 0; i < 10; i++)
            {
                double spawnX = this.getX() + random.nextInt(7) - 3 + random.nextDouble();
                double spawnY = this.getY() + random.nextInt(4) + random.nextDouble();
                double spawnZ = this.getZ() + random.nextInt(7) - 3 + random.nextDouble();
                double distance = Math.sqrt((spawnY - this.getY()) * (spawnY - this.getY()) + (spawnX - this.getX()) * (spawnX - this.getX()) + (spawnZ - this.getZ()) * (spawnZ - this.getZ()));

                if(distance < 3.5)
                    level.addParticle(SSParticles.SWAMP_GAS.get(), spawnX, spawnY, spawnZ, 0, 0, 0);
            }
        }
        // Handles the Explosion Checks / Collision Logic
        handleCollisions();
        // If the Entity is too Old we remove it
        if(this.tickCount > 400)
            this.discard();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        EntityDimensions dimensions = super.getDimensions(pose);
        return this.isCould() ? dimensions.scale(20.0F, 8.0F) : dimensions;
    }

    @Override
    protected void defineSynchedData()
    {
        this.getEntityData().define(IS_CLOUD, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound)
    {
        compound.putBoolean("IsCloud", this.isCould());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound)
    {
        if(compound.contains("IsCloud"))
            this.setIsCould(compound.getBoolean("IsCloud"));
    }

    @Override
    public Packet<?> getAddEntityPacket()
    {
        return new ClientboundAddEntityPacket(this);
    }

    public boolean isCould()
    {
        return this.getEntityData().get(IS_CLOUD);
    }

    public void setIsCould(boolean value)
    {
        this.getEntityData().set(IS_CLOUD, value);
    }

    private void handleCollisions()
    {
        // We make sure the Entity is a Gas Cloud
        if(this.isCould())
        {
            if (this.tickCount % SSConfigs.commonConfig.collisionCheckRate.get() == 0) // Base check frequency on Config
            {
                // We get all the Arrow Entities within the Gas Cloud
                List<Arrow> arrowEntities = this.level.getEntitiesOfClass(Arrow.class, this.getBoundingBox());
                if (!arrowEntities.isEmpty()) // Makes sure there was at least 1 arrow
                {
                    for (Arrow arrow : arrowEntities)
                    {
                        if (arrow.isOnFire()) // We make sure the arrow is burning
                        {
                            for (int i = 0; i < 20; i++)
                            {
                                double spawnX = this.getX() + random.nextInt(7) - 3 + random.nextDouble();
                                double spawnY = this.getY() + random.nextInt(4) + random.nextDouble();
                                double spawnZ = this.getZ() + random.nextInt(7) - 3 + random.nextDouble();

                                level.addAlwaysVisibleParticle(ParticleTypes.FLAME, spawnX, spawnY, spawnZ, 0, 0, 0);
                            }

                            if(!level.isClientSide)
                                level.explode(null, this.getX(), this.getY() + 0.5F, this.getZ(), 4.0F, true, Explosion.BlockInteraction.DESTROY);
                            this.discard();
                        }
                    }
                }
            }

            // Gives Entities Effects
            if (this.tickCount % 5 == 0) // Reduces the check rate to every 5 ticks
            {
                if(SSConfigs.commonConfig.givesNegativeEffects.get()) // Config Check
                {
                    List<LivingEntity> livingEntities = this.level.getEntitiesOfClass(LivingEntity.class, this.getBoundingBox());
                    if (!livingEntities.isEmpty()) // If we found living entities we continue
                    {
                        for (LivingEntity livingEntity : livingEntities) // We loop through the Entities and add the Effects
                        {
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
                            livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 100));
                        }
                    }
                }
            }
        }
    }
}