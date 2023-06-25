package xyz.j8bit_forager.nillachoco;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.ModelUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.extensions.IForgeBakedModel;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;
import xyz.j8bit_forager.nillachoco.client.renderer.entity.ChocolateArrowRenderer;
import xyz.j8bit_forager.nillachoco.effect.ModEffects;
import xyz.j8bit_forager.nillachoco.item.ModItemGroups;
import xyz.j8bit_forager.nillachoco.item.ModItems;
import xyz.j8bit_forager.nillachoco.item.entity.ModEntityTypes;

import java.util.List;
import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NillaChocoMod.MOD_ID)
public class NillaChocoMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "nillachoco";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace

    public NillaChocoMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModItemGroups.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEffects.register(modEventBus);
        ModEntityTypes.register(modEventBus);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

        ((FlowerPotBlock) Blocks.FLOWER_POT).addPlant(ModBlocks.VANILLA_ORCHID.getId(), ModBlocks.POTTED_VANILLA_ORCHID);

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS){

            event.accept(ModBlocks.COCOA_BEAN_BLOCK);
            event.accept(ModBlocks.COCOA_HUSK_BLOCK);
            event.accept(ModBlocks.CHOCOLATE_BLOCK);
            event.accept(ModBlocks.CHOCOLATE_BLOCK_SLAB);
            event.accept(ModBlocks.CHOCOLATE_BLOCK_STAIRS);
            event.accept(ModBlocks.CHOCOLATE_BLOCK_WALL);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK_SLAB);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK_STAIRS);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK_WALL);

        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS){

            event.accept(ModBlocks.VANILLA_ORCHID);

        }
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS){

            event.accept(ModItems.VANILLA_BEAN);
            event.accept(ModItems.VANILLA_EXTRACT);
            event.accept(ModItems.CHOCOLATE_BAR);
            event.accept(ModItems.RAW_DONUT_RING);
            event.accept(ModItems.PLAIN_DONUT);

        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS){

            event.accept(ModItems.SUGAR_COOKIE);
            event.accept(ModItems.CHOCOLATE_BAR);
            event.accept(ModItems.FUDGE_BROWNIE);

            event.accept(ModItems.RAW_DONUT_RING);
            event.accept(ModItems.PLAIN_DONUT);
            event.accept(ModItems.GLAZED_DONUT);
            event.accept(ModItems.VANILLA_FROSTED_DONUT);
            event.accept(ModItems.CHOCOLATE_FROSTED_DONUT);
            event.accept(ModItems.SPRINKLE_DONUT);
            event.accept(ModItems.HONEY_DONUT);
            event.accept(ModItems.SLIME_DONUT);
            event.accept(ModItems.SPIDER_DONUT);

            event.accept(ModBlocks.VANILLA_CAKE);
            event.accept(ModBlocks.CHOCOLATE_CAKE);

        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT){

            event.accept(ModItems.VANILLA_SWORD);
            event.accept(ModItems.CHOCOLATE_RAIN_BOW);

        }
        if (event.getTabKey() == ModItemGroups.VANILLA_CHOCOLATE_TAB.getKey()){

            // vanilla
            event.accept(ModBlocks.VANILLA_ORCHID);
            event.accept(ModItems.VANILLA_BEAN);
            event.accept(ModItems.VANILLA_EXTRACT);
            event.accept(ModItems.SUGAR_COOKIE);

            // chocolate
            event.accept(ModItems.CHOCOLATE_BAR);
            event.accept(Items.COOKIE);
            event.accept(ModItems.FUDGE_BROWNIE);

            event.accept(ModBlocks.COCOA_BEAN_BLOCK);
            event.accept(ModBlocks.COCOA_HUSK_BLOCK);
            event.accept(ModBlocks.CHOCOLATE_BLOCK);
            event.accept(ModBlocks.CHOCOLATE_BLOCK_SLAB);
            event.accept(ModBlocks.CHOCOLATE_BLOCK_STAIRS);
            event.accept(ModBlocks.CHOCOLATE_BLOCK_WALL);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK_SLAB);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK_STAIRS);
            event.accept(ModBlocks.WHITE_CHOCOLATE_BLOCK_WALL);

            // donuts
            event.accept(ModItems.RAW_DONUT_RING);
            event.accept(ModItems.PLAIN_DONUT);
            event.accept(ModItems.GLAZED_DONUT);
            event.accept(ModItems.VANILLA_FROSTED_DONUT);
            event.accept(ModItems.CHOCOLATE_FROSTED_DONUT);
            event.accept(ModItems.SPRINKLE_DONUT);
            event.accept(ModItems.HONEY_DONUT);
            event.accept(ModItems.SLIME_DONUT);
            event.accept(ModItems.SPIDER_DONUT);

            // cake
            event.accept(ModBlocks.VANILLA_CAKE);
            event.accept(ModBlocks.CHOCOLATE_CAKE);

            // weapons
            event.accept(ModItems.VANILLA_SWORD);
            event.accept(ModItems.CHOCOLATE_RAIN_BOW);

            // other blocks
            event.accept(ModBlocks.VANILLA_SCENTED_CANDLE);

        }

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            ItemBlockRenderTypes.setRenderLayer(ModBlocks.VANILLA_PLANT.get(), RenderType.cutout());

        }

        @SubscribeEvent
        public static void entityRendererEvent(EntityRenderersEvent.RegisterRenderers event){
            event.registerEntityRenderer(ModEntityTypes.CHOCOLATE_ARROW_ENTITY.get(), ChocolateArrowRenderer::new);
        }

    }
}
