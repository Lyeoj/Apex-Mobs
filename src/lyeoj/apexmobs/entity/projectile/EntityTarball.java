package lyeoj.apexmobs.entity.projectile;

import lyeoj.apexmobs.init.AMBlocks;
import lyeoj.apexmobs.init.AMPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityTarball extends EntityThrowable {

	public EntityTarball(World worldIn) {
		super(worldIn);
	}
	
	public EntityTarball(World worldIn, EntityLivingBase throwerIn)
    {
        super(worldIn, throwerIn);
    }
	
	public EntityTarball(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
	
	@SuppressWarnings("deprecation")
	protected void onImpact(RayTraceResult result) {
		
		
		if (!this.world.isRemote)
        {
			if(result.entityHit != null && result.entityHit instanceof EntityLivingBase) {
				EntityLivingBase entity = (EntityLivingBase)result.entityHit;
				if(!entity.isImmuneToFire()) {
					entity.attackEntityFrom(DamageSource.causeIndirectDamage(this.getThrower(), entity), 1.0F);
					entity.addPotionEffect(new PotionEffect(AMPotion.TARRED, this.world.getDifficulty() == EnumDifficulty.HARD ? 800 : 500));
				}
			} else if(result.typeOfHit == Type.BLOCK) {
				if(result.sideHit == EnumFacing.UP && this.world.getBlockState(result.getBlockPos()).isOpaqueCube()) {
					for(int i = 0; i < 3; i++) {
						for(int j = 0; j < 3; j++) {
							BlockPos pos = result.getBlockPos().add(1 - i, 1, 1 - j);
							if(this.world.getBlockState(pos).getBlock().isReplaceable(this.world, pos) && this.world.getBlockState(pos.add(0, -1, 0)).getBlock().isTopSolid(this.world.getBlockState(pos.add(0, -1, 0)))) {
								this.world.setBlockState(pos, AMBlocks.TAR_LAYER.getDefaultState());
							}
						}
					}
		            this.world.setBlockState(result.getBlockPos().add(0, 1, 0), AMBlocks.TAR_LAYER.getDefaultState());
				}
			}
			
			this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
	}

}
