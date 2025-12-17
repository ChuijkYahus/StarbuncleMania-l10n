package alexthw.starbunclemania.wixie;

import com.hollingsworth.arsnouveau.api.recipe.MultiRecipeWrapper;
import com.hollingsworth.arsnouveau.api.recipe.ShapedHelper;
import com.hollingsworth.arsnouveau.common.util.PotionUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WaterMilkRecipeWrapper extends MultiRecipeWrapper {

    public record RecipeCacheKey(Item stack, boolean hasMilk, boolean hasWater) {
    }

    public static Map<RecipeCacheKey, MultiRecipeWrapper> RECIPE_CACHE = new HashMap<>();

    public static MultiRecipeWrapper fromStack(ItemStack stack, Level level, boolean hasMilk, boolean hasWater) {
        WaterMilkRecipeWrapper wrapper = new WaterMilkRecipeWrapper();
        RecipeCacheKey key = new RecipeCacheKey(stack.getItem(), hasMilk, hasWater);
        if (RECIPE_CACHE.containsKey(key)) {
            return RECIPE_CACHE.get(key);
        }
        if (level.getServer() == null) return wrapper;
        for (RecipeHolder<?> rh : level.getServer().getRecipeManager().getRecipes()) {
            Recipe<?> r = rh.value();
            if (r.getResultItem(level.registryAccess()) == null || r.getResultItem(level.registryAccess()).getItem() != stack.getItem())
                continue;

            if (r instanceof ShapedRecipe) {
                ShapedHelper helper = new ShapedHelper((ShapedRecipe) r);
                for (List<Ingredient> iList : helper.getPossibleRecipes()) {
                    removeMilkWater(level, hasMilk, hasWater, wrapper, r, iList);
                }
            }

            if (r instanceof ShapelessRecipe) {
                List<Ingredient> ingredients = r.getIngredients();
                removeMilkWater(level, hasMilk, hasWater, wrapper, r, ingredients);
            }
        }
        RECIPE_CACHE.put(key, wrapper);

        return wrapper;
    }

    public boolean needMilk = false;
    public boolean needWater = false;

    private static void removeMilkWater(Level level, boolean hasMilk, boolean hasWater, WaterMilkRecipeWrapper wrapper, Recipe<?> r, List<Ingredient> ingredients) {
        boolean needMilk = false;
        boolean needWater = false;
        // Create a mutable copy of the ingredients list, non-null lists size can't be changed
        ingredients = new ArrayList<>(ingredients);
        if (hasMilk) {
            needMilk = ingredients.removeIf(i -> i.test(Items.MILK_BUCKET.getDefaultInstance()));
        }
        if (hasWater) {
            needWater = ingredients.removeIf(i -> i.test(Items.WATER_BUCKET.getDefaultInstance()) || i.test(PotionUtil.getPotion(Potions.WATER)));
        }
        wrapper.addRecipe(ingredients, r.getResultItem(level.registryAccess()), r, needWater, needMilk);
    }

    public boolean addRecipe(List<Ingredient> recipe, ItemStack outputStack, Recipe<?> iRecipe, boolean needWater, boolean needMilk) {
        this.needMilk = this.needMilk || needMilk;
        this.needWater = this.needWater || needWater;
        return super.addRecipe(recipe, outputStack, iRecipe);
    }

}
