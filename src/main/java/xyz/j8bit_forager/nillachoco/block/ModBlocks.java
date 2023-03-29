package xyz.j8bit_forager.nillachoco.block;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NillaChocoMod.MOD_ID);

    public static final RegistryObject<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.0f, 8.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> WHITE_CHOCOLATE_BLOCK = registerBlock("white_chocolate_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.0f, 8.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> VANILLA_ORCHID = registerBlock("vanilla_orchid",
            () -> new FlowerBlock(MobEffects.LUCK, 1, BlockBehaviour.Properties.copy(Blocks.DANDELION)));

    public static final RegistryObject<Block> POTTED_VANILLA_ORCHID = BLOCKS.register("potted_vanilla_orchid",
            () -> new FlowerPotBlock(() ->  (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.VANILLA_ORCHID, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));

    private static <T extends Block>RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block){

        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));

    }

    private static <T extends Block>RegistryObject<T> registerBlock(String name, Supplier<T> block){

        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;

    }

    public static void register(IEventBus eb){

        BLOCKS.register(eb);

    }

}
