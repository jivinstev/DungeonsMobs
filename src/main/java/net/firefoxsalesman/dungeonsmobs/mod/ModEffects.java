package net.firefoxsalesman.dungeonsmobs.mod;

import net.minecraft.core.registries.Registries;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.effects.WarpedEffect;
import net.firefoxsalesman.dungeonsmobs.entity.projectiles.BlueNethershroomEntity;
import net.firefoxsalesman.dungeonsmobs.effects.EnsnaredEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;
import net.neoforged.neoforge.registries.DeferredHolder;

public class ModEffects {

	public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT,
			DungeonsMobs.MOD_ID);

	public static final DeferredHolder<MobEffect, MobEffect> ENSNARED = EFFECTS.register("ensnared",
			() -> new EnsnaredEffect(MobEffectCategory.HARMFUL, 0xdbe64e).addAttributeModifier(
					Attributes.MOVEMENT_SPEED,
					ResourceLocation.fromNamespaceAndPath(DungeonsMobs.MOD_ID, "ensnared_movement_speed"), -5.0D,
					AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

	public static final Supplier<MobEffect> WARPED = EFFECTS.register("warped",
			() -> new WarpedEffect(MobEffectCategory.HARMFUL,
					BlueNethershroomEntity.LIGHT_BLUE_HEX_COLOR_CODE));

	public static void register(IEventBus eventBus) {
		EFFECTS.register(eventBus);
	}
}
