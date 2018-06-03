package lyeoj.apexmobs.entity.projectile;

import java.util.List;

import lyeoj.apexmobs.items.potion.AMThrownPotionItem;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityAMPotion extends EntityThrowable {
	private static final DataParameter<ItemStack> ITEM = EntityDataManager.<ItemStack>createKey(EntityAMPotion.class, DataSerializers.ITEM_STACK);
    private PotionEffect effect;
    private int splashColor;

    public EntityAMPotion(World worldIn)
    {
        super(worldIn);
    }

    public EntityAMPotion(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn)
    {
        super(worldIn, throwerIn);
        this.setItem(potionDamageIn);
    }

    public EntityAMPotion(World worldIn, double x, double y, double z, ItemStack potionDamageIn)
    {
        super(worldIn, x, y, z);

        if (!potionDamageIn.isEmpty())
        {
        	this.setItem(potionDamageIn);;
        }
    }
    
    protected void entityInit()
    {
        this.getDataManager().register(ITEM, ItemStack.EMPTY);
    }
    
    public EntityAMPotion setPotionEffect(PotionEffect effectIn) {
    	this.effect = effectIn;
    	return this;
    }
    
    public EntityAMPotion setPotionColor(int color) {
    	this.splashColor = color;
    	return this;
    }
    
    public void setItem(ItemStack stack)
    {
    	this.getDataManager().set(ITEM, stack);
        this.getDataManager().setDirty(ITEM);
    }

    public ItemStack getPotion()
    {
        ItemStack itemstack = (ItemStack)this.getDataManager().get(ITEM);
        return itemstack;
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    protected float getGravityVelocity()
    {
        return 0.05F;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {
            ItemStack itemstack = this.getPotion();
            PotionEffect effect = this.effect;

            AMThrownPotionItem potion = (AMThrownPotionItem)this.getPotion().getItem();
        	if (potion.isLingering())
            {
                this.makeAreaOfEffectCloud(itemstack, effect);
            }
            else
            {
                this.applySplash(result, effect);
            }

            int i = 2002;
            this.world.playEvent(i, new BlockPos(this), this.splashColor);
            this.setDead();
        }
    }

    private void applySplash(RayTraceResult result, PotionEffect effect)
    {
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(4.0D, 2.0D, 4.0D);
        List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

        if (!list.isEmpty())
        {
            for (EntityLivingBase entitylivingbase : list)
            {
                if (entitylivingbase.canBeHitWithPotion())
                {
                    double d0 = this.getDistanceSqToEntity(entitylivingbase);

                    if (d0 < 16.0D)
                    {
                        double d1 = 1.0D - Math.sqrt(d0) / 4.0D;

                        if (entitylivingbase == result.entityHit)
                        {
                            d1 = 1.0D;
                        }

                        int i = (int)(d1 * (double)effect.getDuration() + 0.5D);

                        if (i > 20)
                        {
                            entitylivingbase.addPotionEffect(effect);
                        }
                                                    
                    }
                }
            }
        }
    }

    private void makeAreaOfEffectCloud(ItemStack p_190542_1_, PotionEffect effect)
    {
        EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
        entityareaeffectcloud.setOwner(this.getThrower());
        entityareaeffectcloud.setRadius(3.0F);
        entityareaeffectcloud.setRadiusOnUse(-0.5F);
        entityareaeffectcloud.setWaitTime(10);
        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());
        entityareaeffectcloud.addEffect(effect);

        entityareaeffectcloud.setColor(this.splashColor);
        
        this.world.spawnEntity(entityareaeffectcloud);
    }

}
