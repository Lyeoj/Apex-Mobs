package lyeoj.apexmobs.event;

import lyeoj.apexmobs.entity.EntityShadow;
import lyeoj.apexmobs.init.AMDamageSources;
import lyeoj.apexmobs.init.AMPotion;
import lyeoj.apexmobs.init.AMSoundEvents;
import lyeoj.apexmobs.main.ApexMobs;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.client.event.EntityViewRenderEvent.FogColors;
import net.minecraftforge.client.event.EntityViewRenderEvent.RenderFogEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ApexMobs.MODID)
public class AMPotionEvents {

	private static int zapTimer;
	private static int possessKillTimer;
	
	@SubscribeEvent
	public static void onLivingTick(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		//Waterlogged effect
		if(entity.isPotionActive(AMPotion.WATERLOGGED)) {
			if(entity.isInWater()) {
				if(entity.motionY > 0) {
					entity.motionY -= 0.2;
				}
			}
		}
		
		//Possessed effect
		if(entity.isPotionActive(AMPotion.POSSESSED)) {
			if(possessKillTimer > 0) {
				possessKillTimer--;			
			} else {
				int killNum = (int)(Math.random() * 4000);
				int killChance = (int)Math.pow(2, entity.getActivePotionEffect(AMPotion.POSSESSED).getAmplifier() + 2);
				if(killNum <= killChance) {
					entity.attackEntityFrom(AMDamageSources.POSSESSED, Float.MAX_VALUE);
				}
				possessKillTimer = 400;
			}
			if (entity instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)entity;
				player.addExhaustion(0.01F * (float)(entity.getActivePotionEffect(AMPotion.POSSESSED).getAmplifier() + 1));
                if(player.getFoodStats().getFoodLevel() == 0) {
                	player.attackEntityFrom(DamageSource.STARVE, Float.MAX_VALUE);
                }
            }
		}
		
		//Frozen effect
		if(entity.isPotionActive(AMPotion.FROZEN)) {
			if(entity.motionY > 0) {
				entity.motionY -= 0.2;
			}
			if(entity.isBurning()) {
				int dur = entity.getActivePotionEffect(AMPotion.FROZEN).getDuration();
				entity.removeActivePotionEffect(AMPotion.FROZEN);
				entity.addPotionEffect(new PotionEffect(AMPotion.FROZEN, dur - 3));
			}
		}
		
		//Zapped effect
		if(entity.isPotionActive(AMPotion.ZAPPED)) {
			if(zapTimer == 0) {
				entity.attackEntityFrom(AMDamageSources.ELECTRIC, 2.0F);
			}
			if(entity.world.isRemote) {
				if(zapTimer == 20) {
					entity.playSound(AMSoundEvents.ZAPCHARGE, 1.0F, 1.0F);
				}
				if(zapTimer > 0) {
					zapTimer--;
				} else {
					entity.playSound(AMSoundEvents.ZAPPED, 1.0F, 1.0F);
					zapTimer = (int)(Math.random() * 600);
					double x = (Math.random() * -4 + 2);
					double z = (Math.random() * -4 + 2);
					entity.motionX = (x);
					entity.motionY = 0.4;
					entity.motionZ = (z);
				}
			}
		}
		
		//Waterwalking effect
		if(entity.isPotionActive(AMPotion.WATERWALKING)) {
			if(entity.world.handleMaterialAcceleration(entity.getEntityBoundingBox().grow(0.0D, -1.0D, 0.0D).shrink(0.001D), Material.WATER, entity) && !entity.isInsideOfMaterial(Material.WATER) && !entity.isSneaking()) {
				entity.motionY = 0.2;
				if(entity.isSprinting()) {
					entity.motionX *= 2.2;
					entity.motionZ *= 2.2;
				} else {
					entity.motionX *= 1.4;
					entity.motionZ *= 1.4;
				}
			}

			if(entity.isInWater()) {
				if(entity.motionY > 0 && entity.motionY < 2) {
					entity.motionY += 0.2;
				}
			}
		} 
	}
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource source = event.getSource();
		
		if(entity.isPotionActive(AMPotion.TARRED)) {
			if(source == DamageSource.ON_FIRE || source == DamageSource.HOT_FLOOR || source == DamageSource.LIGHTNING_BOLT || source == DamageSource.FIREWORKS
					|| source.damageType == "mob" || source.damageType == "player" || source.damageType == "arrow" || source.damageType == "explosion.player"
					|| source.damageType == "explosion" || source.damageType == "thrown" || source == AMDamageSources.ELECTRIC) {
				entity.setFire(3);
			}
		}
		
	}
	
	@SubscribeEvent
	public static void onLivingJump(LivingJumpEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		//Possessed Jump Boost
		if(entity.isPotionActive(AMPotion.POSSESSED))
        {
            entity.motionY += (double)((float)(entity.getActivePotionEffect(AMPotion.POSSESSED).getAmplifier() + 1) * 0.1F);
        }
			
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		
		if(entity.isPotionActive(AMPotion.POSSESSED)) {		
	        float f = entity.getActivePotionEffect(AMPotion.POSSESSED).getAmplifier() + 1;
	        int i = MathHelper.ceil((event.getDistance() - 3.0F - f) * event.getDamageMultiplier());
	        if(i <= 0) {
	        	event.setCanceled(true);
	        }
		}
	}
	@SubscribeEvent
	public static void renderFogDistance(RenderFogEvent event) {
		if(event.getEntity() instanceof EntityLivingBase) {
			//EntityLivingBase entity = (EntityLivingBase)event.getEntity();					
		}
	}
	
	@SubscribeEvent
	public static void renderFogColor(FogColors event) {
		
		if(event.getEntity() instanceof EntityLivingBase) {
			//EntityLivingBase entity = (EntityLivingBase)event.getEntity();						
		}
	}
	
	@SubscribeEvent
	public static void renderCameraStuff(CameraSetup event) {
		if(event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase)event.getEntity();
			
			if(entity.isPotionActive(AMPotion.ZAPPED)) {
				if(zapTimer < 20) {
					event.setYaw((float)(Math.random() * 360));
					event.setPitch((float)(Math.random() * 360));
					event.setRoll((float)(Math.random() * 360));
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if(entity.isPotionActive(AMPotion.POSSESSED)) {
			
			for(int i = 0; i < entity.getActivePotionEffect(AMPotion.POSSESSED).getAmplifier() + 1; i++) {
				if(i >= 10) {
					break;
				}
				if(!entity.world.isRemote) {
					EntityShadow shadow = new EntityShadow(entity.world);
					shadow.copyLocationAndAnglesFrom(entity);
					entity.world.spawnEntity(shadow);
				}
			}
		}
	}

}
