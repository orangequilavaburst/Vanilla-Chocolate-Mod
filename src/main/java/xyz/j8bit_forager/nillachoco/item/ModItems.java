package xyz.j8bit_forager.nillachoco.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, NillaChocoMod.MOD_ID);

    public static final RegistryObject<Item> VANILLA_BEAN = ITEMS.register("vanilla_bean",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VANILLA_EXTRACT = ITEMS.register("vanilla_extract",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CHOCOLATE_BAR = ITEMS.register("chocolate_bar",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder().nutrition(1).build())));

    public static final RegistryObject<Item> RAW_DONUT_RING = ITEMS.register("raw_donut_ring",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3)
                            .saturationMod(2.0f).build()
                    )));

    public static final RegistryObject<Item> PLAIN_DONUT = ITEMS.register("plain_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationMod(7.0f).build()
                    )));

    public static final RegistryObject<Item> GLAZED_DONUT = ITEMS.register("glazed_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(6)
                            .saturationMod(6.0f).build()
                    )));

    public static final RegistryObject<Item> VANILLA_FROSTED_DONUT = ITEMS.register("vanilla_frosted_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 600), 1.0f)
                            .alwaysEat().build()
                    )));

    public static final RegistryObject<Item> CHOCOLATE_FROSTED_DONUT = ITEMS.register("chocolate_frosted_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 600), 1.0f)
                            .alwaysEat().build()
                    )));

    public static final RegistryObject<Item> SPRINKLE_DONUT = ITEMS.register("sprinkle_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1.0f)
                            .alwaysEat().build()
                    )));

    public static final RegistryObject<Item> HONEY_DONUT = ITEMS.register("honey_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600), 1.0f)
                            .alwaysEat().build()
                    )));

    public static final RegistryObject<Item> SLIME_DONUT = ITEMS.register("slime_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(7)
                            .saturationMod(6.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.JUMP, 600, 1), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1), 1.0f)
                            .alwaysEat().build()
                    )));

    public static final RegistryObject<Item> SPIDER_DONUT = ITEMS.register("spider_donut",
            () -> new DonutItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(8)
                            .saturationMod(8.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 1200), 1.0f)
                            .alwaysEat().build()
                    )));

    public static void register(IEventBus eb){

        ITEMS.register(eb);

    }

}
