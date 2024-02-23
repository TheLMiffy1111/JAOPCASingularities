package thelm.jaopca.singularities.compat.avaritia;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

import codechicken.lib.util.TransformUtils;
import morph.avaritia.client.render.item.HaloRenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.registry.IRegistry;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.config.IDynamicSpecConfig;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormSettings;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.items.IItemModelFunctionCreator;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.singularities.compat.avaritia.items.JAOPCASingularityItem;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

@JAOPCAModule(modDependencies = "avaritia")
public class AvaritiaSingularityModule implements IModule {

	public static final Set<String> BLACKLIST = new TreeSet<>(Arrays.asList(
			"Copper", "Diamond", "ElectrumFlux", "Emerald", "Gold", "Infinity", "Iridium", "Iron", "Lapis",
			"Lead", "Neutronium", "Nickel", "Platinum", "Quartz", "Redstone", "Silver", "Tin"));

	private final IForm singularityForm = ApiImpl.INSTANCE.newForm(this, "avaritia_singularity", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("singularity").setDefaultMaterialBlacklist(BLACKLIST).
			setSettings(ItemFormType.INSTANCE.getNewSettings().
					setDisplayRarityFunction(m->EnumRarity.UNCOMMON).
					setItemCreator(JAOPCASingularityItem::new));

	private Map<IMaterial, IDynamicSpecConfig> configs;

	public AvaritiaSingularityModule() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public String getName() {
		return "avaritia_singularity";
	}

	@Override
	public boolean isPassive() {
		return true;
	}

	@Override
	public Multimap<Integer, String> getModuleDependencies() {
		ImmutableSetMultimap.Builder<Integer, String> builder = ImmutableSetMultimap.builder();
		builder.put(0, "block");
		return builder.build();
	}

	@Override
	public List<IFormRequest> getFormRequests() {
		return Collections.singletonList(singularityForm.toRequest());
	}

	@Override
	public void defineMaterialConfig(IModuleData moduleData, Map<IMaterial, IDynamicSpecConfig> configs) {
		this.configs = configs;
	}

	@Override
	public void onInit(IModuleData moduleData, FMLInitializationEvent event) {
		AvaritiaHelper helper = AvaritiaHelper.INSTANCE;
		IMiscHelper miscHelper = MiscHelper.INSTANCE;
		IItemFormType itemFormType = ItemFormType.INSTANCE;
		for(IMaterial material : singularityForm.getMaterials()) {
			String blockOredict = miscHelper.getOredictName("block", material.getName());
			IItemInfo singularityInfo = itemFormType.getMaterialFormInfo(singularityForm, material);

			IDynamicSpecConfig config = configs.get(material);
			int inputAmount = config.getDefinedInt("avaritia_singularity.inputAmount", 300, "The base amount of blocks required to create one singularity.");
			boolean addToCatalyst = config.getDefinedBoolean("avaritia_singularity.addToCatalyst", true, "Should the singularity be added to the Infinity Catalyst recipe.");

			helper.registerCompressorRecipe(
					miscHelper.getRecipeKey("avaritia_singularity.block_to_singularity", material.getName()),
					blockOredict, inputAmount, singularityInfo, 1, false);
			if(addToCatalyst) {
				helper.registerCatalystIngredient(
						miscHelper.getRecipeKey("avaritia_singularity.singularity_in_catalyst", material.getName()),
						singularityInfo);
			}
		}
	}

	@Override
	public Map<String, String> getLegacyRemaps() {
		ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
		builder.put("singularity", "avaritia_singularity");
		return builder.build();
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onModelBake(ModelBakeEvent event) {
		Set<ModelResourceLocation> locations = new TreeSet<>();
		IItemFormType itemFormType = ItemFormType.INSTANCE;
		IItemFormSettings settings = (IItemFormSettings)singularityForm.getSettings();
		IItemModelFunctionCreator modelFuncCreator = settings.getItemModelFunctionCreator();
		for(IMaterial material : singularityForm.getMaterials()) {
			IItemInfo singularityInfo = itemFormType.getMaterialFormInfo(singularityForm, material);
			locations.addAll(modelFuncCreator.create(singularityInfo.getMaterialFormItem(), settings).getRight());
		}
		IRegistry<ModelResourceLocation, IBakedModel> registry = event.getModelRegistry();
		for(ModelResourceLocation location : locations) {
			HaloRenderItem wrappedModel = new HaloRenderItem(TransformUtils.DEFAULT_ITEM, registry.getObject(location));
			registry.putObject(location, wrappedModel);
		}
	}
}
