package lyeoj.apexmobs.entity;

import javax.annotation.Nullable;

import lyeoj.apexmobs.entity.boss.EntityApexSlime;
import lyeoj.apexmobs.init.AMDamageSources;
import lyeoj.apexmobs.init.AMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFindEntityNearest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityHungrySlime extends EntitySlime {
	
	private int eatTime;
	private int growCounter;
	private int maxGrowCounter;

	public EntityHungrySlime(World worldIn) {
		super(worldIn);
		this.maxGrowCounter = 3;
	}
	
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.targetTasks.addTask(1, new EntityAIFindEntityNearest(this, EntityAnimal.class));
        this.targetTasks.addTask(2, new EntityAIFindEntityNearest(this, EntityMob.class));
    }
	
	public void onUpdate() {
		super.onUpdate();
		if(this.isRiding()) {
			if(this.getRidingEntity() instanceof EntityLivingBase) {
				if(this.eatTime > 0) {
					this.eatTime--;
				} else {
					this.heal(2.0F);
					this.eatTime = 20;
					this.getRidingEntity().attackEntityFrom(AMDamageSources.ACID, (float)this.getAttackStrength());
					if(this.growCounter < this.maxGrowCounter) {
						this.growCounter++;
					} else {
						this.growCounter = 0;
						if(this.getSlimeSize() < 7) {
							this.setSlimeSize(this.getSlimeSize() + 1, true);
							this.maxGrowCounter+=2;
						} else {
							if(!this.world.isRemote) {
								EntityApexSlime boss = new EntityApexSlime(this.world);
								boss.copyLocationAndAnglesFrom(this);
								boss.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(this)), null);
								this.world.spawnEntity(boss);
								this.world.removeEntity(this);
							}
						}
					}
				}
			}
		}
	}
	
	/**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer entityIn)
    {
    	if(!entityIn.capabilities.isCreativeMode) {
    		if(!entityIn.isBeingRidden() && !this.isRiding()) {
    			entityIn.setPositionAndUpdate(this.posX, this.posY, this.posZ);
    			this.startRiding(entityIn, true);
        	} 
    	}
    }
    
    /**
     * Applies a velocity to the entities, to push them away from eachother.
     */
    public void applyEntityCollision(Entity entityIn)
    {
        super.applyEntityCollision(entityIn);

        if (entityIn instanceof EntityAnimal || entityIn instanceof EntityMob)
        {
        	if(!this.isRiding() && !entityIn.isBeingRidden()) {
        		this.startRiding(entityIn, true);
        	}
        }
    }
    
    /**
     * Will get destroyed next tick.
     */
    public void setDead()
    {   	
    	this.isDead = true;
    }
    
    public void onDeath(DamageSource source) {
    	if(!this.world.isRemote) {
    		this.dropItems();
    	}
    	super.onDeath(source);
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
    
    protected void dropItems()
    {	
    	int count = 0;
    	
    	if(this.getSlimeSize() > 2) {
    		count = 1;
    	}
    	if(this.getSlimeSize() > 4) {
    		count = 2;
    	}
    	if(this.getSlimeSize() > 5) {
    		count = 3;
    	}
    	
    	ItemStack itemstack = new ItemStack(AMItems.ITEM_HUNGRY_SLIMEBALL, count);
        
        if (!itemstack.isEmpty())
        {
            this.entityDropItem(itemstack, 0.0F);
        }
    	
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
    	this.setSlimeSize(1, true);
    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(4.0));
    	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)4));
    	this.setHealth(this.getMaxHealth());
    	
        return livingdata;
    }

}
