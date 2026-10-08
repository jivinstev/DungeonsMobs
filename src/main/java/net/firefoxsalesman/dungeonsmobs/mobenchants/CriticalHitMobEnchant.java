package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchi.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class CriticalHitMobEnchant extends MobEnchant {
	private static final float CRIT_CHANCE = 0.05f;

	public CriticalHitMobEnchant(Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity defender, LivingEntity attacker, LivingDamageEvent.Pre event) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.CRITICAL_HIT.getKey(),
				(level) -> {
					if (attacker.getRandom().nextFloat() <= CRIT_CHANCE * (level + 1)) {
						event.setNewDamage(event.getNewDamage() * 3);
					}
				});
	}
}
