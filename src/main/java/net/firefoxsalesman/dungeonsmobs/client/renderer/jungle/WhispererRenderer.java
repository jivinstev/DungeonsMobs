package net.firefoxsalesman.dungeonsmobs.client.renderer.jungle;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.jungle.WhispererModel;
import net.firefoxsalesman.dungeonslibs.client.renderer.layers.GeoEyeLayer;
import net.firefoxsalesman.dungeonsmobs.entity.jungle.AbstractWhispererEntity;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

@OnlyIn(Dist.CLIENT)
public class WhispererRenderer<T extends AbstractWhispererEntity> extends GeoEntityRenderer<T> {
	public WhispererRenderer(EntityRendererProvider.Context renderManager) {
		super(renderManager, new WhispererModel<T>());
		addRenderLayer(new GeoEyeLayer<>(this,
				GeneralHelper.modLoc("textures/entity/jungle/whisperer_glow.png")));
	}

	@Override
	protected void applyRotations(T entityLiving, PoseStack matrixStackIn, float ageInTicks,
			float rotationYaw, float partialTicks) {
		float scaleFactor = 1.0F;
		matrixStackIn.scale(scaleFactor, scaleFactor, scaleFactor);
		super.applyRotations(entityLiving, matrixStackIn, ageInTicks, rotationYaw, partialTicks);

	}

	@Override
	public void renderRecursively(PoseStack poseStack, T animatable, GeoBone bone, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, int colour) {
		if (isArmorBone(bone)) {
			bone.setHidden(true);
		}
		super.renderRecursively(poseStack, animatable, bone, renderType, bufferSource, buffer, isReRender,
				partialTick,
				packedLight, packedOverlay, colour);
	}

	protected boolean isArmorBone(GeoBone bone) {
		return bone.getName().startsWith("armor");
	}

}
