package thelm.jaopcasingularities;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import codechicken.lib.util.TransformUtils;
import morph.avaritia.client.render.item.HaloRenderItem;
import morph.avaritia.init.Recipes;
import morph.avaritia.recipe.compressor.CompressorManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.EnumEntryType;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.ModuleBase;
import thelm.jaopca.api.item.ItemProperties;
import thelm.jaopca.api.utils.Utils;

public class ModuleAvaritia extends ModuleBase {

	public static final ItemProperties SINGULARITY_PROPERTIES = new ItemProperties().setRarity(EnumRarity.UNCOMMON).setItemClass(ItemSingularityBase.class);
	public static final ItemEntry SINGULARITY_ENTRY = new ItemEntry(EnumEntryType.ITEM, "singularity", new ModelResourceLocation("jaopcasingularities:singularity#inventory"), ImmutableList.of(
			"Iron", "Gold", "Copper", "Tin", "Lead", "Silver", "Nickel"
			)).setItemProperties(SINGULARITY_PROPERTIES);

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
			CompressorManager.addOreRecipe(Utils.getOreStack("singularity", entry, 1), energyIReciprocal(entry, 300), "block"+entry.getOreName());
			Recipes.catalyst.getInput().add(Utils.getOreStack("singularity", entry, 1));
		}
	}

	public static int energyIReciprocal(IOreEntry entry, double energy) {
		return (int)((1/entry.getEnergyModifier())*energy);
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
}
