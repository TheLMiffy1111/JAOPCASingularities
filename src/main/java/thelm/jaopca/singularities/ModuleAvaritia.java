package thelm.jaopca.singularities;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import codechicken.lib.util.TransformUtils;
import morph.avaritia.client.render.item.HaloRenderItem;
import morph.avaritia.init.ModItems;
import morph.avaritia.recipe.AvaritiaRecipeManager;
import morph.avaritia.recipe.compressor.CompressorRecipe;
import morph.avaritia.recipe.compressor.ICompressorRecipe;
import morph.avaritia.recipe.extreme.ExtremeShapelessRecipe;
import morph.avaritia.recipe.extreme.IExtremeRecipe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.EnumOreType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.item.ItemProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleAvaritia extends ModuleBase {

	public static final ItemProperties SINGULARITY_PROPERTIES = new ItemProperties().setRarity(EnumRarity.UNCOMMON).setItemClass(ItemSingularityBase.class);
	public static final ItemEntry SINGULARITY_ENTRY = new ItemEntry(EnumEntryType.ITEM, "singularity", new ModelResourceLocation("jaopca:singularity#inventory"), ImmutableList.of(
			"Iron", "Gold", "Copper", "Tin", "Lead", "Silver", "Nickel", "Lapis", "Quartz", "Diamond", "Emerald", "Redstone", "ElectrumFlux", "Platinum", "Iridium", "Infinity"
			)).setProperties(SINGULARITY_PROPERTIES).
			setOreTypes(EnumOreType.values());

	@Override
	public String getName() {
		return "avaritia";
	}

	@Override
	public List<String> getDependencies() {
		return Lists.<String>newArrayList("block");
	}

	@Override
	public List<ItemEntry> getItemRequests() {
		return Lists.<ItemEntry>newArrayList(SINGULARITY_ENTRY);
	}

	@Override
	public void init() {
		JAOPCASingularities.proxy.overrideColors();

		for(IOreEntry entry : JAOPCAApi.ENTRY_NAME_TO_ORES_MAP.get("singularity")) {
			addCompressorRecipe(Utils.getOreStack("singularity", entry, 1), Utils.energyReciprocalI(entry, 300), false, "block"+entry.getOreName());
			addCatalystIngredient(Utils.getOreStack("singularity", entry, 1));
		}
	}

	public static void register() {
		ModuleAvaritia mod = new ModuleAvaritia();
		JAOPCAApi.registerModule(mod);
		MinecraftForge.EVENT_BUS.register(mod);
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
		ModelResourceLocation location = SINGULARITY_ENTRY.itemModelLocation;
		HaloRenderItem wrappedModel = new HaloRenderItem(TransformUtils.DEFAULT_ITEM, event.getModelRegistry().getObject(location));
		event.getModelRegistry().putObject(location, wrappedModel);
	}

	public static void addCompressorRecipe(ItemStack result, int cost, boolean absolute, Object... ingredients) {
		NonNullList<Ingredient> list = NonNullList.<Ingredient>create();
		for(Object ingredient : ingredients) {
			list.add(CraftingHelper.getIngredient(ingredient));
		}
		ICompressorRecipe recipe = new CompressorRecipe(result, cost, absolute, list);
		AvaritiaRecipeManager.COMPRESSOR_RECIPES.put(Utils.getNameForRecipe(result, ingredients), recipe);
	}

	public static void addCatalystIngredient(Object input) {
		Optional<IExtremeRecipe> recOp = AvaritiaRecipeManager.EXTREME_RECIPES.values().stream().filter(recipe->recipe.getRecipeOutput().isItemEqual(ModItems.infinity_catalyst)).findAny();
		if(!recOp.isPresent()) {
			JAOPCAApi.LOGGER.warn("Avaritia's Infinity Catalyst recipe is not present.");
		}
		IExtremeRecipe rec = recOp.get();
		if(rec instanceof ExtremeShapelessRecipe) {
			try {
				ExtremeShapelessRecipe recipe = (ExtremeShapelessRecipe)rec;
				Field inputField = ExtremeShapelessRecipe.class.getDeclaredField("input");
				inputField.setAccessible(true);
				NonNullList<Ingredient> list = (NonNullList<Ingredient>)inputField.get(recipe);
				list.add(CraftingHelper.getIngredient(input));
			}
			catch(NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				new RuntimeException("Something went wrong with Avaritia's Infinity Catalyst recipe.", e).printStackTrace();
			}
		}
		else {
			JAOPCAApi.LOGGER.warn("Avaritia's Infinity Catalyst recipe is not of type ExtremeShapelessRecipe.");
		}
	}
}
