package xyz.j8bit_forager.nillachoco.entity.custom;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.*;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

public class VanillaProjectileEntity extends Projectile {

    private static final EntityDataAccessor<String> OWNER = SynchedEntityData.defineId(VanillaProjectileEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> HIT =
            SynchedEntityData.defineId(VanillaProjectileEntity.class, EntityDataSerializers.BOOLEAN);
    private int life;
    private double baseDamage;

    public VanillaProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.life = 0;
        this.baseDamage = 4;
    }

    public VanillaProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, LivingEntity owner){
        super(pEntityType, pLevel);
        this.life = 0;
        this.setOwner((Entity)owner);
        this.baseDamage = 4;
    }

    public VanillaProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel, Vec3 pos, @Nullable LivingEntity owner){
        super(pEntityType, pLevel);
        this.life = 0;
        this.setPos(pos);
        this.setOwner((Entity)owner);
        this.baseDamage = 4;
    }

    private Vec3 getReflectionVector(Vec3 deltaMovement, Direction dir){
        Vec3 normalVector = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
        Vec3 normalizedProduct = normalVector.scale(2*deltaMovement.dot(normalVector));
        return deltaMovement.subtract(normalizedProduct);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        Vec3 bounceVector = getReflectionVector(this.getDeltaMovement(), pResult.getDirection());
        this.setDeltaMovement(bounceVector);
    }

    @Override
    public void tick() {
        super.tick();

        ++this.life;
        if (this.life > 300){

            this.discard();

        }
        else{

            // delta movement
            Vec3 vec3 = this.getDeltaMovement();
            double d5 = vec3.x;
            double d6 = vec3.y;
            double d1 = vec3.z;
            double d7 = this.getX() + d5;
            double d2 = this.getY() + d6;
            double d3 = this.getZ() + d1;
            this.setPos(d7, d2, d3);

            // home into entities
            Level pLevel = this.level();
            LivingEntity target = null;
            float radius = 30.0f;
            AABB area = this.getBoundingBox().inflate(radius);
            List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class, area).stream()
                    .filter(entry -> entry.hasLineOfSight(this))
                    .filter(entry -> entry.position().distanceTo(this.position()) <= radius)
                    .filter(entity -> !entity.getStringUUID().equals(getOwnerUUID()))
                    .filter(entity -> !((entity instanceof Player player) && player.isCreative()) )
                    .sorted(Comparator.comparing(entry -> entry.position().distanceTo(this.position())))
                    .toList();

            if (!list.isEmpty()) {
                target = list.get(0);

                //Vec3 motion = target.getPosition(1.0f).add(0.0f, 0.0f + target.getBoundingBox().getYsize()/2, 0.0f).subtract(this.position()).normalize().scale(0.5F);
                //this.setDeltaMovement(this.getDeltaMovement().add(motion.x(), motion.y(), motion.z()));

                Vec3 targetVec = target.getPosition(1.0f).add(0.0f, 0.0f + target.getBoundingBox().getYsize()/2, 0.0f).subtract(this.position()).normalize();

                Vec3 a = this.getDeltaMovement().normalize();
                Vec3 b = targetVec;
                float lerpAmount = Mth.lerp(Math.max(this.distanceTo(target), radius)/radius,  0.25f, 0.01f);
                float maxSpeed = 2.0f;
                float savedSpeed = (float)this.getDeltaMovement().length();

                this.setDeltaMovement(a.add(b.subtract(a.scale(lerpAmount))));
                this.setDeltaMovement(this.getDeltaMovement().normalize().scale(Math.max(savedSpeed, maxSpeed)));

            }

            // rotation

            // check for hits
            HitResult hitresult = ProjectileUtil.getEntityHitResult(this.level(), this, this.position(), this.getPosition(1.0f), this.getBoundingBox(), (p) -> (p != this.getOwner()));
            if (hitresult != null) {
                if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult))
                    this.onHit(hitresult);
            }

            // basic checks
            if (this.level().getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)){
                this.discard();
            }

        }
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        if(this.level().isClientSide()) {
            return;
        }

        if(pResult.getType() == HitResult.Type.ENTITY && pResult instanceof EntityHitResult entityHitResult) {
            Entity hit = entityHitResult.getEntity();
            Entity owner = this.getOwner();
            if(owner != hit) {
                this.entityData.set(HIT, true);
            }
        } else {
            this.entityData.set(HIT, true);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        super.onHitEntity(pResult);
        Entity hitEntity = pResult.getEntity();
        Entity owner = this.getOwner();
        if(hitEntity == owner && this.level().isClientSide()) {
            return;
        }

        LivingEntity livingentity = owner instanceof LivingEntity ? (LivingEntity)owner : null;
        boolean hurt = hitEntity.hurt(this.damageSources().mobProjectile(this, livingentity), (float) this.baseDamage);

    }

    @Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }

    protected boolean canHitEntity(Entity p_36743_) {
        return super.canHitEntity(p_36743_);
    }

    // thx SSKirilSS
    public String getOwnerUUID() {
        return this.getEntityData().get(OWNER);
    }

    public void setOwnerUUID(String uuid) {
        this.getEntityData().set(OWNER, uuid);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(OWNER, (this.getOwner() != null) ? this.getOwner().getStringUUID() : "");
        this.entityData.define(HIT, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putShort("life", (short)this.life);
        pCompound.putDouble("damage", this.baseDamage);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
