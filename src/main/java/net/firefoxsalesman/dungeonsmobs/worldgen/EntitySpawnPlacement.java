package net.firefoxsalesman.dungeonsmobs.worldgen;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.creepers.IcyCreeperEntity;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.FungusThrowerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.piglin.ZombifiedFungusThrowerEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.FrozenZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.JungleZombieEntity;
import net.firefoxsalesman.dungeonsmobs.entity.undead.MossySkeletonEntity;
import net.firefoxsalesman.dungeonsmobs.interfaces.IAquaticMob;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.levelgen.Heightmap;

public class EntitySpawnPlacement {
	public static SpawnPlacementType ON_GROUND_ALLOW_LEAVES;

	public static void createPlacementTypes() {
		ON_GROUND_ALLOW_LEAVES = (levelReader, blockPos, entityType) -> {
					BlockState blockstate = levelReader.getBlockState(blockPos);
					FluidState fluidstate = levelReader.getFluidState(blockPos);
					BlockPos above = blockPos.above();
					BlockPos below = blockPos.below();
					BlockState stateBelow = levelReader.getBlockState(below);
					if (!stateBelow.isValidSpawn(levelReader, below, entityType)
							&& !(stateBelow.is(BlockTags.LEAVES))) {
						return false;
					} else {
						return NaturalSpawner.isValidEmptySpawnBlock(levelReader, blockPos,
								blockstate, fluidstate, entityType)
								&& NaturalSpawner.isValidEmptySpawnBlock(levelReader,
										above, levelReader.getBlockState(above),
										levelReader.getFluidState(above),
										entityType);
					}
				};
	}

	/**
	 * Local shim: vanilla SpawnPlacements.register is private now, so calls below
	 * are forwarded to NeoForge's RegisterSpawnPlacementsEvent.
	 */
	private static final class SpawnPlacements {
		private static RegisterSpawnPlacementsEvent event;

		private static <T extends Mob> void register(EntityType<T> type, SpawnPlacementType placement,
				Heightmap.Types heightmap, net.minecraft.world.entity.SpawnPlacements.SpawnPredicate<T> predicate) {
			event.register(type, placement, heightmap, predicate,
					RegisterSpawnPlacementsEvent.Operation.REPLACE);
		}
	}

