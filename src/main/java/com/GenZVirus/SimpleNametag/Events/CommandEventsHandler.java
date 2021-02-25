package com.GenZVirus.SimpleNametag.Events;

import com.GenZVirus.SimpleNametag.SimpleNameplate;
import com.GenZVirus.SimpleNametag.Commands.setColorCommands;
import com.mojang.brigadier.CommandDispatcher;

import net.minecraft.command.CommandSource;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;

@Mod.EventBusSubscriber(modid = SimpleNameplate.MOD_ID)
public class CommandEventsHandler {

	@SubscribeEvent
	public static void onServerStarting(final FMLServerStartingEvent event) {
		CommandDispatcher<CommandSource> dispatcher = event.getServer().getCommandManager().getDispatcher();

		setColorCommands.register(dispatcher);
	}

}
