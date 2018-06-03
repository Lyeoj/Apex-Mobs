package lyeoj.apexmobs.entity;

import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityFlinger extends EntityMob {
	
	private AIFlingMobs flingMobs;
	private AIMoveTowardsMob moveToMob;
	private List<? extends EntityZombie> nearbyZombies;
	private List<EntityCreeper> nearbyCreepers;
	private List<EntityRocketCreeper> nearbyRockets;
	private EntityMob flingThis;
	
	public EntityFlinger(World worldIn)
    {
        super(worldIn);
        
    }
	
	protected void initEntityAI()
    {
		this.flingMobs = new AIFlingMobs(this, true);
		this.moveToMob = new AIMoveTowardsMob(this, true);
		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(5, new EntityAILookIdle(this));
        this.tasks.addTask(2, flingMobs);
        this.tasks.addTask(3, moveToMob);
    }
	
	protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_WITCH_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return SoundEvents.ENTITY_WITCH_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_WITCH_DEATH;
    }
    
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    }
    
    /**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate() {
    	if(this.flingThis != null && this.flingThis.isDead) {
    		this.flingThis = null;
    	}
    	if(this.flingThis == null) {
    		this.nearbyZombies = this.world.<EntityZombie>getEntitiesWithinAABB(EntityZombie.class, this.getEntityBoundingBox().grow(64));
        	this.nearbyCreepers = this.world.<EntityCreeper>getEntitiesWithinAABB(EntityCreeper.class, this.getEntityBoundingBox().grow(64));
        	this.nearbyRockets = this.world.<EntityRocketCreeper>getEntitiesWithinAABB(EntityRocketCreeper.class, this.getEntityBoundingBox().grow(64));
        	
        	for(EntityZombie zomb : this.nearbyZombies) {
        		if(this.getEntitySenses().canSee(zomb) && !zomb.isDead) {
        			this.flingThis = zomb;
        			break;
        		}
        	}
        	if(this.flingThis == null) {
        		for(EntityCreeper creep : this.nearbyCreepers) {
        			if(this.getEntitySenses().canSee(creep) && !creep.isDead) {
            			this.flingThis = creep;
            			break;
            		}
            	}
        	}
        	if(this.flingThis == null) {
        		for(EntityRocketCreeper creep : this.nearbyRockets) {
        			if(this.getEntitySenses().canSee(creep) && !creep.isDead) {
            			this.flingThis = creep;
            			break;
            		}
            	}
        	}
    	}
    	super.onLivingUpdate();
    }
    
    public float getEyeHeight()
    {
        return 1.62F;
    }

    public void setSwingingArms(boolean swingingArms)
    {
    }
    
    static class AIMoveTowardsMob extends EntityAIBase {
    	
    	private EntityFlinger flinger;
    	
    	public AIMoveTowardsMob(EntityFlinger flinger, boolean checkSight) {

			this.flinger = flinger;
		}

		@Override
		public boolean shouldExecute() {
			
			if(this.flinger.flingThis != null) {
				return this.flinger.getDistanceToEntity(this.flinger.flingThis) > 6;
			} else {
				return false;
			}
		}
		
    	public void startExecuting() {
    		this.flinger.getMoveHelper().setMoveTo(this.flinger.flingThis.posX, this.flinger.flingThis.posY, this.flinger.flingThis.posZ, 1);
    	}
    	
    	/**
	     * Keep ticking a continuous task that has already been started
	     */
	    public void updateTask() {
	    	this.flinger.getMoveHelper().setMoveTo(this.flinger.flingThis.posX, this.flinger.flingThis.posY, this.flinger.flingThis.posZ, 1);
	    }
    }
    
    
    static class AIFlingMobs extends EntityAIBase {
    	
    	private int coolDown;
    	private int flingTime;
    	private EntityPlayer target;
    	private EntityFlinger flinger;
    	private EntityMob flingee;
    	private boolean freeze;
    	private Vec3d direction = new Vec3d(0, 0, 0);
    	
		public AIFlingMobs(EntityFlinger flinger, boolean checkSight) {
			
			//Keys: 0 = coolDown, 1 = flingTime
			this.flinger = flinger;
			freeze = false;
		}

		@Override
		public boolean shouldExecute() {
			if(coolDown == 0) {
				if(this.flinger.flingThis != null) {
					this.target = this.flinger.world.getNearestAttackablePlayer(this.flinger, 64, 64);
					return this.target != null;
				} else {
					return false;
				}
			} else {
				coolDown--;
				return false;
			}
		}
		
	    public boolean shouldContinueExecuting()
	    {
	        if(flingTime > 0) {
	        	flingTime--;
	        	return true;
	        } else {
	        	if(flingee != null) {
	        		flingee.setEntityInvulnerable(false);
	        		flingee.noClip = false;
	        	}
	        	freeze = false;
	        	return false;
	        }
	    }
		
		/**
	     * Keep ticking a continuous task that has already been started
	     */
	    public void updateTask()
	    {
	    	if(this.flingee != null && !freeze) {
	    		if(Math.abs(this.flingee.posY - this.target.posY) < 2 && Math.abs(this.flingee.posX - this.target.posX) < 2 && Math.abs(this.flingee.posZ - this.target.posZ) < 2) {
	    			this.flingee.motionX = 0;
	    			this.flingee.motionY = 0;
	    			this.flingee.motionZ = 0;
	    			this.flingee.setPositionAndUpdate(this.target.posX, this.target.posY, this.target.posZ);
	    			freeze = true;
	    			flingee.setEntityInvulnerable(false);
	    			flingee.noClip = false;
	    		} else if(!freeze) {
    	    		flingee.motionX = (.1) * direction.x;
    				flingee.motionY = (.1) * direction.y;
    				flingee.motionZ = (.1) * direction.z;    	
	    		}
	    	}
	    }
		
		/**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {	    	
	    	this.flingee = this.flinger.flingThis;
	    	Vec3d playerPos = new Vec3d(target.posX, target.posY, target.posZ);
			Vec3d flingPos = new Vec3d(flingee.posX, flingee.posY, flingee.posZ);
			Vec3d direction = new Vec3d(target.posX - flingee.posX, target.posY - flingee.posY, target.posZ - flingee.posZ);
			double distance = flingPos.distanceTo(playerPos);
			direction.scale(1.0/distance);
			this.direction = direction;
			
			flingee.motionX = (.2) * direction.x;
			flingee.motionY = (.2) * direction.y;
			flingee.motionZ = (.2) * direction.z;
			flingee.playSound(SoundEvents.ENTITY_GHAST_SCREAM, 3.0F, 1.0F);
			flingee.setEntityInvulnerable(true);
			flingee.noClip = true; 
			
			coolDown = 100;
			flingTime = 60;
	    }
    	
    }
    
    

}
