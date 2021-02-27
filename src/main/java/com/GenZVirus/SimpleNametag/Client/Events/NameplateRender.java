package com.GenZVirus.SimpleNametag.Client.Events;

import java.net.URI;
import java.net.URISyntaxException;

import com.GenZVirus.SimpleNametag.SimpleNameplate;
import com.GenZVirus.SimpleNametag.Client.Render.Renderer;
import com.GenZVirus.SimpleNametag.Util.UpdateNBT;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.GuiScreenEvent.DrawScreenEvent;
import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = SimpleNameplate.MOD_ID, bus = Bus.FORGE, value = Dist.CLIENT)
public class NameplateRender {

	public static float RED, GREEN, BLUE, ALPHA;
	public static Minecraft mc = Minecraft.getInstance();
	private static ResourceLocation DISCORD = new ResourceLocation(SimpleNameplate.MOD_ID, "textures/discord.png");
	private static ResourceLocation DISCORD_BACOGROUND = new ResourceLocation(SimpleNameplate.MOD_ID, "textures/discord_background.png");

	/****************************
	 * 
	 * Cancel vanilla name rendering
	 * 
	 ****************************/

	@SuppressWarnings("resource")
	@SubscribeEvent
	public static void RenderName(RenderNameplateEvent event) {
		if (mc.currentScreen instanceof InventoryScreen) {
			return;
		}
		if (event.getEntity() instanceof FakePlayer) {
			return;
		}
		if (event.getEntity() instanceof PlayerEntity) {
			event.setResult(Result.DENY);
			CompoundNBT ForgeData = ((PlayerEntity) event.getEntity()).serializeNBT().getCompound("ForgeData");
			CompoundNBT simpleNameplate = ForgeData.getCompound("SimpleNameplate");
			Renderer.RED = simpleNameplate.getFloat("RED");
			Renderer.GREEN = simpleNameplate.getFloat("GREEN");
			Renderer.BLUE = simpleNameplate.getFloat("BLUE");
			Renderer.ALPHA = simpleNameplate.getFloat("ALPHA");
			Renderer.BCKGROUND_COLOR = simpleNameplate.getInt("BGColor");
			Renderer.TEXT_COLOR = simpleNameplate.getInt("TextColor");
			new Renderer().renderName((LivingEntity) event.getEntity(), event.getEntity().getName().getUnformattedComponentText(), event.getMatrixStack(), event.getRenderTypeBuffer(),
					event.getPackedLight());
		}

	}

	@SubscribeEvent
	@SuppressWarnings("resource")
	public static void UpdateIfSynced(ClientTickEvent event) {
		if (Minecraft.getInstance().player == null)
			return;
		if (UpdateNBT.NBTs.isEmpty())
			return;
		Minecraft.getInstance().player.world.getPlayerByUuid(UpdateNBT.uuids.get(0)).deserializeNBT(UpdateNBT.NBTs.get(0));
		UpdateNBT.NBTs.remove(0);
		UpdateNBT.uuids.remove(0);
	}

	@SubscribeEvent
	public static void MenuOptions(GuiScreenEvent.InitGuiEvent.Post event) {
		if (!ModList.get().isLoaded("betterux") && !ModList.get().isLoaded("mobplusplus") && !ModList.get().isLoaded("cursedblade")) {
			if (event.getGui() instanceof OptionsScreen && mc.world != null) {
				event.addWidget(new Button(4, mc.getMainWindow().getScaledHeight() - 30, 20, 20, new TranslationTextComponent(""), (x) -> {
					mc.displayGuiScreen(new ConfirmOpenLinkScreen(NameplateRender::confirmLink, new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/ty6gQaD").getValue(), false));

				}));
			}
		}
	}

	@SubscribeEvent
	public static void onMenuOptionsRenderPre(DrawScreenEvent.Pre event) {
		if(!(event.getGui() instanceof OptionsScreen)) return;
		if (!ModList.get().isLoaded("betterux") && !ModList.get().isLoaded("mobplusplus") && !ModList.get().isLoaded("cursedblade")) {
			DiscordBackground();
		}
	}

	@SubscribeEvent
	public static void onMenuOptionsRenderPost(DrawScreenEvent.Post event) {
		if(!(event.getGui() instanceof OptionsScreen)) return;
		if (!ModList.get().isLoaded("betterux") && !ModList.get().isLoaded("mobplusplus") && !ModList.get().isLoaded("cursedblade")) {
			DiscordIcon();
		}
	}

	public static void confirmLink(boolean p_confirmLink_1_) {
		if (p_confirmLink_1_) {
			try {
				openLink(new URI(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.com/invite/ty6gQaD").getValue()));
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		mc.displayGuiScreen((Screen) null);
	}

	private static void openLink(URI p_openLink_1_) {
		Util.getOSType().openURI(p_openLink_1_);
	}

	/****************************
	 * 
	 * Render Discord Logo and draw "GenZVirus"
	 * 
	 ****************************/

	@SuppressWarnings("deprecation")
	public static void DiscordIcon() {
		mc.getTextureManager().bindTexture(DISCORD);
		RenderSystem.scalef(0.1F, 0.1F, 0.1F);
		RenderSystem.enableBlend();
		int posX = 6;
		int posY = mc.getMainWindow().getScaledHeight() - 28;
		AbstractGui.blit(new MatrixStack(), posX * 10, posY * 10, 0, 0, 0, 160, 160, 160, 160);
		RenderSystem.disableBlend();
		RenderSystem.scalef(10.0F, 10.0F, 10.0F);
		mc.fontRenderer.drawString(new MatrixStack(), "GenZVirus", posX + 28, posY + 4, 0xFFFFFFFF);
	}

	/****************************
	 * 
	 * Render Discord background
	 * 
	 ****************************/

	public static void DiscordBackground() {
		int posX = 0;
		int posY = mc.getMainWindow().getScaledHeight() - 40;
		mc.getTextureManager().bindTexture(DISCORD_BACOGROUND);
		RenderSystem.enableBlend();
		AbstractGui.blit(new MatrixStack(), posX, posY, 0, 0, 0, 120, 40, 40, 120);
		RenderSystem.disableBlend();
	}

}
