package lyeoj.apexmobs.init;

import java.util.ArrayList;

import lyeoj.apexmobs.entity.EntityFlinger;
import lyeoj.apexmobs.entity.EntityGiantSquid;
import lyeoj.apexmobs.entity.EntityHungrySlime;
import lyeoj.apexmobs.entity.EntityIceZombie;
import lyeoj.apexmobs.entity.EntityInferno;
import lyeoj.apexmobs.entity.EntityLightningZombie;
import lyeoj.apexmobs.entity.EntityMadScientist;
import lyeoj.apexmobs.entity.EntityRocketCreeper;
import lyeoj.apexmobs.entity.EntityShadow;
import lyeoj.apexmobs.entity.EntitySniperSkeleton;
import lyeoj.apexmobs.entity.EntityStrayGuardian;
import lyeoj.apexmobs.entity.EntityTarZombie;
import lyeoj.apexmobs.entity.EntityTest;
import lyeoj.apexmobs.entity.EntityWanderingFeesh;
import lyeoj.apexmobs.entity.boss.EntityAcidCloud;
import lyeoj.apexmobs.entity.boss.EntityApexFeesh;
import lyeoj.apexmobs.entity.boss.EntityApexFeeshMinion;
import lyeoj.apexmobs.entity.boss.EntityApexSlime;
import lyeoj.apexmobs.entity.boss.EntityApexSlimeMinion;
import lyeoj.apexmobs.entity.projectile.EntityAMPotion;
import lyeoj.apexmobs.entity.projectile.EntitySniperArrow;
import lyeoj.apexmobs.entity.projectile.EntitySplashPotionPossession;
import lyeoj.apexmobs.entity.projectile.EntityTarball;
import lyeoj.apexmobs.renderer.RenderAMPotion;
import lyeoj.apexmobs.renderer.RenderAcidCloud;
import lyeoj.apexmobs.renderer.RenderApexFeesh;
import lyeoj.apexmobs.renderer.RenderApexFeeshMinion;
import lyeoj.apexmobs.renderer.RenderApexSlime;
import lyeoj.apexmobs.renderer.RenderFlinger;
import lyeoj.apexmobs.renderer.RenderGiantSquid;
import lyeoj.apexmobs.renderer.RenderHungrySlime;
import lyeoj.apexmobs.renderer.RenderIceZombie;
import lyeoj.apexmobs.renderer.RenderInferno;
import lyeoj.apexmobs.renderer.RenderLightningZombie;
import lyeoj.apexmobs.renderer.RenderMadScientist;
import lyeoj.apexmobs.renderer.RenderRocketCreeper;
import lyeoj.apexmobs.renderer.RenderShadow;
import lyeoj.apexmobs.renderer.RenderSniperArrow;
import lyeoj.apexmobs.renderer.RenderSniperSkeleton;
import lyeoj.apexmobs.renderer.RenderStrayGuardian;
import lyeoj.apexmobs.renderer.RenderTarZombie;
import lyeoj.apexmobs.renderer.RenderTarball;
import lyeoj.apexmobs.renderer.RenderTestMob;
import lyeoj.apexmobs.renderer.RenderWanderingFeesh;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class AMEntities {

	public static final MobInfo[] MOBLIST = {
		//Don't forget to increment the id field by 1 when adding a new mob Lyeoj you silly person
		new MobInfo("testmob", EntityTest.class, 0, 64, 3, true, 0XFFFFFF, 0X000000, RenderTestMob.FACTORY),
		new MobInfo("wanderingfeesh", EntityWanderingFeesh.class, 1, 64, 3, true, 0X909090, 0X905E12, RenderWanderingFeesh.FACTORY),
		new MobInfo("flinger", EntityFlinger.class, 2, 64, 3, true, 0X4E0404, 0X2F011B, RenderFlinger.FACTORY),
		new MobInfo("sniperskeleton", EntitySniperSkeleton.class, 3, 128, 3, true, 0XCFCFCF, 0X44AE1F, RenderSniperSkeleton.FACTORY),
		new MobInfo("rocketcreeper", EntityRocketCreeper.class, 4, 64, 3, true, 0X00AA00, 0XFFFFFF, RenderRocketCreeper.FACTORY),
		new MobInfo("strayguardian", EntityStrayGuardian.class, 5, 64, 3, true, 0X486674, 0X9036EC, RenderStrayGuardian.FACTORY),
		new MobInfo("hungryslime", EntityHungrySlime.class, 6, 64, 3, true, 0X9ced44, 0X987945, RenderHungrySlime.FACTORY),
		new MobInfo("shadow", EntityShadow.class, 7, 64, 3, true, 0, 0X111111, RenderShadow.FACTORY),
		new MobInfo("madscientist", EntityMadScientist.class, 9, 64, 3, true, 0Xe7e7e7, 0Xbd8b72, RenderMadScientist.FACTORY),
		new MobInfo("giantsquid", EntityGiantSquid.class, 10, 64, 3, true, 0X2222CC, 0X111188, RenderGiantSquid.FACTORY),
		new MobInfo("apexslime", EntityApexSlime.class, 13, 64, 3, true, 0X9aaf5b, 0Xe52617, RenderApexSlime.FACTORY),
		new MobInfo("apexfeesh", EntityApexFeesh.class, 16, 64, 3, true, 0X909090, 0x211001, RenderApexFeesh.FACTORY),
		new MobInfo("inferno", EntityInferno.class, 18, 64, 3, true, 0xda3a02, 0x110500, RenderInferno.FACTORY),
		new MobInfo("icezombie", EntityIceZombie.class, 20, 64, 3, true, 0x1c2f3c, 0x77a9ff, RenderIceZombie.FACTORY),
		new MobInfo("tarzombie", EntityTarZombie.class, 21, 64, 3, true, 0x580200, 0x974700, RenderTarZombie.FACTORY),
		new MobInfo("lightningzombie", EntityLightningZombie.class, 22, 64, 3, true, 0x999900, 0x999999, RenderLightningZombie.FACTORY)
		
	};
	
	public static final MiscEntityInfo[] MISCLIST = {
		new MiscEntityInfo("sniperarrow", EntitySniperArrow.class, 8, 64, 1, true, RenderSniperArrow.FACTORY),
		new MiscEntityInfo("ampotion", EntityAMPotion.class, 11, 64, 3, true, RenderAMPotion.FACTORY),
		new MiscEntityInfo("splashpotionpossession", EntitySplashPotionPossession.class, 12, 64, 3, true, RenderAMPotion.FACTORY),
		new MiscEntityInfo("apexslimeminion", EntityApexSlimeMinion.class, 14, 64, 3, true, RenderApexSlime.FACTORY),
		new MiscEntityInfo("acidcloud", EntityAcidCloud.class, 15, 64, 3, false, RenderAcidCloud.FACTORY),
		new MiscEntityInfo("apexfeeshminion", EntityApexFeeshMinion.class, 17, 64, 3, true, RenderApexFeeshMinion.FACTORY),
		new MiscEntityInfo("tarball", EntityTarball.class, 19, 64, 3, true, RenderTarball.FACTORY)
		
	};
	
	public static final EntitySpawnInfo[] SPAWNLIST = {
		new EntitySpawnInfo(EntityHungrySlime.class, 10, 1, 1, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntitySlime.class)),
		new EntitySpawnInfo(EntityShadow.class, 10, 1, 2, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntityZombie.class)),
		new EntitySpawnInfo(EntityWanderingFeesh.class, 20, 3, 7, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntityZombie.class)),
		new EntitySpawnInfo(EntityFlinger.class, 3, 1, 1, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntityWitch.class)),
		new EntitySpawnInfo(EntityRocketCreeper.class, 20, 1, 1, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntityCreeper.class)),
		new EntitySpawnInfo(EntitySniperSkeleton.class, 5, 1, 1, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntitySkeleton.class)),
		new EntitySpawnInfo(EntityMadScientist.class, 3, 1, 1, EnumCreatureType.MONSTER, EntitySpawnInfo.getSpawnableBiomesFor(EnumCreatureType.MONSTER, EntityWitch.class))
	};
	
	public static class MobInfo {
		
		public String name;
	
		@SuppressWarnings("rawtypes")
		public Class entityClass;
		public int id;
		public int trackingRange;
		public int updateFrequency;
		public boolean sendsVelocityUpdates;
		public int eggP;
		public int eggS;
		@SuppressWarnings("rawtypes")
		public IRenderFactory factory;
		
		
		@SuppressWarnings("rawtypes")
		public MobInfo(String name, Class entityClass, int id, int trackingRange, int updateFrequency, 
				boolean sendsVelocityUpdates, int eggP, int eggS, IRenderFactory factory) {
			this.name = name;
			this.entityClass = entityClass;
			this.id = id;
			this.trackingRange = trackingRange;
			this.updateFrequency = updateFrequency;
			this.sendsVelocityUpdates = sendsVelocityUpdates;
			this.eggP = eggP;
			this.eggS = eggS;
			this.factory = factory;
		}
	}
	
	
	public static class MiscEntityInfo {
		
		public String name;
		
		@SuppressWarnings("rawtypes")
		public Class entityClass;
		public int id;
		public int trackingRange;
		public int updateFrequency;
		public boolean sendsVelocityUpdates;
		@SuppressWarnings("rawtypes")
		public IRenderFactory factory;
		
		
		@SuppressWarnings("rawtypes")
		public MiscEntityInfo(String name, Class entityClass, int id, int trackingRange, int updateFrequency, 
				boolean sendsVelocityUpdates, IRenderFactory factory) {
			this.name = name;
			this.entityClass = entityClass;
			this.id = id;
			this.trackingRange = trackingRange;
			this.updateFrequency = updateFrequency;
			this.sendsVelocityUpdates = sendsVelocityUpdates;
			this.factory = factory;
		}
		
	}
	
	public static class EntitySpawnInfo {
		
		@SuppressWarnings("rawtypes")
		public Class entityClass;
		public int prob;
		public int min;
		public int max;
		public EnumCreatureType type;
		public Biome[] biomes;
		
		@SuppressWarnings("rawtypes")
		public EntitySpawnInfo(Class entityClass, int prob, int min, int max, EnumCreatureType type, Biome[] biomes) {
			this.entityClass = entityClass;
			this.prob = prob;
			this.min = min;
			this.max = max;
			this.type = type;
			this.biomes = biomes;
		}
		
		@SuppressWarnings("rawtypes")
		private static Biome[] getSpawnableBiomesFor(EnumCreatureType type, Class entityClass) {
			
			ArrayList<Biome> biomes = new ArrayList<Biome>();
			
			for (final Biome biome : ForgeRegistries.BIOMES) {
				for(SpawnListEntry entry : biome.getSpawnableList(type)) {
					if(entry.entityClass == entityClass) {
						biomes.add(biome);
					}
				}
			}
			return biomes.toArray(new Biome[] {});
		}
		
	}
	
}
