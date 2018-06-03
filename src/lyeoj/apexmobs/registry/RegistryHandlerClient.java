package lyeoj.apexmobs.registry;

import lyeoj.apexmobs.init.AMBlocks;
import lyeoj.apexmobs.init.AMItems;
import lyeoj.apexmobs.main.ApexMobs;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = ApexMobs.MODID, value = Side.CLIENT)
public class RegistryHandlerClient {
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		
		for(int i = 0; i < AMItems.ITEMLIST.length; i++) {
			registerItemModel(AMItems.ITEMLIST[i]);
		}		

		for(int i = 0; i < AMBlocks.BLOCKLIST.length; i++) {
			registerBlockModel(AMBlocks.BLOCKLIST[i]);
		}
	}
	
	private static void registerItemModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
	
	private static void registerBlockModel(Block block) {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "inventory"));
	}
	
}
