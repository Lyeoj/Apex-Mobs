package lyeoj.apexmobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class GuiAMPotions extends Gui {
	
	public static final ResourceLocation AMPOTIONS = new ResourceLocation("apexmobs:textures/gui/aminventory.png");
	
	public void renderInventory(int x, int y, PotionEffect effect, Minecraft mc) {
		mc.getTextureManager().bindTexture(AMPOTIONS);
		int i1 = effect.getPotion().getStatusIconIndex();
        drawTexturedModalRect(x + 6, y + 7, 0 + i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);
	}
	
	public void renderHUD(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(AMPOTIONS);
		int i1 = effect.getPotion().getStatusIconIndex();
		drawTexturedModalRect(x + 3, y + 3, i1 % 8 * 18, 198 + i1 / 8 * 18, 18, 18);		
	}

}
