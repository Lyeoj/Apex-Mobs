package lyeoj.apexmobs.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemWaterSac extends Item {
	
	public ItemWaterSac() {
		setUnlocalizedName("watersac");
		setRegistryName("watersac");
		setCreativeTab(CreativeTabs.MISC);
		setMaxStackSize(64);
	}

}
