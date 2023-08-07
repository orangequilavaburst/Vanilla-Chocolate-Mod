package xyz.j8bit_forager.nillachoco.potion;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.effect.ModEffects;

public class ModPotions {

    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, NillaChocoMod.MOD_ID);

    public static final RegistryObject<Potion> WARMTH_POTION = POTIONS.register("warmth_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.WARMTH_EFFECT.get(), 600, 0)));

    public static final RegistryObject<Potion> LONG_WARMTH_POTION = POTIONS.register("long_warmth_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.WARMTH_EFFECT.get(), 1800, 0)));

    public static final RegistryObject<Potion> CHILLING_POTION = POTIONS.register("chilling_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CHILLING_EFFECT.get(), 600, 0)));

    public static final RegistryObject<Potion> LONG_CHILLING_POTION = POTIONS.register("long_chilling_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CHILLING_EFFECT.get(), 1800, 0)));

    public static final RegistryObject<Potion> ANIMOSITY_POTION = POTIONS.register("animosity_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ANIMOSITY_EFFECT.get(), 600, 0)));

    public static final RegistryObject<Potion> LONG_ANIMOSITY_POTION = POTIONS.register("long_animosity_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.ANIMOSITY_EFFECT.get(), 1800, 0)));

    public static final RegistryObject<Potion> CALMING_POTION = POTIONS.register("calming_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CALMING_EFFECT.get(), 600, 0)));

    public static final RegistryObject<Potion> LONG_CALMING_POTION = POTIONS.register("long_calming_potion",
            () -> new Potion(new MobEffectInstance(ModEffects.CALMING_EFFECT.get(), 1800, 0)));

    public static void register(IEventBus eb){

        POTIONS.register(eb);

    }

}
