package lyeoj.apexmobs.entity.projectile;

import lyeoj.apexmobs.entity.EntityShadow;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySplashPotionPossession extends EntityAMPotion {
	
	public EntitySplashPotionPossession(World worldIn)
    {
        super(worldIn);
    }
	
	public EntitySplashPotionPossession(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn)
    {
        super(worldIn, throwerIn, potionDamageIn);
    }

    public EntitySplashPotionPossession(World worldIn, double x, double y, double z, ItemStack potionDamageIn)
    {
        super(worldIn, x, y, z, potionDamageIn);
    }
    
    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected void onImpact(RayTraceResult result)
    {
        if (!this.world.isRemote)
        {   	
        	EntityShadow shadow = new EntityShadow(this.world);
        	shadow.setPosition(this.posX, this.posY, this.posZ);
        	this.world.spawnEntity(shadow);
        	
            int i = 2002;
            this.world.playEvent(i, new BlockPos(this), 0x111111);
            this.setDead();
        }
    }

}
