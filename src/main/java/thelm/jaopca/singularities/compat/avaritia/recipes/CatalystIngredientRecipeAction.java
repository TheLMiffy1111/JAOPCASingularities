package thelm.jaopca.singularities.compat.avaritia.recipes;

import java.util.List;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fox.spiteful.avaritia.crafting.Grinder;
import net.minecraft.item.ItemStack;
import thelm.jaopca.api.recipes.IRecipeAction;
import thelm.jaopca.utils.MiscHelper;

public class CatalystIngredientRecipeAction implements IRecipeAction {

	private static final Logger LOGGER = LogManager.getLogger();

	public final String key;
	public final Object input;

	public CatalystIngredientRecipeAction(String key, Object input) {
		this.key = Objects.requireNonNull(key);
		this.input = input;
	}

	@Override
	public boolean register() {
		List<ItemStack> ing = MiscHelper.INSTANCE.getItemStacks(input, 1, true);
		if(ing.isEmpty()) {
			throw new IllegalArgumentException("Empty ingredient in recipe "+key+": "+input);
		}
		return Grinder.catalyst.getInput().add(ing);
	}
}
