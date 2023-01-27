package thelm.jaopca.singularities.compat.avaritia;

import net.minecraft.util.ResourceLocation;
import thelm.jaopca.singularities.compat.avaritia.recipes.CatalystIngredientRecipeAction;
import thelm.jaopca.singularities.compat.avaritia.recipes.CompressorRecipeAction;
import thelm.jaopca.utils.ApiImpl;

public class AvaritiaHelper {

	public static final AvaritiaHelper INSTANCE = new AvaritiaHelper();

	public boolean registerCompressorRecipe(ResourceLocation key, Object input, int inputAmount, Object output, int outputCount, boolean absolute) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CompressorRecipeAction(key, input, inputAmount, output, outputCount, absolute));
	}

	public boolean registerCatalystIngredient(ResourceLocation key, Object input) {
		return ApiImpl.INSTANCE.registerRecipe(key, new CatalystIngredientRecipeAction(key, input));
	}
}
