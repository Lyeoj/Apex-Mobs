package lyeoj.apexmobs.main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lyeoj.apexmobs.init.AMEntities;
import lyeoj.apexmobs.init.AMItems;
import lyeoj.apexmobs.init.AMPotionTypes;
import lyeoj.apexmobs.proxy.CommonProxy;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid=ApexMobs.MODID, name=ApexMobs.NAME, version=ApexMobs.VERSION)
public class ApexMobs {
	
	public static final String MODID = "apexmobs";
	public static final String NAME = "Apex Mobs";
	public static final String VERSION = "1.0.0";
	public static final String CLIENT_PROXY = "lyeoj.apexmobs.proxy.ClientProxy";
	public static final String COMMON_PROXY = "lyeoj.apexmobs.proxy.CommonProxy";
	
	@SidedProxy(clientSide = CLIENT_PROXY, serverSide = COMMON_PROXY)
	public static CommonProxy proxy;
	
	@Mod.Instance
	public static ApexMobs instance;
	
	public static final Logger LOGGER = LogManager.getLogger(MODID);

	@SuppressWarnings("unchecked")
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER.info("Starting Pre-Init...");
		
		for(AMEntities.MobInfo info : AMEntities.MOBLIST) {
			EntityRegistry.registerModEntity(new ResourceLocation(ApexMobs.MODID, info.name), 
					info.entityClass, info.name, info.id, ApexMobs.instance, info.trackingRange, 
					info.updateFrequency, info.sendsVelocityUpdates, info.eggP, info.eggS);
		}
		for(AMEntities.MiscEntityInfo info : AMEntities.MISCLIST) {
			EntityRegistry.registerModEntity(new ResourceLocation(ApexMobs.MODID, info.name), 
					info.entityClass, info.name, info.id, ApexMobs.instance, info.trackingRange, 
					info.updateFrequency, info.sendsVelocityUpdates);
		}
		for(AMEntities.EntitySpawnInfo info : AMEntities.SPAWNLIST) {
			EntityRegistry.addSpawn(info.entityClass, info.prob, info.min, info.max, info.type, info.biomes);
		}
		proxy.preInit(event);
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		LOGGER.info("Starting Init...");
		
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_BASE), new ItemStack(AMItems.ITEM_SILVER_SHELL), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), AMPotionTypes.AMHASTE));
		PotionHelper.addMix(AMPotionTypes.AMHASTE, Items.GLOWSTONE_DUST, AMPotionTypes.AMHASTE_STRONG);
		PotionHelper.addMix(AMPotionTypes.AMHASTE, Items.FERMENTED_SPIDER_EYE, AMPotionTypes.AMFATIGUE);
		PotionHelper.addMix(AMPotionTypes.AMHASTE_STRONG, Items.FERMENTED_SPIDER_EYE, AMPotionTypes.AMFATIGUE_STRONG);
		PotionHelper.addMix(AMPotionTypes.AMFATIGUE, Items.GLOWSTONE_DUST, AMPotionTypes.AMFATIGUE_STRONG);
		
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_BASE), new ItemStack(AMItems.ITEM_WATER_SAC), new ItemStack(AMItems.ITEM_POTION_WATER));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_WATER), new ItemStack(Items.GUNPOWDER), new ItemStack(AMItems.ITEM_POTION_WATER_SPLASH));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_WATER_SPLASH), new ItemStack(Items.DRAGON_BREATH), new ItemStack(AMItems.ITEM_POTION_WATER_LINGERING));
		
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_BASE), new ItemStack(Item.getItemFromBlock(Blocks.ICE)), new ItemStack(AMItems.ITEM_POTION_FROZEN));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_FROZEN), new ItemStack(Items.GUNPOWDER), new ItemStack(AMItems.ITEM_POTION_FROZEN_SPLASH));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_FROZEN_SPLASH), new ItemStack(Items.DRAGON_BREATH), new ItemStack(AMItems.ITEM_POTION_FROZEN_LINGERING));
		
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_BASE), new ItemStack(Items.IRON_INGOT), new ItemStack(AMItems.ITEM_POTION_ZAP));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_ZAP), new ItemStack(Items.GUNPOWDER), new ItemStack(AMItems.ITEM_POTION_ZAP_SPLASH));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_ZAP_SPLASH), new ItemStack(Items.DRAGON_BREATH), new ItemStack(AMItems.ITEM_POTION_ZAP_LINGERING));
		
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_BASE), new ItemStack(Items.COAL), new ItemStack(AMItems.ITEM_POTION_POSSESSION));
		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_POSSESSION), new ItemStack(Items.GUNPOWDER), new ItemStack(AMItems.ITEM_POTION_POSSESSION_SPLASH));

		BrewingRecipeRegistry.addRecipe(new ItemStack(AMItems.ITEM_POTION_BASE), new ItemStack(AMItems.ITEM_HUNGRY_SLIMEBALL_BIG), new ItemStack(AMItems.ITEM_NUTRIENTS));

		
		proxy.init(event);
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		LOGGER.info("Starting Post-Init...");
		proxy.postInit(event);
	}		
	
}	
