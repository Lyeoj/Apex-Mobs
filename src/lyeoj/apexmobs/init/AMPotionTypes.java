package lyeoj.apexmobs.init;

import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class AMPotionTypes extends PotionType {
	
	public static final PotionType TARRED = new PotionType(new PotionEffect[] {new PotionEffect(AMPotion.TARRED, 900)}).setRegistryName("tarred");
	public static final PotionType ZAPPED = new PotionType(new PotionEffect[] {new PotionEffect(AMPotion.ZAPPED, 1200)}).setRegistryName("zapped");
	public static final PotionType FROZEN = new PotionType(new PotionEffect[] {new PotionEffect(AMPotion.FROZEN, 600)}).setRegistryName("frozen");
	public static final PotionType AMHASTE = new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.HASTE, 19200)}).setRegistryName("amhaste");
	public static final PotionType AMHASTE_STRONG = new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.HASTE, 9600, 1)}).setRegistryName("amhastestrong");
	public static final PotionType AMFATIGUE = new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.MINING_FATIGUE, 9600)}).setRegistryName("amfatigue");
	public static final PotionType AMFATIGUE_STRONG = new PotionType(new PotionEffect[] {new PotionEffect(MobEffects.MINING_FATIGUE, 4800, 1)}).setRegistryName("amfatiguestrong");

}
