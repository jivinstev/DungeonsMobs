package net.firefoxsalesman.dungeonsmobs.mod;

import net.firefoxsalesman.dungeonsmobs.items.BlueNethershroomItem;
import net.firefoxsalesman.dungeonsmobs.items.CustomArmorMaterial;
import net.firefoxsalesman.dungeonsmobs.items.GeomancerStaffItem;
import net.firefoxsalesman.dungeonsmobs.items.IceWandItem;
import net.firefoxsalesman.dungeonsmobs.items.PiglinHelmetItem;
import net.firefoxsalesman.dungeonsmobs.items.WindcallerStaffItem;
import net.firefoxsalesman.dungeonsmobs.items.MountaineerAxeItem;
import net.firefoxsalesman.dungeonsmobs.items.NecromancerStaffItem;
import net.firefoxsalesman.dungeonsmobs.items.NecromancerTridentItem;
import net.firefoxsalesman.dungeonsmobs.items.armor.WindcallerArmorGear;
import net.firefoxsalesman.dungeonsmobs.items.shield.RoyalGuardShieldItem;
import net.firefoxsalesman.dungeonsmobs.items.shield.VanguardShieldItem;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorGear;
import net.firefoxsalesman.dungeonslibs.items.gearconfig.ArmorSet;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.ArmorMaterialBaseType;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.DungeonsArmorMaterial;
import net.firefoxsalesman.dungeonslibs.items.materials.armor.DungeonsArmorMaterials;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.DyeColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static net.firefoxsalesman.dungeonsmobs.DungeonsMobs.MOD_ID;
import static net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper.modLoc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ModItems {
	private static final ResourceLocation DEFAULT_ANIMATION_RESOURCE = modLoc(
			"animations/armor/armor_default.animation.json");

	public static final Map<ResourceLocation, Supplier<Item>> ARMORS = new HashMap<>();
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);

	public static final Map<ResourceLocation, Supplier<Item>> ARTIFACTS = new HashMap<>();

	public static final Item.Properties ARMOR_PROPERTIES = new Item.Properties();
	public static final Supplier<Item> ROYAL_GUARD_SHIELD = ITEMS.register("royal_guard_shield",
			() -> new RoyalGuardShieldItem(
					new Item.Properties().durability(336)));

	public static final Supplier<Item> VANGUARD_SHIELD = ITEMS.register("vanguard_shield",
			() -> new VanguardShieldItem(
					new Item.Properties().durability(336)));

	// Armour
	public static final ArmorSet WINDCALLER_ARMOR = registerArmorSetWindcaller("windcaller_armor",
			"windcaller_helmet", "windcaller_chestplate", null, null);

	// SPATULA
	public static final Supplier<Item> WOODEN_LADLE = ITEMS.register("wooden_ladle",
			() -> new ShovelItem(Tiers.WOOD, new Item.Properties()
					.attributes(DiggerItem.createAttributes(Tiers.WOOD, 0.5F, (2.0F - 4.0F)))));

	// MOUNTAINEER AXES
	public static final Supplier<Item> MOUNTAINEER_AXE = ITEMS.register("mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final Supplier<Item> GOLD_MOUNTAINEER_AXE = ITEMS.register("gold_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.IRON, 1, (1.2F - 4.0F), new Item.Properties()));

	public static final Supplier<Item> DIAMOND_MOUNTAINEER_AXE = ITEMS.register("diamond_mountaineer_axe",
			() -> new MountaineerAxeItem(Tiers.DIAMOND, 1, (1.2F - 4.0F), new Item.Properties()));

	// ARTIFACTS
	public static final Supplier<Item> WINDCALLER_STAFF = registerArtifact("windcaller_staff",
			() -> new WindcallerStaffItem(new Item.Properties()));
	public static final Supplier<Item> GEOMANCER_STAFF = registerArtifact("geomancer_staff",
			() -> new GeomancerStaffItem(new Item.Properties()));
	public static final Supplier<Item> NECROMANCER_STAFF = registerArtifact("necromancer_staff",
			() -> new NecromancerStaffItem(new Item.Properties()));
	public static final Supplier<Item> NECROMANCER_TRIDENT = registerArtifact("necromancer_trident",
			() -> new NecromancerTridentItem(new Item.Properties()));
	public static final Supplier<Item> ICE_WAND = registerArtifact("ice_wand",
			() -> new IceWandItem(new Item.Properties()));

	public static void register(IEventBus eventBus) {
		ITEMS.register(eventBus);
	}

	public static Collection<DeferredHolder<Item, ? extends Item>> getEntries() {
		return ITEMS.getEntries();
	}

	private static ArmorSet registerArmorSet(String armorSetId, String helmetId, String chestId, String legsId,
			String bootsId) {
		return registerArmorSet(armorSetId, helmetId, chestId, legsId, bootsId, false);
	}

	// PROJECTILES
	public static final Supplier<Item> BLUE_NETHERSHROOM = ITEMS.register("blue_nethershroom",
			() -> new BlueNethershroomItem(new Item.Properties().stacksTo(16)));
	// TRIDENTS
	public static final Supplier<Item> YELLOW_TRIDENT = ITEMS.register("yellow_trident",
			() -> new ColoredTridentItem((new Item.Properties().durability(250)), DyeColor.YELLOW));

	public static final Supplier<Item> PURPLE_TRIDENT = ITEMS.register("purple_trident",
			() -> new ColoredTridentItem((new Item.Properties().durability(250)), DyeColor.PURPLE));

	private static ArmorSet registerArmorSet(String armorSetId, String helmetId, String chestId, String legsId,
			String bootsId, boolean animated) {
		ResourceLocation armorSet = modLoc(armorSetId);
		ResourceLocation modelLocation = modLoc("geo/armor/" + armorSetId + ".geo.json");
		ResourceLocation textureLocation = modLoc("textures/models/armor/" + armorSetId + ".png");
		ResourceLocation animationFileLocation = animated
				? modLoc("animations/armor/" + armorSetId + ".animation.json")
				: DEFAULT_ANIMATION_RESOURCE;
		return new ArmorSet(
				armorSet,
				registerArmor(helmetId,
						() -> new ArmorGear(Type.HELMET, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(chestId,
						() -> new ArmorGear(Type.CHESTPLATE, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(legsId,
						() -> new ArmorGear(Type.LEGGINGS, ARMOR_PROPERTIES, armorSet,
								modelLocation, textureLocation, animationFileLocation)),
				registerArmor(bootsId, () -> new ArmorGear(Type.BOOTS, ARMOR_PROPERTIES,
						armorSet, modelLocation, textureLocation, animationFileLocation)));
	}

	// ArmorGear resolves its material in its constructor, which runs during registration, before the
	// datapack-loaded material map is populated; seed the iron fallback it defaults to. The real
	// data replaces this map on the first datapack load.
	private static void seedFallbackArmorMaterial() {
		ResourceLocation iron = ResourceLocation.withDefaultNamespace("iron");
		var data = DungeonsArmorMaterials.ARMOR_MATERIALS.getData();
		if (data.containsKey(iron))
			return;
		ArmorMaterial m = ArmorMaterials.IRON.value();
		data.put(iron, DungeonsArmorMaterial.create("iron", 0,
				List.of(m.defense().getOrDefault(Type.BOOTS, 0), m.defense().getOrDefault(Type.LEGGINGS, 0),
						m.defense().getOrDefault(Type.CHESTPLATE, 0),
						m.defense().getOrDefault(Type.HELMET, 0)),
				m.enchantmentValue(), BuiltInRegistries.ITEM.getKey(Items.IRON_INGOT),
				m.equipSound().value(), m.toughness(), m.knockbackResistance(), ArmorMaterialBaseType.METAL));
	}

	private static Supplier<Item> registerArmor(String armorId, Supplier<Item> itemSupplier) {
		if (armorId == null)
			return null;
		Supplier<Item> register = ITEMS.register(armorId, () -> {
			seedFallbackArmorMaterial();
			return itemSupplier.get();
		});
		ARMORS.put(GeneralHelper.modLoc(armorId), register);
		return register;
	}

	private static ArmorSet registerArmorSetWindcaller(String armorSetId, String helmetId, String chestId,
			String legsId, String bootsId) {
		ResourceLocation armorSet = modLoc(armorSetId);
		ResourceLocation modelLocation = modLoc("geo/armor/" + armorSetId + ".geo.json");
		ResourceLocation textureLocation = modLoc("textures/models/armor/" + armorSetId + ".png");
		ResourceLocation animationFileLocation = modLoc("animations/armor/cloaked_armor.animation.json");
		return new ArmorSet(
				armorSet,
				registerArmor(helmetId,
						() -> new WindcallerArmorGear(Type.HELMET, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)),
				registerArmor(chestId,
						() -> new WindcallerArmorGear(Type.CHESTPLATE, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)),
				registerArmor(legsId,
						() -> new WindcallerArmorGear(Type.LEGGINGS, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)),
				registerArmor(bootsId,
						() -> new WindcallerArmorGear(Type.BOOTS, ARMOR_PROPERTIES,
								armorSet, modelLocation, textureLocation,
								animationFileLocation)));
	}

	private static Supplier<Item> registerArtifact(String meleeWeaponId, Supplier<Item> itemSupplier) {
		Supplier<Item> register = ITEMS.register(meleeWeaponId, itemSupplier);
		ARTIFACTS.put(GeneralHelper.modLoc(meleeWeaponId), register);
		return register;
	}
}
