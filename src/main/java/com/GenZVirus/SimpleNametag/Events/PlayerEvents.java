package com.GenZVirus.SimpleNametag.Events;

import java.util.List;

import com.GenZVirus.SimpleNametag.SimpleNameplate;
import com.GenZVirus.SimpleNametag.Util.PlayerData;
import com.GenZVirus.SimpleNametag.Util.UpdateNBT;
import com.google.common.collect.Lists;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.EntityTravelToDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = SimpleNameplate.MOD_ID, bus = Bus.FORGE)
public class PlayerEvents {

	public static List<PlayerData> playerData = Lists.newArrayList();

	@SubscribeEvent
	public static void onPlayerLogging(PlayerLoggedInEvent event) {
		UpdateNBT.updateAllPlayers(event.getPlayer().getUniqueID(), event.getPlayer().world.getServer().getPlayerList().getPlayers());
	}

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
		event.getPlayer().getServer().playerDataManager.savePlayerData(event.getPlayer());
	}
	
	@SubscribeEvent
	public static void onTravelingSave(EntityTravelToDimensionEvent event) {
		if (!(event.getEntity() instanceof PlayerEntity))
			return;
		PlayerEntity player = (PlayerEntity) event.getEntity();
		playerData.add(new PlayerData(player, player.getPersistentData().getCompound("SimpleNameplate")));
	}

	@SubscribeEvent
	public static void onTravelingLoad(PlayerChangedDimensionEvent event) {
		int index = 0;
		for (PlayerData data : playerData) {
			if (data.isPlayer(event.getPlayer())) {
				event.getPlayer().getPersistentData().put("SimpleNameplate", data.data);
				UpdateNBT.updateAllPlayers(event.getPlayer().getUniqueID(), event.getPlayer().world.getServer().getPlayerList().getPlayers());
				break;
			}
			if (index + 1 < playerData.size()) {
				index++;
			}
		}
		playerData.remove(index);
	}
}