package xyz.j8bit_forager.nillachoco.mixin;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.j8bit_forager.nillachoco.effect.ModEffects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "getVisibilityPercent", at = @At("TAIL"), cancellable = true)
    private void aggroEffectModify(Entity pLookingEntity, CallbackInfoReturnable<Double> cir) {
        LivingEntity instance = ((LivingEntity)(Object) this);
        if (instance.hasEffect(ModEffects.ANIMOSITY_EFFECT.get())) {
            cir.setReturnValue(cir.getReturnValue() * 1.5);
        }
        if (instance.hasEffect(ModEffects.CALMING_EFFECT.get())) {
            cir.setReturnValue(cir.getReturnValue() * 0.5);
        }
    }
}
