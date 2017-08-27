package thelm.jaopca.singularities;

import java.awt.Color;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import thelm.jaopca.api.JAOPCAApi;
import thelm.jaopca.api.item.ItemBase;

public class ClientProxy extends CommonProxy {

	public static final IItemColor SINGULARITY_ITEM_COLOR = new IItemColor() {
		@Override
		public int getColorFromItemstack(ItemStack stack, int tintIndex) {
			if(stack.getItem() instanceof ItemBase) {
				if(tintIndex == 0) {
					return (new Color(((ItemBase)stack.getItem()).oreEntry.getColor()).darker()).getRGB();
				}
				else {
					return ((ItemBase)stack.getItem()).oreEntry.getColor();
				}
			}
			return 0xFFFFFF;
		}
	};

	@Override
	public void overrideColors() {
		super.overrideColors();
		if(FMLCommonHandler.instance().getEffectiveSide().isClient()) {
			ItemColors itemcolors = Minecraft.getMinecraft().getItemColors();
			for(Item item : JAOPCAApi.ITEMS_TABLE.row("singularity").values()) {
				itemcolors.registerItemColorHandler(SINGULARITY_ITEM_COLOR, item);
			}
		}
	}
}
