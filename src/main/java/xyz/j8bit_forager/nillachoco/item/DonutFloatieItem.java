package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.entity.model.DonutFloatieModel;

import java.util.List;
import java.util.function.Consumer;

public class DonutFloatieItem extends ArmorItem {

    public static ArmorMaterial DONUT = new ArmorMaterial() {

        @Override
        public int getDurabilityForType(Type ArmorType) {
            return 200;
        }

        @Override
        public int getDefenseForType(Type ArmorType) {
            return 2;
        }

        @Override
        public int getEnchantmentValue() {
            return 1;
        }

        @Override
        public SoundEvent getEquipSound() {
            return SoundEvents.ARMOR_EQUIP_GENERIC;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.of(Items.SLIME_BALL);
        }

        @Override
        public String getName() {
            return "donut_floatie";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0.1f;
        }
    };

    public DonutFloatieItem(Properties properties) {
        super(DONUT, Type.LEGGINGS, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            public static final HumanoidModel<?> MODEL = new DonutFloatieModel<>();

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
                if (slot == EquipmentSlot.LEGS){
                    if (itemStack.getItem() instanceof DonutFloatieItem){
                        return MODEL;
                    }
                }
                return original;
            }

            @Override
            public @NotNull Model getGenericArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
                HumanoidModel<?> replacement = getHumanoidArmorModel(livingEntity, itemStack, slot, original);
                if (replacement == original){
                    return original;
                }

                setModelProperties(replacement, original, slot);
                return replacement;
            }

            @SuppressWarnings("unchecked")
            public static <T extends LivingEntity> void setModelProperties(HumanoidModel<?> replacement, HumanoidModel<T> original, EquipmentSlot slot){
                original.copyPropertiesTo((HumanoidModel<T>) replacement);
                replacement.body.visible = slot == EquipmentSlot.CHEST || slot == EquipmentSlot.LEGS;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String name = stack.getItem().builtInRegistryHolder().key().location().getPath().toString();
        return NillaChocoMod.MOD_ID + ":textures/models/armor/" + name + ".png";
    }

    @Override
    public void onArmorTick(ItemStack stack, Level level, Player player) {
        if (player.isInWater() && level.getBlockState(player.blockPosition().above()) != Blocks.AIR.defaultBlockState()) {
            player.addDeltaMovement(new Vec3(0, 0.1, 0));
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String tooltipName = pStack.getItem().getDescriptionId().concat("_tooltip");
        pTooltipComponents.add(Component.translatable(tooltipName).withStyle(ChatFormatting.GRAY));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        // I'd know what to do here after the constructor's done
    }

}
