package xyz.j8bit_forager.nillachoco.entity.custom;

import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import xyz.j8bit_forager.nillachoco.item.ModItems;
import xyz.j8bit_forager.nillachoco.particle.ModParticles;

public class ChocolateEggEntity extends ThrowableItemProjectile {

    public ChocolateEggEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public ChocolateEggEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(pEntityType, pX, pY, pZ, pLevel);
    }

    public ChocolateEggEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(pEntityType, pShooter, pLevel);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide){

            this.level().addParticle(ModParticles.RAIN_INDICATOR.get(), this.getX(), this.getY(), this.getZ(), 0.0, 0.0, 0.0);

        }
    }

    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        if (!this.level().isClientSide) {

            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }

    }

    public void handleEntityEvent(byte pId) {
        if (pId == 3) {
            double d0 = 0.08D;

            for(int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY() + this.getBbHeight()/2.0f, this.getZ(), ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D, ((double)this.random.nextFloat() - 0.5D) * 0.08D);
            }
        }

    }

    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        pResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 2.0F);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.CHOCOLATE_EGG.get();
    }
}
