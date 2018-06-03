package lyeoj.apexmobs.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGiantSquid extends EntityMob {

	public float squidPitch;
    public float prevSquidPitch;
    public float squidYaw;
    public float prevSquidYaw;
    /** appears to be rotation in radians; we already have pitch & yaw, so this completes the triumvirate. */
    public float squidRotation;
    /** previous squidRotation in radians */
    public float prevSquidRotation;
    /** angle of the tentacles in radians */
    public float tentacleAngle;
    /** the last calculated angle of the tentacles in radians */
    public float lastTentacleAngle;
    private float randomMotionSpeed;
    /** change in squidRotation in radians. */
    private float rotationVelocity;
    private float rotateSpeed;
    private float randomMotionVecX;
    private float randomMotionVecY;
    private float randomMotionVecZ;
    public EntityLivingBase dragTarget;
	
	public EntityGiantSquid(World worldIn) {
		super(worldIn);
        this.setSize(1.2F, 1.2F);
        this.rand.setSeed((long)(1 + this.getEntityId()));
        this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initEntityAI()
    {
		this.tasks.addTask(0, new EntityGiantSquid.AIDragTarget(this));
		this.tasks.addTask(1, new EntityGiantSquid.AIMoveTowardsTarget(this));
		this.tasks.addTask(2, new EntityGiantSquid.AIMoveRandom(this));
		this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
    }
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
    }
	
	protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_SQUID_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_184601_1_)
    {
        return SoundEvents.ENTITY_SQUID_HURT;
    }

    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_SQUID_DEATH;
    }

    /**
     * Returns the volume for the sounds this mob makes.
     */
    protected float getSoundVolume()
    {
        return 0.4F;
    }
    
    public float getEyeHeight()
    {
        return this.height * 0.5F;
    }
    
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    /**
     * Gets called every tick from main Entity class
     */
    public void onEntityUpdate()
    {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater())
        {
            --i;
            this.setAir(i);

            if (this.getAir() == -20)
            {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        }
        else
        {
            this.setAir(300);
        }
    }

    public boolean isPushedByWater()
    {
        return false;
    }
    
    @Override
    public boolean getCanSpawnHere() {
    	return true;
    }
    
    public boolean attackEntityAsMob(Entity entityIn)
    {
    	if(entityIn instanceof EntityLivingBase) {
    		this.dragTarget = (EntityLivingBase) entityIn;
    	}

        return super.attackEntityAsMob(entityIn);
    }
        
	/**
     * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
     * use this to react to sunlight and start to burn.
     */
    public void onLivingUpdate()
    {
        super.onLivingUpdate();
        this.prevSquidPitch = this.squidPitch;
        this.prevSquidYaw = this.squidYaw;
        this.prevSquidRotation = this.squidRotation;
        this.lastTentacleAngle = this.tentacleAngle;
        this.squidRotation += this.rotationVelocity;

        if ((double)this.squidRotation > (Math.PI * 2D))
        {
            if (this.world.isRemote)
            {
                this.squidRotation = ((float)Math.PI * 2F);
            }
            else
            {
                this.squidRotation = (float)((double)this.squidRotation - (Math.PI * 2D));

                if (this.rand.nextInt(10) == 0)
                {
                    this.rotationVelocity = 1.0F / (this.rand.nextFloat() + 1.0F) * 0.2F;
                }

                this.world.setEntityState(this, (byte)19);
            }
        }

        if (this.inWater)
        {
            if (this.squidRotation < (float)Math.PI)
            {
                float f = this.squidRotation / (float)Math.PI;
                this.tentacleAngle = MathHelper.sin(f * f * (float)Math.PI) * (float)Math.PI * 0.25F;

                if ((double)f > 0.75D)
                {
                    this.randomMotionSpeed = 1.0F;
                    this.rotateSpeed = 1.0F;
                }
                else
                {
                    this.rotateSpeed *= 0.8F;
                }
            }
            else
            {
                this.tentacleAngle = 0.0F;
                this.randomMotionSpeed *= 0.9F;
                this.rotateSpeed *= 0.99F;
            }

            if (!this.world.isRemote)
            {
                this.motionX = (double)(this.randomMotionVecX * this.randomMotionSpeed);
                this.motionY = (double)(this.randomMotionVecY * this.randomMotionSpeed);
                this.motionZ = (double)(this.randomMotionVecZ * this.randomMotionSpeed);
            }

            float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.renderYawOffset += (-((float)MathHelper.atan2(this.motionX, this.motionZ)) * (180F / (float)Math.PI) - this.renderYawOffset) * 0.1F;
            this.rotationYaw = this.renderYawOffset;
            this.squidYaw = (float)((double)this.squidYaw + Math.PI * (double)this.rotateSpeed * 1.5D);
            this.squidPitch += (-((float)MathHelper.atan2((double)f1, this.motionY)) * (180F / (float)Math.PI) - this.squidPitch) * 0.1F;
        }
        else
        {
            this.tentacleAngle = MathHelper.abs(MathHelper.sin(this.squidRotation)) * (float)Math.PI * 0.25F;

            if (!this.world.isRemote)
            {
                this.motionX = 0.0D;
                this.motionZ = 0.0D;

                if (this.isPotionActive(MobEffects.LEVITATION))
                {
                    this.motionY += 0.05D * (double)(this.getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY;
                }
                else if (!this.hasNoGravity())
                {
                    this.motionY -= 0.08D;
                }

                this.motionY *= 0.9800000190734863D;
            }

            this.squidPitch = (float)((double)this.squidPitch + (double)(-90.0F - this.squidPitch) * 0.02D);
        }
    }
    
    public void travel(float p_191986_1_, float p_191986_2_, float p_191986_3_)
    {
        this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    }
    
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 19)
        {
            this.squidRotation = 0.0F;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
    }
    
    public void setMovementVector(float randomMotionVecXIn, float randomMotionVecYIn, float randomMotionVecZIn)
    {
        this.randomMotionVecX = randomMotionVecXIn;
        this.randomMotionVecY = randomMotionVecYIn;
        this.randomMotionVecZ = randomMotionVecZIn;
    }

    public boolean hasMovementVector()
    {
        return this.randomMotionVecX != 0.0F || this.randomMotionVecY != 0.0F || this.randomMotionVecZ != 0.0F;
    }

    static class AIMoveRandom extends EntityAIBase
        {
            private final EntityGiantSquid squid;

            public AIMoveRandom(EntityGiantSquid p_i45859_1_)
            {
                this.squid = p_i45859_1_;
            }

            /**
             * Returns whether the EntityAIBase should begin execution.
             */
            public boolean shouldExecute()
            {
                return true;
            }

            /**
             * Keep ticking a continuous task that has already been started
             */
            public void updateTask()
            {
                int i = this.squid.getIdleTime();

                if (i > 100)
                {
                    this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
                }
                else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.hasMovementVector())
                {
                    float f = this.squid.getRNG().nextFloat() * ((float)Math.PI * 2F);
                    float f1 = MathHelper.cos(f) * 0.2F;
                    float f2 = -0.1F + this.squid.getRNG().nextFloat() * 0.2F;
                    float f3 = MathHelper.sin(f) * 0.2F;
                    this.squid.setMovementVector(f1, f2, f3);
                }
            }
        }
    
    static class AIMoveTowardsTarget extends EntityAIBase
    {
        private final EntityGiantSquid squid;

        public AIMoveTowardsTarget(EntityGiantSquid entity)
        {
            this.squid = entity;
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute()
        {
            return squid.getAttackTarget() != null && squid.getAttackTarget().isInWater();
        }
        
        /**
         * Keep ticking a continuous task that has already been started
         */
        public void updateTask()
        {
            int i = this.squid.getIdleTime();

            if (i > 100)
            {
                this.squid.setMovementVector(0.0F, 0.0F, 0.0F);
            }
            else if (this.squid.getRNG().nextInt(50) == 0 || !this.squid.inWater || !this.squid.hasMovementVector())
            {
                if(this.squid.getAttackTarget() != null) {
                	float distance = squid.getDistanceToEntity(squid.getAttackTarget());
                	float f = (float)((this.squid.getAttackTarget().posX - this.squid.posX) / distance) * 0.7F;
                    float f1 = (float)((this.squid.getAttackTarget().posY - this.squid.posY) / distance) * 0.7F;
                    float f2 = (float)((this.squid.getAttackTarget().posZ - this.squid.posZ) / distance) * 0.7F;
                    this.squid.setMovementVector(f, f1, f2);
                }
            }
        }
           
    }
    
    static class AIDragTarget extends EntityAIBase {

    	private final EntityGiantSquid squid;
    	private int dragTime = 80;
    	
    	public AIDragTarget(EntityGiantSquid entity)
        {
            this.squid = entity;
        }
    	
    	@Override
		public boolean shouldExecute() {
			return squid.dragTarget != null;
		}
    	
    	/**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask()
        {
        	this.dragTime = 80;
        	this.squid.dragTarget = null;
        }
    	
    	public void updateTask() {
    		if(squid.dragTarget.isDead) {
    			this.dragTime = 80;
            	this.squid.dragTarget = null;
    		} else {
    			if(dragTime > 0) {
        			dragTime--;
        			squid.motionY = -0.1;
            		squid.dragTarget.setPositionAndUpdate(squid.posX, squid.posY + squid.getMountedYOffset(), squid.posZ);
        		} else {
        			dragTime = 80;
        			squid.dragTarget = null;
        		}
    		}
    	}
    	
    }

}
