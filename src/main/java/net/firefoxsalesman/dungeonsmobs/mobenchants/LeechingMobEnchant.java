package net.firefoxsalesman.dungeonsmobs.mobenchants;

import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.LivingEntity;

public class LeechingMobEnchant {
	public static void doEffect(LivingEntity attacker, LivingEntity defender) {
		NewMobEnchantUtils.executeIfPresentWithLevel(attacker, ModMobEnchants.LEECHING.getKey(),
				level -> attacker.heal((0.03F + (0.02F * level)) * defender.getMaxHealth()));
	}
}
