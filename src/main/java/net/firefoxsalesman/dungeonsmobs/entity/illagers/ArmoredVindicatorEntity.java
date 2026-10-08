package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.HashMap;
import java.util.Map;

import net.firefoxsalesman.dungeonsmobs.entity.SpawnEquipmentHelper;
import net.firefoxsalesman.dungeonslibs.client.KeyframeEntity;
import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.WalkAnimationState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;

public class ArmoredVindicatorEntity extends Vindicator implements KeyframeEntity {

	public ArmoredVindicatorEntity(EntityType<? extends ArmoredVindicatorEntity> pEntityType,
			Level pLevel) {
		super(pEntityType, pLevel);
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Vindicator.createAttributes().add(Attributes.ARMOR, 12);
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
		if (ModHelper.hasMod("dungeonsgear"))
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(BuiltInRegistries.ITEM
					.get(ResourceLocation.fromNamespaceAndPath("dungeonsgear", "double_axe"))));
		else
			super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
	}

	@Override
	public void applyRaidBuffs(ServerLevel level, int waveAmount, boolean b) {
		ItemStack mainhandWeapon = new ItemStack(Items.IRON_AXE);
		if (ModHelper.hasMod("dungeonsgear")) {
			Item DOUBLE_AXE = BuiltInRegistries.ITEM
					.get(ResourceLocation.fromNamespaceAndPath("dungeonsmobs", "double_axe"));

			mainhandWeapon = new ItemStack(DOUBLE_AXE);
		}
		Raid raid = getCurrentRaid();
		int enchantmentLevel = 1;
		if (raid != null && waveAmount > raid.getNumGroups(Difficulty.NORMAL)) {
			enchantmentLevel = 2;
		}

		boolean applyEnchant = false;
		if (raid != null) {
			applyEnchant = random.nextFloat() <= raid.getEnchantOdds();
		}
		if (applyEnchant) {
			ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(
					ItemEnchantments.EMPTY);
			Holder<Enchantment> sharpness = level.registryAccess()
					.registryOrThrow(Registries.ENCHANTMENT).getHolderOrThrow(Enchantments.SHARPNESS);
			enchantments.set(sharpness, enchantmentLevel);
			EnchantmentHelper.setEnchantments(mainhandWeapon, enchantments.toImmutable());
		}

		SpawnEquipmentHelper.equipMainhand(mainhandWeapon, this);
	}

	@Override
	public Map<String, AnimationState> getStates() {
		return new HashMap<>();
	}

	@Override
	public WalkAnimationState getWalkAnimation() {
		return walkAnimation;
	}
}