	public static void initSpawnPlacements(RegisterSpawnPlacementsEvent registerEvent) {
		createPlacementTypes();
		SpawnPlacements.event = registerEvent;
		SpawnPlacements.register(ModEntities.WRAITH.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::goetyFriendlySpawnRule);
		SpawnPlacements.register(ModEntities.JUNGLE_ZOMBIE.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				JungleZombieEntity::canJungleZombieSpawn);
		SpawnPlacements.register(ModEntities.FROZEN_ZOMBIE.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FrozenZombieEntity::canFrozenZombieSpawn);
		SpawnPlacements.register(ModEntities.MOSSY_SKELETON.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				MossySkeletonEntity::canMossySkeletonSpawn);
		SpawnPlacements.register(ModEntities.ICY_CREEPER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				IcyCreeperEntity::canIcyCreeperSpawn);

		SpawnPlacements.register(ModEntities.SKELETON_VANGUARD.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);

		SpawnPlacements.register(ModEntities.NECROMANCER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::goetyFriendlySpawnRule);

		// Illager
		SpawnPlacements.register(ModEntities.ROYAL_GUARD.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canIllagerSpawn);
		SpawnPlacements.register(ModEntities.MOUNTAINEER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canIllagerSpawn);
		SpawnPlacements.register(ModEntities.GEOMANCER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canIllagerSpawn);
		SpawnPlacements.register(ModEntities.VINDICATOR_CHEF.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canIllagerSpawn);
		SpawnPlacements.register(ModEntities.ICEOLOGER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canIllagerSpawn);
		SpawnPlacements.register(ModEntities.SQUALL_GOLEM.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canRaiderSpawn);
		SpawnPlacements.register(ModEntities.WINDCALLER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::canIllagerSpawn);

		SpawnPlacements.register(ModEntities.REDSTONE_GOLEM.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.REDSTONE_CUBE.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.CONJURED_SLIME.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Mob::checkMobSpawnRules);

		// Jungle
		SpawnPlacements.register(ModEntities.WHISPERER.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				EntitySpawnPlacement::canJungleMobSpawn);
		SpawnPlacements.register(ModEntities.LEAPLEAF.get(),
				ON_GROUND_ALLOW_LEAVES,
				Heightmap.Types.MOTION_BLOCKING,
				EntitySpawnPlacement::canJungleMobSpawn);

		// Piglin
		SpawnPlacements.register(ModEntities.FUNGUS_THROWER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				FungusThrowerEntity::checkFungusThrowerSpawnRules);
		SpawnPlacements.register(ModEntities.ZOMBIFIED_FUNGUS_THROWER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				ZombifiedFungusThrowerEntity::checkZombifiedFungusThrowerSpawnRules);
		// Blaze
		SpawnPlacements.register(ModEntities.WILDFIRE.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkAnyLightMonsterSpawnRules);

		// Ocean
		SpawnPlacements.register(ModEntities.WAVEWHISPERER.get(),
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::checkAquaticMobSpawnRules);
		SpawnPlacements.register(ModEntities.DROWNED_NECROMANCER.get(),
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::checkAquaticMobSpawnRules);
		SpawnPlacements.register(ModEntities.SUNKEN_SKELETON.get(),
				SpawnPlacementTypes.IN_WATER,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				EntitySpawnPlacement::checkAquaticMobSpawnRules);

		// Enderlings
		SpawnPlacements.register(ModEntities.ENDERSENT_EYE_HOLDER.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.BLASTLING.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.WATCHLING.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
		SpawnPlacements.register(ModEntities.SNARELING.get(),
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
				Monster::checkMonsterSpawnRules);
	}

	private static boolean goetyFriendlySpawnRule(EntityType<? extends Monster> type, ServerLevelAccessor world,
			MobSpawnType spawnReason,
			BlockPos pos, RandomSource random) {
		return !ModHelper.hasGoety()
				&& Monster.checkMonsterSpawnRules(type, world, spawnReason, pos, random);
	}

	public static boolean checkAquaticMobSpawnRules(EntityType<? extends Mob> type,
			ServerLevelAccessor pServerLevel, MobSpawnType pMobSpawnType, BlockPos pPos,
			RandomSource pRandom) {
		if (!pServerLevel.getFluidState(pPos.below()).is(FluidTags.WATER)) {
			return false;
		} else {
			Holder<Biome> holder = pServerLevel.getBiome(pPos);
			boolean flag = pServerLevel.getDifficulty() != Difficulty.PEACEFUL
					&& Monster.isDarkEnoughToSpawn(pServerLevel, pPos, pRandom)
					&& (pMobSpawnType == MobSpawnType.SPAWNER
							|| pServerLevel.getFluidState(pPos).is(FluidTags.WATER));
			if (holder.is(BiomeTags.MORE_FREQUENT_DROWNED_SPAWNS)) {
				return pRandom.nextInt(15) == 0 && flag;
			} else {
				return pRandom.nextInt(40) == 0 && IAquaticMob.isDeepEnoughToSpawn(pServerLevel, pPos)
						&& flag;
			}
		}
	}

	public static boolean canSeeSkyLight(ServerLevelAccessor world, BlockPos blockPos) {
		return world.getBrightness(LightLayer.SKY, blockPos) > 4;
	}

	public static boolean canRaiderSpawn(EntityType<? extends Raider> entityType, ServerLevelAccessor world,
			MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return Monster.checkMonsterSpawnRules(entityType, world, spawnReason, blockPos, rand);
	}

	public static boolean canJungleMobSpawn(EntityType<? extends Monster> entityType, ServerLevelAccessor world,
			MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return Monster.checkMonsterSpawnRules(entityType, world, spawnReason, blockPos, rand)
				&& world.getSeaLevel() <= blockPos.getY();
	}

	public static boolean canIllagerSpawn(EntityType<? extends AbstractIllager> entityType,
			ServerLevelAccessor world, MobSpawnType spawnReason, BlockPos blockPos, RandomSource rand) {
		return Monster.checkMonsterSpawnRules(entityType, world, spawnReason, blockPos, rand);
	}
}
