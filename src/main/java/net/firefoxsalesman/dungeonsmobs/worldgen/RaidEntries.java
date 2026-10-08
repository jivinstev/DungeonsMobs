package net.firefoxsalesman.dungeonsmobs.worldgen;

import java.util.function.Supplier;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class RaidEntries {

	/*
	 * VINDICATOR(EntityType.VINDICATOR, new int[]{0, 0, 2, 0, 1, 4, 2, 5}),
	 * EVOKER(EntityType.EVOKER, new int[]{0, 0, 0, 0, 0, 1, 1, 2}),
	 * PILLAGER(EntityType.PILLAGER, new int[]{0, 4, 3, 3, 4, 4, 4, 2}),
	 * WITCH(EntityType.WITCH, new int[]{0, 0, 0, 0, 3, 0, 0, 1}),
	 * RAVAGER(EntityType.RAVAGER, new int[]{0, 0, 0, 1, 0, 1, 0, 2});
	 */

	// Raid.RaiderType.create is gone; these are declared in META-INF/enumextensions.json instead
	// WARRIOR
	public static final int[] ARMORED_PILLAGER_WAVES = new int[] { 0, 1, 2, 0, 3, 1, 0, 3 };
	public static final EnumProxy<Raid.RaiderType> ARMORED_PILLAGER = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.ARMORED_PILLAGER.get(), ARMORED_PILLAGER_WAVES);

	public static final int[] ARMORED_VINDICATOR_WAVES = new int[] { 0, 0, 1, 2, 0, 1, 1, 2 };
	public static final EnumProxy<Raid.RaiderType> ARMORED_VINDICATOR = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.ARMORED_VINDICATOR.get(), ARMORED_VINDICATOR_WAVES);

	public static final int[] MOUNTAINEER_WAVES = new int[] { 0, 0, 2, 0, 1, 4, 2, 5 };
	public static final EnumProxy<Raid.RaiderType> MOUNTAINEER = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.MOUNTAINEER.get(), MOUNTAINEER_WAVES);

	public static final int[] ROYAL_GUARD_WAVES = new int[] { 0, 0, 1, 0, 0, 2, 1, 2 };
	public static final EnumProxy<Raid.RaiderType> ROYAL_GUARD = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.ROYAL_GUARD.get(), ROYAL_GUARD_WAVES);

	public static final int[] GEOMANCER_WAVES = new int[] { 0, 0, 0, 0, 0, 1, 1, 2 };
	public static final EnumProxy<Raid.RaiderType> GEOMANCER = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.GEOMANCER.get(), GEOMANCER_WAVES);

	public static final int[] MAGE_WAVES = new int[] { 0, 0, 1, 0, 0, 1, 0, 2 };
	public static final EnumProxy<Raid.RaiderType> MAGE = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.MAGE.get(), MAGE_WAVES);

	public static final int[] ILLUSIONER_WAVES = new int[] { 0, 0, 0, 0, 0, 1, 1, 2 };
	public static final EnumProxy<Raid.RaiderType> ILLUSIONER = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> EntityType.ILLUSIONER, ILLUSIONER_WAVES);

	public static final int[] ICEOLOGER_WAVES = new int[] { 0, 0, 0, 0, 0, 1, 1, 2 };
	public static final EnumProxy<Raid.RaiderType> ICEOLOGER = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.ICEOLOGER.get(), ICEOLOGER_WAVES);

	public static final int[] WINDCALLER_WAVES = new int[] { 0, 0, 0, 0, 0, 1, 1, 2 };
	public static final EnumProxy<Raid.RaiderType> WINDCALLER = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.WINDCALLER.get(), WINDCALLER_WAVES);

	public static final int[] SQUALL_GOLEM_WAVES = new int[] { 0, 0, 0, 1, 0, 1, 0, 2 };
	public static final EnumProxy<Raid.RaiderType> SQUALL_GOLEM = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.SQUALL_GOLEM.get(), SQUALL_GOLEM_WAVES);

	public static final int[] REDSTONE_GOLEM_WAVES = new int[] { 0, 0, 0, 0, 0, 0, 0, 1 };
	public static final EnumProxy<Raid.RaiderType> REDSTONE_GOLEM = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.REDSTONE_GOLEM.get(), REDSTONE_GOLEM_WAVES);

	public static final int[] REDSTONE_MONSTROSITY_WAVES = new int[] { 0, 0, 0, 0, 0, 0, 0, 1 };
	public static final EnumProxy<Raid.RaiderType> REDSTONE_MONSTROSITY = new EnumProxy<>(Raid.RaiderType.class,
			(Supplier<EntityType<? extends Raider>>) () -> ModEntities.REDSTONE_MONSTROSITY.get(), REDSTONE_MONSTROSITY_WAVES);

	public static void initWaveMemberEntries() {
		// The enum entries always exist, so a disabled raider just gets zero spawns in every wave
		if (!DungeonsMobsConfig.COMMON.ENABLE_ARMORED_PILLAGERS_IN_RAIDS.get())
			java.util.Arrays.fill(ARMORED_PILLAGER_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_ARMORED_VINDICATORS_IN_RAIDS.get())
			java.util.Arrays.fill(ARMORED_VINDICATOR_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_MOUNTAINEERS_IN_RAIDS.get())
			java.util.Arrays.fill(MOUNTAINEER_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_ROYAL_GUARDS_IN_RAIDS.get())
			java.util.Arrays.fill(ROYAL_GUARD_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_GEOMANCERS_IN_RAIDS.get())
			java.util.Arrays.fill(GEOMANCER_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_MAGES_IN_RAIDS.get())
			java.util.Arrays.fill(MAGE_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_ILLUSIONERS_IN_RAIDS.get())
			java.util.Arrays.fill(ILLUSIONER_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_ICEOLOGERS_IN_RAIDS.get())
			java.util.Arrays.fill(ICEOLOGER_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_WINDCALLERS_IN_RAIDS.get())
			java.util.Arrays.fill(WINDCALLER_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_SQUALL_GOLEMS_IN_RAIDS.get())
			java.util.Arrays.fill(SQUALL_GOLEM_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_REDSTONE_GOLEMS_IN_RAIDS.get())
			java.util.Arrays.fill(REDSTONE_GOLEM_WAVES, 0);

		if (!DungeonsMobsConfig.COMMON.ENABLE_REDSTONE_MONSTROSITIES_IN_RAIDS.get())
			java.util.Arrays.fill(REDSTONE_MONSTROSITY_WAVES, 0);
	}
}
