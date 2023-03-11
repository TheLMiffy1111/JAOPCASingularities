package thelm.jaopca.singularities.compat.avaritia;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.items.IItemFormSettings;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.items.JAOPCAItem;

public class JAOPCASingularityItem extends JAOPCAItem implements IHaloRenderItem {

	public JAOPCASingularityItem(IForm form, IMaterial material, IItemFormSettings settings) {
		super(form, material, settings);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean drawHalo(ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getHaloTexture(ItemStack stack) {
		return ((ItemResource)LudicrousItems.resource).halo[0];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getHaloSize(ItemStack stack) {
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean drawPulseEffect(ItemStack stack) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getHaloColour(ItemStack stack) {
		return 0xFF000000;
	}
}
