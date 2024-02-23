package thelm.jaopca.singularities.compat.avaritia;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Multimap;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.item.EnumRarity;
import thelm.jaopca.api.config.IDynamicSpecConfig;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.forms.IFormRequest;
import thelm.jaopca.api.helpers.IMiscHelper;
import thelm.jaopca.api.items.IItemFormType;
import thelm.jaopca.api.items.IItemInfo;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.api.materials.MaterialType;
import thelm.jaopca.api.modules.IModule;
import thelm.jaopca.api.modules.IModuleData;
import thelm.jaopca.api.modules.JAOPCAModule;
import thelm.jaopca.items.ItemFormType;
import thelm.jaopca.singularities.compat.avaritia.items.JAOPCASingularityItem;
import thelm.jaopca.utils.ApiImpl;
import thelm.jaopca.utils.MiscHelper;

@JAOPCAModule(modDependencies = "Avaritia")
public class AvaritiaSingularityModule implements IModule {

	public static final Set<String> BLACKLIST = new TreeSet<>(Arrays.asList(
			"Clay", "Copper", "Gold", "Infinity", "Iron", "Lapis", "Lead", "Neutronium", "Nickel", "Quartz",
			"Redstone", "Silver", "Tin"));

	static {
		if(Loader.isModLoaded("thermsingul")) {
			Collections.addAll(BLACKLIST, "Enderium", "Lumium", "Mithril", "Platinum", "Signalum");
		}
		if(Loader.isModLoaded("universalsingularities")) {
			Collections.addAll(BLACKLIST, "Aluminium", "Aluminum", "Brass", "Bronze", "Charcoal", "Coal", "Diamond",
					"Electrum", "Emerald", "Invar", "Magnesium", "Osmium", "Peridot", "Ruby", "Sapphire", "Steel",
					"Titanium", "Tungsten", "Uranium", "Zinc");
			if(Loader.isModLoaded("bigReactors")) {
				Collections.addAll(BLACKLIST, "Blutonium", "Cyanite", "Graphite", "Ludicrite", "Yellorium");
			}
			if(Loader.isModLoaded("draconicEvolution")) {
				Collections.addAll(BLACKLIST, "Draconium", "DraconiumAwakened");
			}
			if(Loader.isModLoaded("enderIO")) {
				Collections.addAll(BLACKLIST, "ConductiveIron", "DarkSteel", "ElectricalSteel", "EnergeticAlloy",
						"PulsatingIron", "RedstoneAlloy", "Soularium", "VibrantAlloy");
			}
			if(Loader.isModLoaded("extraPlanets")) {
				Collections.addAll(BLACKLIST, "BlueGem", "Carbon", "Crystal", "Palladium", "RedGem", "WhiteGem");
			}
			if(Loader.isModLoaded("extraTiC")) {
				Collections.addAll(BLACKLIST, "Fairy", "Pokefennium");
			}
			if(Loader.isModLoaded("extraUtilities")) {
				Collections.addAll(BLACKLIST, "Unstable");
			}
			if(Loader.isModLoaded("mekanism")) {
				Collections.addAll(BLACKLIST, "RefinedGlowstone", "RefinedObsidian");
			}
			if(Loader.isModLoaded("metallurgy")) {
				Collections.addAll(BLACKLIST, "Amordrine", "Angmallen", "Bitumen", "BlackSteel", "Celenegil",
						"DamascusSteel", "Desichalkos", "Haderoth", "Hepatizon", "Inolashite", "Phosphorus",
						"Potash", "Quicksilver", "Saltpeter", "ShadowSteel", "Sulfur", "Tartarite");
			}
			if(Loader.isModLoaded("pneumaticCraft")) {
				Collections.addAll(BLACKLIST, "IronCompressed");
			}
			if(Loader.isModLoaded("projectRed")) {
				Collections.addAll(BLACKLIST, "Electrotine");
			}
			if(Loader.isModLoaded("redstoneArsenal")) {
				Collections.addAll(BLACKLIST, "CrystalFlux");
				Collections.addAll(BLACKLIST, "ElectrumFlux");
			}
			if(Loader.isModLoaded("tinkersConstruct")) {
				Collections.addAll(BLACKLIST, "AluminiumBrass", "AluminumBrass", "Alumite", "Ardite", "Cobalt",
						"Ender", "Glue", "Manyullyn");
			}
		}
	}

	private final IForm singularityForm = ApiImpl.INSTANCE.newForm(this, "avaritia_singularity", ItemFormType.INSTANCE).
			setMaterialTypes(MaterialType.values()).setSecondaryName("singularity").setDefaultMaterialBlacklist(BLACKLIST).
			setSettings(ItemFormType.INSTANCE.getNewSettings().
					setDisplayRarityFunction(m->EnumRarity.uncommon).
					setItemCreator(JAOPCASingularityItem::new));

	private Map<IMaterial, IDynamicSpecConfig> configs;

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
	public Set<MaterialType> getMaterialTypes() {
		return EnumSet.allOf(MaterialType.class);
	}

	@Override
	public Set<String> getDefaultMaterialBlacklist() {
		return BLACKLIST;
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
}
