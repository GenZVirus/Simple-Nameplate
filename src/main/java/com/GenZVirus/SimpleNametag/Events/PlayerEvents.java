package com.GenZVirus.SimpleNametag.Events;

import java.util.List;

import com.GenZVirus.SimpleNametag.SimpleNameplate;
import com.GenZVirus.SimpleNametag.Util.UpdateNBT;
import com.google.common.collect.Lists;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = SimpleNameplate.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	public static List<PlayerEntity> players = Lists.newArrayList();

	@SubscribeEvent
	public static void onPlayerLogging(PlayerLoggedInEvent event) {
		players.add(event.getPlayer());
		UpdateNBT.updateAllPlayers(event.getPlayer().getUniqueID());
	}

	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
		players.remove(event.getPlayer());
	}

}