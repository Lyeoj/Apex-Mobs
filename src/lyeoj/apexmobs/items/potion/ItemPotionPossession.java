package lyeoj.apexmobs.items.potion;

import java.util.List;

import javax.annotation.Nullable;

import lyeoj.apexmobs.init.AMPotion;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPotionPossession extends AMPotionItem {
	
	public ItemPotionPossession() {
		setUnlocalizedName("potionpossession");
		setRegistryName("potionpossession");
	}
	
	/**
     * Called when the player finishes using this Item (E.g. finishes eating.). Not called when the player stops using
     * the Item before the action is complete.
     */
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
    {
        EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer)entityLiving : null;

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
            stack.shrink(1);
        }

        if (entityplayer instanceof EntityPlayerMP)
        {
            CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)entityplayer, stack);
        }

        if (!worldIn.isRemote)
        {
            if(entityLiving.isPotionActive(AMPotion.POSSESSED)) {
            	entityLiving.addPotionEffect(new PotionEffect(AMPotion.POSSESSED, 9999999, entityLiving.getActivePotionEffect(AMPotion.POSSESSED).getAmplifier() + 1));
            } else {
            	entityLiving.addPotionEffect(new PotionEffect(AMPotion.POSSESSED, 9999999));
            }
            entityLiving.setHealth(entityLiving.getMaxHealth());
        }

        if (entityplayer == null || !entityplayer.capabilities.isCreativeMode)
        {
            if (stack.isEmpty())
            {
                return new ItemStack(Items.GLASS_BOTTLE);
            }

            if (entityplayer != null)
            {
                entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
        }

        return stack;
    }
    
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	tooltip.add("Spooky ghost");
    }

}
