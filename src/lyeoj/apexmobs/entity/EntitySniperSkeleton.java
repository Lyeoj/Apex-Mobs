package lyeoj.apexmobs.entity;

import lyeoj.apexmobs.entity.projectile.EntitySniperArrow;
import lyeoj.apexmobs.init.AMSoundEvents;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRangedBow;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySniperSkeleton extends EntitySkeleton {
	
	private final EntityAIAttackRangedBow<AbstractSkeleton> aiArrowAttack = new EntityAIAttackRangedBow<AbstractSkeleton>(this, 1.0D, 20, 80.0F);
	private int glowCooldown;
	private int firingDelay;
	private EntityLivingBase target;
	private float distanceFactor;
	
	public EntitySniperSkeleton(World worldIn) {
		super(worldIn);
		glowCooldown = 45;
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(80.0D);
    }
	
//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	protected void initEntityAI() {
//		this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityPlayer.class, 10.0F, 1.0D, 1.2D));
//	}
	
	/**
     * Determines if an entity can be despawned, used on idle far away entities
     */
    protected boolean canDespawn()
    {
        return this.getAttackTarget() == null;
    }
    
    public void onLivingUpdate() {
    	if(this.isGlowing()) {
    		if(glowCooldown > 0) {
    			glowCooldown--;
    		} else {
    			glowCooldown = 45;
    			this.setGlowing(false);
    		}
    	}
    	if(firingDelay > 0) {
    		firingDelay--;
    	}
    	if(firingDelay == 1 && this.target != null) {
    		fire(target, distanceFactor);
    	}
    	super.onLivingUpdate();
    }
	
	/**
     * sets this entity's combat AI.
     */
    public void setCombatTask()
    {
        if (this.world != null && !this.world.isRemote)
        {
            this.tasks.removeTask(this.aiArrowAttack);
            ItemStack itemstack = this.getHeldItemMainhand();

            if (itemstack.getItem() == Items.BOW)
            {
                int i = 60;

                if (this.world.getDifficulty() != EnumDifficulty.HARD)
                {
                    i = 80;
                }

                this.aiArrowAttack.setAttackCooldown(i);
                this.tasks.addTask(4, this.aiArrowAttack);
            }
        }
    }
    
    /**
     * Attack the specified entity using a ranged attack.
     */
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
        this.target = target;
        this.distanceFactor = distanceFactor;
    	firingDelay = 20;
    	this.playSound(AMSoundEvents.SNIPER_PREPARE, 5.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
    	this.setGlowing(true);
    }
    
    private void fire(EntityLivingBase target, float distanceFactor) {
    	EntitySniperArrow entityarrow = new EntitySniperArrow(this.world, this);
        entityarrow.setEnchantmentEffectsFromEntity(this, distanceFactor);
        double d0 = target.posX - this.posX;
        double d1 = target.getEntityBoundingBox().minY + (double)(target.height / 3.0F) - entityarrow.posY;
        double d2 = target.posZ - this.posZ;
        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        entityarrow.setThrowableHeading(d0, d1 + d3 * 0.01D, d2, 8.5F, 0);
        this.playSound(AMSoundEvents.SNIPER_SHOOT, 5.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entityarrow);
    }
	
}
