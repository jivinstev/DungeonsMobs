package net.firefoxsalesman.dungeonsmobs.client.models.armor;

import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorGear;
import net.firefoxsalesman.dungeonslibs.client.renderer.gearconfig.ArmorGearModel;
import net.firefoxsalesman.dungeonsmobs.entity.illagers.WindcallerEntity;
import net.minecraft.world.entity.LivingEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.animation.AnimationState;

public class WindcallerArmorGearModel<T extends ArmorGear> extends ArmorGearModel<T> {

	LivingEntity wearer;

	public LivingEntity getWearer() {
		return wearer;
	}

	public void setWearer(LivingEntity wearer) {
		this.wearer = wearer;
	}

	@Override
	public void setCustomAnimations(T entity, long uniqueID, AnimationState<T> customPredicate) {
		super.setCustomAnimations(entity, uniqueID, customPredicate);

		GeoBone cloak = this.getAnimationProcessor().getBone("armorCloak");

		cloak.setHidden(this.getWearer() != null && this.getWearer() instanceof WindcallerEntity);
	}
}
