package lyeoj.apexmobs.items.potion;

import lyeoj.apexmobs.entity.projectile.EntityAMPotion;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class AMThrownPotionItem extends AMPotionItem {
	
	private boolean lingering;
	
	public AMThrownPotionItem(int dur, int amp)
    {
        super(dur, amp);
    }
	
	public AMThrownPotionItem setLingering(boolean lingeringIn) {
		this.lingering = lingeringIn;
		return this;
	}
	
	public boolean isLingering() {
		return lingering;
	}
	
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        ItemStack itemstack1 = playerIn.capabilities.isCreativeMode ? itemstack.copy() : itemstack.splitStack(1);
        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_SPLASH_POTION_THROW, SoundCategory.PLAYERS, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!worldIn.isRemote)
        {
            EntityAMPotion entitypotion = new EntityAMPotion(worldIn, playerIn, itemstack1).setPotionColor(this.getColor()).setPotionEffect(new PotionEffect(this.getEffect(), this.getDuration(), this.getAmplifier()));
            entitypotion.setHeadingFromThrower(playerIn, playerIn.rotationPitch, playerIn.rotationYaw, -20.0F, 0.5F, 1.0F);
            worldIn.spawnEntity(entitypotion);
        }

        playerIn.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
    }

}
