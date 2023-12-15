package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.nbt.CompoundTag;
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

public class DyeableDonutFloatieItem extends DonutFloatieItem implements DyeableLeatherItem {

    String TAG_COLOR = "color";
    String TAG_DISPLAY = "display";

    public DyeableDonutFloatieItem(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        String name = stack.getItem().builtInRegistryHolder().key().location().getPath().toString();
        String overlay = (type != null && type == "overlay") ? "_overlay" : "";
        return NillaChocoMod.MOD_ID + ":textures/models/armor/" + name + overlay + ".png";
    }

    @Override
    public int getColor(ItemStack pStack) {
        CompoundTag compoundtag = pStack.getTagElement(TAG_DISPLAY);
        return compoundtag != null && compoundtag.contains(TAG_COLOR, 99)  ? compoundtag.getInt(TAG_COLOR) : 16777215;
    }

}
