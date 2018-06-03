package lyeoj.apexmobs.event;

import java.util.Random;

import lyeoj.apexmobs.entity.EntityGiantSquid;
import lyeoj.apexmobs.entity.EntityHungrySlime;
import lyeoj.apexmobs.entity.EntityStrayGuardian;
import lyeoj.apexmobs.entity.EntityWanderingFeesh;
import lyeoj.apexmobs.entity.boss.EntityApexFeesh;
import lyeoj.apexmobs.entity.boss.EntityApexFeeshMinion;
import lyeoj.apexmobs.entity.boss.EntityApexSlime;
import lyeoj.apexmobs.init.AMPotion;
import lyeoj.apexmobs.init.AMSoundEvents;
import lyeoj.apexmobs.items.ItemSniperBow;
import lyeoj.apexmobs.main.ApexMobs;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.init.Biomes;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ApexMobs.MODID)
public class AMMobEvents {
	
	private static Random rand = new Random();
	
	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		DamageSource source = event.getSource();
		
		if(source == DamageSource.LIGHTNING_BOLT) {
			entity.addPotionEffect(new PotionEffect(AMPotion.ZAPPED, 2000));
		}
		
		if(entity instanceof EntitySquid || entity instanceof EntityGiantSquid) {
			if(Math.random() * 7 < 1) {
				for(int i = 0; i < 2; i++) {
					EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(entity.world, entity.posX, entity.posY + i, entity.posZ);
			        entityareaeffectcloud.setRadius(3.0F);
			        entityareaeffectcloud.setRadiusOnUse(-0.5F);
			        entityareaeffectcloud.setWaitTime(10);
			        entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
			        entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / (float)entityareaeffectcloud.getDuration());

			        entityareaeffectcloud.addEffect(new PotionEffect(AMPotion.INKED, 400));

			        entity.world.spawnEntity(entityareaeffectcloud);
				}
			}
		}
				
		if(entity instanceof EntityHungrySlime || entity instanceof EntityWanderingFeesh || entity instanceof EntityApexFeesh || entity instanceof EntityApexFeeshMinion) {
			if(source == DamageSource.IN_WALL) {
				event.setCanceled(true);
				if(!entity.isRiding() && entity instanceof EntityHungrySlime) {
					if(entity.isEntityInsideOpaqueBlock()) {			
						boolean flag = false;
						int i = MathHelper.floor(entity.posX);
				        int j = MathHelper.floor(entity.posY);
				        int k = MathHelper.floor(entity.posZ);
						for (int l = 0; l < 50; ++l)
				        {
				            int i1 = i + MathHelper.getInt(AMMobEvents.rand, 1, 6) * MathHelper.getInt(AMMobEvents.rand, -1, 1);
				            int j1 = j + MathHelper.getInt(AMMobEvents.rand, 1, 6) * MathHelper.getInt(AMMobEvents.rand, -1, 1);
				            int k1 = k + MathHelper.getInt(AMMobEvents.rand, 1, 6) * MathHelper.getInt(AMMobEvents.rand, -1, 1);

				            if (entity.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(entity.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP))
				            {
				                entity.setPosition((double)i1, (double)j1, (double)k1);

				                if (entity.world.checkNoEntityCollision(entity.getEntityBoundingBox(), entity) && entity.world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !entity.world.containsAnyLiquid(entity.getEntityBoundingBox()))
				                {

				                    flag = true;
				                    break;
				                }
				            }
				        }
						if(!flag) {
							entity.setPositionAndUpdate(entity.posX, entity.posY + 1, entity.posZ);
						}
					}
				}
			}
		}
		
		if(entity instanceof EntityApexSlime) {
			if(((EntityApexSlime) entity).getSlimeSize() > 1) {
				if(source != DamageSource.OUT_OF_WORLD) {
					((EntityApexSlime) entity).incHitCount();
					event.setCanceled(true);
				}
				if(source == DamageSource.IN_WALL) {
					entity.setPositionAndUpdate(entity.posX, entity.posY + 1, entity.posZ);
				}
			} else {
				if(source == DamageSource.FALL) {
					event.setCanceled(true);
				}
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingFall(LivingFallEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if(entity instanceof EntityApexFeesh || entity instanceof EntityApexFeeshMinion) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public static void onLivingAttack(LivingAttackEvent event) {
		if(event.getSource().getTrueSource() instanceof EntityStrayGuardian) {
			event.getEntityLiving().addPotionEffect(new PotionEffect(AMPotion.WATERLOGGED, 
					event.getEntityLiving().getEntityWorld().getDifficulty() == EnumDifficulty.HARD ? 260 : 160));
		}
		
	}
	
	@SubscribeEvent
	public static void onLivingUseItem(LivingEntityUseItemEvent.Tick event) {
		Item item = event.getItem().getItem();
		int useTime = item.getMaxItemUseDuration(null) - event.getDuration();
		if(item instanceof ItemSniperBow) {
			if(useTime == 60) {
				event.getEntityLiving().playSound(AMSoundEvents.SNIPER_PREPARE, 5.0F, 1.0F / (AMMobEvents.rand.nextFloat() * 0.4F + 0.8F));
			} 
			if(useTime >= 55) {
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20));
				event.getEntityLiving().addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 254));
			}
		}
	}
	
	@SubscribeEvent
	public static void onLivingSpawn(EntityJoinWorldEvent event) {
		if(event.getEntity() instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase)event.getEntity();
			if(entity instanceof EntitySquid) {
				if(!entity.hasCustomName() && (entity.world.getBiome(new BlockPos(entity)) == Biomes.OCEAN || entity.world.getBiome(new BlockPos(entity)) == Biomes.DEEP_OCEAN || entity.world.getBiome(new BlockPos(entity)) == Biomes.FROZEN_OCEAN) && entity.world.getDifficulty() != EnumDifficulty.PEACEFUL && !entity.world.isRemote) {
					if(entity.getRNG().nextInt(50) == 1) {
						EntityGiantSquid squid = new EntityGiantSquid(entity.world);
						squid.copyLocationAndAnglesFrom(entity);
						entity.world.spawnEntity(squid);
						entity.setDead();
					} else {
						if(entity.getRNG().nextInt(60) == 1) {
							EntityStrayGuardian guardian = new EntityStrayGuardian(entity.world);
							guardian.copyLocationAndAnglesFrom(entity);
							entity.world.spawnEntity(guardian);
							guardian.motionY = -30;
							entity.setDead();
						}
					}
				}
			}
		}
	}

}
