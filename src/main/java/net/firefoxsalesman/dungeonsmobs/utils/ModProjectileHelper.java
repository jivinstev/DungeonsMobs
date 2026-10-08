package net.firefoxsalesman.dungeonsmobs.utils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ModProjectileHelper {

	private static final double RAYTRACE_DISTANCE = 16.0D;

	public static InteractionHand getHandWith(LivingEntity livingEntity, Predicate<Item> itemPredicate) {

		return itemPredicate.test(livingEntity.getMainHandItem().getItem()) ? InteractionHand.MAIN_HAND
				: InteractionHand.OFF_HAND;
	}

	public static ItemStack createRocket(int explosions, DyeColor... dyeColor) {
		ItemStack rocket = new ItemStack(Items.FIREWORK_ROCKET);
		ItemStack star = new ItemStack(Items.FIREWORK_STAR);
		// making firework pink
		IntList colorList = new IntArrayList();
		for (DyeColor color : dyeColor) {
			colorList.add(color.getFireworkColor());
		}
		FireworkExplosion starExplosion = new FireworkExplosion(FireworkExplosion.Shape.BURST, colorList, colorList,
				false, false);
		star.set(DataComponents.FIREWORK_EXPLOSION, starExplosion);
		// adding starExplosion to the rocket's explosion list
		List<FireworkExplosion> rocketExplosions = new ArrayList<>();
		for (int i = 0; i < explosions; i++) {
			rocketExplosions.add(starExplosion);
		}
		if (!rocketExplosions.isEmpty()) {
			rocket.set(DataComponents.FIREWORKS, new Fireworks(0, rocketExplosions));
		}
		return rocket;
	}

	public static HitResult getLaserRayTrace(LivingEntity shooter) {
		Level world = shooter.level();
		BlockHitResult blockRTR = (BlockHitResult) shooter.pick(RAYTRACE_DISTANCE, 1.0F, false);
		Vec3 startVec = shooter.getEyePosition(1.0F);
		Vec3 lookVec = shooter.getViewVector(1.0F);
		Vec3 endVec = startVec.add(lookVec.x * RAYTRACE_DISTANCE, lookVec.y * RAYTRACE_DISTANCE,
				lookVec.z * RAYTRACE_DISTANCE);
		if (blockRTR.getType() != HitResult.Type.MISS)
			endVec = blockRTR.getLocation();

		AABB targetAreaBoundingBox = shooter.getBoundingBox().expandTowards(lookVec.scale(RAYTRACE_DISTANCE))
				.inflate(1.0D);
		EntityHitResult entityRTR = ProjectileUtil.getEntityHitResult(world, shooter, startVec, endVec,
				targetAreaBoundingBox, entity -> !entity.isSpectator() && entity.isPickable());

		if (entityRTR != null) {
			return entityRTR;
		} else {
			return blockRTR;
		}
	}
}
