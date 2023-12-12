package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.entity.model.ApronModel;

import java.util.function.Consumer;

public class ApronItem extends DyeableArmorItem implements DyeableLeatherItem {

    public static ArmorMaterial APRON = new ArmorMaterial() {

        @Override
        public int getDurabilityForType(Type ArmorType) {
            return 100;
        }

        @Override
        public int getDefenseForType(Type ArmorType) {
            return 1;
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
            return Ingredient.of(ItemTags.WOOL);
        }

        @Override
        public String getName() {
            return "apron";
        }

        @Override
        public float getToughness() {
            return 0;
        }

        @Override
        public float getKnockbackResistance() {
            return 0;
        }
    };

    String TAG_COLOR = "color";
    String TAG_DISPLAY = "display";

    public ApronItem(Properties properties) {
        super(APRON, Type.CHESTPLATE, properties);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot slot, HumanoidModel<?> original) {
                return new ApronModel<>();
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
                //replacement.head.visible = true;
                replacement.body.visible = true;
                //replacement.rightArm.visible = true;
                //replacement.leftArm.visible = true;
                //replacement.rightLeg.visible = true;
                //replacement.leftLeg.visible = true;
            }
        });
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return NillaChocoMod.MOD_ID + ":textures/models/armor/apron_layer.png";
    }

    @Override
    public int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement("display");
        return compoundtag != null && compoundtag.contains("color", 99)  ? compoundtag.getInt("color") : 16777215;
    }

}
