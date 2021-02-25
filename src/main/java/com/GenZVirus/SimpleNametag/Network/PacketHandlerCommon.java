package com.GenZVirus.SimpleNametag.Network;

import com.GenZVirus.SimpleNametag.SimpleNameplate;
import com.GenZVirus.SimpleNametag.Network.Packets.SyncCompoundNBT;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandlerCommon {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(SimpleNameplate.MOD_ID, "soulmate"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);

	public static void init() {
		int id = 0;

		INSTANCE.messageBuilder(SyncCompoundNBT.class, id++).encoder(SyncCompoundNBT::encode).decoder(SyncCompoundNBT::decode).consumer(SyncCompoundNBT::handle).add();
	}
}
