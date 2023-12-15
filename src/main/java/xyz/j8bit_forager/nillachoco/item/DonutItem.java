package xyz.j8bit_forager.nillachoco.item;

import com.mojang.datafixers.util.Pair;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

import java.util.ArrayList;
import java.util.List;

public class DonutItem extends Item {

    //private List<MobEffectInstance> donutEffects;

    public DonutItem(Properties pProperties/*, int nutrition, float saturation, MobEffectInstance... effects*/) {
        super(pProperties);
        /*
        donutEffects = new ArrayList<MobEffectInstance>();
        FoodProperties.Builder fp = new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation);
        if (effects != null) {
            for (MobEffectInstance e : effects) {
                fp.effect(() -> e, 1.0f);
                donutEffects.add(e);
            }
        }
         */

    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        // I'd know what to do here after the constructor's done
        if (!pStack.getFoodProperties(null).getEffects().isEmpty()) {
            List<MobEffectInstance> mobEffects = new ArrayList<MobEffectInstance>();
            for (Pair<MobEffectInstance, Float> e : pStack.getFoodProperties(null).getEffects()){
                mobEffects.add(e.getFirst());
            }
            PotionUtils.addPotionTooltip(mobEffects, pTooltipComponents, 1.0f);
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

}