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

    // to remake

    public ChocolateRainBowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public Predicate<ItemStack> getAllSupportedProjectiles() {
        return null;
    }

    @Override
    public int getDefaultProjectileRange() {
        return 1;
    }
}
