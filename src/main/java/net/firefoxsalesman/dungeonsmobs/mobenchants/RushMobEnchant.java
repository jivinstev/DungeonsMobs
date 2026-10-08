package net.firefoxsalesman.dungeonsmobs.mobenchants;

import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RushMobEnchant {

	public static void doEffect(LivingEntity defender) {
		NewMobEnchantUtils.executeIfPresentWithLevel(defender, ModMobEnchants.RUSH.getKey(), (level) -> {
			defender.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 10 + 20 * level, 3,
					false,
					false));
		});
	}
}
