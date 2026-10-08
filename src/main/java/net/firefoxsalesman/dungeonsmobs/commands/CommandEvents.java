package net.firefoxsalesman.dungeonsmobs.commands;

import com.mojang.brigadier.CommandDispatcher;

import net.firefoxsalesman.dungeonslibs.utils.ModHelper;
import net.firefoxsalesman.dungeonsmobs.DungeonsMobs;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber(modid = DungeonsMobs.MOD_ID)
public class CommandEvents {
	@SubscribeEvent
	public static void onRegisterCommandEvent(RegisterCommandsEvent event) {
		if (ModHelper.hasMod("enchantwithmob")) {
			CommandDispatcher<CommandSourceStack> commandDispatcher = event.getDispatcher();
			SummonUniqueAncientCommand.register(commandDispatcher, event.getBuildContext());
			SummonNonUniqueAncientCommand.register(commandDispatcher, event.getBuildContext());
		}
	}
}
