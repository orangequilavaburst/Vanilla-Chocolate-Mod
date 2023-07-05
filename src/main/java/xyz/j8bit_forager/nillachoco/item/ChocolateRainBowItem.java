package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class ChocolateRainBowItem extends ProjectileWeaponItem {

    private final double distance = 200.0d;
    private final double maxHeight = 5.0d;

    public ChocolateRainBowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return ARROW_ONLY;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 1;
    }

    @Override
    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        boolean flag = !pPlayer.getProjectile(itemstack).isEmpty();

        InteractionResultHolder<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, pLevel, pPlayer, pHand, flag);
        if (ret != null) return ret;

        if (!pPlayer.getAbilities().instabuild && !flag) {
            return InteractionResultHolder.fail(itemstack);
        } else {
            pPlayer.startUsingItem(pHand);
            return InteractionResultHolder.consume(itemstack);
        }
    }

    @Override
    public void onUseTick(Level pLevel, LivingEntity pLivingEntity, ItemStack pStack, int pRemainingUseDuration) {
        if (pLivingEntity instanceof Player player) {

            int useTime = this.getUseDuration(pStack) - pRemainingUseDuration;
            Vec3 view = player.getViewVector(0);
            Vec3 eyeVec = player.getEyePosition(0);
            HitResult ray = null;
            BlockHitResult bray = pLevel.clip(new ClipContext(eyeVec, eyeVec.add(
                    view.x * this.distance, view.y * this.distance, view.z * this.distance),
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
            EntityHitResult eray = ProjectileUtil.getEntityHitResult(player, eyeVec, eyeVec.add(view.scale(this.distance)),
                    new AABB(eyeVec, eyeVec.add(0.5, 0.5, 0.5)), (e) ->{
                        return !e.is(player) && !e.isSpectator();
                    }, distance);
            if (eray == null || bray.distanceTo(player) < eray.distanceTo(player)){
                ray = bray;
            }
            else{
                ray = eray;
            }

            Vec3 hit = ray.getLocation();

            if (ray != null && ray.distanceTo(player) <= distance && ray.getType() != HitResult.Type.MISS){

                Direction dir = Direction.UP;
                if (ray.getType() == HitResult.Type.BLOCK){
                    dir = ((BlockHitResult) ray).getDirection();
                }
                if (ray.getType() == HitResult.Type.ENTITY){
                    dir = Direction.getNearest(view.x(), view.y(), view.z());
                }

                Vec3 normal = new Vec3(dir.getNormal().getX(), dir.getNormal().getY(), dir.getNormal().getZ());
                if (pLevel.isClientSide()) {
                    int num = 2;
                    for (int i = 0; i < num; i++) {
                        double pX = hit.x;
                        double pY = hit.y;
                        double pZ = hit.z;
                        double radius = Mth.lerp(Math.min(0.1f, getPowerForTime(useTime))*10.0f, 0.0d, 0.5d);
                        double t = pRemainingUseDuration / 5.0D + i * Mth.TWO_PI/((double)num);
                        if (normal.x() != 0) {
                            pY -= Math.sin(t) * radius;
                            pZ += Math.cos(t) * radius;
                        }
                        if (normal.y() != 0) {
                            pX -= Math.sin(t) * radius;
                            pZ += Math.cos(t) * radius;
                        }
                        if (normal.z() != 0) {
                            pX -= Math.sin(t) * radius;
                            pY += Math.cos(t) * radius;
                        }
                        pLevel.addParticle(ParticleTypes.ELECTRIC_SPARK, pX, pY, pZ, 0.0f, 0.0f, 0.0f);
                    }
                }

            }

        }

        super.onUseTick(pLevel, pLivingEntity, pStack, pRemainingUseDuration);
    }

    @Override
    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW, 64);
                }

                float f = getPowerForTime(i);
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                    if (!pLevel.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);

                        // arrow stuff

                        Vec3 view = player.getViewVector(0);
                        Vec3 eyeVec = player.getEyePosition(0);

                        HitResult ray = null;
                        BlockHitResult bray = pLevel.clip(new ClipContext(eyeVec, eyeVec.add(
                                view.x * this.distance, view.y * this.distance, view.z * this.distance),
                                ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));
                        EntityHitResult eray = ProjectileUtil.getEntityHitResult(player, eyeVec, eyeVec.add(view.scale(this.distance)),
                                new AABB(eyeVec, eyeVec.add(0.5, 0.5, 0.5)), (e) ->{
                                    return !e.is(player) && !e.isSpectator();
                                }, distance);
                        if (eray == null || bray.distanceTo(player) < eray.distanceTo(player)){
                            ray = bray;
                        }
                        else{
                            ray = eray;
                        }
                        Vec3 hit = ray.getLocation();
                        Random random = new Random();

                        if (ray != null && ray.distanceTo(player) <= this.distance && ray.getType() != HitResult.Type.MISS){

                            //pLevel.addParticle(ParticleTypes.EFFECT, hit.x, hit.y, hit.z, 0.0f, 0.0f, 0.0f);
                            int num = Mth.lerpInt(f, 1, 5);
                            for (int a = 0; a < Math.min(num, itemstack.getCount()); a++){

                                int height = 0;

                                while (pLevel.getBlockState(BlockPos.containing(ray.getLocation().add(0, height, 0))).isAir() && height < maxHeight - 1){
                                    height++;
                                }

                                AbstractArrow abstractarrow = arrowitem.createArrow(pLevel, itemstack, player);
                                abstractarrow = customArrow(abstractarrow);
                                abstractarrow.setOwner(player);
                                abstractarrow.setPos(hit.add(0.0d, 5.0d, 0.0d));
                                abstractarrow.setPos(abstractarrow.position().add(random.nextFloat(-0.5f, 0.5f), random.nextFloat(-1.0f, 1.0f), random.nextFloat(-0.5f, 0.5f)));
                                abstractarrow.shoot(0.0f, -1.0f, 0.0f, 2.0f, 0.5f);
                                if (f == 1.0F) {
                                    abstractarrow.setCritArrow(true);
                                }

                                int j = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER_ARROWS, pStack);
                                if (j > 0) {
                                    abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                                }

                                int k = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH_ARROWS, pStack);
                                if (k > 0) {
                                    abstractarrow.setKnockback(k);
                                }

                                if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAMING_ARROWS, pStack) > 0) {
                                    abstractarrow.setSecondsOnFire(100);
                                }

                                if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                                    abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                                }

                                pLevel.addFreshEntity(abstractarrow);
                                if (pLevel.isClientSide()){
                                    pLevel.addParticle(ParticleTypes.CLOUD, abstractarrow.getX(), abstractarrow.getY(), abstractarrow.getZ(), 0.0f, 0.0f, 0.0f);
                                }

                                if (!flag1 && !player.getAbilities().instabuild) {
                                    itemstack.shrink(1);
                                    if (itemstack.isEmpty()) {
                                        player.getInventory().removeItem(itemstack);
                                    }
                                }

                            }

                        }

                        pStack.hurtAndBreak(1, player, (p_289501_) -> {
                            p_289501_.broadcastBreakEvent(player.getUsedItemHand());
                        });

                        pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);

                        player.awardStat(Stats.ITEM_USED.get(this));

                    }
                }
            }
        }
    }

    public static float getPowerForTime(int pCharge) {
        float f = (float)pCharge / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public AbstractArrow customArrow(AbstractArrow arrow) {
        return arrow;
    }
}
