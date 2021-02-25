package com.GenZVirus.SimpleNametag.Commands;

import com.GenZVirus.SimpleNametag.Events.PlayerEvents;
import com.GenZVirus.SimpleNametag.Network.PacketHandlerCommon;
import com.GenZVirus.SimpleNametag.Network.Packets.SyncCompoundNBT;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;

public class setColorCommands {
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		dispatcher.register(Commands.literal("sn")
		.then(Commands.literal("player").requires((context)-> context.hasPermissionLevel(4))
				.then(Commands.literal("set")
					.then(Commands.literal("BorderColor")
						.then(Commands.argument("target", EntityArgument.player())
								.then(Commands.argument("Amount of RED", IntegerArgumentType.integer(0, 255))
										.then(Commands.argument("Amount of GREEN", IntegerArgumentType.integer(0, 255))
												.then(Commands.argument("Amount of BLUE", IntegerArgumentType.integer(0, 255))
														.then(Commands.argument("Amount of ALPHA", IntegerArgumentType.integer(0, 255))
															.executes(command -> {
																sendMessage(command, "Border colors of " + EntityArgument.getPlayer(command, "target").getName().getUnformattedComponentText() + " have been changed to match R: " + IntegerArgumentType.getInteger(command, "Amount of RED")
																		+ " G: " + IntegerArgumentType.getInteger(command, "Amount of GREEN")
																		+ " B: " + IntegerArgumentType.getInteger(command, "Amount of BLUE")
																		+ " A: " + IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																return setBorderColor(command.getSource(), EntityArgument.getPlayer(command, "target"), 
																		IntegerArgumentType.getInteger(command, "Amount of RED"), 
																		IntegerArgumentType.getInteger(command, "Amount of GREEN"), 
																		IntegerArgumentType.getInteger(command, "Amount of BLUE"), 
																		IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
															})))))))
					.then(Commands.literal("BGColor")
							.then(Commands.argument("target", EntityArgument.player())
									.then(Commands.argument("Amount of RED", IntegerArgumentType.integer(0, 255))
											.then(Commands.argument("Amount of GREEN", IntegerArgumentType.integer(0, 255))
													.then(Commands.argument("Amount of BLUE", IntegerArgumentType.integer(0, 255))
															.then(Commands.argument("Amount of ALPHA", IntegerArgumentType.integer(0, 255))
																.executes(command -> {
																	sendMessage(command, "Background colors of " + EntityArgument.getPlayer(command, "target").getName().getUnformattedComponentText() + " have been changed to match R: " + IntegerArgumentType.getInteger(command, "Amount of RED")
																			+ " G: " + IntegerArgumentType.getInteger(command, "Amount of GREEN")
																			+ " B: " + IntegerArgumentType.getInteger(command, "Amount of BLUE")
																			+ " A: " + IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																	return setBackgroundColor(command.getSource(), EntityArgument.getPlayer(command, "target"), 
																			IntegerArgumentType.getInteger(command, "Amount of RED"), 
																			IntegerArgumentType.getInteger(command, "Amount of GREEN"), 
																			IntegerArgumentType.getInteger(command, "Amount of BLUE"), 
																			IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																})))))))
					.then(Commands.literal("TextColor")
							.then(Commands.argument("target", EntityArgument.player())
									.then(Commands.argument("Amount of RED", IntegerArgumentType.integer(0, 255))
											.then(Commands.argument("Amount of GREEN", IntegerArgumentType.integer(0, 255))
													.then(Commands.argument("Amount of BLUE", IntegerArgumentType.integer(0, 255))
															.then(Commands.argument("Amount of ALPHA", IntegerArgumentType.integer(0, 255))
																.executes(command -> {
																	sendMessage(command, "Text colors of " + EntityArgument.getPlayer(command, "target").getName().getUnformattedComponentText() + " have been changed to match R: " + IntegerArgumentType.getInteger(command, "Amount of RED")
																			+ " G: " + IntegerArgumentType.getInteger(command, "Amount of GREEN")
																			+ " B: " + IntegerArgumentType.getInteger(command, "Amount of BLUE")
																			+ " A: " + IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																	return setTextColor(command.getSource(), EntityArgument.getPlayer(command, "target"), 
																			IntegerArgumentType.getInteger(command, "Amount of RED"), 
																			IntegerArgumentType.getInteger(command, "Amount of GREEN"), 
																			IntegerArgumentType.getInteger(command, "Amount of BLUE"), 
																			IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																})))))))))
		.then(Commands.literal("set")
					.then(Commands.literal("BorderColor")
								.then(Commands.argument("Amount of RED", IntegerArgumentType.integer(0, 255))
										.then(Commands.argument("Amount of GREEN", IntegerArgumentType.integer(0, 255))
												.then(Commands.argument("Amount of BLUE", IntegerArgumentType.integer(0, 255))
														.then(Commands.argument("Amount of ALPHA", IntegerArgumentType.integer(0, 255))
															.executes(command -> {
																sendMessage(command, "Border colors of " + command.getSource().getEntity().getName().getUnformattedComponentText() + " have been changed to match R: " + IntegerArgumentType.getInteger(command, "Amount of RED")
																		+ " G: " + IntegerArgumentType.getInteger(command, "Amount of GREEN")
																		+ " B: " + IntegerArgumentType.getInteger(command, "Amount of BLUE")
																		+ " A: " + IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																return setBorderColor(command.getSource(), (PlayerEntity) command.getSource().getEntity(), 
																		IntegerArgumentType.getInteger(command, "Amount of RED"), 
																		IntegerArgumentType.getInteger(command, "Amount of GREEN"), 
																		IntegerArgumentType.getInteger(command, "Amount of BLUE"), 
																		IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
															}))))))
					.then(Commands.literal("BGColor")
									.then(Commands.argument("Amount of RED", IntegerArgumentType.integer(0, 255))
											.then(Commands.argument("Amount of GREEN", IntegerArgumentType.integer(0, 255))
													.then(Commands.argument("Amount of BLUE", IntegerArgumentType.integer(0, 255))
															.then(Commands.argument("Amount of ALPHA", IntegerArgumentType.integer(0, 255))
																.executes(command -> {
																	sendMessage(command, "Background colors of " + command.getSource().getEntity().getName().getUnformattedComponentText() + " have been changed to match R: " + IntegerArgumentType.getInteger(command, "Amount of RED")
																			+ " G: " + IntegerArgumentType.getInteger(command, "Amount of GREEN")
																			+ " B: " + IntegerArgumentType.getInteger(command, "Amount of BLUE")
																			+ " A: " + IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																	return setBackgroundColor(command.getSource(), (PlayerEntity) command.getSource().getEntity(), 
																			IntegerArgumentType.getInteger(command, "Amount of RED"), 
																			IntegerArgumentType.getInteger(command, "Amount of GREEN"), 
																			IntegerArgumentType.getInteger(command, "Amount of BLUE"), 
																			IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																}))))))
					.then(Commands.literal("TextColor")
									.then(Commands.argument("Amount of RED", IntegerArgumentType.integer(0, 255))
											.then(Commands.argument("Amount of GREEN", IntegerArgumentType.integer(0, 255))
													.then(Commands.argument("Amount of BLUE", IntegerArgumentType.integer(0, 255))
															.then(Commands.argument("Amount of ALPHA", IntegerArgumentType.integer(0, 255))
																.executes(command -> {
																	sendMessage(command, "Text colors of " + command.getSource().getEntity().getName().getUnformattedComponentText() + " have been changed to match R: " + IntegerArgumentType.getInteger(command, "Amount of RED")
																			+ " G: " + IntegerArgumentType.getInteger(command, "Amount of GREEN")
																			+ " B: " + IntegerArgumentType.getInteger(command, "Amount of BLUE")
																			+ " A: " + IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																	return setTextColor(command.getSource(), (PlayerEntity) command.getSource().getEntity(), 
																			IntegerArgumentType.getInteger(command, "Amount of RED"), 
																			IntegerArgumentType.getInteger(command, "Amount of GREEN"), 
																			IntegerArgumentType.getInteger(command, "Amount of BLUE"), 
																			IntegerArgumentType.getInteger(command, "Amount of ALPHA"));
																}))))))
		));
	}

	private static int setBorderColor(CommandSource source, PlayerEntity target, int RED, int GREEN, int BLUE, int ALPHA) {
		CompoundNBT ForgeData = target.getPersistentData();
		CompoundNBT simpleNameplate = ForgeData.getCompound("SimpleNameplate");
		simpleNameplate.putFloat("RED", ((float) RED / 255));
		simpleNameplate.putFloat("GREEN", ((float) GREEN / 255));
		simpleNameplate.putFloat("BLUE", ((float) BLUE / 255));
		simpleNameplate.putFloat("ALPHA", ((float) ALPHA / 255));
		CompoundNBT nbt = target.serializeNBT();
		for (PlayerEntity player : PlayerEvents.players) {
			PacketHandlerCommon.INSTANCE.sendTo(new SyncCompoundNBT(nbt, target.getUniqueID()), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
		return 1;
	}
	
	private static int setBackgroundColor(CommandSource source, PlayerEntity target, int RED, int GREEN, int BLUE, int ALPHA) {
		CompoundNBT ForgeData = target.getPersistentData();
		CompoundNBT simpleNameplate = ForgeData.getCompound("SimpleNameplate");
		int ARGB = ALPHA << 24 | RED << 16 | GREEN << 8 | BLUE;
		simpleNameplate.putInt("BGColor", ARGB);
		CompoundNBT nbt = target.serializeNBT();
		for (PlayerEntity player : PlayerEvents.players) {
			PacketHandlerCommon.INSTANCE.sendTo(new SyncCompoundNBT(nbt, target.getUniqueID()), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
		return 1;
	}
	
	private static int setTextColor(CommandSource source, PlayerEntity target, int RED, int GREEN, int BLUE, int ALPHA) {
		CompoundNBT ForgeData = target.getPersistentData();
		CompoundNBT simpleNameplate = ForgeData.getCompound("SimpleNameplate");
		int ARGB = ALPHA << 24 | RED << 16 | GREEN << 8 | BLUE;
		simpleNameplate.putInt("TextColor", ARGB);
		CompoundNBT nbt = target.serializeNBT();
		for (PlayerEntity player : PlayerEvents.players) {
			PacketHandlerCommon.INSTANCE.sendTo(new SyncCompoundNBT(nbt, target.getUniqueID()), ((ServerPlayerEntity) player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
		}
		return 1;
	}
	
	private static int sendMessage(CommandContext<CommandSource> commandContext, String message) throws CommandSyntaxException {
	    TranslationTextComponent finalText = new TranslationTextComponent("chat.type.announcement",
	            commandContext.getSource().getDisplayName(), new StringTextComponent(message));

	    Entity entity = commandContext.getSource().getEntity();
	    if (entity != null) {
	      commandContext.getSource().getServer().getPlayerList().func_232641_a_(finalText, ChatType.CHAT, entity.getUniqueID());
	      //func_232641_a_ is sendMessage()
	    } else {
	      commandContext.getSource().getServer().getPlayerList().func_232641_a_(finalText, ChatType.SYSTEM, Util.DUMMY_UUID);
	    }
	    return 1;
	  }
}