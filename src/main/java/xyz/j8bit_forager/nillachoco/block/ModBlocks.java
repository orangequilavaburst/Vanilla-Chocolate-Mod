package xyz.j8bit_forager.nillachoco.block;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, NillaChocoMod.MOD_ID);

    public static final RegistryObject<Block> VANILLA_PLANT = BLOCKS.register("vanilla_plant",
            () -> new VanillaPlantCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<Block> COCOA_BEAN_BLOCK = registerBlock("cocoa_bean_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(0.2F, 3.0F).sound(SoundType.WOOD)));

    public static final RegistryObject<Block> COCOA_HUSK_BLOCK = registerBlock("cocoa_husk_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK).strength(0.2F, 3.0F).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> CHOCOLATE_BLOCK = registerBlock("chocolate_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0f, 8.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> CHOCOLATE_BLOCK_SLAB = registerBlock("chocolate_block_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.CHOCOLATE_BLOCK.get())));

    public static final RegistryObject<Block> CHOCOLATE_BLOCK_STAIRS = registerBlock("chocolate_block_stairs",
            () -> new StairBlock(() -> ModBlocks.CHOCOLATE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(ModBlocks.CHOCOLATE_BLOCK.get())));

    public static final RegistryObject<Block> CHOCOLATE_BLOCK_WALL = registerBlock("chocolate_block_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.CHOCOLATE_BLOCK.get())));

    public static final RegistryObject<Block> WHITE_CHOCOLATE_BLOCK = registerBlock("white_chocolate_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1.0f, 8.0f).requiresCorrectToolForDrops()));

    public static final RegistryObject<Block> WHITE_CHOCOLATE_BLOCK_SLAB = registerBlock("white_chocolate_block_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.CHOCOLATE_BLOCK.get())));

    public static final RegistryObject<Block> WHITE_CHOCOLATE_BLOCK_STAIRS = registerBlock("white_chocolate_block_stairs",
            () -> new StairBlock(() -> ModBlocks.WHITE_CHOCOLATE_BLOCK.get().defaultBlockState(), BlockBehaviour.Properties.copy(ModBlocks.CHOCOLATE_BLOCK.get())));

    public static final RegistryObject<Block> WHITE_CHOCOLATE_BLOCK_WALL = registerBlock("white_chocolate_block_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ModBlocks.CHOCOLATE_BLOCK.get())));

    public static final RegistryObject<Block> VANILLA_CAKE = registerBlock("vanilla_cake",
            () -> new CakeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Block> CHOCOLATE_CAKE = registerBlock("chocolate_cake",
            () -> new CakeBlock(BlockBehaviour.Properties.copy(Blocks.CAKE)));

    public static final RegistryObject<Block> VANILLA_ORCHID = registerBlock("vanilla_orchid",
            () -> new FlowerBlock(MobEffects.LUCK, 1, BlockBehaviour.Properties.copy(Blocks.DANDELION)));

    public static final RegistryObject<Block> POTTED_VANILLA_ORCHID = BLOCKS.register("potted_vanilla_orchid",
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, ModBlocks.VANILLA_ORCHID, BlockBehaviour.Properties.copy(Blocks.POTTED_DANDELION)));

    public static final RegistryObject<Block> VANILLA_SCENTED_CANDLE = registerBlock("vanilla_scented_candle",
            () -> new VanillaScentCandleBlock(BlockBehaviour.Properties.copy(Blocks.CANDLE)));


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

    public static class Tags{

        public static final TagKey<Block> DESTROYED_BY_WARMTH = create("destroyed_by_warmth");
        public static final TagKey<Block> DESTROYED_BY_CHILLING = create("destroyed_by_chilling");

        private static TagKey<Block> create(String location){
            return BlockTags.create(new ResourceLocation(NillaChocoMod.MOD_ID, location));
        }

    }

}
