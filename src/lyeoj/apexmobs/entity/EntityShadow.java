package lyeoj.apexmobs.entity;

import lyeoj.apexmobs.init.AMPotion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityShadow extends EntityMob {

	
	public EntityShadow(World worldIn) {
		super(worldIn);
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void initEntityAI()
    {
		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityMob.class, true));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
    }
	
	public void onLivingUpdate() {
		if (this.world.isRemote)
        {
            for (int i = 0; i < 2; ++i)
            {
                this.world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.1D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D ) * 0.5, this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 0.5);
            }
        }
		super.onLivingUpdate();
	}
	
	/**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class <? extends EntityLivingBase > cls)
    {
    	return cls == EntityCreeper.class ? false : cls == EntityShadow.class ? false : super.canAttackClass(cls);
    }
	
	public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float)((int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

        if (flag)
        {
            this.applyEnchantments(this, entityIn);
        }
        
        this.setDead();
        if(entityIn instanceof EntityLivingBase) {
        	if(((EntityLivingBase) entityIn).isPotionActive(AMPotion.POSSESSED)) {
        		int level = ((EntityLivingBase) entityIn).getActivePotionEffect(AMPotion.POSSESSED).getAmplifier();
        		((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(AMPotion.POSSESSED, 9999999, level+1));
        	}
        	((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(AMPotion.POSSESSED, 9999999));
        	((EntityLivingBase) entityIn).setHealth(((EntityLivingBase) entityIn).getMaxHealth());
        }

        return flag;
    }
	
	/**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        super.onUpdate();

        if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
        {
            this.setDead();
        }
    }
    
}
