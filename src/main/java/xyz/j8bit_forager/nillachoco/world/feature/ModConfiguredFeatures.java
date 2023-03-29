package xyz.j8bit_forager.nillachoco.world.feature;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;

public class ModConfiguredFeatures {

    /*public static final RegistryObject<ConfiguredFeature<?, ?>> VANILLA_ORCHID = CONFIGURED_FEATURES.register("vanilla_orchid",
            () -> new ConfiguredFeature<>(Feature.FLOWER,
                    new RandomPatchConfiguration(16, 6, 2, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                            new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.VANILLA_ORCHID.get()))))));*/

    public static final ResourceKey<ConfiguredFeature<?, ?>> VANILLA_ORCHID_KEY = registerKey("vanilla_orchid");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {

        register(context, VANILLA_ORCHID_KEY, Feature.FLOWER,
                new RandomPatchConfiguration(48, 6, 2,
                        PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.VANILLA_ORCHID.get())))));

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(NillaChocoMod.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
