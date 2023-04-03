package xyz.j8bit_forager.nillachoco.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.PartialNBTIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.j8bit_forager.nillachoco.item.ModItems;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {

        // vanilla extract

        CompoundTag waterBottleTag = new CompoundTag();
        waterBottleTag.putString("Potion", ForgeRegistries.POTIONS.getKey(Potions.WATER).toString());

        /*ItemLike vanillaExtractResult = ModItems.VANILLA_EXTRACT.get();
        ShapelessRecipeBuilder vanillaExtractBuilder = new ShapelessRecipeBuilder(RecipeCategory.MISC, vanillaExtractResult, 1)
                .requires(PartialNBTIngredient.of(Items.POTION, waterBottleTag))
                .requires(Ingredient.of(ModItems.VANILLA_BEAN.get()))
                .requires(Ingredient.of(ModItems.VANILLA_BEAN.get()));

        vanillaExtractBuilder.save(pWriter);*/

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.VANILLA_EXTRACT.get(), 1)
                .requires(PartialNBTIngredient.of(Items.POTION, waterBottleTag))
                .requires(Ingredient.of(ModItems.VANILLA_BEAN.get()))
                .requires(Ingredient.of(ModItems.VANILLA_BEAN.get()))
                .unlockedBy("has_vanilla_beans", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.VANILLA_BEAN.get()).build()))
                .save(pWriter);

    }
}
