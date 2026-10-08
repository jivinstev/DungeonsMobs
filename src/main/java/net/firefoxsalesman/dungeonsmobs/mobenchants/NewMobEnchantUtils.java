package net.firefoxsalesman.dungeonsmobs.mobenchants;

import java.util.List;
import java.util.function.Consumer;
import baguchi.enchantwithmob.api.IEnchantCap;
import baguchi.enchantwithmob.capability.MobEnchantCapability;
import baguchi.enchantwithmob.mobenchant.MobEnchant;
import baguchi.enchantwithmob.utils.MobEnchantUtils;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class NewMobEnchantUtils {
	public static MobEnchantCapability getEnchantCapability(Entity entity) {
		return entity instanceof IEnchantCap enchantedEntity
				? enchantedEntity.getEnchantCap()
				: new MobEnchantCapability();
	}

	public static void executeIfPresentWithLevel(LivingEntity entity, ResourceKey<MobEnchant> mobEnchantment,
			Consumer<Integer> consumer) {
		if (entity != null && entity instanceof IEnchantCap cap) {
			int level = MobEnchantUtils.getMobEnchantLevelFromHandler(cap.getEnchantCap().getMobEnchants(),
					mobEnchantment);
			if (level > 0)
				consumer.accept(level);
		}
	}

	// Here rather than in EntityEvents: scanning an @EventBusSubscriber resolves its methods'
	// parameter types, and this one names Enchant With Mob, which the mod declares optional.
	public static void setupEnchants(LivingEntity entity, String name,
			List<Holder<MobEnchant>> enchants, MobEnchantCapability cap) {
		entity.setCustomName(Component.literal(name));
		enchants.forEach(enchant -> {
			cap.addMobEnchant(entity, enchant, enchant.value().getMaxLevel());
		});
	}
}
