package net.firefoxsalesman.dungeonsmobs;

import java.util.List;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.client.ModItemModelProperties;
import net.firefoxsalesman.dungeonsmobs.client.particle.ModParticleTypes;
import net.firefoxsalesman.dungeonsmobs.config.DungeonsMobsConfig;
import net.firefoxsalesman.dungeonsmobs.entity.ModEntities;
import net.firefoxsalesman.dungeonsmobs.mod.ModEffects;
import net.firefoxsalesman.dungeonsmobs.mod.ModItems;
import net.firefoxsalesman.dungeonsmobs.mod.ModStructureModifiers;
import net.firefoxsalesman.dungeonsmobs.utils.GeneralHelper;
import net.firefoxsalesman.dungeonsmobs.worldgen.EntitySpawnPlacement;
import net.firefoxsalesman.dungeonsmobs.worldgen.RaidEntries;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.firefoxsalesman.dungeonsmobs.network.NetworkHandler;
import net.firefoxsalesman.dungeonslibs.network.CommonProxy;
import net.firefoxsalesman.dungeonslibs.client.ClientProxy;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(DungeonsMobs.MOD_ID)
public class DungeonsMobs {
	// Define mod id in a common place for everything to reference
	public static final String MOD_ID = "dungeonsmobs";
	// Directly reference a slf4j logger
	public static final Logger LOGGER = LogUtils.getLogger();

	public DungeonsMobs(IEventBus modEventBus, ModContainer modContainer) {
		PROXY = FMLEnvironment.dist == Dist.CLIENT ? new ClientProxy() : new CommonProxy();
		modContainer.registerConfig(Type.COMMON, DungeonsMobsConfig.COMMON_SPEC,
				"dungeons-mobs-common.toml");
		modEventBus.addListener(this::setup);
		modEventBus.addListener(EntitySpawnPlacement::initSpawnPlacements);
		modEventBus.addListener(this::doClientStuff);

		ModSoundEvents.register(modEventBus);
		ModEffects.register(modEventBus);

		modEventBus.addListener(this::commonSetup);
		ModEntities.register(modEventBus);
		ModItems.register(modEventBus);
		ModParticleTypes.register(modEventBus);

		ModCapabilities.ATTACHMENTS.register(modEventBus);
		modEventBus.addListener(NetworkHandler::register);

		if (ModHelper.hasMod("enchantwithmob"))
			ModMobEnchants.register(modEventBus);

		NeoForge.EVENT_BUS.register(this);
		modEventBus.addListener(this::addCreative);
		ModStructureModifiers.STRUCTURE_MODIFIER_SERIALIZERS.register(modEventBus);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
	}

	private void addCreative(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			ModItems.getEntries().forEach(item -> event.accept(item.get()));
		}
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS)
			ModEntities.getEntries().forEach(item -> {
				boolean idMatches = false;
				for (String s : List.of("wraith", "necromancer"))
					if (GeneralHelper.modLoc(s + "_spawn_egg").equals(BuiltInRegistries.ITEM.getKey(item.get())))
						idMatches = true;
				if (!(ModHelper.hasGoety() && idMatches))
					event.accept(item.get());
			});
	}

	private void setup(final FMLCommonSetupEvent event) {

		event.enqueueWork(EntitySpawnPlacement::createPlacementTypes);

		event.enqueueWork(RaidEntries::initWaveMemberEntries);
	}

	// You can use SubscribeEvent and let the Event Bus discover methods to call
	@SubscribeEvent
	public void onServerStarting(ServerStartingEvent event) {
	}

	private void doClientStuff(final FMLClientSetupEvent event) {
		// ITEM MODEL PROPERTIES
		event.enqueueWork(ModItemModelProperties::registerProperties);
	}

	// You can use EventBusSubscriber to automatically register all static methods
	// in the class annotated with @SubscribeEvent
	@EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
	public static class ClientModEvents {
		@SubscribeEvent
		public static void onClientSetup(FMLClientSetupEvent event) {
		}
	}
}
