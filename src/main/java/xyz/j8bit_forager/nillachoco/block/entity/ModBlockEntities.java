package xyz.j8bit_forager.nillachoco.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, NillaChocoMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<VanillaCandleBlockEntity>> VANILLA_CANDLE =
            BLOCK_ENTITIES.register("vanilla_scented_candle", () ->
                    BlockEntityType.Builder.of(VanillaCandleBlockEntity::new, ModBlocks.VANILLA_SCENTED_CANDLE.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
