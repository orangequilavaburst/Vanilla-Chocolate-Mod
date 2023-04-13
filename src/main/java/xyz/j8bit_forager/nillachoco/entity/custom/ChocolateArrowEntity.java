package xyz.j8bit_forager.nillachoco.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;

public class ChocolateArrowEntity extends Arrow {

    private double baseDamage = 5.0D;
    private final int blockChecks = 10;

    public ChocolateArrowEntity(EntityType<? extends Arrow> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isEffectiveAi()) {

            if (this.isInWall()){
                this.remove(RemovalReason.DISCARDED);
            }

        }

    }

    @Override
    protected boolean tryPickup(Player pPlayer) {
        return false;
    }

    @Override
    public void setBaseDamage(double pBaseDamage) {
        this.baseDamage = pBaseDamage;
    }

    @Override
    public double getBaseDamage() {
        return this.baseDamage;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putDouble("damage", this.baseDamage);
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        boolean check = false;
        int i = 0;
        while(i < blockChecks && !check){
            if (this.level.isEmptyBlock(this.blockPosition().below(i + 1))){
                check = true;
            }
            i++;
        }
        if (i >= blockChecks){
            this.remove(RemovalReason.DISCARDED);
        }
        else{
            this.setOnGround(false);
            this.setPos(this.getX(), this.getY() - (double)i, this.getZ());
            this.setDeltaMovement(0.0, -5.0, 0.0);
        }

    }
}
