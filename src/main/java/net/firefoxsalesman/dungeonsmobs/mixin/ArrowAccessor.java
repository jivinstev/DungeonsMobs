package net.firefoxsalesman.dungeonsmobs.mixin;

import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AbstractArrow.class)
public interface ArrowAccessor {

	@Accessor("pickupItemStack")
	ItemStack dungeonsmobs$getPickupItemStack();
}
