package xyz.j8bit_forager.nillachoco.item;

import com.ibm.icu.impl.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.builder.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DonutItem extends Item {

    List<Pair<MobEffectInstance, Float>> effects;

    public DonutItem(Properties pProperties, float nutrition, float saturation, MobEffect... effects) {
        super(pProperties);
        pProperties.food(new FoodProperties.Builder().build()); // ...?
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        // I'd know what to do here after the constructor's done
    }

}