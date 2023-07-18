package xyz.j8bit_forager.nillachoco.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, NillaChocoMod.MOD_ID);

    public static final RegistryObject<MobEffect> WARMTH_EFFECT = MOB_EFFECTS.register("warmth",
            () -> new WarmthEffect(MobEffectCategory.NEUTRAL, 0xde2e02));
    public static final RegistryObject<MobEffect> CHILLING_EFFECT = MOB_EFFECTS.register("chilling",
            () -> new ChillingEffect(MobEffectCategory.NEUTRAL, 0x37e5ff));

    public static void register(IEventBus bus){

        MOB_EFFECTS.register(bus);

    }

}
