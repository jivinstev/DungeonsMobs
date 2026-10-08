package net.firefoxsalesman.dungeonsmobs.mobenchants;

import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

public class FrenziedMobEnchant {

	public static void doEffect(LivingEntity defender, Entity entity, float amount, LivingDamageEvent.Pre event) {
		if (entity instanceof LivingEntity attacker) {
			NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.FRENZIED.getKey(),
					(level) -> {
						if (attacker.getHealth() <= attacker.getMaxHealth() / 2)
							event.setNewDamage(amount + (amount * .1F * level));
					});
		}
	}
}
