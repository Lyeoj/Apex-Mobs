package lyeoj.apexmobs.entity.boss;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityApexFeeshMinion extends EntityMob {

	private EntityApexFeeshMinion.AIDigEscape digEscape;
	private EntityApexFeeshMinion.AIGroupBoss groupBoss;
	private EntityApexFeesh boss;
	private UUID bossID;
	private static final DataParameter<Integer> STRAFE = EntityDataManager.<Integer>createKey(EntityApexFeeshMinion.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> STRAFING = EntityDataManager.<Boolean>createKey(EntityApexFeeshMinion.class, DataSerializers.BOOLEAN);
	private int despawnCounter;
	private int strafeCounter;
	private boolean regrouping;
	private int regroupTimer;
	
	public EntityApexFeeshMinion(World worldIn)
    {
        super(worldIn);
        this.setSize(0.5F, 0.4F);
    }
	
	public EntityApexFeeshMinion(World worldIn, EntityApexFeesh master)
    {
        super(worldIn);
        this.setBoss(master);     
    }
	
	protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(STRAFING, Boolean.valueOf(false));
        this.dataManager.register(STRAFE, 0);
        if(!this.world.isRemote) {
        	this.setStrafe(MathHelper.getInt(this.rand, -1, 1));
        }
        
    }
	
	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);
        
        compound.setInteger("despawnCounter", despawnCounter);
        compound.setInteger("strafeCounter", strafeCounter);
        compound.setInteger("regroupTimer", regroupTimer);
        
        compound.setBoolean("regrouping", regrouping);

        compound.setInteger("strafe", this.dataManager.get(STRAFE));
        compound.setBoolean("strafing", this.dataManager.get(STRAFING));
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
        
        this.despawnCounter = compound.getInteger("despawnCounter");
        this.strafeCounter = compound.getInteger("strafeCounter");
        this.regroupTimer = compound.getInteger("regroupTimer");
        
        this.regrouping = compound.getBoolean("regrouping");

        this.dataManager.set(STRAFING, Boolean.valueOf(compound.getBoolean("strafing")));
        
        if (compound.hasKey("strafe", 99))
        {
            this.dataManager.set(STRAFE, compound.getInteger("strafe"));
        }
        this.bossID = compound.getUniqueId("bossid");
    }

    public static void registerFixesSilverfish(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityApexFeeshMinion.class);
    }
    
    public int getStrafe() {
    	return this.getDataManager().get(STRAFE);
    }
    
    public void setStrafe(int strafeIn) {
    	this.getDataManager().set(STRAFE, strafeIn);
    }
    
    public void setStrafing(boolean flag)
    {
    	this.getDataManager().set(STRAFING, flag);
        this.getDataManager().setDirty(STRAFING);
    }

    public boolean isStrafing()
    {
        return this.getDataManager().get(STRAFING);
    }
    
    public void setBoss(@Nullable EntityApexFeesh ownerIn)
    {
        this.boss = ownerIn;
        this.bossID = ownerIn == null ? null : ownerIn.getUniqueID();
    }

    @Nullable
    public EntityApexFeesh getBoss()
    {
        if (this.boss == null && this.bossID != null && this.world instanceof WorldServer)
        {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.bossID);

            if (entity instanceof EntityApexSlime)
            {
                this.boss = (EntityApexFeesh)entity;
            }
        }

        return this.boss;
    }


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initEntityAI()
    {
        this.digEscape = new AIDigEscape(this);
        this.groupBoss = new AIGroupBoss(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.digEscape);
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, this.groupBoss);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
    }
	

    /**
     * Returns the Y Offset of this entity.
     */
    public double getYOffset()
    {
        return 0.1D;
    }

    public float getEyeHeight()
    {
        return 0.1F;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SILVERFISH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return SoundEvents.ENTITY_SILVERFISH_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SILVERFISH_DEATH;
    }

    protected void playStepSound(BlockPos pos, Block blockIn)
    {
        this.playSound(SoundEvents.ENTITY_SILVERFISH_STEP, 0.15F, 1.0F);
    }
    
    public boolean attackEntityAsMob(Entity entityIn) {
    	boolean flag = super.attackEntityAsMob(entityIn);
    	if(flag) {
    		entityIn.motionY = -4;
    	}
    	return flag;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
        	if ((source instanceof EntityDamageSource || source == DamageSource.MAGIC) && this.digEscape != null)
            {
        		if(this.getPassengers().isEmpty()) {
        			this.digEscape.notifyHurt();
        		}
            }
        	
        	return super.attackEntityFrom(source, amount);
        }
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_SILVERFISH;
    }
    
    public boolean canDespawn() {
    	return false;
    }
    
    public void scatter() {
    	this.digEscape.notifyHurt();
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    public void regroup() {
    	this.setInvisible(true);
    	this.regrouping = true;
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
    	
    	if(!this.world.isRemote) {
    		if(this.getBoss() == null) {
        		this.setDead();
        		return;
        	}
        	
        	if(this.getBoss().isDead) {
        		this.setPositionAndUpdate(this.posX, this.posY - 2, this.posZ);
        	}
        	
        	if(this.getDistanceToEntity(this.getBoss()) > 10) {
        		if(this.despawnCounter < 600) {
        			this.despawnCounter++;
        		} else {
        			this.setDead();
        		}
        	} else {
        		this.despawnCounter = 0;
        	}
        	if(this.regrouping) {
    			if(this.regroupTimer < 20) {
        			regroupTimer++;
        			if(this.regroupTimer == 12) {
        				this.setPositionAndUpdate(this.getBoss().posX, this.getBoss().posY, this.getBoss().posZ);
        			}
        		} else {
        			this.setInvisible(false);
        			this.regrouping = false;
        			this.regroupTimer = 0;
        		}      		
        	}
    	}
    	
    	if(this.isEntityInsideOpaqueBlock()) {
			this.setPositionAndUpdate(this.posX, this.posY + 1, this.posZ);
    		if(this.world.isRemote) {
    			for (int i = 0; i < 10; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5, this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5, Block.getStateId(Block.getBlockById(3).getDefaultState()));
                }
    		}
    	}
    	if (this.world.isRemote && this.isInvisible())
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5, this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5, Block.getStateId(Block.getBlockById(3).getDefaultState()));
            }
            if(!this.noClip && !this.onGround) {
                for (int i = 0; i < 10; ++i)
                {
                    this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5, this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5, Block.getStateId(Block.getBlockById(3).getDefaultState()));
                }
            }
        }
    	
    	if(!this.world.isRemote) {
    		if(this.isStrafing() && this.getAttackTarget() != null) {
    			if(this.strafeCounter < 40) {
    				this.setMoveStrafing(this.getStrafe());
    				this.strafeCounter++;
    			} else {
    				this.strafeCounter = 0;
    				this.setStrafing(false);
    			}
        	}
    	}
    	
    	super.onLivingUpdate();
    }

    /**
     * Set the render yaw offset
     */
    public void setRenderYawOffset(float offset)
    {
        this.rotationYaw = offset;
        super.setRenderYawOffset(offset);
    }

    public float getBlockPathWeight(BlockPos pos)
    {
        return this.world.getBlockState(pos.down()).getBlock() == Blocks.STONE ? 10.0F : super.getBlockPathWeight(pos);
    }

    /**
     * Checks to make sure the light is not too bright where the mob is spawning
     */
    protected boolean isValidLightLevel()
    {
        return true;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        if (super.getCanSpawnHere())
        {
            EntityPlayer entityplayer = this.world.getNearestPlayerNotCreative(this, 5.0D);
            return entityplayer == null;
        }
        else
        {
            return false;
        }
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.ARTHROPOD;
    }
    
    static class AIDigEscape extends EntityAIBase {

		private boolean angry = false;
		private EntityApexFeeshMinion feesh;
		private int maxDigTime;
		private int digTime;
		private double offset1;
		private double offset2;
		
		public AIDigEscape(EntityApexFeeshMinion feesh)
        {
            this.feesh = feesh;
        }
		
		public void notifyHurt(){
            if(!angry && !feesh.isInWater() && !feesh.isInLava()) {
            	angry = true;
            }
        }
    	
    	@Override
		public boolean shouldExecute() {
			return angry;
		}
    	    	
    	/**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
        	this.digTime = (int)(Math.random() * 20) + 20;
        	this.maxDigTime = digTime;
        	this.offset1 = (Math.random()) - 0.5;
        	this.offset2 = (Math.random()) - 0.5;
        	feesh.setNoGravity(true);
	    	feesh.noClip = true;
	    	feesh.setEntityInvulnerable(true);
        	feesh.motionX = 0;
        	feesh.motionZ = 0;
        	feesh.motionY = 0;
        	feesh.setPositionAndUpdate(feesh.posX, feesh.posY - 2, feesh.posZ);
        	feesh.setInvisible(true);
        }
		
		/**
	     * Keep ticking a continuous task that has already been started
	     */
	    public void updateTask()
	    {
	    	feesh.noClip = false;
	    	if(!feesh.isEntityInsideOpaqueBlock()) {
	    		feesh.setPositionAndUpdate(feesh.posX, feesh.posY - 1, feesh.posZ);
	    	}
	    	feesh.noClip = true;
	    	
	    	if(digTime > 0) {
	    		digTime--;
	    	} else {
	    		feesh.setPositionAndUpdate(feesh.posX, feesh.posY + 2, feesh.posZ);
	    		feesh.motionX = 0;
	    		feesh.motionY = 0;
	    		feesh.noClip = false;
	    		feesh.setNoGravity(false);
	    		feesh.setEntityInvulnerable(false);
	    		feesh.setInvisible(false);
	    		angry = false;
	    	}
	    	if(digTime < maxDigTime - 5) {
	    		feesh.motionX = offset1;
		    	feesh.motionZ = offset2;
	    	}
	    }
	    
	    /**
	     * Reset the task's internal state. Called when this task is interrupted by another one
	     */
	    public void resetTask()
	    {
	    	feesh.setPositionAndUpdate(feesh.posX, feesh.posY + 2, feesh.posZ);
    		feesh.motionX = 0;
    		feesh.motionY = 0;
    		feesh.noClip = false;
    		feesh.setNoGravity(false);
    		feesh.setEntityInvulnerable(false);
    		feesh.setInvisible(false);
    		angry = false;
	    }
    	
    }
    
    static class AIGroupBoss extends EntityAIBase {
    	
    	private EntityApexFeeshMinion feesh;
    	private int timeToRecalcPath;
    	private float oldWaterCost;
    	private final PathNavigate navigation;
    	
    	public AIGroupBoss(EntityApexFeeshMinion feesh)
        {
            this.feesh = feesh;    
            this.navigation = feesh.getNavigator();
        }

		@Override
		public boolean shouldExecute() {
			if(feesh.getBoss() != null && !feesh.getBoss().isDead) {
				if(feesh.getDistanceToEntity(feesh.getBoss()) > 4) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		
		public boolean shouldContinueExecuting() {
			if(feesh.getBoss() != null && !feesh.getBoss().isDead && !this.navigation.noPath()) {
				if(feesh.getDistanceToEntity(feesh.getBoss()) > 4) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		
		public void startExecuting()
	    {
	        this.timeToRecalcPath = 0;
	        this.oldWaterCost = feesh.getPathPriority(PathNodeType.WATER);
	        feesh.setPathPriority(PathNodeType.WATER, 0.0F);
	    }
		
		/**
	     * Reset the task's internal state. Called when this task is interrupted by another one
	     */
	    public void resetTask()
	    {
	        this.navigation.clearPathEntity();
	        feesh.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
	    }
		
		/**
	     * Keep ticking a continuous task that has already been started
	     */
	    public void updateTask()
	    {
	    	if (feesh.getBoss() != null)
	        {
	            feesh.getLookHelper().setLookPositionWithEntity(feesh.getBoss(), 10.0F, (float)feesh.getVerticalFaceSpeed());

	            if (--this.timeToRecalcPath <= 0)
	            {
	                this.timeToRecalcPath = 10;
	                double d0 = feesh.posX - feesh.getBoss().posX;
	                double d1 = feesh.posY - feesh.getBoss().posY;
	                double d2 = feesh.posZ - feesh.getBoss().posZ;
	                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

	                if (d3 > (double)(20))
	                {
	                    this.navigation.tryMoveToEntityLiving(feesh.getBoss(), 1);
	                }
	                else
	                {
	                    this.navigation.clearPathEntity();
	                    EntityLookHelper entitylookhelper = feesh.getBoss().getLookHelper();

	                    if (d3 <= (double)20 || entitylookhelper.getLookPosX() == feesh.posX && entitylookhelper.getLookPosY() == feesh.posY && entitylookhelper.getLookPosZ() == feesh.posZ)
	                    {
	                        double d4 = feesh.getBoss().posX - feesh.posX;
	                        double d5 = feesh.getBoss().posZ - feesh.posZ;
	                        this.navigation.tryMoveToXYZ(feesh.posX - d4, feesh.posY, feesh.posZ - d5, 1);
	                    }
	                }
	            }
	        }
	    }
    	
    }

}
