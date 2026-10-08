package net.firefoxsalesman.dungeonsmobs.mobenchants;

import baguchi.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RampagingMobEnchant extends MobEnchant {

	public RampagingMobEnchant(MobEnchant.Properties properties) {
		super(properties);
	}

	public static void doEffect(LivingEntity attacker) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.RAMPAGING.getKey(),
				level -> attacker.addEffect(
						new MobEffectInstance(MobEffects.DAMAGE_BOOST, 100 * level, 2)));
	}
}
