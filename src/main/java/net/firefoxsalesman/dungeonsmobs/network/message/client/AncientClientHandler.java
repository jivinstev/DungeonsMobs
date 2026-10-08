package net.firefoxsalesman.dungeonsmobs.network.message.client;

import net.firefoxsalesman.dungeonsmobs.capabilities.ancient.Ancient;
import net.firefoxsalesman.dungeonsmobs.capabilities.ancient.AncientHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

/** Client-only; only reached from clientbound packet handling. */
public class AncientClientHandler {
	public static void handle(int entityId, boolean ancient) {
		Entity entity = Minecraft.getInstance().player.level().getEntity(entityId);
		if (entity instanceof LivingEntity) {
			Ancient cap = AncientHelper.getAncientCapability(entity);
			cap.setAncient(ancient);
			entity.refreshDimensions();
		}
	}
}
