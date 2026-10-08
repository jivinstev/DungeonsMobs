package net.firefoxsalesman.dungeonsmobs.client;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;

/**
 * Client-only helpers for IllagerArmsUtil. Only reach this class behind a client dist check.
 */
public class IllagerArmsClient {
    public static ResourceLocation getArmorResourceStatic(Entity entity, ItemStack stack, EquipmentSlot slot) {
        ArmorItem item = (ArmorItem) stack.getItem();
        ResourceLocation materialId = ResourceLocation.parse(item.getMaterial().getRegisteredName());
        ArmorMaterial.Layer layer = new ArmorMaterial.Layer(materialId, "_crossed", false);

        String s1 = ClientHooks.getArmorTexture(entity, stack, layer, false, slot).toString();
        if (!s1.endsWith("_crossed.png")) {
            s1 = s1.replace(".png", "_crossed.png");
        }

        return ResourceLocation.parse(s1);
    }

    public static boolean resourceExists(ResourceLocation resourceLocation) {
        return Minecraft.getInstance().getResourceManager().getResource(resourceLocation).isPresent();
    }
}
