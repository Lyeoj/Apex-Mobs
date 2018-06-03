package lyeoj.apexmobs.init;

import lyeoj.apexmobs.main.ApexMobs;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class AMSoundEvents {
	
	public static final SoundEvent SNIPER_SHOOT = new SoundEvent(new ResourceLocation(ApexMobs.MODID, "sniperskeleton.shoot")).setRegistryName("snipershoot");
	public static final SoundEvent SNIPER_PREPARE = new SoundEvent(new ResourceLocation(ApexMobs.MODID, "sniperskeleton.prepare")).setRegistryName("sniperprepare");
	public static final SoundEvent ZAPCHARGE = new SoundEvent(new ResourceLocation(ApexMobs.MODID, "charging")).setRegistryName("zapcharge");
	public static final SoundEvent ZAPPED = new SoundEvent(new ResourceLocation(ApexMobs.MODID, "zapped")).setRegistryName("zapped");

}
