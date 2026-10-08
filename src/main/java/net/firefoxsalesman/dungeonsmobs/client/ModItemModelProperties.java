package net.firefoxsalesman.dungeonsmobs.client;

import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemModelProperties {

	public static void registerProperties() {
		ItemProperties.register(ModItems.ROYAL_GUARD_SHIELD.get(),
				ResourceLocation.parse("blocking"),
				(stack, clientWorld, livingEntity, i) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
				});
		ItemProperties.register(ModItems.VANGUARD_SHIELD.get(),
				ResourceLocation.parse("blocking"),
				(stack, clientWorld, livingEntity, i) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
				});
		ItemProperties.register(ModItems.YELLOW_TRIDENT.get(),
				ResourceLocation.parse("throwing"),
				(stack, clientWorld, livingEntity, i) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
				});
		ItemProperties.register(ModItems.PURPLE_TRIDENT.get(),
				ResourceLocation.parse("throwing"),
				(stack, clientWorld, livingEntity, i) -> {
					return livingEntity != null && livingEntity.isUsingItem()
							&& livingEntity.getUseItem() == stack ? 1.0F : 0.0F;
				});
	}
}
