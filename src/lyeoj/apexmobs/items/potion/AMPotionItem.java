package lyeoj.apexmobs.items.potion;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Tuple;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class AMPotionItem extends Item {
	
	private Potion potion;
	private int duration;
	private int amplifier;
	private int color;
	
	public AMPotionItem()
    {
        this.setMaxStackSize(1);
        this.setCreativeTab(CreativeTabs.BREWING);
    }
	
	public AMPotionItem(int duration, int amplifier) {
		this();
		this.duration = duration;
		this.amplifier = amplifier;
	}
	
	public AMPotionItem setEffect(Potion effectIn) {
		this.potion = effectIn;
		return this;
	}
	
	public Potion getEffect() {
		return potion;
	}
	
	public int getDuration() { 
		return duration;
	}
	
	public int getAmplifier() {
		return amplifier;
	}
	
	public AMPotionItem setColor(int colorIn) {
		this.color = colorIn;
		return this;
	}
	
	public int getColor() {
		return color;
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
            entityLiving.addPotionEffect(new PotionEffect(getEffect(), this.duration));
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

    /**
     * How long it takes to use or consume an item
     */
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 32;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    /**
     * Called when the equipped item is right clicked.
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
        playerIn.setActiveHand(handIn);
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }
    
    /**
     * allows items to add custom lines of information to the mouseover description
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	Potion potion = this.getEffect();
    	if(potion != null) {
            Map<IAttribute, AttributeModifier> map = potion.getAttributeModifierMap();
            List<Tuple<String, AttributeModifier>> list1 = Lists.<Tuple<String, AttributeModifier>>newArrayList();
            
            String s1 = I18n.translateToLocal(potion.getName()).trim();
            if (!map.isEmpty())
            {
                for (Entry<IAttribute, AttributeModifier> entry : map.entrySet())
                {
                    AttributeModifier attributemodifier = entry.getValue();
                    AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), potion.getAttributeModifierAmount(this.amplifier, attributemodifier), attributemodifier.getOperation());
                    list1.add(new Tuple(((IAttribute)entry.getKey()).getName(), attributemodifier1));
                }
            }
            if (this.amplifier > 0)
            {
                s1 = s1 + " " + I18n.translateToLocal("potion.potency." + this.amplifier).trim();
            }
            if (this.duration > 20)
            {
                s1 = s1 + " (" + Potion.getPotionDurationString(new PotionEffect(this.potion, this.duration), 1.0F) + ")";
            }
            if (potion.isBadEffect())
            {
                tooltip.add(TextFormatting.RED + s1);
            }
            else
            {
                tooltip.add(TextFormatting.BLUE + s1);
            }
            
            if (!list1.isEmpty())
            {
                tooltip.add("");
                tooltip.add(TextFormatting.DARK_PURPLE + I18n.translateToLocal("potion.whenDrank"));

                for (Tuple<String, AttributeModifier> tuple : list1)
                {
                    AttributeModifier attributemodifier2 = tuple.getSecond();
                    double d0 = attributemodifier2.getAmount();
                    double d1;

                    if (attributemodifier2.getOperation() != 1 && attributemodifier2.getOperation() != 2)
                    {
                        d1 = attributemodifier2.getAmount();
                    }
                    else
                    {
                        d1 = attributemodifier2.getAmount() * 100.0D;
                    }

                    if (d0 > 0.0D)
                    {
                        tooltip.add(TextFormatting.BLUE + I18n.translateToLocalFormatted("attribute.modifier.plus." + attributemodifier2.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())));
                    }
                    else if (d0 < 0.0D)
                    {
                        d1 = d1 * -1.0D;
                        tooltip.add(TextFormatting.RED + I18n.translateToLocalFormatted("attribute.modifier.take." + attributemodifier2.getOperation(), ItemStack.DECIMALFORMAT.format(d1), I18n.translateToLocal("attribute.name." + (String)tuple.getFirst())));
                    }
                }
            }
    	}
    }

}
