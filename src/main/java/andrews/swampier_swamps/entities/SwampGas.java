package andrews.swampier_swamps.entities;

import andrews.swampier_swamps.registry.SSParticles;
import com.mojang.math.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
        if(this.isInWater() && !this.isCould())
        {
            Vec3 vec3 = this.getDeltaMovement();
            double deltaX = this.getX() + vec3.x;
            double deltaY = this.getY() + vec3.y + 0.1D;
            double deltaZ = this.getZ() + vec3.z;
            this.setPos(deltaX, deltaY, deltaZ);
            this.setDeltaMovement(vec3);
        }
        else if(!this.isInWater() && !this.isCould())
        {
            this.setIsCould(true);
            this.refreshDimensions();
//            level.addParticle(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 0, 0, 0); TODO maybe make a nice pop particle
        }

        if (this.isCould() && random.nextInt(1) == 0)
        {
            for(int i = 0; i < 10; i++)
            {
                double spawnX = this.getX() + random.nextInt(7) - 3 + random.nextDouble(); //TODO make sure nextDouble is 0-1
                double spawnY = this.getY() + random.nextInt(7) - 3 + random.nextDouble(); //TODO make sure nextDouble is 0-1
                double spawnZ = this.getZ() + random.nextInt(7) - 3 + random.nextDouble(); //TODO make sure nextDouble is 0-1
                double distance = Math.sqrt((spawnY - this.getY()) * (spawnY - this.getY()) + (spawnX - this.getX()) * (spawnX - this.getX()) + (spawnZ - this.getZ()) * (spawnZ - this.getZ()));

                if(distance < 2.5)
                    level.addParticle(SSParticles.SWAMP_GAS.get(), spawnX, spawnY, spawnZ, 0, 0, 0); // TODO adjust distance
            }
        }

        // If the Entity is too Old we remove it
        if(this.tickCount > 200)
            this.discard();
    }

    @Override
    public EntityDimensions getDimensions(Pose pose)
    {
        EntityDimensions dimensions = super.getDimensions(pose);
        return this.isCould() ? dimensions.scale(14.0F, 5.0F) : dimensions;
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
}