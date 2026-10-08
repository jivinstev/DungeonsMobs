package net.firefoxsalesman.dungeonsmobs.items;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import net.minecraft.core.Holder;

public class PiglinHelmetItem extends ArmorItem {

    public PiglinHelmetItem(Holder<ArmorMaterial> armorMaterial, Type slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Nullable
    @Override
    @OnlyIn(Dist.CLIENT)
    public ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.parse(String.format(DungeonsMobs.MOD_ID + ":textures/models/armor/%s.png", BuiltInRegistries.ITEM.getKey(this).getPath()));
    }
}
