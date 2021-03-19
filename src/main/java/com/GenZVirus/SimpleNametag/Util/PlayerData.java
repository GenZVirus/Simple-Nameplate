package com.GenZVirus.SimpleNametag.Util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class PlayerData {
	public PlayerEntity player;
	public CompoundNBT data;
	
	public PlayerData(PlayerEntity player, CompoundNBT data) {
		this.player = player;
		this.data = data;
	}
	
	public boolean isPlayer(PlayerEntity player) {
		if(this.player.getUniqueID().equals(player.getUniqueID())) return true;
		return false;
	}
}
