package xyz.j8bit_forager.nillachoco.world.feature;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.VegetationPatchFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> VANILLA_ORCHID_PLACED_KEY = createKey("vanilla_orchid_placed");

    public static final DeferredRegister<PlacedFeature> PLACED_FEATURES =
            DeferredRegister.create(Registries.PLACED_FEATURE, NillaChocoMod.MOD_ID);

    /*public static final RegistryObject<PlacedFeature> VANILLA_ORCHID_PLACED = PLACED_FEATURES.register("vanilla_orchid_placed",
            () -> new PlacedFeature(ModConfiguredFeatures.VANILLA_ORCHID.getHolder().get(), List.of(RarityFilter.onAverageOnceEvery(16),
                    InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())));*/

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, VANILLA_ORCHID_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.VANILLA_ORCHID_KEY),
                ImmutableList.<PlacementModifier>builder().add(RarityFilter.onAverageOnceEvery(6),
                        InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome())
                        .add(BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(ModBlocks.VANILLA_ORCHID.get().defaultBlockState(), BlockPos.ZERO))).build());

    }

    public static void register(IEventBus eventBus) {
        PLACED_FEATURES.register(eventBus);
    }

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(NillaChocoMod.MOD_ID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 PlacementModifier... modifiers) {
        register(context, key, configuration, List.of(modifiers));
    }

}
