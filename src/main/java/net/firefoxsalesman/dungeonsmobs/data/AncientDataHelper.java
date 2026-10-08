package net.firefoxsalesman.dungeonsmobs.data;

import net.minecraft.core.registries.BuiltInRegistries;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import baguchi.enchantwithmob.capability.MobEnchantCapability;
import baguchi.enchantwithmob.mobenchant.MobEnchant;
import baguchi.enchantwithmob.registry.MobEnchants;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonslibs.attribute.AttributeRegistry;
import net.firefoxsalesman.dungeonslibs.data.util.MergeableCodecDataManager;
import net.firefoxsalesman.dungeonslibs.summon.SummonHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = DungeonsMobs.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class AncientDataHelper {

	private static final MergeableCodecDataManager<MobAncientData, MobAncientData> MOB_ANCIENT_DATA = new MergeableCodecDataManager<>(
			"ancient/mob_ancient_data", MobAncientData.CODEC, AncientDataHelper::mobMerger);
	private static final MergeableCodecDataManager<MobEnchantmentAncientData, MobEnchantmentAncientData> MOB_ENCHANTMENT_ANCIENT_DATA = new MergeableCodecDataManager<>(
			"ancient/mob_enchantment_ancient_data", MobEnchantmentAncientData.CODEC,
			AncientDataHelper::mobEnchantmentMerger);

	private static MobAncientData mobMerger(List<MobAncientData> raws) {
		List<String> adjectives = new ObjectArrayList<>();
		List<String> nouns = new ObjectArrayList<>();
		List<ResourceLocation> minions = new ObjectArrayList<>();
		List<UniqueAncientData> uniques = new ObjectArrayList<>();
		raws.forEach(raw -> {
			adjectives.addAll(raw.getAdjectives());
			nouns.addAll(raw.getNouns());
			minions.addAll(raw.getMinions());
			uniques.addAll(raw.getUniques());
		});
		return new MobAncientData(adjectives, nouns, minions, uniques);
	}

	private static MobEnchantmentAncientData mobEnchantmentMerger(List<MobEnchantmentAncientData> raws) {
		List<String> adjectives = new ObjectArrayList<>();
		List<String> nouns = new ObjectArrayList<>();
		raws.forEach(raw -> {
			adjectives.addAll(raw.getAdjectives());
			nouns.addAll(raw.getNouns());
		});
		return new MobEnchantmentAncientData(adjectives, nouns);
	}

	private static MobAncientData getMobAncientData(ResourceLocation mobResourceLocation) {

		return MOB_ANCIENT_DATA.getData().getOrDefault(mobResourceLocation, MobAncientData.DEFAULT);
	}

	private static MobEnchantmentAncientData getMobEnchantmentAncientData(
			ResourceLocation mobEnchantmentResourceLocation) {
		return MOB_ENCHANTMENT_ANCIENT_DATA.getData().getOrDefault(mobEnchantmentResourceLocation,
				MobEnchantmentAncientData.DEFAULT);
	}

	private static void addEnchant(LivingEntity entity, ResourceLocation enchant, boolean ancient) {
		MobEnchantCapability enchantCap = NewMobEnchantUtils.getEnchantCapability(entity);
		Holder<MobEnchant> enchantment = MobEnchants.getRegistry().getHolder(enchant).orElseThrow();
		enchantCap.addMobEnchant(entity, enchantment, enchantment.value().getMaxLevel());

	}

	private static <T> T getRandomElement(RandomSource random, Collection<T> collection) {
		return (T) collection.toArray()[random.nextInt(0, collection.size())];
	}

	private static void ancientHelper(LivingEntity entity, MobAncientData mobAncientData,
			List<ResourceLocation> mobEnchants, int minionCount,
			EntityType<?> minion, List<ResourceLocation> minionEnchants) {
		RandomSource random = entity.getRandom();
		mobEnchants.forEach(enchant -> addEnchant(entity, enchant, true));
		AttributeInstance attributeInstance = entity.getAttribute(AttributeRegistry.SUMMON_CAP);
		if (attributeInstance != null) {
			attributeInstance.addTransientModifier(new AttributeModifier(
					ResourceLocation.fromNamespaceAndPath("dungeonsmobs", "ancientmob"), minionCount,
					AttributeModifier.Operation.ADD_VALUE));
		}
		for (int i = 0; i < minionCount; i++) {
			BlockPos pos = entity.blockPosition().offset(random.nextInt(5), 0, random.nextInt(5));
			Entity summon = SummonHelper.summonEntity(entity, pos, minion);
			if (summon != null && summon instanceof LivingEntity) {
				minionEnchants.forEach(enchant -> addEnchant((LivingEntity) summon, enchant, false));
				if (summon instanceof Mob mob) {
					mob.finalizeSpawn((ServerLevel) mob.level(),
							mob.level().getCurrentDifficultyAt(pos),
							MobSpawnType.MOB_SUMMONED, null);
				}
			}
		}

	}

	private static Optional<String> doNonUniques(LivingEntity entity, MobAncientData mobAncientData) {
		RandomSource random = entity.getRandom();
		Collection<ResourceLocation> enchants = MobEnchants.getRegistry().keySet();
		List<ResourceLocation> mobEnchants = new ArrayList<>();
		for (int i = 0; i < 3; i++)
			mobEnchants.add(getRandomElement(random, enchants));
		ancientHelper(entity, mobAncientData, mobEnchants, 7, BuiltInRegistries.ENTITY_TYPE
				.get(getRandomElement(random, mobAncientData.getMinions())),
				List.of(getRandomElement(random, enchants)));
		return Optional.empty();
	}

	private static Optional<String> doUniques(LivingEntity entity, MobAncientData mobAncientData) {
		List<UniqueAncientData> uniques = mobAncientData.getUniques();
		if (uniques.size() > 0) {
			RandomSource random = entity.getRandom();
			UniqueAncientData unique = getRandomElement(random, uniques);
			ancientHelper(entity, mobAncientData, unique.getMobEnchantments(), unique.getMinionCount(),
					BuiltInRegistries.ENTITY_TYPE.get(unique.getMinion()),
					unique.getMinionMobEnchantments());
			return Optional.of(unique.getName());

		} else
			return doNonUniques(entity, mobAncientData);
	}

	public static String getAncientName(LivingEntity entity, boolean unique) {
		Set<String> adjectives = new HashSet<>();
		Set<String> nouns = new HashSet<>();
		MobEnchantCapability enchantCap = NewMobEnchantUtils.getEnchantCapability(entity);
		enchantCap.getMobEnchants().forEach(mobEnchantment -> {
			MobEnchantmentAncientData mobEnchantmentAncientData = getMobEnchantmentAncientData(
					MobEnchants.getRegistry().getKey(mobEnchantment.getMobEnchant().value()));
			adjectives.addAll(mobEnchantmentAncientData.getAdjectives());
			nouns.addAll(mobEnchantmentAncientData.getNouns());
		});
		MobAncientData mobAncientData = getMobAncientData(
				BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()));
		Optional<String> uniqueName = unique ? doUniques(entity, mobAncientData)
				: doNonUniques(entity, mobAncientData);
		adjectives.addAll(mobAncientData.getAdjectives());
		nouns.addAll(mobAncientData.getNouns());
		return uniqueName.isPresent() ? uniqueName.get()
				: new ObjectArrayList<>(adjectives).get(entity.getRandom().nextInt(adjectives.size()))
						+ " " + new ObjectArrayList<>(nouns)
								.get(entity.getRandom().nextInt(nouns.size()));
	}

	@SubscribeEvent
	public static void onAddReloadListeners(AddReloadListenerEvent event) {
		event.addListener(MOB_ANCIENT_DATA);
		event.addListener(MOB_ENCHANTMENT_ANCIENT_DATA);
	}
}
