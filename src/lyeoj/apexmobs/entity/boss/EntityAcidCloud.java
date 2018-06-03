package lyeoj.apexmobs.entity.boss;

import java.util.List;

import lyeoj.apexmobs.init.AMDamageSources;
import lyeoj.apexmobs.init.AMItems;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityAcidCloud extends Entity {
	
	private static final DataParameter<Float> RADIUS = EntityDataManager.<Float>createKey(EntityAreaEffectCloud.class, DataSerializers.FLOAT);
    private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityAreaEffectCloud.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.<Boolean>createKey(EntityAreaEffectCloud.class, DataSerializers.BOOLEAN);
    private int duration;
    private int waitTime;
    private boolean colorSet;
    private ItemStack stack;

    public EntityAcidCloud(World worldIn)
    {
        super(worldIn);
        this.duration = 600;
        this.waitTime = 20;
        this.noClip = true;
        this.isImmuneToFire = true;
        this.setRadius(3.0F);
        stack = new ItemStack(AMItems.ITEM_TEST);
    }
    
    public EntityAcidCloud(World worldIn, double x, double y, double z)
    {
        this(worldIn);
        this.setPosition(x, y, z);
    }
    
    protected void entityInit()
    {
        this.getDataManager().register(COLOR, Integer.valueOf(0));
        this.getDataManager().register(RADIUS, Float.valueOf(0.5F));
        this.getDataManager().register(IGNORE_RADIUS, Boolean.valueOf(false));
    }
    
    public void setRadius(float radiusIn)
    {
        double d0 = this.posX;
        double d1 = this.posY;
        double d2 = this.posZ;
        this.setSize(radiusIn * 2.0F, 0.5F);
        this.setPosition(d0, d1, d2);

        if (!this.world.isRemote)
        {
            this.getDataManager().set(RADIUS, Float.valueOf(radiusIn));
        }
    }

    public float getRadius()
    {
        return ((Float)this.getDataManager().get(RADIUS)).floatValue();
    }
    
    public int getColor()
    {
        return ((Integer)this.getDataManager().get(COLOR)).intValue();
    }

    public void setColor(int colorIn)
    {
        this.colorSet = true;
        this.getDataManager().set(COLOR, Integer.valueOf(colorIn));
    }
        
    public int getDuration()
    {
        return this.duration;
    }

    public void setDuration(int durationIn)
    {
        this.duration = durationIn;
    }
    
    /**
     * Sets if the radius should be ignored, and the effect should be shown in a single point instead of an area
     */
    protected void setIgnoreRadius(boolean ignoreRadius)
    {
        this.getDataManager().set(IGNORE_RADIUS, Boolean.valueOf(ignoreRadius));
    }
    
    /**
     * Returns true if the radius should be ignored, and the effect should be shown in a single point instead of an area
     */
    public boolean shouldIgnoreRadius()
    {
        return ((Boolean)this.getDataManager().get(IGNORE_RADIUS)).booleanValue();
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();
        boolean flag = this.shouldIgnoreRadius();
        float f = this.getRadius();

        if (this.world.isRemote)
        {   
            if (flag)
            {
                if (this.rand.nextBoolean())
                {
                    for (int i = 0; i < 2; ++i)
                    {
                        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.getRadius(), this.posY, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.getRadius(), (this.rand.nextDouble() - 0.5D) * 0.5, 0.01, (this.rand.nextDouble() - 0.5D) * 0.5, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
                        
                    }
                }
            }
            else
            {
                float f5 = (float)Math.PI * f * f;

                for (int k1 = 0; (float)k1 < f5; ++k1)
                {
                    this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.getRadius(), this.posY, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.getRadius(), (this.rand.nextDouble() - 0.5D) * 0.5, 0.01, (this.rand.nextDouble() - 0.5D) * 0.5, Item.getIdFromItem(stack.getItem()), stack.getMetadata());
                }
            }
        }
        else
        {
            if (this.ticksExisted >= this.waitTime + this.duration)
            {
                this.setDead();
                return;
            }

            boolean flag1 = this.ticksExisted < this.waitTime;

            if (flag != flag1)
            {
                this.setIgnoreRadius(flag1);
            }

            if (flag1)
            {
                return;
            }

            if (this.ticksExisted % 3 == 0)
            {

                List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox());

                if (!list.isEmpty())
                {
                    for (EntityLivingBase entitylivingbase : list)
                    {
                        if (!(entitylivingbase instanceof EntitySlime))
                        {
                            double d0 = entitylivingbase.posX - this.posX;
                            double d1 = entitylivingbase.posZ - this.posZ;
                            double d2 = d0 * d0 + d1 * d1;

                            if (d2 <= (double)(f * f))
                            {
                                entitylivingbase.attackEntityFrom(AMDamageSources.ACID, 3.0F);
                            }
                        }
                    }
                }
                this.duration--;
                if (this.duration <= 0)
                {
                    this.setDead();
                    return;
                }
                
            }
        }
    }
    
    public void setWaitTime(int waitTimeIn)
    {
        this.waitTime = waitTimeIn;
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
        this.ticksExisted = compound.getInteger("Age");
        this.duration = compound.getInteger("Duration");
        this.waitTime = compound.getInteger("WaitTime");
        this.setRadius(compound.getFloat("Radius"));

        if (compound.hasKey("Color", 99))
        {
            this.setColor(compound.getInteger("Color"));
        }
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Age", this.ticksExisted);
        compound.setInteger("Duration", this.duration);
        compound.setInteger("WaitTime", this.waitTime);
        compound.setFloat("Radius", this.getRadius());

        if (this.colorSet)
        {
            compound.setInteger("Color", this.getColor());
        }
    }

    public void notifyDataManagerChange(DataParameter<?> key)
    {
        if (RADIUS.equals(key))
        {
            this.setRadius(this.getRadius());
        }

        super.notifyDataManagerChange(key);
    }

    public EnumPushReaction getPushReaction()
    {
        return EnumPushReaction.IGNORE;
    }

}
