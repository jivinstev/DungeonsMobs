package net.firefoxsalesman.dungeonsmobs.items;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

public class MountaineerAxeItem extends PickaxeItem {
    public MountaineerAxeItem(Tier tier, int attackDamageIn, float attackSpeedIn, Item.Properties builder) {
        super(tier, builder.attributes(DiggerItem.createAttributes(tier, attackDamageIn, attackSpeedIn)));
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return (enchantment.value().definition().supportedItems().contains(Items.IRON_SWORD.builtInRegistryHolder())
                || enchantment.value().definition().supportedItems().contains(Items.IRON_AXE.builtInRegistryHolder()))
                && !enchantment.is(Enchantments.SWEEPING_EDGE);
    }

    @Override
    public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
        return true;
    }
}
