package lyeoj.apexmobs.items.potion;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPotionBase extends AMPotionItem {
	
	public ItemPotionBase() {
		setUnlocalizedName("potionbase");
		setRegistryName("potionbase");
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
            int chance = entityplayer.getRNG().nextInt(11);
            switch(chance) {
            case 0:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 400));
            	break;
            case 1:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
            	break;
            case 2:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 400));
            	break;
            case 3:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 400));
            	break;
            case 4:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.POISON, 400));
            	break;
            case 5:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.POISON, 400));
            	break;
            case 6:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.WITHER, 400));
            	break;
            case 7:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 400));
            	break;
            case 8:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 400));
            	break;
            case 9:
            	entityplayer.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 4));
            	break;
            default:
            	break;
            }
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
    	tooltip.add("Used for brewing special potions, you probably shouldn't drink this by itself...");
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return false;
    }

}
