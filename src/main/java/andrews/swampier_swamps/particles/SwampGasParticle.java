package andrews.swampier_swamps.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class SwampGasParticle extends TextureSheetParticle
{
    SwampGasParticle(ClientLevel clientLevel, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed)
    {
        super(clientLevel, x, y, z);
        this.scale(4.0F);
        this.setSize(0.25F, 0.25F);
        this.lifetime = this.random.nextInt(20) + 20;
        this.gravity = 3.0E-6F;
        this.xd = xSpeed + (double)((this.random.nextFloat() - 0.5F) / 100.0F);
        this.yd = ySpeed + (double)(this.random.nextFloat() / 100.0F);
        this.zd = zSpeed + (double)((this.random.nextFloat() - 0.5F) / 100.0F);
    }

    @Override
    public void tick()
    {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ < this.lifetime)
        {
            this.xd += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.zd += this.random.nextFloat() / 5000.0F * (float)(this.random.nextBoolean() ? 1 : -1);
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
        }
        else
        {
            this.remove();
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
            SwampGasParticle swampGasParticle = new SwampGasParticle(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);
            swampGasParticle.setAlpha(0.6F);
            swampGasParticle.pickSprite(this.sprites);
            return swampGasParticle;
        }
    }
}