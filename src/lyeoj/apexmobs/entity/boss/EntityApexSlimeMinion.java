package lyeoj.apexmobs.entity.boss;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import lyeoj.apexmobs.init.AMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityApexSlimeMinion extends EntitySlime {

	private EntityApexSlime boss;
	private UUID bossID;
	private boolean consumed;
	private static final DataParameter<Integer> LIFETIME = EntityDataManager.<Integer>createKey(EntityApexSlimeMinion.class, DataSerializers.VARINT);
	
	public EntityApexSlimeMinion(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
	}
	public EntityApexSlimeMinion(World worldIn, EntityApexSlime boss) {
		super(worldIn);
		this.boss = boss;
		this.bossID = boss.getUniqueID();
	}
	
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(LIFETIME, (int)(Math.random() * 400 + 200));
    }
	
	public void setLifetime(int count) {
    	this.getDataManager().set(LIFETIME, count);
    }
    
    public int getLifetime() {
    	return this.getDataManager().get(LIFETIME);
    }
    
    public void setBoss(@Nullable EntityApexSlime ownerIn)
    {
        this.boss = ownerIn;
        this.bossID = ownerIn == null ? null : ownerIn.getUniqueID();
    }

    @Nullable
    public EntityApexSlime getBoss()
    {
        if (this.boss == null && this.bossID != null && this.world instanceof WorldServer)
        {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.bossID);

            if (entity instanceof EntityApexSlime)
            {
                this.boss = (EntityApexSlime)entity;
            }
        }

        return this.boss;
    }
	
	public boolean isBoss() {
		return this.getBoss() == null;
	}
	
	/**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean canDamagePlayer()
    {
        return true;
    }
    
    public boolean canBeHitWithPotion() {
    	return false;
    }
    
    public boolean canDespawn() {
    	return false;
    }
    
    /**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    protected int getAttackStrength()
    {
        return this.getSlimeSize() + 1;
    }
    
    public void onLivingUpdate() {
    	if(!this.isBoss()) {
    		if(this.getLifetime() > 0) {
    			this.setLifetime(this.getLifetime() - 1);
    		} else {
    			this.consumed = true;
    			this.setDead();
    		}
    		if(this.getBoss().isDead) {
    			this.setDead();
    		}
    		if(this.isInLava()) {
    			this.consumed = true;
    			this.setDead();
    		}
    		if(this.onGround) {
    			List<EntityAcidCloud> list = this.world.<EntityAcidCloud>getEntitiesWithinAABB(EntityAcidCloud.class, this.getEntityBoundingBox());
    			if(list.isEmpty()) {
    				EntityAcidCloud acid = new EntityAcidCloud(this.world, this.posX, this.posY, this.posZ);
    				acid.setRadius(this.getSlimeSize() / 2.0F);
    	            acid.setWaitTime(10);
    	            acid.setColor(0x11aa11);         
    	            this.world.spawnEntity(acid);
    			}
    		}
    		
    	}
    	super.onLivingUpdate();
    }
    
    protected void dropItems()
    {	  	
    	ItemStack itemstack;
    	ItemStack itemstack2;
    	
    	if(this.isBoss()) {
    		itemstack = new ItemStack(AMItems.ITEM_HUNGRY_SLIMEBALL_BIG, 4);
    		itemstack2 = new ItemStack(AMItems.ITEM_NUTRIENTS, 2);
    	} else {
    		if(rand.nextInt(5) == 3) {
    			itemstack = new ItemStack(AMItems.ITEM_HUNGRY_SLIMEBALL, 1);
    		} else {
    			itemstack = new ItemStack(AMItems.ITEM_HUNGRY_SLIMEBALL, 0);
    		}
    		itemstack2 = new ItemStack(AMItems.ITEM_NUTRIENTS, 0);
    	}
    	
        
        if (!itemstack.isEmpty())
        {
            this.entityDropItem(itemstack, 0.0F);
            if(!itemstack2.isEmpty()) {
            	this.entityDropItem(itemstack2, 0.0F);
            }
        }    	
    }
    
    protected Item getDropItem()
    {
        return null;
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return null;
    }
    
    public void onDeath(DamageSource source) {
    	if(!this.world.isRemote) {
    		this.dropItems();
    	}
    	super.onDeath(source);
    }
    
    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {
        this.isDead = true;
        if(!this.isBoss()) {
        	if(!this.getBoss().isDead) {
        		if(!consumed) {
        			this.getBoss().attackEntityFrom(DamageSource.OUT_OF_WORLD, this.getSlimeSize() * 2.0F);
        		}
        	}
        }
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("lifetime", this.dataManager.get(LIFETIME));
        compound.setBoolean("consumed", this.consumed);
        if (this.bossID != null)
        {
            compound.setUniqueId("bossid", this.bossID);
        }
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.consumed = compound.getBoolean("consumed");
        
        if (compound.hasKey("lifetime", 99))
        {
            this.dataManager.set(LIFETIME, compound.getInteger("lifetime"));
        }
        this.bossID = compound.getUniqueId("bossid");
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
    	int size = 1;
    	if(!this.isBoss() && this.getBoss().getSlimeSize() > 3) {
    		size = 3;
    	} else if(!this.isBoss() && this.getBoss().getSlimeSize() > 2) {
    		size = 2;
    	}
    	this.setSlimeSize(this.rand.nextInt(size) + 1, true);
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(8.0 * this.getSlimeSize()));
    	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)4));
    	this.setHealth(this.getMaxHealth());
    	
        return livingdata;
    }

}
