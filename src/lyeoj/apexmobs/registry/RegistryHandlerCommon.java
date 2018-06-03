package lyeoj.apexmobs.registry;

import lyeoj.apexmobs.init.AMBlocks;
import lyeoj.apexmobs.init.AMItems;
import lyeoj.apexmobs.init.AMPotion;
import lyeoj.apexmobs.init.AMPotionTypes;
import lyeoj.apexmobs.init.AMSoundEvents;
import lyeoj.apexmobs.main.ApexMobs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ApexMobs.MODID)
public class RegistryHandlerCommon {
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		
		event.getRegistry().registerAll(AMItems.ITEMLIST);
		
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(AMBlocks.BLOCKLIST);
	}
	
	@SubscribeEvent
	public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
		
		event.getRegistry().register(AMSoundEvents.SNIPER_SHOOT);
		event.getRegistry().register(AMSoundEvents.SNIPER_PREPARE);
		event.getRegistry().register(AMSoundEvents.ZAPCHARGE);
		event.getRegistry().register(AMSoundEvents.ZAPPED);
	}
	
	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event) {
		
		event.getRegistry().register(AMPotion.WATERLOGGED);
		event.getRegistry().register(AMPotion.POSSESSED);
		event.getRegistry().register(AMPotion.FROZEN);
		event.getRegistry().register(AMPotion.TARRED);
		event.getRegistry().register(AMPotion.ZAPPED);
		event.getRegistry().register(AMPotion.INKED);
		event.getRegistry().register(AMPotion.MUDDY);
		
		event.getRegistry().register(AMPotion.WATERWALKING);
	}
	
	@SubscribeEvent
	public static void registerPotionTypes(RegistryEvent.Register<PotionType> event) {
		
		event.getRegistry().register(AMPotionTypes.TARRED);
		event.getRegistry().register(AMPotionTypes.FROZEN);
		event.getRegistry().register(AMPotionTypes.ZAPPED);
		event.getRegistry().register(AMPotionTypes.AMHASTE);
		event.getRegistry().register(AMPotionTypes.AMHASTE_STRONG);
		event.getRegistry().register(AMPotionTypes.AMFATIGUE);
		event.getRegistry().register(AMPotionTypes.AMFATIGUE_STRONG);
		
	}
	
}
