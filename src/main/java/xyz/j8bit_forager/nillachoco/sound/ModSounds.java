package xyz.j8bit_forager.nillachoco.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, NillaChocoMod.MOD_ID);

    public static final RegistryObject<SoundEvent> YOSHI_COOKIE_SOUND = registerFixedRangeSoundEvent("yoshi_cookie");

    private static RegistryObject<SoundEvent> registerFixedRangeSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(NillaChocoMod.MOD_ID, name), 0.0f));
    }

    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(NillaChocoMod.MOD_ID, name)));
    }

    public static void register(IEventBus eb){

        SOUND_EVENTS.register(eb);

    }

}
