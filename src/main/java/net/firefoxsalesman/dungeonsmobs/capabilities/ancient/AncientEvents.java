package net.firefoxsalesman.dungeonsmobs.capabilities.ancient;

import net.minecraft.core.registries.BuiltInRegistries;

import java.util.List;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.firefoxsalesman.dungeonsmobs.items.GildedItemHelper;

import net.firefoxsalesman.dungeonsmobs.network.message.AncientMessage;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class AncientEvents {

	@SubscribeEvent
	public static void onPlayerStartTracking(PlayerEvent.StartTracking event) {
		Player player = event.getEntity();
		Entity target = event.getTarget();
		if (player instanceof ServerPlayer && target != null && player != null) {
			Ancient cap = AncientHelper.getAncientCapability(target);
			if (cap.isAncient()) {
				PacketDistributor.sendToPlayer((ServerPlayer) player,
						new AncientMessage(target.getId(), cap.isAncient()));
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdate(EntityTickEvent.Pre event) {
	    if (!(event.getEntity() instanceof net.minecraft.world.entity.LivingEntity living)) return;
		LivingEntity entityLiving = living;
		if (!entityLiving.level().isClientSide) {
			Ancient cap = AncientHelper.getAncientCapability(entityLiving);
			if (cap.isAncient() && cap.getBossInfo() != null) {
				if (entityLiving.isAlive()) {
					List<ServerPlayer> nearbyEntities = entityLiving.level().getNearbyEntities(
							ServerPlayer.class,
							TargetingConditions.forNonCombat().range(20.0D)
									.ignoreInvisibilityTesting(),
							entityLiving,
							entityLiving.getBoundingBox().inflate(20D, 10D, 20D));
					nearbyEntities.forEach(
							playerEntity -> cap.getBossInfo().addPlayer(playerEntity));
					List<ServerPlayer> trackingPlayers = new ObjectArrayList<>(
							cap.getBossInfo().getPlayers());
					List<ServerPlayer> furtherEntities = entityLiving.level().getNearbyEntities(
							ServerPlayer.class,
							TargetingConditions.forNonCombat().range(50.0D)
									.ignoreInvisibilityTesting(),
							entityLiving,
							entityLiving.getBoundingBox().inflate(50D, 20D, 50D));
					trackingPlayers.forEach(playerEntity -> {
						if (!furtherEntities.contains(playerEntity)) {
							cap.getBossInfo().removePlayer(playerEntity);
						}
					});

				} else {
					cap.getBossInfo().removeAllPlayers();
				}
			}
		}
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(EntityTickEvent.Pre event) {
	    if (!(event.getEntity() instanceof net.minecraft.world.entity.LivingEntity living)) return;
		LivingEntity livingEntity = living;
		Ancient cap = AncientHelper.getAncientCapability(livingEntity);
		if (cap.isAncient() && cap.getBossInfo() != null) {
			cap.getBossInfo().setProgress(livingEntity.getHealth() / livingEntity.getMaxHealth());
		}
	}

	@SubscribeEvent
	public static void onJoinLevel(EntityJoinLevelEvent event) {
		Entity entity = event.getEntity();
		if (entity instanceof Mob mob) {
			Ancient cap = AncientHelper.getAncientCapability(mob);
			if (cap.isAncient())
				cap.initiateBossBar(mob, cap.getDisplayName());
		}
	}

	@SubscribeEvent
	public static void onLivingDeathEvent(LivingDeathEvent event) {
		Entity attacker = event.getSource().getEntity();
		if (AncientHelper.getAncientCapability(event.getEntity()).isAncient()
				&& attacker instanceof ServerPlayer player)
			dropGildedItem(player,
					BuiltInRegistries.ITEM.get(GeneralHelper.modLoc("windcaller_helmet")));
	}

	private static void dropGildedItem(LivingEntity entity, Item item) {
		ItemStack sword = new ItemStack(item);
		ItemStack gildedItem = GildedItemHelper.getGildedItem(entity.getRandom(), sword,
				entity.level().registryAccess());
		ItemEntity gildedItemDrop = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(),
				gildedItem);
		entity.level().addFreshEntity(gildedItemDrop);
	}
}
