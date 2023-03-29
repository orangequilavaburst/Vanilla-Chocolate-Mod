package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

@Mod.EventBusSubscriber(modid = NillaChocoMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItemGroups {

    public static CreativeModeTab vanillaChocolateTab;

    @SubscribeEvent
    public static void registerItemGroups(CreativeModeTabEvent.Register event){

        vanillaChocolateTab = event.registerCreativeModeTab(new ResourceLocation(NillaChocoMod.MOD_ID, "nillachoco_tab"),
                builder -> builder.icon(() -> new ItemStack(ModItems.VANILLA_EXTRACT.get())).title(Component.translatable("creativemodetab.nillachoco_tab")));

    }

}
