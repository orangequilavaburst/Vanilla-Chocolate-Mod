package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeTier;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;
import xyz.j8bit_forager.nillachoco.entity.custom.VanillaProjectileEntity;

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

                    //player.sendSystemMessage(Component.literal("Shoot!"));
                    for (int i = 0; i < 4; i++){

                        VanillaProjectileEntity proj = new VanillaProjectileEntity(ModEntityTypes.VANILLA_PROJECTILE_ENTITY.get(), player.level(), player.getPosition(1.0f).add(0.0, player.getBbHeight()/2.0, 0.0), player);
                        proj.shootFromRotation(player, 0.0f /*xrot*/, player.getYRot() + (90.0f*(float)i + 45.0f), 0.0f, 1.25f, 0.01f);
                        proj.setOwner(player);
                        proj.setOwnerUUID(player.getStringUUID());
                        player.level().addFreshEntity(proj);

                    }

                }
            }
        }
        return super.onEntitySwing(stack, entity);
    }
}
