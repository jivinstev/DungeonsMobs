package net.firefoxsalesman.dungeonsmobs.interfaces;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public interface IHasItemStackData {

	ItemStack getDataItem();

	void setDataItem(ItemStack dataItem);

	default void writeDataItem(CompoundTag tag, String key, HolderLookup.Provider registries) {
		ItemStack itemStack = this.getDataItem();
		if (!itemStack.isEmpty()) {
			tag.put(key, itemStack.save(registries));
		}
	}

	default void readDataItem(CompoundTag tag, String key, HolderLookup.Provider registries) {
		ItemStack itemstack = ItemStack.parseOptional(registries, tag.getCompound(key));
		this.setDataItem(itemstack);
	}
}
