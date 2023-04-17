package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import xyz.j8bit_forager.nillachoco.item.entity.custom.ChocolateArrowEntity;

import java.util.function.Predicate;

public class ChocolateRainBowItem extends ProjectileWeaponItem {

    public static final int MAX_DRAW_DURATION = 20;
    public static final int DEFAULT_RANGE = 15;

    public ChocolateRainBowItem(Properties pProperties) {
        super(pProperties);
    }

    public void releaseUsing(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        if (pEntityLiving instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || EnchantmentHelper.getItemEnchantmentLevel(Enchantments.INFINITY_ARROWS, pStack) > 0;
            ItemStack itemstack = player.getProjectile(pStack);

            int i = this.getUseDuration(pStack) - pTimeLeft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(pStack, pLevel, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = 1.0f;
                boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, pStack, player));
                if (!pLevel.isClientSide) {

                    ChocolateArrowEntity arrow = new ChocolateArrowEntity(EntityType.ARROW, pLevel);
                    arrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3.0F, 0.0F);

                    pStack.hurtAndBreak(1, player, (p_276007_) -> {
                        p_276007_.broadcastBreakEvent(player.getUsedItemHand());
                    });

                    pLevel.addFreshEntity(arrow);

                }

                pLevel.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (pLevel.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                if (!flag1 && !player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        player.getInventory().removeItem(itemstack);
                    }
                }

                player.awardStat(Stats.ITEM_USED.get(this));

            }

        }

    }

    public int getUseDuration(ItemStack pStack) {
        return 72000;
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.BOW;
    }

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

    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return (arrow)->{ return arrow.is(ModItems.CHOCOLATE_BAR.get()); };
    }

    public int getDefaultProjectileRange() {
        return DEFAULT_RANGE;
    }

}
