package thelm.jaopca.singularities.compat.avaritia.recipes;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import morph.avaritia.recipe.AvaritiaRecipeManager;
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import morph.avaritia.recipe.extreme.IExtremeRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import thelm.jaopca.api.recipes.IRecipeAction;
import thelm.jaopca.utils.MiscHelper;

public class CatalystIngredientRecipeAction implements IRecipeAction {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final ResourceLocation CATALYST_RECIPE_LOCATION = new ResourceLocation("avaritia:items/infinity_catalyst");
	private static IExtremeRecipe catalystRecipe;
	private static Field inputField;

	static {
		try {
			inputField = ExtremeShapelessRecipe.class.getDeclaredField("input");
			inputField.setAccessible(true);
		}
		catch(Exception e) {
			LOGGER.error("Could not access extreme shapeless recipe ingredient list.", e);
		}
	}

	public final ResourceLocation key;
	public final Object input;

	public CatalystIngredientRecipeAction(ResourceLocation key, Object input) {
		this.key = Objects.requireNonNull(key);
		this.input = input;
	}

	@Override
	public boolean register() {
		if(catalystRecipe == null) {
			catalystRecipe = AvaritiaRecipeManager.EXTREME_RECIPES.get(CATALYST_RECIPE_LOCATION);
		}
		if(!(catalystRecipe instanceof ExtremeShapelessRecipe)) {
			throw new IllegalStateException("Infinity Catalyst recipe is not of type ExtremeShapelessRecipe");
		}
		List<Ingredient> ingredients;
		try {
			ingredients = (List<Ingredient>)inputField.get(catalystRecipe);
		}
		catch(Exception e) {
			throw new IllegalStateException("Could not access extreme shapeless recipe ingredient list.", e);
		}
		Ingredient ing = MiscHelper.INSTANCE.getIngredient(input);
		if(ing == null) {
			throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+input);
		}
		return ingredients.add(ing);
	}
}
