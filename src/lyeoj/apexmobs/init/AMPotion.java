package lyeoj.apexmobs.init;

import lyeoj.apexmobs.gui.GuiAMPotions;
import lyeoj.apexmobs.gui.GuiDarkHearts;
import lyeoj.apexmobs.gui.GuiFrozenScreen;
import lyeoj.apexmobs.gui.GuiInkScreen;
import lyeoj.apexmobs.gui.GuiMuddyScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AMPotion extends Potion {
	
	private int statusIconIndex;
	private String name;
	private final GuiAMPotions ICONS = new GuiAMPotions();
	
	public static final Potion WATERLOGGED = new AMPotion(true, 0).setPotionName("effect.waterlogged").setIconIndex(0, 0).setRegistryName("waterlogged");
	public static final Potion POSSESSED = new AMPotionPossession(true, 0X111111).setPotionName("effect.possessed").setIconIndex(2, 0).setRegistryName("possessed").registerPotionAttributeModifier(SharedMonsterAttributes.MAX_HEALTH, "795d2e9a-641c-45d5-b1f7-74e2dee5d024", 8.0D, 0).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "795d2e9a-641c-45d5-b1f7-74e2dee5d024", 0.11D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_DAMAGE, "1c7c25c8-b0b1-40f0-bb52-3919bb6368e3", 1.0, 0);	
	public static final Potion FROZEN = new AMPotionFrozen(true, 0).setPotionName("effect.frozen").setIconIndex(1, 0).setRegistryName("frozen").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "1a0f6acd-8060-4548-90a5-11ed293be495", -0.99D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "cbad6d28-3568-4bb9-a140-c3a456c4a164", -0.99D, 2);
	public static final Potion TARRED = new AMPotion(true, 0).setPotionName("effect.tarred").setIconIndex(4, 0).setRegistryName("tarred").registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "b638d401-161e-4d6b-8aba-9e43c0f7b719", -0.15D, 2);
	public static final Potion ZAPPED = new AMPotion(true, 0).setPotionName("effect.zapped").setIconIndex(3,  0).setRegistryName("zapped");
	public static final Potion INKED = new AMPotionInked(true, 0).setPotionName("effect.inked").setIconIndex(5, 0).setRegistryName("inked");
	public static final Potion MUDDY = new AMPotionMuddy(true, 0).setPotionName("effect.muddy").setIconIndex(7, 0).setRegistryName("muddy").registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, "1beaf425-149e-4a80-ba93-6c4f2413c0bc", -0.75D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, "ba116984-4bc7-4b40-a338-fa50ebacf12c", -0.15D, 2);
	
	public static final Potion WATERWALKING = new AMPotion(false, 0).setPotionName("effect.waterwalking").setIconIndex(6, 0).setRegistryName("waterwalking").setBeneficial();
	
	protected AMPotion(boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
	}
		
	/**
     * Sets the index for the icon displayed in the player's inventory when the status is active.
     */
    protected AMPotion setIconIndex(int p_76399_1_, int p_76399_2_)
    {
        this.statusIconIndex = p_76399_1_ + p_76399_2_ * 8;
        return this;
    }
    
    /**
     * Returns the index for the icon to display when the potion is active.
     */
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex()
    {
        return this.statusIconIndex;
    }
    
    /**
     * Set the potion name.
     */
    public AMPotion setPotionName(String nameIn)
    {
        this.name = nameIn;
        return this;
    }

    /**
     * returns the name of the potion
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Called to draw the this Potion onto the player's inventory when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param effect the active PotionEffect
     * @param mc the Minecraft instance, for convenience
     */
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) { 
    	ICONS.renderInventory(x, y, effect, mc);
    }

    /**
     * Called to draw the this Potion onto the player's ingame HUD when it's active.
     * This can be used to e.g. render Potion icons from your own texture.
     * @param x the x coordinate
     * @param y the y coordinate
     * @param effect the active PotionEffect
     * @param mc the Minecraft instance, for convenience
     * @param alpha the alpha value, blinks when the potion is about to run out
     */
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) { 
    	ICONS.renderHUD(x, y, effect, mc, alpha);
    }
    
    /**
     * Get a fresh list of items that can cure this Potion.
     * All new PotionEffects created from this Potion will call this to initialize the default curative items
     * @see PotionEffect#getCurativeItems
     * @return A list of items that can cure this Potion
     */
    public java.util.List<net.minecraft.item.ItemStack> getCurativeItems()
    {
        java.util.ArrayList<net.minecraft.item.ItemStack> ret = new java.util.ArrayList<net.minecraft.item.ItemStack>();
        ret.add(new net.minecraft.item.ItemStack(net.minecraft.init.Items.MILK_BUCKET));
        return ret;
    }
    
    public static class AMPotionFrozen extends AMPotion {
    	
    	protected AMPotionFrozen(boolean isBadEffectIn, int liquidColorIn) {
			super(isBadEffectIn, liquidColorIn);
		}

		private final GuiFrozenScreen ICE = new GuiFrozenScreen();
    	
    	@SideOnly(Side.CLIENT)
        public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) { 
        	super.renderHUDEffect(x, y, effect, mc, alpha);
        	ICE.drawIce(mc);
        }
    }
    
    public static class AMPotionMuddy extends AMPotion {
    	
    	protected AMPotionMuddy(boolean isBadEffectIn, int liquidColorIn) {
			super(isBadEffectIn, liquidColorIn);
		}

		private final GuiMuddyScreen MUD = new GuiMuddyScreen();
    	
    	@SideOnly(Side.CLIENT)
        public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) { 
        	super.renderHUDEffect(x, y, effect, mc, alpha);
        	MUD.drawMud(mc);
        }
    }
    
    public static class AMPotionInked extends AMPotion {
    	
    	protected AMPotionInked(boolean isBadEffectIn, int liquidColorIn) {
			super(isBadEffectIn, liquidColorIn);
		}

		private final GuiInkScreen INK = new GuiInkScreen();
    	
    	@SideOnly(Side.CLIENT)
        public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) { 
        	super.renderHUDEffect(x, y, effect, mc, alpha);
        	INK.drawInk(mc);
        }
    }
    
    
    
    public static class AMPotionPossession extends AMPotion {
    	private final GuiDarkHearts HEARTS = new GuiDarkHearts();
    	
    	protected AMPotionPossession(boolean isBadEffectIn, int liquidColorIn)
        {
            super(isBadEffectIn, liquidColorIn);
        }

        public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier)
        {
            super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);

            if (entityLivingBaseIn.getHealth() > entityLivingBaseIn.getMaxHealth())
            {
                entityLivingBaseIn.setHealth(entityLivingBaseIn.getMaxHealth());
            }
        }
        
        /**
         * Called to draw the this Potion onto the player's ingame HUD when it's active.
         * This can be used to e.g. render Potion icons from your own texture.
         * @param x the x coordinate
         * @param y the y coordinate
         * @param effect the active PotionEffect
         * @param mc the Minecraft instance, for convenience
         * @param alpha the alpha value, blinks when the potion is about to run out
         */
        @SideOnly(Side.CLIENT)
        public void renderHUDEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc, float alpha) { 
        	super.renderHUDEffect(x, y, effect, mc, alpha);
        	HEARTS.renderHearts(effect, mc);
        	HEARTS.drawDarkScreen(effect, mc);
        }
    }
	


}
