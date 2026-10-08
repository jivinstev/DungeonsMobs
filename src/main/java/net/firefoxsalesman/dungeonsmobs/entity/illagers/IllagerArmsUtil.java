package net.firefoxsalesman.dungeonsmobs.entity.illagers;

import net.firefoxsalesman.dungeonsmobs.client.IllagerArmsClient;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public class IllagerArmsUtil {
    public static boolean armorHasCrossedArms(AbstractIllager p_241739_3_, ItemStack itemstack) {
        if (!(itemstack.getItem() instanceof ArmorItem)) {
            return true;
        }
        if (FMLEnvironment.dist != Dist.CLIENT) {
            return true;
        }
        return resourceExists(IllagerArmsClient.getArmorResourceStatic(p_241739_3_, itemstack, EquipmentSlot.CHEST));
    }

    public static boolean resourceExists(ResourceLocation resourceLocation) {
        if (resourceLocation != null && FMLEnvironment.dist == Dist.CLIENT) {
            return IllagerArmsClient.resourceExists(resourceLocation);
        }
        return false;
    }
}
