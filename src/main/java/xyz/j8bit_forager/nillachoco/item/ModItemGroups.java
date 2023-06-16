package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

@Mod.EventBusSubscriber(modid = NillaChocoMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemGroups {

    public static DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NillaChocoMod.MOD_ID);
    public static RegistryObject<CreativeModeTab> VANILLA_CHOCOLATE_TAB = CREATIVE_MODE_TABS.register("nillachoco_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.VANILLA_EXTRACT.get())).title(Component.translatable("creativemodetab.nillachoco_tab")).build());

    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
