package thelm.jaopca.singularities.compat.avaritia.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import fox.spiteful.avaritia.items.ItemResource;
import fox.spiteful.avaritia.items.LudicrousItems;
import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import thelm.jaopca.api.forms.IForm;
import thelm.jaopca.api.items.IItemFormSettings;
import thelm.jaopca.api.materials.IMaterial;
import thelm.jaopca.items.JAOPCAItem;
import thelm.jaopca.singularities.compat.avaritia.client.renderer.JAOPCAHaloItemRenderer;

public class JAOPCASingularityItem extends JAOPCAItem implements IHaloRenderItem {

	public JAOPCASingularityItem(IForm form, IMaterial material, IItemFormSettings settings) {
		super(form, material, settings);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IItemRenderer getRenderer() {
		return JAOPCAHaloItemRenderer.INSTANCE;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean drawHalo(ItemStack stack) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getHaloTexture(ItemStack stack) {
		return ((ItemResource)LudicrousItems.resource).halo[0];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getHaloSize(ItemStack stack) {
		return 4;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean drawPulseEffect(ItemStack stack) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getHaloColour(ItemStack stack) {
		return 0xFF000000;
	}
}
