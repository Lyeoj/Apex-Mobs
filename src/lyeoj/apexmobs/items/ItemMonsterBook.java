package lyeoj.apexmobs.items;

import lyeoj.apexmobs.main.ApexMobs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemMonsterBook extends Item {
	
	public ItemMonsterBook() {
		setUnlocalizedName("monsterbook");
		setRegistryName("monsterbook");
		setCreativeTab(CreativeTabs.MISC);
		setMaxStackSize(1);
	}
	
	/**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ApexMobs.proxy.drawMonsterBook();	
    	return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
