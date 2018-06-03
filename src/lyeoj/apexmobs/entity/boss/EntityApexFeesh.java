package lyeoj.apexmobs.entity.boss;

import java.util.ArrayList;

import lyeoj.apexmobs.init.AMBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityApexFeesh extends EntityMob {
	
	private EntityApexFeesh.AIDigEscape digEscape;
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.WHITE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private ArrayList<EntityApexFeeshMinion> minions;
	private EntityApexFeeshMinion projectile;
	private int recallTimer;
	private boolean recentlyRecalled;
	private int strafeTimer;
	private int mudTimer;
	private int stackTimer;
	private int stackIndex;
	private boolean stacking;
	private int jumpTimer;
	private int projectileTimer;
	
	public EntityApexFeesh(World worldIn)
    {
        super(worldIn);
        this.setSize(0.6F, 0.5F);  
    }
	
	/**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        compound.setInteger("recallTimer", recallTimer);
        compound.setInteger("strafeTimer", strafeTimer);
        compound.setInteger("mudTimer", mudTimer);
        compound.setInteger("stackTimer", stackTimer);
        compound.setInteger("stackIndex", stackIndex);
        compound.setInteger("jumpTimer", jumpTimer);
        compound.setInteger("projectileTimer", projectileTimer);
        
        compound.setBoolean("recentlyRecalled", recentlyRecalled);
        compound.setBoolean("stacking", stacking);
        
        
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        
        this.recallTimer = compound.getInteger("recallTimer");
        this.strafeTimer = compound.getInteger("strafeTimer");
        this.mudTimer = compound.getInteger("mudTimer");
        this.stackTimer = compound.getInteger("stackTimer");
        this.stackIndex = compound.getInteger("stackIndex");
        this.jumpTimer = compound.getInteger("jumpTimer");
        this.projectileTimer = compound.getInteger("projectileTimer");
        
        this.recentlyRecalled = compound.getBoolean("recentlyRecalled");
        this.stacking = compound.getBoolean("stacking");

    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void initEntityAI()
    {
		this.digEscape = new AIDigEscape(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.digEscape);
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
    }
	
	protected void entityInit()
    {
        super.entityInit();
        minions = new ArrayList<EntityApexFeeshMinion>();
    }
	
	/**
     * Add the given player to the list of players tracking this entity. For instance, a player may track a boss in
     * order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See {@link Entity#addTrackingPlayer} for
     * more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
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
    
    public boolean canDespawn() {
    	return false;
    }

    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
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
                if(this.rand.nextInt(4) == 2) {
                	this.digEscape.notifyHurt();
                	if(this.rand.nextInt(3) == 1) {
                		for(int i = minions.size() - 1; i >= 0; i--) {
                			if(minions.get(i) instanceof EntityApexFeeshMinion) {
                				if(minions.get(i).isDead) {
                					minions.remove(i);
                				} else {
                					minions.get(i).scatter();
                				}
                			}
                		}
                	} else {
                		this.recallTimer = 30;
                	}
                }
            }
        	
        	return super.attackEntityFrom(source, amount);
        }
    }
    
    public boolean attackEntityAsMob(Entity entityIn) {
    	boolean flag = super.attackEntityAsMob(entityIn);
    	if(flag) {
    		entityIn.motionY = -4;
    	}
    	return flag;
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
    
    private void orderStrafe() {
    	for(int i = minions.size() - 1; i >= 0; i--) {
			if(minions.get(i) instanceof EntityApexFeeshMinion) {
				if(minions.get(i).isDead) {
					minions.remove(i);
				} else {
					minions.get(i).setStrafing(true);
				}
			}
		}
    }
    
    private void recallMinions() {
    	for(int i = minions.size() - 1; i >= 0; i--) {
			if(minions.get(i) instanceof EntityApexFeeshMinion) {
				if(minions.get(i).isDead) {
					minions.remove(i);
				} else {
					minions.get(i).regroup();
				}
			}
		}
    	this.recentlyRecalled = true;
    }
    
    private void stack(int index) {
    	cleanMinionList();
		EntityApexFeeshMinion minion = minions.get(index);
		if(index == minions.size() - 1) {
			this.startRiding(minion, true);
			this.stacking = false;
			this.jumpTimer = 0;
			this.stackIndex = 0;
		} else {
			minions.get(index + 1).startRiding(minion, true);
		}
		
    }
    
    private void breakDown() {
    	this.dismountRidingEntity();
    	this.noClip = false;
    	cleanMinionList();
    	for(int i = 0; i < minions.size(); i++) {
    		EntityApexFeeshMinion minion = minions.get(i);
			minion.dismountRidingEntity();
			minion.noClip = false;
			minion.setPositionAndUpdate(minion.posX + (Math.random() - 0.5), minion.posY + (Math.random() - 0.5), minion.posZ + (Math.random() - 0.5));
		}
    }
    
    private void cleanMinionList() {
    	if(this.world.getDifficulty() != EnumDifficulty.HARD && minions.size() > 10) {
    		for(int i = 0; i < minions.size(); i++) {
    			minions.get(i).setDead();
    			minions.remove(i);
    		}
    	}
    	for(int i = 0; i < minions.size(); i++) {
			if(minions.get(i) instanceof EntityApexFeeshMinion) {
				if(minions.get(i).isDead) {
					minions.remove(i);
				}
			}
		}
    }
    
    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
    	this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
    	this.renderYawOffset = this.rotationYaw;
        super.onUpdate();
    }
    
    public void onLivingUpdate() {
    	
    	EntityLivingBase target = this.getAttackTarget();
    	
    	if(this.projectile == null && target != null && MathHelper.floor(this.getAttackTarget().posY) >= MathHelper.floor(this.posY) + 2 && !stacking) {
    		this.jumpTimer++;
    		if(this.jumpTimer > 100) {
    			cleanMinionList();
    			if(minions.size() > 0) {
    				this.projectile = minions.get(0);
    				this.projectile.setInvisible(true);
    			}
    			jumpTimer = 0;
    		}
    	} else {
    		this.jumpTimer = 0;
    	}
    	
    	if(this.projectile != null && target != null) {
    		if(this.projectile.isDead) {
    			this.projectile = null;
    			this.projectileTimer = 0;
    		}
    		if(this.projectileTimer < 80) {
    			this.projectileTimer++;
    			Vec3d path = new Vec3d(target.posX - projectile.posX, target.posY - projectile.posY, target.posZ - projectile.posZ);
        		path = path.normalize();
        		path = path.scale(.8);
        		projectile.motionX = path.x;
        		projectile.motionY = path.y;
        		projectile.motionZ = path.z;
    		} else {
    			this.projectileTimer = 0;
    			this.projectile.setInvisible(false);
    			this.projectile = null;
    		}
    	} else {
    		projectileTimer = 0;
    	}
    	
    	if(stacking && !this.world.isRemote) {
    		this.stack(stackIndex);
    		stackIndex++;
    	}
    	
    	if(this.isRiding() && this.getRidingEntity() instanceof EntityLiving) {
    		if(this.stackTimer < 200) {
    			this.stackTimer++;
    		} else {
    			this.stackTimer = 0;
    			this.breakDown();
    		}
    	}
    	
    	if(this.mudTimer < 25) {
    		this.mudTimer++;
    	} else {
    		this.mudTimer = 0;
    		int a = MathHelper.floor(this.posX);
            int b = MathHelper.floor(this.posY);
            int c = MathHelper.floor(this.posZ);
        	
            for (int l = 0; l < 4; ++l)
            {
                a = MathHelper.floor(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                b = MathHelper.floor(this.posY);
                c = MathHelper.floor(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockpos = new BlockPos(a, b, c);

                if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && AMBlocks.MUD_LAYER.canPlaceBlockAt(this.world, blockpos))
                {
                	this.world.setBlockState(blockpos, AMBlocks.MUD_LAYER.getDefaultState());
                }
            }
    	}
    	
    	if(this.recallTimer > 0) {
    		recallTimer--;
    		if(this.recallTimer == 1) {
    			this.recallMinions();
    		}
    	}
    	
    	if(this.recentlyRecalled) {
			if(this.strafeTimer < 50) {
    			this.strafeTimer++;
    		} else {
    			this.strafeTimer = 0;
    			this.recentlyRecalled = false;
    			if(this.rand.nextInt(2) == 0) {
    				this.orderStrafe();
    			} else {
    				if(this.projectile == null) {
    					this.stacking = true;
    				}
    			}
    		}
    		 
    	}
    	
    	if(this.ticksExisted % 400 == 0) {
    		cleanMinionList();
    		if(rand.nextInt(4) == 2) {
    			this.orderStrafe();
    		} else if(rand.nextInt(4) == 1) {
    			if(this.projectile == null) {
    				this.stacking = true;
    			}
    		}
    	}
    	
    	if(this.minions.size() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 15 : 10)) {
    		if(!this.world.isRemote) {
    			for(int i = 0; i < (this.world.getDifficulty() == EnumDifficulty.HARD ? 15 : 10) - minions.size(); i++) {
        			EntityApexFeeshMinion minion = new EntityApexFeeshMinion(this.world, this);
        			minion.copyLocationAndAnglesFrom(this);
        			minion.setPositionAndUpdate(this.posX + MathHelper.getInt(this.rand, -3, 3), this.posY + MathHelper.getInt(this.rand, -1, -5), this.posZ + MathHelper.getInt(this.rand, -3, 3));
        			this.minions.add(minion);
        			this.world.spawnEntity(minion);
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
        }
    	
    	super.onLivingUpdate();
    }
    
    static class AIDigEscape extends EntityAIBase {

		private boolean angry = false;
		private EntityApexFeesh feesh;
		private int maxDigTime;
		private int digTime;
		private double offset1;
		private double offset2;
		
		public AIDigEscape(EntityApexFeesh feesh)
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
        	int a = feesh.getRNG().nextBoolean() ? 1 : -1;
        	int b = feesh.getRNG().nextBoolean() ? 1 : -1;
        	this.offset1 = (Math.random() * 0.5 + 0.25) * a;
        	this.offset2 = (Math.random() * 0.5 + 0.25) * b;
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

    
    

}
