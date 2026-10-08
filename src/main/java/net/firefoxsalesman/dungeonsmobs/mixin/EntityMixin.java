package net.firefoxsalesman.dungeonsmobs.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.mobenchants.NewMobEnchantUtils;
import net.firefoxsalesman.dungeonsmobs.mod.ModMobEnchants;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

@Mixin(Entity.class)
public class EntityMixin {
	@Inject(at = @At("RETURN"), method = "fireImmune", cancellable = true)
	private void fireImmune(CallbackInfoReturnable<Boolean> ci) {
		// Enchant With Mob is optional: without it, ModMobEnchants cannot load.
		if (ModHelper.hasMod("enchantwithmob") && (Entity) ((Object) this) instanceof LivingEntity living)
			NewMobEnchantUtils.executeIfPresentWithLevel(living, ModMobEnchants.FIRE_TRAIL.getKey(),
					level -> ci.setReturnValue(true));
	}
}
