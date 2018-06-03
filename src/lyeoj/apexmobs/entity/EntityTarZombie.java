package lyeoj.apexmobs.entity;

import lyeoj.apexmobs.init.AMBlocks;
import lyeoj.apexmobs.init.AMPotion;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityTarZombie extends EntityZombie {
	
	public EntityTarZombie(World worldIn) {
		super(worldIn);
		this.isImmuneToFire = true;
	}
	
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
    }
	
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		int a = MathHelper.floor(this.posX);
        int b = MathHelper.floor(this.posY);
        int c = MathHelper.floor(this.posZ);
    	
        for (int l = 0; l < 4; ++l)
        {
            a = MathHelper.floor(this.posX + (double)((float)(l % 2 * 2 - 1) * 0.25F));
            b = MathHelper.floor(this.posY);
            c = MathHelper.floor(this.posZ + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos blockpos = new BlockPos(a, b, c);

            if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && AMBlocks.TAR_LAYER.canPlaceBlockAt(this.world, blockpos))
            {
            	this.world.setBlockState(blockpos, AMBlocks.TAR_LAYER.getDefaultState());
            }
        }
	}
	
	public boolean attackEntityAsMob(Entity entityIn)
    {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag && entityIn instanceof EntityLivingBase)
        {
            float f = this.world.getDifficultyForLocation(new BlockPos(this)).getAdditionalDifficulty() + 1;
            ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(AMPotion.TARRED, 200 * (int)f));
        }

        return flag;
    }

}
