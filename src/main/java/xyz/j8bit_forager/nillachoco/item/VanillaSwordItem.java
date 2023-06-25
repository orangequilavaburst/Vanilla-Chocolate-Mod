package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;

public class VanillaSwordItem extends SwordItem {

    private static final ForgeTier VANILLA_TIER = new ForgeTier(4, 3000, 8.0f, 5.0f, 5, BlockTags.NEEDS_DIAMOND_TOOL, () -> Ingredient.EMPTY);

    public VanillaSwordItem(Properties pProperties) {
        super(VANILLA_TIER, 4, -3.5f, pProperties);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        if (!entity.level().isClientSide()){
            if (entity instanceof Player player){
                if (!player.getCooldowns().isOnCooldown(stack.getItem())){

                    player.getCooldowns().addCooldown(this, 120);

                    // temporary
                    player.sendSystemMessage(Component.literal("Shoot!"));

                }
            }
        }
        return super.onEntitySwing(stack, entity);
    }
}
