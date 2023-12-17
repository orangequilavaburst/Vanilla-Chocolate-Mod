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
import java.util.Random;

public class VanillaProjectileEntity extends Projectile {

    private static final EntityDataAccessor<String> OWNER = SynchedEntityData.defineId(VanillaProjectileEntity.class, EntityDataSerializers.STRING);
    private int life;
    private double baseDamage;
    private final int rotateDirection;
    private Random random = new Random();

    public VanillaProjectileEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
        this.life = 0;
        this.baseDamage = 4;
        this.rotateDirection = 1 - random.nextInt(2)*2;
    }

    public VanillaProjectileEntity(EntityType<? extends Projectile> entityType, Level level, LivingEntity owner){
        super(entityType, level);
        this.life = 0;
        this.setOwner(owner);
        this.baseDamage = 4;
        this.rotateDirection = 1 - random.nextInt(2)*2;
    }

    public VanillaProjectileEntity(EntityType<? extends Projectile> entityType, Level level, Vec3 pos, @Nullable LivingEntity owner){
        super(entityType, level);
        this.life = 0;
        this.setPos(pos);
        this.setOwner(owner);
        this.baseDamage = 4;
        this.rotateDirection = 1 - random.nextInt(2)*2;
    }

    private Vec3 getReflectionVector(Vec3 deltaMovement, Direction dir){
        Vec3 normalVector = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
        Vec3 normalizedProduct = normalVector.scale(2*deltaMovement.dot(normalVector));
        return deltaMovement.subtract(normalizedProduct);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        Vec3 bounceVector = getReflectionVector(this.getDeltaMovement(), result.getDirection());
        this.setDeltaMovement(bounceVector);
    }

    @Override
    public void tick() {
        super.tick();

        Level level = this.level();

        if (++this.life > 150) {
            if (!level.isClientSide()) {
                this.remove(RemovalReason.DISCARDED);
            }
        } else {
            // delta movement
            Vec3 vec3 = this.getDeltaMovement();
            double x = vec3.x;
            double y = vec3.y;
            double z = vec3.z;
            this.setPos(this.getX() + x, this.getY() + y, this.getZ() + z);

            // home into entities
            LivingEntity target;
            float radius = 30.0f;
            AABB area = this.getBoundingBox().inflate(radius);
            List<LivingEntity> list = level.getEntitiesOfClass(LivingEntity.class, area, entity -> !entity.getStringUUID().equals(getOwnerUUID())).stream()
                    .filter(entry -> entry.hasLineOfSight(this))
                    .filter(entry -> entry.position().distanceTo(this.position()) <= radius)
                    .filter(entity -> !((entity instanceof Player player) && player.isCreative()) )
                    .sorted(Comparator.comparing(entry -> entry.position().distanceTo(this.position())))
                    .toList();

            if (!list.isEmpty()) {
                target = list.get(0);

                //Vec3 motion = target.getPosition(1.0f).add(0.0f, 0.0f + target.getBoundingBox().getYsize()/2, 0.0f).subtract(this.position()).normalize().scale(0.5F);
                //this.setDeltaMovement(this.getDeltaMovement().add(motion.x(), motion.y(), motion.z()));

                Vec3 targetVec = target.getPosition(1.0f).add(0.0f, 0.0f + target.getBoundingBox().getYsize()/2, 0.0f).subtract(this.position()).normalize();

                Vec3 a = this.getDeltaMovement().normalize();
                float lerpAmount = Mth.lerp(Math.max(this.distanceTo(target), radius)/radius,  0.05f, 0.001f);
                float maxSpeed = 1.25f;
                float savedSpeed = (float)this.getDeltaMovement().length();

                this.setDeltaMovement(a.add(targetVec.subtract(a.scale(lerpAmount))));
                this.setDeltaMovement(this.getDeltaMovement().normalize().scale(Math.max(savedSpeed, maxSpeed)));
            }

            // rotation
            double d0 = vec3.horizontalDistance();
            this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
            this.setXRot((float)(Mth.atan2(vec3.y, d0) * (double)(180F / (float)Math.PI)));
            this.yRotO = this.getYRot();
            this.xRotO = this.getXRot();

            // hit detection
            HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
            if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
                this.onHit(hitresult);
            }

            // basic checks
            this.checkInsideBlocks();
            if (level.getBlockStates(this.getBoundingBox()).noneMatch(BlockBehaviour.BlockStateBase::isAir)){
                if (!level.isClientSide()) this.remove(RemovalReason.DISCARDED);
            }

        }
    }

    // Why was it here in the first place? lmao
    /*@Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);

        if(this.level().isClientSide()) {
            return;
        }
    }*/

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
        if (hurt){
            this.remove(RemovalReason.DISCARDED);
        }

    }

    /*@Nullable
    protected EntityHitResult findHitEntity(Vec3 pStartVec, Vec3 pEndVec) {
        return ProjectileUtil.getEntityHitResult(this.level(), this, pStartVec, pEndVec, this.getBoundingBox().expandTowards(this.getDeltaMovement()).inflate(1.0D), this::canHitEntity);
    }*/

    protected boolean canHitEntity(Entity entity) {
        return super.canHitEntity(entity) && !this.noPhysics;
    }

    // thx SSKirilSS
    public String getOwnerUUID() {
        return this.getEntityData().get(OWNER);
    }

    public void setOwnerUUID(String uuid) {
        this.getEntityData().set(OWNER, uuid);
    }

    public int getRotateDirection(){
        return this.rotateDirection;
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(OWNER, (this.getOwner() != null) ? this.getOwner().getStringUUID() : "");
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
