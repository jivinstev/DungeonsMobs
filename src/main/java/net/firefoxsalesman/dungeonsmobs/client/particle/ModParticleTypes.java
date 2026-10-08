package net.firefoxsalesman.dungeonsmobs.client.particle;

import net.minecraft.core.registries.BuiltInRegistries;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModParticleTypes {
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister
			.create(BuiltInRegistries.PARTICLE_TYPE, DungeonsMobs.MOD_ID);

	public static final Supplier<SimpleParticleType> REDSTONE_SPARK = registerParticle("redstone_spark");
	public static final Supplier<SimpleParticleType> DUST = registerParticle("dust");
	public static final Supplier<SimpleParticleType> WIND = registerParticle("wind");
	public static final Supplier<SimpleParticleType> NECROMANCY = registerParticle("necromancy");
	public static final Supplier<SimpleParticleType> CORRUPTED_DUST = registerParticle("corrupted_dust");
	public static final Supplier<SimpleParticleType> CORRUPTED_MAGIC = registerParticle("corrupted_magic");
	public static final Supplier<SimpleParticleType> ELECTRIC_SHOCK = registerParticle("electric_shock");

	private static Supplier<SimpleParticleType> registerParticle(String name) {
		return PARTICLES.register(name, () -> new SimpleParticleType(true));
	}

	public static void register(IEventBus eventBus) {
		PARTICLES.register(eventBus);
	}
}
