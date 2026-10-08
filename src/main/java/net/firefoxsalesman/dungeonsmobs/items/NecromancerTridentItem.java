package net.firefoxsalesman.dungeonsmobs.items;

import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.entity.summonables.TridentStormEntity;
import net.firefoxsalesman.dungeonsmobs.interfaces.IHasInventorySprite;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactItem;
import net.firefoxsalesman.dungeonslibs.items.artifacts.ArtifactUseContext;
import net.firefoxsalesman.dungeonslibs.network.BreakItemMessage;
import net.firefoxsalesman.dungeonslibs.utils.PositionUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class NecromancerTridentItem extends ArtifactItem implements IHasInventorySprite {
	public NecromancerTridentItem(Properties properties) {
		super(properties);
	}

	public int tridentSummonRange = 5;

	public InteractionResultHolder<ItemStack> procArtifact(ArtifactUseContext itemUseContext) {
		Level world = itemUseContext.getLevel();
		if (world.isClientSide) {
			return InteractionResultHolder.success(itemUseContext.getItemStack());
		} else {
			ItemStack itemUseContextItem = itemUseContext.getItemStack();
			Player itemUseContextPlayer = itemUseContext.getPlayer();
			BlockPos itemUseContextPos = itemUseContext.getClickedPos();

			if (itemUseContextPlayer != null) {
				for (int i = 0; i < 8; i++) {
					TridentStormEntity tridentStorm = ModEntities.TRIDENT_STORM.get()
							.create(itemUseContextPlayer.level());
					tridentStorm.owner = itemUseContextPlayer;
					tridentStorm.moveTo(new BlockPos(
							itemUseContextPos.getX() - tridentSummonRange
									+ itemUseContextPlayer.getRandom().nextInt(
											tridentSummonRange * 2),
							itemUseContextPos.getY(),
							itemUseContextPos.getZ() - tridentSummonRange
									+ itemUseContextPlayer.getRandom().nextInt(
											tridentSummonRange * 2)),
							0, 0);
					tridentStorm.setYRot(itemUseContextPlayer.getRandom().nextInt(360));
					itemUseContextPlayer.level().addFreshEntity(tridentStorm);
					PositionUtils.moveToCorrectHeight(tridentStorm);
				}
				ServerLevel serverLevel = (ServerLevel) world;
				ServerPlayer serverPlayer = itemUseContextPlayer instanceof ServerPlayer sp ? sp : null;
				itemUseContextItem.hurtAndBreak(1, serverLevel, serverPlayer,
						(item) -> PacketDistributor.sendToPlayersTrackingEntityAndSelf(itemUseContextPlayer,
								new BreakItemMessage(itemUseContextPlayer.getId(), itemUseContextItem)));
				ArtifactItem.putArtifactOnCooldown(itemUseContextPlayer, itemUseContextItem.getItem());
			}
			return InteractionResultHolder.consume(itemUseContextItem);
		}
	}

	@Override
	public int getCooldownInSeconds() {
		return 10;
	}

	@Override
	public int getDurationInSeconds() {
		return 0;
	}
}
