package lyeoj.apexmobs.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class GuiInkScreen extends Gui {
public static final ResourceLocation INK = new ResourceLocation("apexmobs:textures/gui/amink.png");
	
	public void drawInk(Minecraft mc) {
		mc.getTextureManager().bindTexture(INK);
		ScaledResolution scaledRes = new ScaledResolution(mc);	
		Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, scaledRes.getScaledWidth(), scaledRes.getScaledHeight(), scaledRes.getScaledWidth(), scaledRes.getScaledHeight());		
	}
}
