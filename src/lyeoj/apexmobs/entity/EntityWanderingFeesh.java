package lyeoj.apexmobs.entity;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
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
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityWanderingFeesh extends EntityMob {
	
	private EntityWanderingFeesh.AIDigEscape digEscape;
	private EntityWanderingFeesh.AIGroupFriends groupFriends;
	private List<EntityWanderingFeesh> nearbyFriends;
	private EntityWanderingFeesh nearestFriend;
	
	public EntityWanderingFeesh(World worldIn)
    {
        super(worldIn);
        this.setSize(0.5F, 0.4F);
        
    }

    public static void registerFixesSilverfish(DataFixer fixer)
    {
        EntityLiving.registerFixesMob(fixer, EntityWanderingFeesh.class);
    }


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initEntityAI()
    {
        this.digEscape = new AIDigEscape(this);
        this.groupFriends = new AIGroupFriends(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.digEscape);
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, this.groupFriends);
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
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
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
                this.digEscape.notifyHurt();
            }
        	
        	return super.attackEntityFrom(source, amount);
        }
    }

    @Nullable
    protected ResourceLocation getLootTable()
    {
        return LootTableList.ENTITIES_SILVERFISH;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
    	if(this.isEntityInsideOpaqueBlock()) {
    		this.setPositionAndUpdate(this.posX, this.posY + 1, this.posZ);
    	}
    	if (this.world.isRemote && this.isInvisible())
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 0.5, this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5, Block.getStateId(Block.getBlockById(3).getDefaultState()));
            }
        }
    	if(this.nearestFriend == null || this.nearestFriend.isDead) {
    		this.nearbyFriends = this.world.<EntityWanderingFeesh>getEntitiesWithinAABB(EntityWanderingFeesh.class, this.getEntityBoundingBox().grow(30));
    		for(EntityWanderingFeesh friend : this.nearbyFriends) {
				if(this.getEntitySenses().canSee(friend) && !friend.equals(this)) {
					nearestFriend = friend;
//					System.out.println("I have a friend! " + friend);
					break;
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
		private EntityWanderingFeesh feesh;
		private int maxDigTime;
		private int digTime;
		private double offset1;
		private double offset2;
		
		public AIDigEscape(EntityWanderingFeesh feesh)
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
        	this.digTime = (int)Math.random() * 20 + 20;
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
    
    static class AIGroupFriends extends EntityAIBase {
    	
    	private EntityWanderingFeesh feesh;
    	private int timeToRecalcPath;
    	private float oldWaterCost;
    	private final PathNavigate navigation;
    	
    	public AIGroupFriends(EntityWanderingFeesh feesh)
        {
            this.feesh = feesh;    
            this.navigation = feesh.getNavigator();
        }

		@Override
		public boolean shouldExecute() {
			if(feesh.nearestFriend != null && !feesh.nearestFriend.isDead) {
				if(feesh.getDistanceToEntity(feesh.nearestFriend) > 5) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		
		public boolean shouldContinueExecuting() {
			if(feesh.nearestFriend != null && !feesh.nearestFriend.isDead && !this.navigation.noPath()) {
				if(feesh.getDistanceToEntity(feesh.nearestFriend) > 5) {
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
	    	if (feesh.nearestFriend != null)
	        {
	            feesh.getLookHelper().setLookPositionWithEntity(feesh.nearestFriend, 10.0F, (float)feesh.getVerticalFaceSpeed());

	            if (--this.timeToRecalcPath <= 0)
	            {
	                this.timeToRecalcPath = 10;
	                double d0 = feesh.posX - feesh.nearestFriend.posX;
	                double d1 = feesh.posY - feesh.nearestFriend.posY;
	                double d2 = feesh.posZ - feesh.nearestFriend.posZ;
	                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

	                if (d3 > (double)(25))
	                {
	                    this.navigation.tryMoveToEntityLiving(feesh.nearestFriend, 1);
	                }
	                else
	                {
	                    this.navigation.clearPathEntity();
	                    EntityLookHelper entitylookhelper = feesh.nearestFriend.getLookHelper();

	                    if (d3 <= (double)25 || entitylookhelper.getLookPosX() == feesh.posX && entitylookhelper.getLookPosY() == feesh.posY && entitylookhelper.getLookPosZ() == feesh.posZ)
	                    {
	                        double d4 = feesh.nearestFriend.posX - feesh.posX;
	                        double d5 = feesh.nearestFriend.posZ - feesh.posZ;
	                        this.navigation.tryMoveToXYZ(feesh.posX - d4, feesh.posY, feesh.posZ - d5, 1);
	                    }
	                }
	            }
	        }
	    }
    	
    }

}
