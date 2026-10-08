package net.firefoxsalesman.dungeonsmobs.mod;

import baguchi.enchantwithmob.EnchantWithMob;
import baguchi.enchantwithmob.mobenchant.MobEnchant;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mobenchants.BurningMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.ChillingMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.CommittedMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.CriticalHitMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.DoubleDamageMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.FireTrailMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.HealsAlliesMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.RampagingMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.ShockwaveMobEnchant;
import net.firefoxsalesman.dungeonsmobs.mobenchants.ThunderingMobEnchant;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMobEnchants {
	private static final DeferredRegister<MobEnchant> MOB_ENCHANTS_DEFERRED = DeferredRegister
			.create(ResourceLocation.fromNamespaceAndPath(EnchantWithMob.MODID, "mob_enchant"), DungeonsMobs.MOD_ID);
	public static final DeferredHolder<MobEnchant, BurningMobEnchant> BURNING = MOB_ENCHANTS_DEFERRED.register("burning",
			() -> new BurningMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> CHAINS = MOB_ENCHANTS_DEFERRED.register("chains",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, ChillingMobEnchant> CHILLING = MOB_ENCHANTS_DEFERRED.register("chilling",
			() -> new ChillingMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, CommittedMobEnchant> COMMITTED = MOB_ENCHANTS_DEFERRED.register("committed",
			() -> new CommittedMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, CriticalHitMobEnchant> CRITICAL_HIT = MOB_ENCHANTS_DEFERRED.register(
			"critical_hit",
			() -> new CriticalHitMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, DoubleDamageMobEnchant> DOUBLE_DAMAGE = MOB_ENCHANTS_DEFERRED.register(
			"double_damage",
			() -> new DoubleDamageMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.VERY_RARE, 1, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> ECHO = MOB_ENCHANTS_DEFERRED.register("echo",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.VERY_RARE, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> FRENZIED = MOB_ENCHANTS_DEFERRED.register("frenzied",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.VERY_RARE, 3, 1)));
	public static final DeferredHolder<MobEnchant, FireTrailMobEnchant> FIRE_TRAIL = MOB_ENCHANTS_DEFERRED.register(
			"fire_trail",
			() -> new FireTrailMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.VERY_RARE, 1, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> GRAVITY_PULSE = MOB_ENCHANTS_DEFERRED.register(
			"gravity_pulse",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.RARE, 3, 1)));
	public static final DeferredHolder<MobEnchant, HealsAlliesMobEnchant> HEALS_ALLIES = MOB_ENCHANTS_DEFERRED.register(
			"heals_allies",
			() -> new HealsAlliesMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.RARE, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> LEECHING = MOB_ENCHANTS_DEFERRED.register("leeching",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> RADIANCE = MOB_ENCHANTS_DEFERRED.register("radiance",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.RARE, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> REGENERATION = MOB_ENCHANTS_DEFERRED.register(
			"regeneration",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.COMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, RampagingMobEnchant> RAMPAGING = MOB_ENCHANTS_DEFERRED.register("rampaging",
			() -> new RampagingMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.COMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> RUSH = MOB_ENCHANTS_DEFERRED.register("rush",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.COMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, ShockwaveMobEnchant> SHOCKWAVE = MOB_ENCHANTS_DEFERRED.register(
			"shockwave",
			() -> new ShockwaveMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, ThunderingMobEnchant> THUNDERING = MOB_ENCHANTS_DEFERRED.register(
			"thundering",
			() -> new ThunderingMobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.UNCOMMON, 3, 1)));
	public static final DeferredHolder<MobEnchant, MobEnchant> WEAKENING = MOB_ENCHANTS_DEFERRED.register("weakening",
			() -> new MobEnchant(new MobEnchant.Properties(MobEnchant.Rarity.COMMON, 3, 1)));

	public static void register(IEventBus eventBus) {
		MOB_ENCHANTS_DEFERRED.register(eventBus);
	}
}
