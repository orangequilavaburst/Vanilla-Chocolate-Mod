package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;

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
