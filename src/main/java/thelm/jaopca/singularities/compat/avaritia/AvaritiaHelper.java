package thelm.jaopca.singularities.compat.avaritia;

import thelm.jaopca.singularities.compat.avaritia.recipes.CatalystIngredientRecipeAction;
import thelm.jaopca.singularities.compat.avaritia.recipes.CompressorRecipeAction;
import thelm.jaopca.utils.ApiImpl;

public class AvaritiaHelper {

	public static final AvaritiaHelper INSTANCE = new AvaritiaHelper();

	public boolean registerCompressorRecipe(String key, Object input, int inputAmount, Object output, int outputCount, boolean absolute) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CompressorRecipeAction(key, input, inputAmount, output, outputCount, absolute));
	}

	public boolean registerCatalystIngredient(String key, Object input) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CatalystIngredientRecipeAction(key, input));
	}
}
