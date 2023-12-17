package xyz.j8bit_forager.nillachoco.block;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

public class ModBannerPatterns {

    public static final DeferredRegister<BannerPattern> BANNER_PATTERNS = DeferredRegister.create(Registries.BANNER_PATTERN, NillaChocoMod.MOD_ID);

    public static final TagKey<BannerPattern> PATTERN_ITEM_STAR = TagKey.create(Registries.BANNER_PATTERN, new ResourceLocation(NillaChocoMod.MOD_ID,"pattern_item/star"));

    public static final RegistryObject<BannerPattern> STAR = BANNER_PATTERNS.register("star",
            () -> new BannerPattern(PATTERN_ITEM_STAR.toString()));

    public static void register(IEventBus eb){

        BANNER_PATTERNS.register(eb);

    }

}
