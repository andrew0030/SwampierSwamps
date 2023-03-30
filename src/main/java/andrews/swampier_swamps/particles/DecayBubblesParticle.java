package andrews.swampier_swamps.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;

public class DecayBubblesParticle extends TextureSheetParticle
{
    DecayBubblesParticle(ClientLevel clientLevel, double x, double y, double z, double speedX, double speedY, double speedZ)
    {
        super(clientLevel, x, y, z);
        this.setSize(0.02F, 0.02F);
        this.scale(1.4F);
        this.quadSize *= this.random.nextFloat() * 0.6F + 0.2F;
        this.xd = speedX * 0.2D + (Math.random() * 2.0D - 1.0D) * 0.02D;
        this.yd = speedY * 0.2D + (Math.random() * 2.0D - 1.0D) * 0.02D;
        this.zd = speedZ * 0.2D + (Math.random() * 2.0D - 1.0D) * 0.02D;
        this.lifetime = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0)
        {
            this.remove();
        }
        else
        {
            this.yd += 0.002D;
            this.move(this.xd, this.yd, this.zd);
            this.xd *= 0.85D;
            this.yd *= 0.85D;
            this.zd *= 0.85D;
            if (!this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER))
            {
                this.remove();
            }
        }
    }

    @Override
    public ParticleRenderType getRenderType()
    {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Factory implements ParticleProvider<SimpleParticleType>
    {
        private final SpriteSet sprites;

        public Factory(SpriteSet sprite)
        {
            this.sprites = sprite;
        }

        public Particle createParticle(SimpleParticleType type, ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
        {
            DecayBubblesParticle decayBubblesParticle = new DecayBubblesParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
            decayBubblesParticle.pickSprite(this.sprites);
            return decayBubblesParticle;
        }
    }
}