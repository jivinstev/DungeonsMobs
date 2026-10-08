package net.firefoxsalesman.dungeonsmobs.items.shield;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.firefoxsalesman.dungeonsmobs.client.models.armor.VanguardShieldModel;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import static net.firefoxsalesman.dungeonsmobs.client.models.geom.ModModelLayers.VANGUARD_SHIELD;
import static net.firefoxsalesman.dungeonsmobs.items.shield.ShieldTextures.*;
import static net.minecraft.client.model.geom.ModelLayers.SHIELD;

@OnlyIn(Dist.CLIENT)
public class CustomISTER extends BlockEntityWithoutLevelRenderer {

	private final ShieldModel royalGuardShieldModel;
	private final VanguardShieldModel modelVanguardShield;

	public CustomISTER(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
		super(p_172550_, p_172551_);
		modelVanguardShield = new VanguardShieldModel(p_172551_.bakeLayer(VANGUARD_SHIELD));
		royalGuardShieldModel = new ShieldModel(p_172551_.bakeLayer(SHIELD));
	}

	@Override
	public void renderByItem(ItemStack stack, ItemDisplayContext pDisplayContext, PoseStack matrixStack,
			MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
		Item item = stack.getItem();
		if (item instanceof RoyalGuardShieldItem) {
			boolean flag = stack.has(DataComponents.BLOCK_ENTITY_DATA);
			matrixStack.pushPose();
			matrixStack.scale(1.0F, -1.0F, -1.0F);
			Material rendermaterial = flag ? LOCATION_ROYAL_GUARD_SHIELD_BASE
					: LOCATION_ROYAL_GUARD_SHIELD_NO_PATTERN;
			VertexConsumer ivertexbuilder = rendermaterial.sprite()
					.wrap(ItemRenderer.getFoilBufferDirect(buffer,
							royalGuardShieldModel
									.renderType(rendermaterial.atlasLocation()),
							true, stack.hasFoil()));
			royalGuardShieldModel.handle().render(matrixStack, ivertexbuilder, combinedLight,
					combinedOverlay, -1);
			if (flag) {
				BannerPatternLayers patterns = stack.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY);
				BannerRenderer.renderPatterns(matrixStack, buffer, combinedLight, combinedOverlay,
						royalGuardShieldModel.plate(), rendermaterial, false, DyeColor.getColor(stack),
						patterns, stack.hasFoil());
			} else {
				royalGuardShieldModel.plate().render(matrixStack, ivertexbuilder, combinedLight,
						combinedOverlay, -1);
			}

			matrixStack.popPose();
		} else if (item instanceof VanguardShieldItem) {
			matrixStack.pushPose();
			matrixStack.scale(1.0F, -1.0F, -1.0F);
			Material rendermaterial = LOCATION_VANGUARD_SHIELD;
			VertexConsumer ivertexbuilder = rendermaterial.sprite()
					.wrap(ItemRenderer.getFoilBufferDirect(buffer,
							royalGuardShieldModel
									.renderType(rendermaterial.atlasLocation()),
							true, stack.hasFoil()));
			modelVanguardShield.getRoot().render(matrixStack, ivertexbuilder, combinedLight,
					combinedOverlay, -1);

			matrixStack.popPose();
		}
	}

	public static ResourceLocation getTridentTexture(DyeColor dyeColor) {
		return GeneralHelper.modLoc(String.format("textures/entity/%s_trident.png", dyeColor.getName()));
	}

	public static ModelResourceLocation getTridentMRL(DyeColor dyeColor, boolean inHand) {
		ResourceLocation resourceLoc = GeneralHelper
				.modLoc(String.format("%s_trident%s", dyeColor.getName(), inHand ? "_in_hand" : ""));
		return new ModelResourceLocation(resourceLoc, ModelResourceLocation.STANDALONE_VARIANT);
	}
}
