package com.GenZVirus.SimpleNametag.Network.Packets;

import java.util.UUID;
import java.util.function.Supplier;

import com.GenZVirus.SimpleNametag.Util.UpdateNBT;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

public class SyncCompoundNBT {

	public CompoundNBT nbt;
	public UUID uuid;

	public SyncCompoundNBT(CompoundNBT nbt, UUID uuid) {
		this.nbt = nbt;
		this.uuid = uuid;
	}

	public static void encode(SyncCompoundNBT pkt, PacketBuffer buf) {
		buf.writeCompoundTag(pkt.nbt);
		buf.writeUniqueId(pkt.uuid);
	}

	public static SyncCompoundNBT decode(PacketBuffer buf) {
		return new SyncCompoundNBT(buf.readCompoundTag(), buf.readUniqueId());
	}

	public static void handle(SyncCompoundNBT pkt, Supplier<NetworkEvent.Context> ctx) {

		ctx.get().enqueueWork(() -> {
			if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
				UpdateNBT.run(pkt.nbt, pkt.uuid);
			}
		});
		ctx.get().setPacketHandled(true);
	}

}
