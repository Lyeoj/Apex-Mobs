package lyeoj.apexmobs.init;

import net.minecraft.util.DamageSource;

public class AMDamageSources {
	
	public static final DamageSource POSSESSED = new DamageSource("possessed").setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource ELECTRIC = new DamageSource("electric").setDamageBypassesArmor();
	public static final DamageSource ACID = new DamageSource("acid").setDamageBypassesArmor();

}
