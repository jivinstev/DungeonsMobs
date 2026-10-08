package net.firefoxsalesman.dungeonsmobs.mobenchants;

import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanHealPredicate;
import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.PROXY;
import static net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils.executeIfPresentWithLevel;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class MobEnchantEvents {
	@SubscribeEvent
	public static void onLivingDamage(LivingDamageEvent.Pre event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity defender = event.getEntity();
			RushMobEnchant.doEffect(defender);
			HealsAlliesMobEnchant.doEffect(defender, event.getNewDamage());
			Entity attacker;
			if (!event.getSource().is(DamageTypeTags.IS_PROJECTILE))
				attacker = event.getSource().getDirectEntity();
			else
				attacker = event.getSource().getEntity();
			if (attacker instanceof LivingEntity livingAttacker) {

				CommittedMobEnchant.doEffect(defender, livingAttacker, event);
				CriticalHitMobEnchant.doEffect(defender, livingAttacker, event);
				ChainsMobEnchant.doEffect(defender, livingAttacker);
				DoubleDamageMobEnchant.doEffect(defender, livingAttacker, event);
				FrenziedMobEnchant.doEffect(defender, livingAttacker, event.getNewDamage(),
						event);
				WeakeningMobEnchant.doEffect(defender, livingAttacker);
				// radiance
				executeIfPresentWithLevel(livingAttacker, ModMobEnchants.RADIANCE.getKey(),
						(level) -> {
							LivingEntity source = event.getSource()
									.is(DamageTypeTags.IS_PROJECTILE)
											? event.getEntity()
											: livingAttacker;
							applyToNearbyEntities(source, 1.5F,
									getCanHealPredicate(source),
									(LivingEntity nearbyEntity) -> {
										nearbyEntity.heal(level);
										PROXY.spawnParticles(nearbyEntity,
												ParticleTypes.HEART);
									});
						});
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(EntityTickEvent.Pre event) {
	    if (!(event.getEntity() instanceof net.minecraft.world.entity.LivingEntity living)) return;
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity entity = living;
			BurningMobEnchant.doEffect(entity);
			ChillingMobEnchant.doEffect(entity);
			FireTrailMobEnchant.doEffect(entity);
			GravityPulseMobEnchant.doEffect(entity);
			RegenerationMobEnchant.doEffect(entity);
		}
	}

	@SubscribeEvent
	public static void onLivingAttack(LivingIncomingDamageEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity defender = event.getEntity();
			Entity entity = event.getSource().getEntity();
			EchoMobEnchant.doEffect(defender, entity, event.getSource(), event.getAmount());
			ShockwaveMobEnchant.doEffect(defender, entity, event.getAmount());
			ThunderingMobEnchant.doEffect(defender, entity, event.getSource(), event.getAmount());
		}
	}

	@SubscribeEvent
	public static void onLivingDeath(LivingDeathEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			LivingEntity defender = event.getEntity();
			Entity entity = event.getSource().getEntity();
			if (entity instanceof LivingEntity attacker) {
				LeechingMobEnchant.doEffect(attacker, defender);
				RampagingMobEnchant.doEffect(attacker);
			}
		}
	}
}
