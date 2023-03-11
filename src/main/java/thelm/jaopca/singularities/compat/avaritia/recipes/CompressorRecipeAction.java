package thelm.jaopca.singularities.compat.avaritia.recipes;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fox.spiteful.avaritia.crafting.CompressOreRecipe;
import fox.spiteful.avaritia.crafting.CompressorManager;
import fox.spiteful.avaritia.crafting.CompressorRecipe;
import net.minecraft.item.ItemStack;
import thelm.jaopca.api.recipes.IRecipeAction;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

public class CompressorRecipeAction implements IRecipeAction {

	private static final Logger LOGGER = LogManager.getLogger();

	public final String key;
	public final Object input;
	public final int inputAmount;
	public final Object output;
	public final int outputCount;
	public final boolean absolute;

	public CompressorRecipeAction(String key, Object input, int inputAmount, Object output, int outputCount, boolean absolute) {
		this.key = Objects.requireNonNull(key);
		this.input = input;
		this.inputAmount = inputAmount;
		this.output = output;
		this.outputCount = outputCount;
		this.absolute = absolute;
	}

	@Override
	public boolean register() {
		ItemStack stack = MiscHelper.INSTANCE.getItemStack(output, outputCount, false);
		if(stack == null) {
			throw new IllegalArgumentException("Empty output in recipe "+key+": "+output);
		}
		if(input instanceof String) {
			if(!ApiImpl.INSTANCE.getOredict().contains(input)) {
				throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+input);
			}
			CompressorManager.getRecipes().add(new CompressOreRecipe(stack, inputAmount, (String)input, absolute));
		}
		else {
			List<ItemStack> ing = MiscHelper.INSTANCE.getItemStacks(input, 1, true);
			if(ing.isEmpty()) {
				throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+input);
			}
			for(ItemStack in : ing) {
				CompressorManager.getRecipes().add(new CompressorRecipe(stack, inputAmount, in, absolute));
			}
		}
		return true;
	}
}
