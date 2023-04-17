package xyz.j8bit_forager.nillachoco.item.entity;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import xyz.j8bit_forager.nillachoco.NillaChocoMod;
import xyz.j8bit_forager.nillachoco.item.entity.custom.ChocolateArrowEntity;

public class ModEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, NillaChocoMod.MOD_ID);

    public static final RegistryObject<EntityType<ChocolateArrowEntity>> CHOCOLATE_ARROW_ENTITY =
            ENTITY_TYPES.register("chocolate_arrow",
                    () -> EntityType.Builder.of(ChocolateArrowEntity::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20).build(new ResourceLocation(NillaChocoMod.MOD_ID, "chocolate_arrow").toString()));

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }

}
