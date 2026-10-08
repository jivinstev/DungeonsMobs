package net.firefoxsalesman.dungeonsmobs.mod;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.worldgen.DungeonsMobsStructureModifiers;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.neoforged.neoforge.common.world.StructureModifier;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import java.util.function.Supplier;

import java.util.List;
import java.util.function.Function;

public class ModStructureModifiers {
    public static final Codec<HolderSet<Structure>> LIST_CODEC = RegistryCodecs.homogeneousList(Registries.STRUCTURE, Structure.DIRECT_CODEC);

	private static final DeferredRegister<MapCodec<? extends StructureModifier>> STRUCTURE_MODIFIER_SERIALIZERS = DeferredRegister
			.create(NeoForgeRegistries.Keys.STRUCTURE_MODIFIER_SERIALIZERS, DungeonsMobs.MOD_ID);

	/**
	 * Stock structure modifier for adding mob spawns to structures.
	 */
	public static final Supplier<MapCodec<DungeonsMobsStructureModifiers.AddSpawnsStructureModifier>> ADD_SPAWNS_STRUCTURE_MODIFIER_TYPE = STRUCTURE_MODIFIER_SERIALIZERS
			.register("add_spawns", () -> RecordCodecBuilder.mapCodec(builder -> builder.group(
					LIST_CODEC.fieldOf("structures").forGetter(
							DungeonsMobsStructureModifiers.AddSpawnsStructureModifier::structures),
					// Allow either a list or single spawner, attempting to decode the list format
					// first.
					// Uses the better EitherCodec that logs both errors if both formats fail to
					// parse.
					Codec.either(MobSpawnSettings.SpawnerData.CODEC.listOf(),
							MobSpawnSettings.SpawnerData.CODEC).xmap(
									either -> either.map(Function.identity(),
											List::of), // convert
													// list/singleton
													// to list when
													// decoding
									list -> list.size() == 1
											? Either.right(list.get(0))
											: Either.left(list) // convert
														// list
														// to
														// singleton/list
														// when
														// encoding
					).fieldOf("spawners").forGetter(
							DungeonsMobsStructureModifiers.AddSpawnsStructureModifier::spawners))
					.apply(builder, DungeonsMobsStructureModifiers.AddSpawnsStructureModifier::new)));

	/**
	 * Stock structure modifier for removing mob spawns from structures.
	 */
	public static final Supplier<MapCodec<DungeonsMobsStructureModifiers.RemoveSpawnsStructureModifier>> REMOVE_SPAWNS_STRUCTURE_MODIFIER_TYPE = STRUCTURE_MODIFIER_SERIALIZERS
			.register("remove_spawns", () -> RecordCodecBuilder.mapCodec(builder -> builder.group(
					LIST_CODEC.fieldOf("structures").forGetter(
							DungeonsMobsStructureModifiers.RemoveSpawnsStructureModifier::structures),
					RegistryCodecs.homogeneousList(Registries.ENTITY_TYPE)
							.fieldOf("entity_types")
							.forGetter(DungeonsMobsStructureModifiers.RemoveSpawnsStructureModifier::entityTypes))
					.apply(builder, DungeonsMobsStructureModifiers.RemoveSpawnsStructureModifier::new)));

	public static void register(IEventBus eventBus) {
		STRUCTURE_MODIFIER_SERIALIZERS.register(eventBus);
	}
}
