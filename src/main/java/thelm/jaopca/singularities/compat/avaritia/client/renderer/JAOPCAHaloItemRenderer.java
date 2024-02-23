package thelm.jaopca.singularities.compat.avaritia.client.renderer;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import fox.spiteful.avaritia.render.IHaloRenderItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import thelm.jaopca.client.renderer.JAOPCAItemRenderer;

public class JAOPCAHaloItemRenderer extends JAOPCAItemRenderer {

	public static final JAOPCAHaloItemRenderer INSTANCE = new JAOPCAHaloItemRenderer();
	public Random rand = new Random();

	@Override
	public boolean handleRenderType(ItemStack stack, ItemRenderType type) {
		if(stack.getItem() instanceof IHaloRenderItem) {
			IHaloRenderItem haloRenderItem = (IHaloRenderItem)stack.getItem();
			return type == ItemRenderType.INVENTORY && (haloRenderItem.drawHalo(stack) || haloRenderItem.drawPulseEffect(stack));
		}
		return super.handleRenderType(stack, type);
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack stack, Object... data) {
		if(type == ItemRenderType.INVENTORY && stack.getItem() instanceof IHaloRenderItem) {
			IHaloRenderItem haloRenderItem = (IHaloRenderItem)stack.getItem();
			boolean renderHalo = haloRenderItem.drawHalo(stack);
			boolean renderPulse = haloRenderItem.drawPulseEffect(stack);
			int spread = haloRenderItem.getHaloSize(stack);
			IIcon halo = haloRenderItem.getHaloTexture(stack);
			int haloColour = haloRenderItem.getHaloColour(stack);
			RenderItem r = RenderItem.getInstance();
			Minecraft mc = Minecraft.getMinecraft();
			Tessellator t = Tessellator.instance;
			GL11.glPushMatrix();
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			RenderHelper.enableGUIStandardItemLighting();
			GL11.glDisable(GL11.GL_ALPHA_TEST);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			if(renderHalo) {
				float ca = (float)(haloColour >> 24 & 255) / 255F;
				float cr = (float)(haloColour >> 16 & 255) / 255F;
				float cg = (float)(haloColour >> 8 & 255) / 255F;
				float cb = (float)(haloColour & 255) / 255F;
				GL11.glColor4f(cr, cg, cb, ca);
				t.startDrawingQuads();
				t.addVertexWithUV(0-spread, 0-spread, 0, halo.getMinU(), halo.getMinV());
				t.addVertexWithUV(0-spread, 16+spread, 0, halo.getMinU(), halo.getMaxV());
				t.addVertexWithUV(16+spread, 16+spread, 0, halo.getMaxU(), halo.getMaxV());
				t.addVertexWithUV(16+spread, 0-spread, 0, halo.getMaxU(), halo.getMinV());
				t.draw();
			}
			if(renderPulse) {
				GL11.glPushMatrix();
				double xs = (rand.nextGaussian()*0.15) + 0.95;
				double ox = (1-xs)/2;
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glTranslated(ox*16, ox*16, 1);
				GL11.glScaled(xs, xs, 1);
				IIcon icon = stack.getItem().getIcon(stack, 0);
				t.startDrawingQuads();
				t.setColorRGBA_F(1, 1, 1, 0.6F);
				t.addVertexWithUV(0-ox, 0-ox, 0, icon.getMinU(), icon.getMinV());
				t.addVertexWithUV(0-ox, 16+ox, 0, icon.getMinU(), icon.getMaxV());
				t.addVertexWithUV(16+ox, 16+ox, 0, icon.getMaxU(), icon.getMaxV());
				t.addVertexWithUV(16+ox, 0-ox, 0, icon.getMaxU(), icon.getMinV());
				t.draw();
				GL11.glPopMatrix();
			}
			r.renderItemIntoGUI(mc.fontRenderer, mc.getTextureManager(), stack, 0, 0, true);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			r.renderWithColor = true;
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glPopMatrix();
		}
		else {
			super.renderItem(type, stack, data);
		}
	}
}
