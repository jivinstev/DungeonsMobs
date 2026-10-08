package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.entity.jungle.LeapleafEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;

public class LeapleafModel extends GeoModel<LeapleafEntity> {

	private float groundSpeed;

	@Override
	public ResourceLocation getAnimationResource(LeapleafEntity entity) {
		return modLoc("animations/leapleaf.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(LeapleafEntity entity) {
		return modLoc("geo/leapleaf.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LeapleafEntity entity) {
		return modLoc("textures/entity/jungle/leapleaf.png");
	}

	@Override
	public void setCustomAnimations(LeapleafEntity animatable, long instanceId, AnimationState<LeapleafEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);
		LivingEntity livingEntity = (LivingEntity) animatable;
		Vec3 velocity = livingEntity.getDeltaMovement();
		float speed = Mth.sqrt((float) ((velocity.x * velocity.x) + (velocity.z * velocity.z)));
		this.groundSpeed = speed * 17.5f;
	}
}
