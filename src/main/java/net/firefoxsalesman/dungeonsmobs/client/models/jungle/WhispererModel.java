package net.firefoxsalesman.dungeonsmobs.client.models.jungle;

import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractWhispererEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class WhispererModel<T extends AbstractWhispererEntity> extends GeoModel<T> {

	@Override
	public ResourceLocation getAnimationResource(T entity) {
		return modLoc("animations/whisperer.animation.json");
	}

	@Override
	public ResourceLocation getModelResource(T entity) {
		return modLoc("geo/whisperer.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(T entity) {
		return modLoc("textures/entity/jungle/whisperer.png");
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		GeoBone head = getAnimationProcessor().getBone("jaw");
		GeoBone cape = getAnimationProcessor().getBone("bipedCape");

		cape.setHidden(true);

		GeoBone leftHand = getAnimationProcessor().getBone("bipedHandLeft");
		GeoBone rightHand = getAnimationProcessor().getBone("bipedHandRight");

		if (entity.tickCount % 1 == 0 && rightHand instanceof GeoBone && leftHand instanceof GeoBone
				&& entity.isSpellcasting()) {
			GeoBone leftHandBone = ((GeoBone) leftHand);
			GeoBone rightHandBone = ((GeoBone) rightHand);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_MAGIC.get(),
					leftHandBone.getPosX(), leftHandBone.getPosY(),
					leftHandBone.getPosZ(), 0, 0, 0);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_MAGIC.get(),
					rightHandBone.getPosX(), rightHandBone.getPosY(),
					rightHandBone.getPosZ(), 0, 0, 0);
		}

		if (entity.tickCount % 2 == 0 && rightHand instanceof GeoBone && leftHand instanceof GeoBone
				&& entity.isSpellcasting()) {
			GeoBone leftHandBone = ((GeoBone) leftHand);
			GeoBone rightHandBone = ((GeoBone) rightHand);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_DUST.get(),
					leftHandBone.getPosX(), leftHandBone.getPosY(),
					leftHandBone.getPosZ(), entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01);
			entity.level().addParticle(ModParticleTypes.CORRUPTED_DUST.get(),
					rightHandBone.getPosX(), rightHandBone.getPosY(),
					rightHandBone.getPosZ(), entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01,
					entity.getRandom().nextGaussian() * 0.01);
		}

		EntityModelData extraData = customPredicate.getData(DataTickets.ENTITY_MODEL_DATA);
		if (extraData.headPitch() != 0 || extraData.netHeadYaw() != 0) {
			head.setRotX(head.getRotX() + (extraData.headPitch() * ((float) Math.PI / 180F)));
			head.setRotY(head.getRotY() + (extraData.netHeadYaw() * ((float) Math.PI / 180F)));
		}
	}

}
