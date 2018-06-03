package lyeoj.apexmobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiMuddyScreen extends Gui {
	
	public static final ResourceLocation MUD = new ResourceLocation("apexmobs:textures/gui/ammud.png");
	
	public void drawMud(Minecraft mc) {
		mc.getTextureManager().bindTexture(MUD);
		ScaledResolution scaledRes = new ScaledResolution(mc);	
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());		
	}

}
