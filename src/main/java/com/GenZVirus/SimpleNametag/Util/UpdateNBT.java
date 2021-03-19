package com.GenZVirus.SimpleNametag.Util;

import java.util.List;
import java.util.UUID;

import com.GenZVirus.SimpleNametag.Network.PacketHandlerCommon;
import com.GenZVirus.SimpleNametag.Network.Packets.SyncCompoundNBT;
import com.google.common.collect.Lists;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.fml.network.NetworkDirection;

public class UpdateNBT {

	public static List<CompoundNBT> NBTs = Lists.newArrayList();
	public static List<UUID> uuids = Lists.newArrayList();

	public static void run(CompoundNBT nbt, UUID uuid) {
		NBTs.add(nbt);
		uuids.add(uuid);
	}

	public static void updateAllPlayers(UUID uuid, List<ServerPlayerEntity> players) {
		for (PlayerEntity player : players) {
			CompoundNBT nbt = player.getPersistentData();
			if (!nbt.contains("SimpleNameplate")) {
				createNBT(nbt);
			}
			nbt = player.serializeNBT();
			PacketHandlerCommon.INSTANCE.sendTo(new SyncCompoundNBT(nbt, uuid), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
	}

	private static void createNBT(CompoundNBT nbt) {
		CompoundNBT simpleNameplate = new CompoundNBT();
		simpleNameplate.putFloat("RED", 1.0F);
		simpleNameplate.putFloat("GREEN", 1.0F);
		simpleNameplate.putFloat("BLUE", 1.0F);
		simpleNameplate.putFloat("ALPHA", 1.0F);
		simpleNameplate.putInt("BGColor", 553648127);
		simpleNameplate.putInt("TextColor", -1);
		nbt.put("SimpleNameplate", simpleNameplate);
	}

}
