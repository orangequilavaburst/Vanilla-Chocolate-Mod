package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;
import xyz.j8bit_forager.nillachoco.entity.custom.ChocolateArrowEntity;

public class ChocolateArrowItem extends ArrowItem {
    public ChocolateArrowItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public AbstractArrow createArrow(Level pLevel, ItemStack pStack, LivingEntity pShooter) {
        return new ChocolateArrowEntity(ModEntityTypes.CHOCOLATE_ARROW_ENTITY.get(), pShooter, pLevel);
    }
}
