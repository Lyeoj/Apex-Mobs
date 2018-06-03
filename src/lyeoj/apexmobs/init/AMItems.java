package lyeoj.apexmobs.init;

import lyeoj.apexmobs.items.ItemHungrySlimeball;
import lyeoj.apexmobs.items.ItemHungrySlimeballBig;
import lyeoj.apexmobs.items.ItemMonsterBook;
import lyeoj.apexmobs.items.ItemNutrients;
import lyeoj.apexmobs.items.ItemSniperBow;
import lyeoj.apexmobs.items.ItemTarball;
import lyeoj.apexmobs.items.ItemTest;
import lyeoj.apexmobs.items.ItemWaterSac;
import lyeoj.apexmobs.items.potion.AMPotionItem;
import lyeoj.apexmobs.items.potion.AMThrownPotionItem;
import lyeoj.apexmobs.items.potion.ItemPotionBase;
import lyeoj.apexmobs.items.potion.ItemPotionPossession;
import lyeoj.apexmobs.items.potion.ItemSplashPotionPossession;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class AMItems {
	
	public static final Item ITEM_TEST = new ItemTest();
	public static final Item ITEM_MONSTER_BOOK = new ItemMonsterBook();
	public static final Item ITEM_WATER_SAC = new ItemWaterSac();
	public static final Item ITEM_HUNGRY_SLIMEBALL = new ItemHungrySlimeball();
	public static final Item ITEM_HUNGRY_SLIMEBALL_BIG = new ItemHungrySlimeballBig();
	public static final Item ITEM_NUTRIENTS = new ItemNutrients();
	public static final Item ITEM_SILVER_SHELL = new Item().setUnlocalizedName("silvershell").setRegistryName("silvershell").setCreativeTab(CreativeTabs.MISC);
	public static final Item ITEM_TARBALL = new ItemTarball();
	public static final Item ITEM_SNIPER_BOW = new ItemSniperBow();
		
	public static final Item ITEM_POTION_BASE = new ItemPotionBase();
	public static final Item ITEM_POTION_POSSESSION = new ItemPotionPossession();
	public static final Item ITEM_POTION_POSSESSION_SPLASH = new ItemSplashPotionPossession();
	public static final Item ITEM_POTION_FROZEN = new AMPotionItem(1200, 0).setEffect(AMPotion.FROZEN).setUnlocalizedName("potionfrozen").setRegistryName("potionfrozen");
	public static final Item ITEM_POTION_FROZEN_LINGERING = new AMThrownPotionItem(400, 0).setLingering(true).setColor(0x93FFE4).setEffect(AMPotion.FROZEN).setUnlocalizedName("potionfrozenlingering").setRegistryName("potionfrozenlingering");
	public static final Item ITEM_POTION_FROZEN_SPLASH = new AMThrownPotionItem(1200, 0).setColor(0x93FFE4).setEffect(AMPotion.FROZEN).setUnlocalizedName("potionfrozensplash").setRegistryName("potionfrozensplash");
	public static final Item ITEM_POTION_WATER = new AMPotionItem(2400, 0).setEffect(AMPotion.WATERLOGGED).setUnlocalizedName("potionwaterlogged").setRegistryName("potionwaterlogged");
	public static final Item ITEM_POTION_WATER_LINGERING = new AMThrownPotionItem(1200, 0).setLingering(true).setColor(0x1166CC).setEffect(AMPotion.WATERLOGGED).setUnlocalizedName("potionwaterloggedlingering").setRegistryName("potionwaterloggedlingering");
	public static final Item ITEM_POTION_WATER_SPLASH = new AMThrownPotionItem(2400, 0).setColor(0x1166CC).setEffect(AMPotion.WATERLOGGED).setUnlocalizedName("potionwaterloggedsplash").setRegistryName("potionwaterloggedsplash");
	public static final Item ITEM_POTION_ZAP = new AMPotionItem(2400, 0).setColor(0xBBBB11).setEffect(AMPotion.ZAPPED).setUnlocalizedName("potionzap").setRegistryName("potionzap");
	public static final Item ITEM_POTION_ZAP_LINGERING = new AMThrownPotionItem(1200, 0).setLingering(true).setColor(0xBBBB11).setEffect(AMPotion.ZAPPED).setUnlocalizedName("potionzaplingering").setRegistryName("potionzaplingering");
	public static final Item ITEM_POTION_ZAP_SPLASH = new AMThrownPotionItem(2400, 0).setColor(0xBBBB11).setEffect(AMPotion.ZAPPED).setUnlocalizedName("potionzapsplash").setRegistryName("potionzapsplash");
	
	public static final Item[] ITEMLIST = {
		ITEM_TEST,
		ITEM_MONSTER_BOOK,
		ITEM_SILVER_SHELL,
		ITEM_WATER_SAC,
		ITEM_HUNGRY_SLIMEBALL,
		ITEM_HUNGRY_SLIMEBALL_BIG,
		ITEM_NUTRIENTS,
		ITEM_TARBALL,
		ITEM_SNIPER_BOW,
		
		ITEM_POTION_BASE,
		ITEM_POTION_POSSESSION,
		ITEM_POTION_POSSESSION_SPLASH,
		ITEM_POTION_FROZEN,
		ITEM_POTION_FROZEN_SPLASH,
		ITEM_POTION_FROZEN_LINGERING,
		ITEM_POTION_WATER,
		ITEM_POTION_WATER_SPLASH,
		ITEM_POTION_WATER_LINGERING,
		ITEM_POTION_ZAP,
		ITEM_POTION_ZAP_LINGERING,
		ITEM_POTION_ZAP_SPLASH,
		
		AMBlocks.MUD_LAYER_ITEM,
		AMBlocks.TEST_BLOCK_ITEM,
		AMBlocks.TAR_LAYER_ITEM
	};

	
}
