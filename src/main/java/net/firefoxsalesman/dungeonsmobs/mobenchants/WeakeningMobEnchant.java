package net.firefoxsalesman.dungeonsmobs.mobenchants;

import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class WeakeningMobEnchant {

	public static void doEffect(LivingEntity defender, LivingEntity attacker) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.WEAKENING.getKey(),
				(level) -> {
					defender.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, level - 1));
				});
	}
}
