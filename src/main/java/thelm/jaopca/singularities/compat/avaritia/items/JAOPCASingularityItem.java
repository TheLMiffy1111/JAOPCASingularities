package thelm.jaopca.singularities.compat.avaritia.items;

import morph.avaritia.api.IHaloRenderItem;
import morph.avaritia.init.AvaritiaTextures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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
	public boolean shouldDrawHalo(ItemStack stack) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TextureAtlasSprite getHaloTexture(ItemStack stack) {
		return AvaritiaTextures.HALO;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getHaloSize(ItemStack stack) {
		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getHaloColour(ItemStack stack) {
		return 0xFF000000;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldDrawPulse(ItemStack stack) {
		return false;
	}
}
