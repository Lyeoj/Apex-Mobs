package lyeoj.apexmobs.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemTest extends Item {
	
	public ItemTest() {
		setUnlocalizedName("testitem");
		setRegistryName("testitem");
		setCreativeTab(CreativeTabs.MISC);
		setMaxStackSize(64);
	}
	
	/**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        if(!playerIn.capabilities.allowFlying) {
        	playerIn.capabilities.allowFlying = true;
        } else {
        	playerIn.capabilities.allowFlying = false;
        }
    	return super.onItemRightClick(worldIn, playerIn, handIn);
    }

}
