package thelm.jaopca.singularities;

import morph.avaritia.api.IHaloRenderItem;
import morph.avaritia.init.AvaritiaTextures;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thelm.jaopca.api.IOreEntry;
import thelm.jaopca.api.ItemEntry;
import thelm.jaopca.api.item.ItemBase;

public class ItemSingularityBase extends ItemBase implements IHaloRenderItem {

	public ItemSingularityBase(ItemEntry itemEntry, IOreEntry oreEntry) {
		super(itemEntry, oreEntry);
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
