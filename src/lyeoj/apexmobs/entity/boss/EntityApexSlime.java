package lyeoj.apexmobs.entity.boss;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityApexSlime extends EntityApexSlimeMinion {
	
	private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(this.getDisplayName(), BossInfo.Color.GREEN, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
	private static final DataParameter<Boolean> SPAWNING = EntityDataManager.<Boolean>createKey(EntityApexSlime.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> SPAWNCOUNTER = EntityDataManager.<Integer>createKey(EntityApexSlime.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> HITCOUNTER = EntityDataManager.<Integer>createKey(EntityApexSlime.class, DataSerializers.VARINT);
	private boolean spawnStart;
	
	public EntityApexSlime(World worldIn) {
		super(worldIn);	
	}
	
	protected void entityInit()
    {
        super.entityInit();
        spawnStart = false;
        this.dataManager.register(SPAWNING, Boolean.valueOf(false));
        this.dataManager.register(SPAWNCOUNTER, 0);
        this.dataManager.register(HITCOUNTER, 0);
    }
	
	public void setSpawning(boolean flag)
    {
    	this.getDataManager().set(SPAWNING, flag);
        this.getDataManager().setDirty(SPAWNING);
    }

    public boolean isSpawning()
    {
        return this.getDataManager().get(SPAWNING);
    }
    
    public void setSpawnCounter(int count) {
    	this.getDataManager().set(SPAWNCOUNTER, count);
    }
    
    public int getSpawnCounter() {
    	return this.getDataManager().get(SPAWNCOUNTER);
    }
    
    public int getHitCounter() {
    	return this.getDataManager().get(HITCOUNTER);
    }
    
    public void setHitCounter(int count) {
    	this.getDataManager().set(HITCOUNTER, count);
    }
	
	protected void initEntityAI()
    {
		super.initEntityAI();
		this.targetTasks.addTask(2, new EntityApexSlime.AIApexSlimeMegaJump(this));
    }
	
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64);
	}
	
	/**
     * Sets the custom name tag for this entity
     */
    public void setCustomNameTag(String name)
    {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
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
	
	@Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
    	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextGaussian() * 0.05D, 1));
    	this.setSlimeSize(10, true);
    	
        return livingdata;
    }
	
	protected SoundEvent getJumpSound()
    {
        return SoundEvents.ENTITY_SLIME_JUMP;
    }
	
	protected void setSlimeSizeVisual(int size) {
		if(size > 10) {
			size = 10;
		}
		this.setSlimeSize(size, false);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double)(10 * 10));
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((double)(0.2F + 0.1F * (float)10));
	}
	
	protected float getSquishAmount(int size) {
		switch(size) {
		case 10: return -4.5F;
		case 9: return -4.0F;
		case 8: return -3.5F;
		case 7: return -3.0F;
		case 6: return -2.5F;
		case 5: return -2.0F;
		case 4: return -1.7F;
		case 3: return -1.2F;
		case 2: return -0.8F;
		default: return -0.5F;
		}
	}
	
	protected float getSquishOffset(int size) {
		switch(size) {

		case 7: return 0.9F;
		case 6: return 0.85F;
		case 5: return 0.8F;
		case 4: return 0.7F;
		case 3: return 0.6F;
		case 2: return 0.45F;
		case 1: return 0.4F;
		default: return 1;
		}
		
	}
	
	/**
     * Gets the amount of damage dealt to the player when "attacked" by the slime.
     */
    protected int getAttackStrength()
    {
        return 8;
    }
	
	protected void updateAITasks() {
		super.updateAITasks();
    }
	
	private boolean startSpawning() {
		if(this.onGround) {
			setSpawning(true);
			this.setNoGravity(true);
			this.noClip = true;
			this.setPositionAndUpdate(this.posX, this.posY - this.getSquishOffset(this.getSlimeSize()), this.posZ);
			return true;
		} else {
			return false;
		}
	}
	
	public void incHitCount() {
		if(!isSpawning()) {
			if(getHitCounter() < this.getSlimeSize()) {
				setHitCounter(getHitCounter() + 1);
			} else {
				setHitCounter(0);
				spawnStart = true;
			}
		}
	}
	
	public boolean spawnMinion() {
		int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);
        
        boolean flag = false;
		
        EntityApexSlimeMinion minion = new EntityApexSlimeMinion(this.world, this);
        
		for (int l = 0; l < 50; ++l)
        {
            int i1 = i + MathHelper.getInt(this.rand, 1, this.getSlimeSize() + 1) * MathHelper.getInt(this.rand, -1, 1);
            int j1 = j + MathHelper.getInt(this.rand, 1, 10) * MathHelper.getInt(this.rand, -1, 1);
            int k1 = k + MathHelper.getInt(this.rand, 1, this.getSlimeSize() + 1) * MathHelper.getInt(this.rand, -1, 1);

            if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP))
            {
                minion.setPosition((double)i1, (double)j1, (double)k1);

                if (!this.world.isRemote && this.world.checkNoEntityCollision(minion.getEntityBoundingBox(), minion) && this.world.getCollisionBoxes(minion, minion.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(minion.getEntityBoundingBox()))
                {
                    this.world.spawnEntity(minion);
                    minion.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(minion)), (IEntityLivingData)null);
                    flag = true;
                    break;
                }
            }
        }
		return flag;
	}
	
	public void onUpdate() {
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
		super.onUpdate();
		if(!isSpawning()) {
			this.setSlimeSizeVisual(((int)((this.getHealth() / this.getMaxHealth()) * 10) + 1));
		}
		//System.out.println(this.getSlimeSize() + " " + this.getSquishAmount(this.getSlimeSize()) + " " + this.getSquishOffset(this.getSlimeSize()));
		if(isSpawning()) {
			if(getSpawnCounter() < 40 * this.getSlimeSize()) {
				if(getSpawnCounter() % 20 == 0) {
					spawnMinion();					
				}
				//System.out.println(getSpawnCounter());
				setSpawnCounter(getSpawnCounter() + 1);
			} else {
				setSpawning(false);
				this.setNoGravity(false);
				this.noClip = false;
				this.setPositionAndUpdate(this.posX, this.posY + this.getSquishOffset(this.getSlimeSize()), this.posZ);
				setSpawnCounter(0);
			}
		} else {
			if(spawnStart) {
				if(startSpawning()) {
					setSpawnCounter(0);
					spawnStart = false;
				}
			}
		}
		if(isSpawning()) {
			this.motionY = 0;
			this.squishAmount = this.getSquishAmount(this.getSlimeSize());
		}
		
		if(this.isInLava()) {
			boolean flag = false;
			int i = MathHelper.floor(this.posX);
	        int j = MathHelper.floor(this.posY);
	        int k = MathHelper.floor(this.posZ);
			for (int l = 0; l < 50; ++l)
	        {
	            int i1 = i + MathHelper.getInt(this.rand, 1, 7) * MathHelper.getInt(this.rand, -1, 1);
	            int j1 = j + MathHelper.getInt(this.rand, 1, 7) * MathHelper.getInt(this.rand, -1, 1);
	            int k1 = k + MathHelper.getInt(this.rand, 1, 7) * MathHelper.getInt(this.rand, -1, 1);

	            if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP))
	            {
	                this.setPositionAndUpdate((double)i1, (double)j1, (double)k1);

	                if (!this.isEntityInsideOpaqueBlock() && !this.world.containsAnyLiquid(this.getEntityBoundingBox()))
	                {
	                    flag = true;
	                    break;	           
	                }
	            }
	        }
			if(!flag) {
				this.setPositionAndUpdate(this.posX, this.posY + 1, this.posZ);
			}
		}
	}
	
	/**
     * Indicates weather the slime is able to damage the player (based upon the slime's size)
     */
    protected boolean canDamagePlayer()
    {
        return true;
    }
    
    @Override
    public boolean isAIDisabled() {
    	return isSpawning();
    }
    
    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {
        super.writeEntityToNBT(compound);

        if (((Boolean)this.dataManager.get(SPAWNING)).booleanValue())
        {
            compound.setBoolean("spawning", true);
        }

        compound.setInteger("hitcounter", this.dataManager.get(HITCOUNTER));
        compound.setInteger("spawncounter", this.dataManager.get(SPAWNCOUNTER));
        compound.setBoolean("spawnstart", this.spawnStart);
    }
    
    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        super.readEntityFromNBT(compound);
        this.dataManager.set(SPAWNING, Boolean.valueOf(compound.getBoolean("spawning")));
        this.spawnStart = compound.getBoolean("spawnstart");
        
        if (compound.hasKey("hitcounter", 99))
        {
            this.dataManager.set(HITCOUNTER, compound.getInteger("hitcounter"));
        }
        
        if (compound.hasKey("spawncounter", 99))
        {
            this.dataManager.set(SPAWNCOUNTER, compound.getInteger("spawncounter"));
        }

    }
    
    static class AIApexSlimeMegaJump extends EntityAIBase {

    	private final EntityApexSlime slime;
    	private int jumpCooldown;

        public AIApexSlimeMegaJump(EntityApexSlime slimeIn)
        {
            this.slime = slimeIn;
            this.setMutexBits(2);
        }
    	
    	@Override
		public boolean shouldExecute() {
    		if(jumpCooldown < 80) {
    			jumpCooldown++;
    			return false;
    		} else {
    			if (slime.getAttackTarget() != null && slime.navigator.getPathToEntityLiving(slime.getAttackTarget()) == null) {
    				jumpCooldown = 0;
    				return true;
    			} else {
    				return false;
    			}
    		}
		}
    	
    	public void startExecuting() {
    		float distance = slime.getDistanceToEntity(slime.getAttackTarget());
    		slime.motionX = (slime.getAttackTarget().posX - slime.posX) / distance;
    		slime.motionY = 1.5F;
    		slime.motionZ = (slime.getAttackTarget().posZ - slime.posZ) / distance;
    	}
    }
	
}
