package xyz.j8bit_forager.nillachoco.particle.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

import java.awt.*;

public class RainIndicatorParticle extends TextureSheetParticle {

    private ResourceLocation LOCATION = new ResourceLocation(NillaChocoMod.MOD_ID, "textures/particle/rain_indicator.png");
    private float[] initHSB;

    protected RainIndicatorParticle(ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, SpriteSet pSprites) {
        super(pLevel, pX, pY, pZ, pXSpeed, pYSpeed, pZSpeed);

        this.friction = 0.0f;
        this.xd = pXSpeed;
        this.yd = pYSpeed;
        this.zd = pZSpeed;

        this.lifetime = 10;
        this.setSpriteFromAge(pSprites);
        this.quadSize = 0.1f;

        this.alpha = 0;

        float mt = (Minecraft.getInstance().player.level().getGameTime() / 50.0f) % 1.0f;
        this.initHSB = new float[]{mt % 1.0f, 0.75f, 1.0f};
        Color colorRGB = new Color(Color.HSBtoRGB(this.initHSB[0], this.initHSB[1], this.initHSB[2]));

        this.setColor(colorRGB.getRed(), colorRGB.getGreen(), colorRGB.getBlue());

    }

    @Override
    public void tick() {
        super.tick();

        fadeOut();
        scale();
        color();
    }

    @Override
    protected int getLightColor(float pPartialTick) {
        return 255;
    }

    private void fadeOut(){

        float t = (float)this.age / (float)this.lifetime;
        this.alpha = Mth.lerp(t*t*t*t*t, 1.0f, 0.0f);

    }

    private void scale(){
        float t = (float)this.age / (float)this.lifetime;
        this.quadSize = (Mth.lerp(t*t*t*t, 0.1f, 0.0f));
    }

    private void color(){

        float t = (float)this.age / (float)this.lifetime;
        float lt = Math.min(1.0f, t*1.25f);

        float hue = (this.initHSB[0] + this.age/10.0f) % 1.0f;
        float saturation = Mth.lerp(lt, this.initHSB[1], 0.0f);
        float brightness = Mth.lerp(lt, this.initHSB[2], 1.0f);
        Color colorRGB = new Color(Color.HSBtoRGB(hue, saturation, brightness));

        float r = Mth.lerp(lt, colorRGB.getRed() / 255.0f, 1.0f);
        float g = Mth.lerp(lt, colorRGB.getGreen() / 255.0f, 1.0f);
        float b = Mth.lerp(lt, colorRGB.getBlue() / 255.0f, 1.0f);

        this.setColor(r, g, b);

    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet spriteSet) {
            this.sprites = spriteSet;
        }

        public Particle createParticle(SimpleParticleType particleType, ClientLevel level,
                                       double x, double y, double z,
                                       double dx, double dy, double dz) {
            return new RainIndicatorParticle(level, x, y, z, dx, dy, dz, this.sprites);
        }
    }

}
