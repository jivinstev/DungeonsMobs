package net.firefoxsalesman.dungeonsmobs.items;

import net.firefoxsalesman.dungeonsmobs.entity.summonables.IceCloudEntity;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonslibs.network.BreakItemMessage;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.apache.commons.lang3.tuple.MutablePair;

import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.applyToNearbyEntities;
import static net.firefoxsalesman.dungeonslibs.utils.AreaOfEffectHelper.getCanApplyToEnemyPredicate;

public class IceWandItem extends ArtifactItem {
	public IceWandItem(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult interactLivingEntity(ItemStack stack, Player playerIn, LivingEntity target,
			InteractionHand hand) {
		return freezeEntity(stack, playerIn, target);
	}

	@Override
	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext c) {
		Player playerIn = c.getPlayer();
		ItemStack itemstack = c.getItemStack();
		final MutablePair<LivingEntity, Double> targetDistance = new MutablePair(null, 123456);
		applyToNearbyEntities(playerIn, 16,
				getCanApplyToEnemyPredicate(playerIn),
				(LivingEntity nearbyEntity) -> {
					if (targetDistance.getLeft() == null || nearbyEntity
							.distanceToSqr(playerIn) < targetDistance.getRight()) {
						targetDistance.setLeft(nearbyEntity);
						targetDistance.setRight(nearbyEntity.distanceToSqr(playerIn));
					}
				});
		return new InteractionResultHolder<>(freezeEntity(itemstack, playerIn, targetDistance.getLeft()),
				itemstack);
	}

	private InteractionResult freezeEntity(ItemStack stack, Player playerIn, LivingEntity target) {
		if (target != null) {
			IceCloudEntity.spawn(playerIn, target);
			ItemStack stackBeforeUse = stack.copy();
			EquipmentSlot slot = playerIn.getOffhandItem() == stack ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
			stack.hurtAndBreak(1, playerIn, slot);
			if (stack.isEmpty() && playerIn instanceof ServerPlayer serverPlayer) {
				PacketDistributor.sendToPlayersTrackingEntityAndSelf(serverPlayer,
						new BreakItemMessage(playerIn.getId(), stackBeforeUse));
			}
			ArtifactItem.putArtifactOnCooldown(playerIn, stack.getItem());
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public int getCooldownInSeconds() {
		return 20;
	}

	@Override
	public int getDurationInSeconds() {
		return 0;
	}
}
