package com.GenZVirus.SimpleNametag.Client.Render;

import java.util.List;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Renderer {

	public static float RED = 1.0F;
	public static float GREEN = 1.0F;
	public static float BLUE = 1.0F;
	public static float ALPHA = 1.0F;
	public static int BCKGROUND_COLOR = 553648127;
	public static int TEXT_COLOR = -1;
	public static boolean BORDER = true;

	public Renderer() {
	}

	/****************************
	 * 
	 * Entity name renderer
	 * 
	 ****************************/

	public void renderName(LivingEntity entityIn, String displayNameIn, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		double d0 = renderManager.squareDistanceTo(entityIn);
		boolean isDiscrete = entityIn.isDiscrete();
		if (!(d0 > 4096.0D)) {
			float f = entityIn.getHeight() + 0.6F;
			int i = 0;
			matrixStackIn.push();

			/****************************
			 * 
			 * Set entity name's position and orientation
			 * 
			 ****************************/

			matrixStackIn.translate(0.0D, (double) f, 0.0D);
			matrixStackIn.rotate(renderManager.getCameraOrientation());
			matrixStackIn.scale(-0.025F, -0.025F, 0.025F);
			Matrix4f matrix4f = matrixStackIn.getLast().getMatrix();

			/****************************
			 * 
			 * Render Entity Name
			 * 
			 ****************************/

			FontRenderer fontrenderer = renderManager.getFontRenderer();
			float f2 = (float) (-fontrenderer.getStringWidth(displayNameIn) / 2);
			if (!isDiscrete)
				renderString(displayNameIn, f2, (float) i, TEXT_COLOR, false, matrix4f, bufferIn, false, BCKGROUND_COLOR, packedLightIn);
			matrixStackIn.pop();
		}
	}

	public static int renderString(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean transparentIn, int colorBackgroundIn,
			int packedLight) {
		return renderStringAt(text, x, y, color, dropShadow, matrix, buffer, transparentIn, colorBackgroundIn, packedLight);
	}

	private static int renderStringAt(String text, float x, float y, int color, boolean dropShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean transparentIn, int colorBackgroundIn,
			int packedLight) {
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		FontRenderer fontrenderer = renderManager.getFontRenderer();
		boolean bidiFlag = fontrenderer.getBidiFlag();
		if (bidiFlag) {
			text = fontrenderer.bidiReorder(text);
		}

		if ((color & -67108864) == 0) {
			color |= -16777216;
		}

		if (dropShadow) {
			renderStringAtPos(text, x, y, color, true, matrix, buffer, transparentIn, colorBackgroundIn, packedLight);
		}

		Matrix4f matrix4f = matrix.copy();
		matrix4f.translate(new Vector3f(0.0F, 0.0F, 0.001F));
		x = renderStringAtPos(text, x, y, color, false, matrix4f, buffer, transparentIn, colorBackgroundIn, packedLight);
		return (int) x + (dropShadow ? 1 : 0);
	}

	private static float renderStringAtPos(String text, float x, float y, int color, boolean isShadow, Matrix4f matrix, IRenderTypeBuffer buffer, boolean isTransparent, int colorBackgroundIn,
			int packedLight) {
		float f = isShadow ? 0.25F : 1.0F;
		float f1 = (float) (color >> 16 & 255) / 255.0F * f;
		float f2 = (float) (color >> 8 & 255) / 255.0F * f;
		float f3 = (float) (color & 255) / 255.0F * f;
		float f4 = x;
		float redIn = f1;
		float greenIn = f2;
		float blueIn = f3;
		float alphaIn = (float) (color >> 24 & 255) / 255.0F;
		boolean flag = false;
		boolean boldIn = false;
		boolean italicIn = false;
		boolean flag3 = false;
		boolean flag4 = false;
		List<Effect> list = Lists.newArrayList();
		EntityRendererManager renderManager = Minecraft.getInstance().getRenderManager();
		FontRenderer fontrenderer = renderManager.getFontRenderer();

		float offset = x;

		for (int i = 0; i < text.length(); ++i) {
			char c0 = text.charAt(i);
			if (c0 == 167 && i + 1 < text.length()) {
				++i;
			} else {
				IGlyph iglyph = fontrenderer.getFont(Style.EMPTY.getFontId()).func_238557_a_(c0);
				float f15 = iglyph.getAdvance(boldIn);
				offset += f15;
			}
		}

		if (colorBackgroundIn != 0) {
			float f11 = (float) (colorBackgroundIn >> 24 & 255) / 255.0F;
			float f12 = (float) (colorBackgroundIn >> 16 & 255) / 255.0F;
			float f13 = (float) (colorBackgroundIn >> 8 & 255) / 255.0F;
			float f14 = (float) (colorBackgroundIn & 255) / 255.0F;
			TexturedGlyph texturedglyph1 = fontrenderer.getFont(Style.EMPTY.getFontId()).getWhiteGlyph();
			IVertexBuilder ivertexbuilder1 = buffer.getBuffer(texturedglyph1.getRenderType(isTransparent));
			renderEffect(texturedglyph1, new Effect(x - 1.0F, y + 9.0F, offset + 1.0F, y - 1.0F, 0.01F, f12, f13, f14, f11), matrix, ivertexbuilder1, packedLight);

			if (BORDER) {
				// ADD BORDER

				// horizontal up
				list.add(new Effect(x - 1.0F, y - 0.5F, offset + 1.0F, y - 1.0F, 0.009F, RED, GREEN, BLUE, ALPHA));
				// horizontal down
				list.add(new Effect(x - 1.0F, y + 9.0F, offset + 1.0F, y + 8.5F, 0.009F, RED, GREEN, BLUE, ALPHA));
				// vertical up
				list.add(new Effect(x - 1.0F, y + 9.0F, x - 0.5F, y - 1.0F, 0.009F, RED, GREEN, BLUE, ALPHA));
				// vertical down
				list.add(new Effect(offset + 0.5F, y + 9.0F, offset + 1.0F, y - 1.0F, 0.009F, RED, GREEN, BLUE, ALPHA));
			}
		}

		for (int i = 0; i < text.length(); ++i) {
			char c0 = text.charAt(i);
			if (c0 == 167 && i + 1 < text.length()) {
				TextFormatting textformatting = TextFormatting.fromFormattingCode(text.charAt(i + 1));
				if (textformatting != null) {
					if (!textformatting.isFancyStyling()) {
						flag = false;
						boldIn = false;
						flag4 = false;
						flag3 = false;
						italicIn = false;
						redIn = f1;
						greenIn = f2;
						blueIn = f3;
					}

					if (textformatting.getColor() != null) {
						int j = textformatting.getColor();
						redIn = (float) (j >> 16 & 255) / 255.0F * f;
						greenIn = (float) (j >> 8 & 255) / 255.0F * f;
						blueIn = (float) (j & 255) / 255.0F * f;
					} else if (textformatting == TextFormatting.OBFUSCATED) {
						flag = true;
					} else if (textformatting == TextFormatting.BOLD) {
						boldIn = true;
					} else if (textformatting == TextFormatting.STRIKETHROUGH) {
						flag4 = true;
					} else if (textformatting == TextFormatting.UNDERLINE) {
						flag3 = true;
					} else if (textformatting == TextFormatting.ITALIC) {
						italicIn = true;
					}
				}

				++i;
			} else {
				IGlyph iglyph = fontrenderer.getFont(Style.EMPTY.getFontId()).func_238557_a_(c0);
				TexturedGlyph texturedglyph = flag && c0 != ' ' ? fontrenderer.getFont(Style.EMPTY.getFontId()).obfuscate(iglyph) : fontrenderer.getFont(Style.EMPTY.getFontId()).func_238559_b_(c0);
				if (!(texturedglyph instanceof EmptyGlyph)) {
					float f9 = boldIn ? iglyph.getBoldOffset() : 0.0F;
					float f10 = isShadow ? iglyph.getShadowOffset() : 0.0F;
					IVertexBuilder ivertexbuilder = buffer.getBuffer(texturedglyph.getRenderType(isTransparent));
					float xIn = f4 + f10;
					float yIn = y + f10;
					texturedglyph.render(italicIn, xIn, yIn, matrix, ivertexbuilder, redIn, greenIn, blueIn, alphaIn, packedLight);
					if (boldIn) {
						texturedglyph.render(italicIn, xIn + f9, yIn, matrix, ivertexbuilder, redIn, greenIn, blueIn, alphaIn, packedLight);
					}
				}

				float f15 = iglyph.getAdvance(boldIn);
				float f16 = isShadow ? 1.0F : 0.0F;
				if (flag4) {
					list.add(new Effect(f4 + f16 - 1.0F, y + f16 + 4.5F, f4 + f16 + f15, y + f16 + 4.5F - 1.0F, -0.01F, redIn, greenIn, blueIn, alphaIn));
				}

				if (flag3) {
					list.add(new Effect(f4 + f16 - 1.0F, y + f16 + 9.0F, f4 + f16 + f15, y + f16 + 9.0F - 1.0F, -0.01F, redIn, greenIn, blueIn, alphaIn));
				}

				f4 += f15;
			}
		}

		if (!list.isEmpty()) {
			TexturedGlyph texturedglyph1 = fontrenderer.getFont(Style.EMPTY.getFontId()).getWhiteGlyph();
			IVertexBuilder ivertexbuilder1 = buffer.getBuffer(texturedglyph1.getRenderType(isTransparent));
			for (Effect texturedglyph$effect : list) {
				renderEffect(texturedglyph1, texturedglyph$effect, matrix, ivertexbuilder1, packedLight);
			}
		}

		return f4;
	}

	public static void renderEffect(TexturedGlyph glyph, Effect effectIn, Matrix4f matrixIn, IVertexBuilder bufferIn, int packedLightIn) {
		bufferIn.pos(matrixIn, effectIn.x0, effectIn.y0, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u0, glyph.v0).lightmap(packedLightIn).endVertex();
		bufferIn.pos(matrixIn, effectIn.x1, effectIn.y0, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u0, glyph.v1).lightmap(packedLightIn).endVertex();
		bufferIn.pos(matrixIn, effectIn.x1, effectIn.y1, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u1, glyph.v1).lightmap(packedLightIn).endVertex();
		bufferIn.pos(matrixIn, effectIn.x0, effectIn.y1, effectIn.depth).color(effectIn.r, effectIn.g, effectIn.b, effectIn.a).tex(glyph.u1, glyph.v0).lightmap(packedLightIn).endVertex();
	}

	@OnlyIn(Dist.CLIENT)
	public static class Effect {
		protected final float x0;
		protected final float y0;
		protected final float x1;
		protected final float y1;
		protected final float depth;
		protected final float r;
		protected final float g;
		protected final float b;
		protected final float a;

		public Effect(float p_i225923_1_, float p_i225923_2_, float p_i225923_3_, float p_i225923_4_, float p_i225923_5_, float p_i225923_6_, float p_i225923_7_, float p_i225923_8_,
				float p_i225923_9_) {
			this.x0 = p_i225923_1_;
			this.y0 = p_i225923_2_;
			this.x1 = p_i225923_3_;
			this.y1 = p_i225923_4_;
			this.depth = p_i225923_5_;
			this.r = p_i225923_6_;
			this.g = p_i225923_7_;
			this.b = p_i225923_8_;
			this.a = p_i225923_9_;
		}
	}
}
