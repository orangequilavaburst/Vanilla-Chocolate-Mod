package xyz.j8bit_forager.nillachoco.entity.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.network.NetworkHooks;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;
import xyz.j8bit_forager.nillachoco.item.ModItems;

public class ChocolateArrowEntity extends AbstractArrow {

    private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<String> OWNER = SynchedEntityData.defineId(ChocolateArrowEntity.class, EntityDataSerializers.STRING);
    private int life;

    public ChocolateArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(ModEntityTypes.CHOCOLATE_ARROW_ENTITY.get(), pLevel);
        this.life = 0;
    }
    public ChocolateArrowEntity(EntityType<? extends AbstractArrow> pEntityType, LivingEntity pShooter, Level pLevel) {
        super(ModEntityTypes.CHOCOLATE_ARROW_ENTITY.get(), pShooter, pLevel);
        setOwnerUUID(pShooter.getStringUUID());
        this.life = 0;
    }

    public ChocolateArrowEntity(EntityType<? extends AbstractArrow> pEntityType, double pX, double pY, double pZ, Level pLevel) {
        super(ModEntityTypes.CHOCOLATE_ARROW_ENTITY.get(), pLevel);
        this.life = 0;
    }

    @Override
    protected ItemStack getPickupItem() {
        return new ItemStack(ModItems.CHOCOLATE_BAR.get(), 1);
    }

    @Override
    protected boolean tryPickup(Player pPlayer) {
        return false;
    }

    @Override
    protected void onHit(HitResult pResult) {
        super.onHit(pResult);
        this.remove(RemovalReason.DISCARDED);
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
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putShort("life", (short)this.life);
        pCompound.putDouble("damage", this.getBaseDamage());
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

}
