package xyz.j8bit_forager.nillachoco.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.entity.custom.ChocolateArrowEntity;
import xyz.j8bit_forager.nillachoco.entity.custom.ChocolateEggEntity;
import xyz.j8bit_forager.nillachoco.entity.custom.VanillaProjectileEntity;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NillaChocoMod.MOD_ID);

    public static final RegistryObject<EntityType<ChocolateArrowEntity>> CHOCOLATE_ARROW_ENTITY =
            ENTITY_TYPES.register("chocolate_arrow",
                    () -> EntityType.Builder.<ChocolateArrowEntity>of(ChocolateArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build(new ResourceLocation(NillaChocoMod.MOD_ID, "chocolate_arrow").toString()));

    public static final RegistryObject<EntityType<ChocolateEggEntity>> CHOCOLATE_EGG_ENTITY =
            ENTITY_TYPES.register("chocolate_egg",
                    () -> EntityType.Builder.<ChocolateEggEntity>of(ChocolateEggEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build(new ResourceLocation(NillaChocoMod.MOD_ID, "chocolate_egg").toString()));

    public static final RegistryObject<EntityType<VanillaProjectileEntity>> VANILLA_PROJECTILE_ENTITY =
            ENTITY_TYPES.register("vanilla_projectile",
                    () -> EntityType.Builder.<VanillaProjectileEntity>of(VanillaProjectileEntity::new, MobCategory.MISC).sized(1.0f, 0.5f).clientTrackingRange(4).updateInterval(20).build(new ResourceLocation(NillaChocoMod.MOD_ID, "vanilla_projectile").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}
