package net.firefoxsalesman.dungeonsmobs.client;

import java.util.UUID;

import net.firefoxsalesman.dungeonsmobs.client.renderer.BossBarRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;

/**
 * Client-only handling of boss bar packets. Only reached for clientbound packets.
 */
public class BossBarClientHandler {
	public static void handle(UUID bar, int bossId, boolean remove) {
		Player player = Minecraft.getInstance().player;
		if (player != null) {
			Entity boss = player.level().getEntity(bossId);
			if (boss instanceof Mob mob) {
				if (remove) {
					BossBarRenderer.removeBossBar(bar, mob);
				} else {
					BossBarRenderer.addBossBar(bar, mob);
				}
			}
		}
	}
}
