package xyz.j8bit_forager.nillachoco.item.entity.custom;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import xyz.j8bit_forager.nillachoco.item.ModItems;

public class ChocolateArrowEntity extends AbstractArrow {

    private static final EntityDataAccessor<Byte> ID_FLAGS = SynchedEntityData.defineId(AbstractArrow.class, EntityDataSerializers.BYTE);

    public ChocolateArrowEntity(EntityType<? extends AbstractArrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
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

    protected void defineSynchedData() {
        this.entityData.define(ID_FLAGS, (byte)0);
    }

}
