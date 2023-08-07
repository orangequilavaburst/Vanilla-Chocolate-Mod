package xyz.j8bit_forager.nillachoco;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import xyz.j8bit_forager.nillachoco.block.ModBlocks;
import xyz.j8bit_forager.nillachoco.client.renderer.entity.ChocolateArrowRenderer;
import xyz.j8bit_forager.nillachoco.client.renderer.entity.VanillaProjectileRenderer;
import xyz.j8bit_forager.nillachoco.effect.ModEffects;
import xyz.j8bit_forager.nillachoco.entity.ModEntityTypes;
import xyz.j8bit_forager.nillachoco.item.ModItemGroups;
import xyz.j8bit_forager.nillachoco.item.ModItems;
import xyz.j8bit_forager.nillachoco.loot.ModLootModifiers;
import xyz.j8bit_forager.nillachoco.particle.ModParticles;
import xyz.j8bit_forager.nillachoco.particle.custom.RainIndicatorParticle;
import xyz.j8bit_forager.nillachoco.potion.ModPotions;
import xyz.j8bit_forager.nillachoco.sound.ModSounds;

import java.util.Comparator;
import java.util.List;

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
        ModParticles.register(modEventBus);
        ModPotions.register(modEventBus);
        ModSounds.register(modEventBus);
        ModLootModifiers.register(modEventBus);

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
        ComposterBlock.COMPOSTABLES.put(ModItems.VANILLA_BEAN.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(Item.byBlock(ModBlocks.VANILLA_ORCHID.get()), 0.65f);
        ComposterBlock.COMPOSTABLES.put(Item.byBlock(ModBlocks.COCOA_BEAN_BLOCK.get()), 1.0f);
        ComposterBlock.COMPOSTABLES.put(Item.byBlock(ModBlocks.COCOA_HUSK_BLOCK.get()), 1.0f);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_BAR.get(), 0.75f);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_EGG.get(), 0.8f);
        ComposterBlock.COMPOSTABLES.put(ModItems.SUGAR_COOKIE.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.YOSHI_COOKIE.get(), 1.0f);
        ComposterBlock.COMPOSTABLES.put(ModItems.RAW_DONUT_RING.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.PLAIN_DONUT.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(ModItems.GLAZED_DONUT.get(), 0.9f);
        ComposterBlock.COMPOSTABLES.put(ModItems.VANILLA_FROSTED_DONUT.get(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(ModItems.CHOCOLATE_FROSTED_DONUT.get(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(ModItems.SLIME_DONUT.get(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(ModItems.SPIDER_DONUT.get(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(ModItems.SPRINKLE_DONUT.get(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(ModItems.HONEY_DONUT.get(), 0.95f);
        ComposterBlock.COMPOSTABLES.put(ModItems.FUDGE_BROWNIE.get(), 1.0f);
        ComposterBlock.COMPOSTABLES.put(ModItems.VANILLA_CREAM_PIE.get(), 1.0f);
        ComposterBlock.COMPOSTABLES.put(Item.byBlock(ModBlocks.VANILLA_CAKE.get()), 1.0f);
        ComposterBlock.COMPOSTABLES.put(Item.byBlock(ModBlocks.CHOCOLATE_CAKE.get()), 1.0f);

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
            event.accept(ModItems.YOSHI_COOKIE);
            event.accept(ModItems.CHOCOLATE_BAR);
            event.accept(ModItems.FUDGE_BROWNIE);
            event.accept(ModItems.VANILLA_CREAM_PIE);

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
            event.accept(ModItems.CHOCOLATE_ARROW);
            event.accept(ModItems.CHOCOLATE_EGG);

        }
        if (event.getTabKey() == ModItemGroups.VANILLA_CHOCOLATE_TAB.getKey()){

            // vanilla
            event.accept(ModBlocks.VANILLA_ORCHID);
            event.accept(ModItems.VANILLA_BEAN);
            event.accept(ModItems.VANILLA_EXTRACT);
            event.accept(ModItems.VANILLA_CREAM_PIE);
            event.accept(ModItems.SUGAR_COOKIE);
            event.accept(ModItems.YOSHI_COOKIE);

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
            event.accept(ModItems.CHOCOLATE_ARROW);
            event.accept(ModItems.CHOCOLATE_EGG);

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
            event.registerEntityRenderer(ModEntityTypes.VANILLA_PROJECTILE_ENTITY.get(), VanillaProjectileRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.CHOCOLATE_EGG_ENTITY.get(), ThrownItemRenderer::new);
        }

        @SubscribeEvent
        public static void registerParticleFactories(RegisterParticleProvidersEvent event){
            Minecraft.getInstance().particleEngine.register(ModParticles.RAIN_INDICATOR.get(), RainIndicatorParticle.Provider::new);
        }

    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class ModEvents{

        @SubscribeEvent
        public static void selectTarget(LivingChangeTargetEvent event){

            Player instancePlayer = Minecraft.getInstance().player;

            LivingEntity mob = event.getEntity();
            LivingEntity originalTarget = event.getOriginalTarget();
            LivingEntity newTarget = event.getNewTarget();

            if (instancePlayer != null && mob != null){

                double targetRange = mob.getAttributes().getValue(Attributes.FOLLOW_RANGE);
                AABB area = mob.getBoundingBox().inflate(targetRange);
                List<LivingEntity> animosityList = mob.level().getEntitiesOfClass(LivingEntity.class, area).stream()
                        .filter(entry -> entry != mob)
                        //.filter(entry -> entry.hasLineOfSight(mob))
                        .filter(LivingEntity::canBeSeenAsEnemy)
                        .filter(entry -> entry.position().distanceTo(mob.position()) <= targetRange*2)
                        .filter(entity -> !((entity instanceof Player player) && player.isCreative()) )
                        .filter(entry -> entry.hasEffect(ModEffects.ANIMOSITY_EFFECT.get()))
                        .sorted(Comparator.comparing(entry -> entry.position().distanceTo(mob.position())))
                        .toList();
                List<LivingEntity> calmingList = mob.level().getEntitiesOfClass(LivingEntity.class, area).stream()
                        .filter(entry -> entry != mob)
                        //.filter(entry -> entry.hasLineOfSight(mob))
                        .filter(LivingEntity::canBeSeenAsEnemy)
                        .filter(entry -> entry.position().distanceTo(mob.position()) > targetRange/2 && entry.position().distanceTo(mob.position()) <= targetRange)
                        .filter(entity -> !((entity instanceof Player player) && player.isCreative()) )
                        .filter(entry -> entry.hasEffect(ModEffects.CALMING_EFFECT.get()))
                        .sorted(Comparator.comparing(entry -> entry.position().distanceTo(mob.position())))
                        .toList();

                if (animosityList.size() > 0){
                    LivingEntity closestAnimosity = animosityList.get(0);
                    event.setNewTarget(closestAnimosity);
                }

                if (calmingList.size() > 0){
                    LivingEntity closestCalming = calmingList.get(0);
                    if (newTarget == closestCalming){
                        event.setCanceled(true);
                    }
                }

                /*

                instancePlayer.displayClientMessage(
                        Component.literal("Entity ")
                                .append(mob.getDisplayName())
                                .append(Component.literal("'s target changed from "))
                                .append((originalTarget != null) ? originalTarget.getDisplayName() : Component.literal("nothing"))
                                .append(Component.literal(" to "))
                                .append((newTarget != null) ? newTarget.getDisplayName() : Component.literal("nothing"))
                        , false);
                 */

            }

        }

        @SubscribeEvent
        public static void onItemUseFinish(LivingEntityUseItemEvent.Finish event){

            if (event.getEntity() instanceof Player player) {
                ItemStack itemStack = event.getItem();
                if (itemStack.is(ModItems.YOSHI_COOKIE.get())) {
                    player.playSound(ModSounds.YOSHI_COOKIE_SOUND.get(), 0.5f, 1.0f);
                    player.setDeltaMovement(player.getDeltaMovement().add(0.0, 0.25, 0.0));
                }
            }

        }

        @SubscribeEvent
        public static void villagerTradesSetup(VillagerTradesEvent event){

            List<VillagerTrades.ItemListing> noviceTrades = event.getTrades().get(1);
            List<VillagerTrades.ItemListing> apprenticeTrades = event.getTrades().get(2);
            List<VillagerTrades.ItemListing> journeymanTrades = event.getTrades().get(3);
            List<VillagerTrades.ItemListing> expertTrades = event.getTrades().get(4);
            List<VillagerTrades.ItemListing> masterTrades = event.getTrades().get(5);

            if (event.getType() == VillagerProfession.WEAPONSMITH) {

                masterTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(48, 64)), new ItemStack(ModItems.CHOCOLATE_BAR.get(), rand.nextInt(48, 64)), new ItemStack(ModItems.VANILLA_SWORD.get(), 1), 1, 8, 0.02f));

            }
            if (event.getType() == VillagerProfession.FLETCHER){

                journeymanTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(4, 8)), new ItemStack(Items.ARROW, rand.nextInt(4, 8)), new ItemStack(ModItems.CHOCOLATE_ARROW.get(), rand.nextInt(4, 8)), 16, 8, 0.02f));
                masterTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(48, 64)), new ItemStack(ModItems.CHOCOLATE_BAR.get(), rand.nextInt(48, 64)), new ItemStack(ModItems.CHOCOLATE_RAIN_BOW.get(), 1), 1, 8, 0.02f));

            }
            if (event.getType() == VillagerProfession.FARMER){

                journeymanTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(8, 12)), new ItemStack(ModItems.VANILLA_BEAN.get(), rand.nextInt(1, 3)), 16, 8, 0.02f));

            }
            if (event.getType() == VillagerProfession.CLERIC){

                apprenticeTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, rand.nextInt(8, 12)), new ItemStack(ModItems.VANILLA_EXTRACT.get(), rand.nextInt(1, 3)), 64, 8, 0.02f));

            }

        }

        @SubscribeEvent
        public static void wanderingTraderTradesSetup(WandererTradesEvent event){

            List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
            List<VillagerTrades.ItemListing> specialTrades = event.getRareTrades();
            trades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(ModBlocks.VANILLA_ORCHID.get(), 1), 10, 8, 0.0f));
            trades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(ModItems.CHOCOLATE_BAR.get(), rand.nextInt(2, 6)), 10, 8, 0.0f));
            trades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 1), new ItemStack(ModItems.VANILLA_BEAN.get(), 1), 10, 8, 0.0f));
            trades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 2), new ItemStack(ModItems.RAW_DONUT_RING.get(), 1), 10, 8, 0.0f));
            specialTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 3), new ItemStack(ModItems.YOSHI_COOKIE.get(), 1), 10, 8, 0.0f));
            specialTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 64), new ItemStack(ModItems.VANILLA_SWORD.get(), 1), 10, 8, 0.0f));
            specialTrades.add((trader, rand) -> new MerchantOffer(new ItemStack(Items.EMERALD, 64), new ItemStack(ModItems.CHOCOLATE_RAIN_BOW.get(), 1), 10, 8, 0.0f));

        }

    }

}
