package lyeoj.apexmobs.proxy;

import lyeoj.apexmobs.gui.GuiMonsterBook;
import lyeoj.apexmobs.init.AMEntities;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

public class ClientProxy extends CommonProxy {
	
	@SuppressWarnings("unchecked")
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
		for(AMEntities.MobInfo info : AMEntities.MOBLIST) {
			RenderingRegistry.registerEntityRenderingHandler(info.entityClass,  info.factory);
		}
		for(AMEntities.MiscEntityInfo info : AMEntities.MISCLIST) {
			RenderingRegistry.registerEntityRenderingHandler(info.entityClass,  info.factory);
		}
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		
		super.init(event);
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
	}
	
	@Override
	public void serverStarting(FMLServerStartingEvent event) {
		super.serverStarting(event);
	}
	@Override
	public void serverStopping(FMLServerStoppingEvent event) {
		super.serverStopping(event);
	}
	
	public void drawMonsterBook() {
		Minecraft.getMinecraft().displayGuiScreen(new GuiMonsterBook());
	}
}
