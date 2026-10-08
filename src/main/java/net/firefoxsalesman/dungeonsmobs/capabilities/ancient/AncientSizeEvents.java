package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class AncientSizeEvents {
	@SubscribeEvent
	public static void onEntityEventSize(EntityEvent.Size event) {
		Entity entity = event.getEntity();

		Ancient cap = AncientHelper.getAncientCapability(entity);
		if (cap.isAncient()) {
			float totalWidth = event.getNewSize().width() * 1.2F;
			float totalHeight = event.getNewSize().height() * 1.2F;
			float totalEyeHeight = event.getNewSize().eyeHeight() * 1.2F;
			event.setNewSize(EntityDimensions.fixed(totalWidth, totalHeight).withEyeHeight(totalEyeHeight));
		}
	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onRenderLivingEventPre(
			RenderLivingEvent.Pre<LivingEntity, EntityModel<LivingEntity>> event) {
		final LivingEntity entity = event.getEntity();
		Ancient cap = AncientHelper.getAncientCapability(entity);
		if (cap.isAncient()) {
			event.getPoseStack().pushPose();
			event.getPoseStack().scale(1.1F, 1.1F, 1.1F);

		}

	}

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void onRenderLivingEventPost(
			RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>> event) {
		final LivingEntity entity = event.getEntity();
		Ancient cap = AncientHelper.getAncientCapability(entity);
		if (cap.isAncient()) {
			event.getPoseStack().popPose();
		}

	}
}
