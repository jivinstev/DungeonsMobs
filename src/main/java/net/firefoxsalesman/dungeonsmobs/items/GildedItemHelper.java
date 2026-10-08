package net.firefoxsalesman.dungeonsmobs.items;

import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import net.firefoxsalesman.dungeonslibs.capabilities.builtinenchantments.BuiltInEnchantments;
import net.firefoxsalesman.dungeonslibs.capabilities.builtinenchantments.BuiltInEnchantmentsHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.RenderTooltipEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = DungeonsMobs.MOD_ID, value = Dist.CLIENT)
public class GildedItemHelper {

	public static final ResourceLocation GILDED_ITEM_RESOURCELOCATION = GeneralHelper.modLoc("gilded_item");

	public static ItemStack getGildedItem(RandomSource random, ItemStack itemStack, HolderLookup.Provider registries) {
		BuiltInEnchantments cap = BuiltInEnchantmentsHelper.getBuiltInEnchantmentsCapability(itemStack);
		List<EnchantmentInstance> list1 = getAvailableEnchantmentResults(1, 1, itemStack, true, registries);
		Optional<EnchantmentInstance> randomItem = WeightedRandom.getRandomItem(random, list1, list1.size());
		randomItem.ifPresent(randomEnchantment -> {
			cap.addBuiltInEnchantment(GILDED_ITEM_RESOURCELOCATION, randomEnchantment);
			itemStack.set(DataComponents.CUSTOM_NAME, Component.translatable("dungeonsmobs.gilded").append(" ")
					.append(itemStack.getHoverName()));
		});
		return itemStack;
	}

	private static List<EnchantmentInstance> getAvailableEnchantmentResults(int minLevel, int maxLevel,
			ItemStack itemStack, boolean includeTreasures, HolderLookup.Provider registries) {
		List<EnchantmentInstance> list = Lists.newArrayList();
		boolean flag = itemStack.getItem() == Items.BOOK;

		HolderLookup.RegistryLookup<Enchantment> lookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
		for (Holder.Reference<Enchantment> enchantment : lookup.listElements().toList()) {
			Enchantment value = enchantment.value();
			if ((!enchantment.is(EnchantmentTags.TREASURE) || includeTreasures) && enchantment.is(EnchantmentTags.IN_ENCHANTING_TABLE)
					&& (value.canEnchant(itemStack)
							|| (flag && value.definition().supportedItems().contains(Items.BOOK.builtInRegistryHolder())))) {
				for (int i = Math.min(value.getMaxLevel(), maxLevel); i > Math
						.min(value.getMinLevel(), minLevel) - 1; --i) {
					list.add(new EnchantmentInstance(enchantment, i));
				}
			}
		}

		return list;
	}

	@SubscribeEvent
	public static void onItemTooltip(ItemTooltipEvent event) {
		BuiltInEnchantments cap = BuiltInEnchantmentsHelper
				.getBuiltInEnchantmentsCapability(event.getItemStack());
		List<EnchantmentInstance> builtInEnchantments = cap
				.getBuiltInEnchantments(GILDED_ITEM_RESOURCELOCATION);
		builtInEnchantments.forEach(enchantmentData -> {
			event.getToolTip().add(Enchantment.getFullname(enchantmentData.enchantment, enchantmentData.level).copy()
					.withStyle(ChatFormatting.GOLD));
		});
	}

	@SubscribeEvent
	public static void onRenderTooltip(RenderTooltipEvent.Color event) {
		BuiltInEnchantments cap = BuiltInEnchantmentsHelper
				.getBuiltInEnchantmentsCapability(event.getItemStack());
		List<EnchantmentInstance> builtInEnchantments = cap
				.getBuiltInEnchantments(GILDED_ITEM_RESOURCELOCATION);
		if (!builtInEnchantments.isEmpty()) {
			event.setBorderStart(0xF0FFD700);
			event.setBorderEnd(0x50F5CC27);
			event.setBackground(0xF0AF7923);
		}
	}

}
