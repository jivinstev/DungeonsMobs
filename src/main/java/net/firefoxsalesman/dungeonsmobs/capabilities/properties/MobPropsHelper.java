package net.firefoxsalesman.dungeonsmobs.capabilities.properties;

import java.util.Optional;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import static net.firefoxsalesman.dungeonsmobs.capabilities.ModCapabilities.MOB_PROPS_CAPABILITY;

public class MobPropsHelper {

	public static Optional<MobProps> getMobPropsCapabilityLazy(Entity entity) {
		if (MOB_PROPS_CAPABILITY == null || !(entity instanceof LivingEntity)) {
			return Optional.empty();
		}
		return Optional.of(entity.getData(MOB_PROPS_CAPABILITY));
	}

	public static MobProps getMobPropsCapability(Entity entity) {
		Optional<MobProps> mobProps = getMobPropsCapabilityLazy(entity);
		if (mobProps.isPresent()) {
			return mobProps.orElseThrow(() -> new IllegalStateException(
					"Couldn't get the MobProps capability from the Entity!"));
		}
		return null;
	}
}
