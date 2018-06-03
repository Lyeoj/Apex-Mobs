package lyeoj.apexmobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiFrozenScreen extends Gui {
	
	public static final ResourceLocation ICE = new ResourceLocation("apexmobs:textures/gui/amice.png");
	
	public void drawIce(Minecraft mc) {
		mc.getTextureManager().bindTexture(ICE);
		ScaledResolution scaledRes = new ScaledResolution(mc);	
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());		
	}

}
