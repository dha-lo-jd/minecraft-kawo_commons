package net.minecraft.src;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class RenderHasRenderer extends Render {
	protected ModelBase mainModel;

	/** The model to be used during the render passes. */
	protected ModelBase renderPassModel;

	public RenderHasRenderer(ModelBase par1ModelBase, float par2) {
		mainModel = par1ModelBase;
		shadowSize = par2;
	}

	/**
	 * Sets the model to be used in the current render pass (the first render
	 * pass is done after the primary model is rendered) Args: model
	 */
	public void setRenderPassModel(ModelBase par1ModelBase) {
		renderPassModel = par1ModelBase;
	}

	private float func_48418_a(float par1, float par2, float par3) {
		float f;

		for (f = par2 - par1; f < -180F; f += 360F) {
		}

		for (; f >= 180F; f -= 360F) {
		}

		return par1 + par3 * f;
	}

	public void doRenderLiving(EntityHasRenderer par1EntityHasRenderer,
			double par2, double par4, double par6, float par8, float par9) {
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);

		if (renderPassModel != null) {
			renderPassModel.onGround = mainModel.onGround;
		}

		mainModel.isRiding = par1EntityHasRenderer.isRiding();

		if (renderPassModel != null) {
			renderPassModel.isRiding = mainModel.isRiding;
		}

		if (renderPassModel != null) {
			renderPassModel.isChild = mainModel.isChild;
		}

		try {
			float f = func_48418_a(par1EntityHasRenderer.prevRenderYawOffset,
					par1EntityHasRenderer.renderYawOffset, par9);
			float f1 = func_48418_a(par1EntityHasRenderer.prevRotationYawHead,
					par1EntityHasRenderer.rotationYawHead, par9);
			float f2 = par1EntityHasRenderer.prevRotationPitch
					+ (par1EntityHasRenderer.rotationPitch - par1EntityHasRenderer.prevRotationPitch)
					* par9;
			renderLivingAt(par1EntityHasRenderer, par2, par4, par6);
			float f3 = handleRotationFloat(par1EntityHasRenderer, par9);
			GL11.glRotatef(180F - f, 0.0F, 1.0F, 0.0F);
			float f4 = 0.0625F;
			GL11.glEnable(GL12.GL_RESCALE_NORMAL);
			GL11.glScalef(-1F, -1F, 1.0F);
			preRenderCallback(par1EntityHasRenderer, par9);
			GL11.glTranslatef(0.0F, -24F * f4 - 0.0078125F, 0.0F);
			float f5 = par1EntityHasRenderer.field_705_Q
					+ (par1EntityHasRenderer.field_704_R - par1EntityHasRenderer.field_705_Q)
					* par9;
			float f6 = par1EntityHasRenderer.field_703_S
					- par1EntityHasRenderer.field_704_R * (1.0F - par9);

			if (f5 > 1.0F) {
				f5 = 1.0F;
			}

			GL11.glEnable(GL11.GL_ALPHA_TEST);
			renderModel(par1EntityHasRenderer, f6, f5, f3, f1 - f, f2, f4);

			for (int i = 0; i < 4; i++) {
				int j = shouldRenderPass(par1EntityHasRenderer, i, par9);

				if (j <= 0) {
					continue;
				}

				renderPassModel.render(par1EntityHasRenderer, f6, f5, f3, f1
						- f, f2, f4);

				if (j == 15) {
					float f8 = (float) par1EntityHasRenderer.ticksExisted
							+ par9;
					loadTexture("%blur%/misc/glint.png");
					GL11.glEnable(GL11.GL_BLEND);
					float f10 = 0.5F;
					GL11.glColor4f(f10, f10, f10, 1.0F);
					GL11.glDepthFunc(GL11.GL_EQUAL);
					GL11.glDepthMask(false);

					for (int i1 = 0; i1 < 2; i1++) {
						GL11.glDisable(GL11.GL_LIGHTING);
						float f13 = 0.76F;
						GL11.glColor4f(0.5F * f13, 0.25F * f13, 0.8F * f13,
								1.0F);
						GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
						GL11.glMatrixMode(GL11.GL_TEXTURE);
						GL11.glLoadIdentity();
						float f15 = f8 * (0.001F + (float) i1 * 0.003F) * 20F;
						float f16 = 0.3333333F;
						GL11.glScalef(f16, f16, f16);
						GL11.glRotatef(30F - (float) i1 * 60F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(0.0F, f15, 0.0F);
						GL11.glMatrixMode(GL11.GL_MODELVIEW);
						renderPassModel.render(par1EntityHasRenderer, f6, f5,
								f3, f1 - f, f2, f4);
					}

					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glDepthMask(true);
					GL11.glLoadIdentity();
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					GL11.glEnable(GL11.GL_LIGHTING);
					GL11.glDisable(GL11.GL_BLEND);
					GL11.glDepthFunc(GL11.GL_LEQUAL);
				}

				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
			}

			renderEquippedItems(par1EntityHasRenderer, par9);
			float f7 = par1EntityHasRenderer.getBrightness(par9);
			int k = getColorMultiplier(par1EntityHasRenderer, f7, par9);
			OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

			if ((k >> 24 & 0xff) > 0) {
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDepthFunc(GL11.GL_EQUAL);

				if ((k >> 24 & 0xff) > 0) {
					float f9 = (float) (k >> 16 & 0xff) / 255F;
					float f11 = (float) (k >> 8 & 0xff) / 255F;
					float f12 = (float) (k & 0xff) / 255F;
					float f14 = (float) (k >> 24 & 0xff) / 255F;
					GL11.glColor4f(f9, f11, f12, f14);
					mainModel.render(par1EntityHasRenderer, f6, f5, f3, f1 - f,
							f2, f4);

					for (int j1 = 0; j1 < 4; j1++) {
						if (inheritRenderPass(par1EntityHasRenderer, j1, par9) >= 0) {
							GL11.glColor4f(f9, f11, f12, f14);
							renderPassModel.render(par1EntityHasRenderer, f6,
									f5, f3, f1 - f, f2, f4);
						}
					}
				}

				GL11.glDepthFunc(GL11.GL_LEQUAL);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glEnable(GL11.GL_ALPHA_TEST);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
			}

			GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
		passSpecialRender(par1EntityHasRenderer, par2, par4, par6);
	}

	/**
	 * Renders the model in RenderLiving
	 */
	protected void renderModel(EntityHasRenderer par1EntityHasRenderer,
			float par2, float par3, float par4, float par5, float par6,
			float par7) {
		loadDownloadableImageTexture(par1EntityHasRenderer.skinUrl,
				par1EntityHasRenderer.getTexture());
		mainModel.render(par1EntityHasRenderer, par2, par3, par4, par5, par6,
				par7);
	}

	/**
	 * Sets a simple glTranslate on a LivingEntity.
	 */
	protected void renderLivingAt(EntityHasRenderer par1EntityHasRenderer,
			double par2, double par4, double par6) {
		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	protected float handleRotationFloat(
			EntityHasRenderer par1EntityHasRenderer, float par2) {
		return (float) par1EntityHasRenderer.ticksExisted + par2;
	}

	protected void renderEquippedItems(EntityHasRenderer EntityHasRenderer,
			float f) {
	}

	protected int inheritRenderPass(EntityHasRenderer par1EntityHasRenderer,
			int par2, float par3) {
		return shouldRenderPass(par1EntityHasRenderer, par2, par3);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	protected int shouldRenderPass(EntityHasRenderer par1EntityHasRenderer,
			int par2, float par3) {
		return -1;
	}

	protected float getDeathMaxRotation(EntityHasRenderer par1EntityHasRenderer) {
		return 90F;
	}

	/**
	 * Returns an ARGB int color back. Args: EntityHasRenderer, lightBrightness,
	 * partialTickTime
	 */
	protected int getColorMultiplier(EntityHasRenderer par1EntityHasRenderer,
			float par2, float par3) {
		return 0;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before
	 * the model is rendered. Args: EntityHasRenderer, partialTickTime
	 */
	protected void preRenderCallback(EntityHasRenderer EntityHasRenderer,
			float f) {
	}

	/**
	 * Passes the specialRender and renders it
	 */
	protected void passSpecialRender(EntityHasRenderer par1EntityHasRenderer,
			double par2, double par4, double par6) {
		if (!Minecraft.isDebugInfoEnabled())
			;
	}

	/**
	 * Draws the debug or playername text above a living
	 */
	protected void renderLivingLabel(EntityHasRenderer par1EntityHasRenderer,
			String par2Str, double par3, double par5, double par7, int par9) {
		float f = par1EntityHasRenderer
				.getDistanceToEntity(renderManager.livingPlayer);

		if (f > (float) par9) {
			return;
		}

		FontRenderer fontrenderer = getFontRendererFromRenderManager();
		float f1 = 1.6F;
		float f2 = 0.01666667F * f1;
		GL11.glPushMatrix();
		GL11.glTranslatef((float) par3 + 0.0F, (float) par5 + 2.3F,
				(float) par7);
		GL11.glNormal3f(0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(-f2, -f2, f2);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDepthMask(false);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		Tessellator tessellator = Tessellator.instance;
		byte byte0 = 0;

		if (par2Str.equals("deadmau5")) {
			byte0 = -10;
		}

		GL11.glDisable(GL11.GL_TEXTURE_2D);
		tessellator.startDrawingQuads();
		int i = fontrenderer.getStringWidth(par2Str) / 2;
		tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
		tessellator.addVertex(-i - 1, -1 + byte0, 0.0D);
		tessellator.addVertex(-i - 1, 8 + byte0, 0.0D);
		tessellator.addVertex(i + 1, 8 + byte0, 0.0D);
		tessellator.addVertex(i + 1, -1 + byte0, 0.0D);
		tessellator.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		fontrenderer.drawString(par2Str,
				-fontrenderer.getStringWidth(par2Str) / 2, byte0, 0x20ffffff);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		fontrenderer.drawString(par2Str,
				-fontrenderer.getStringWidth(par2Str) / 2, byte0, -1);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glPopMatrix();
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method,
	 * always casting down its argument and then handing it off to a worker
	 * function which does the actual work. In all probabilty, the class Render
	 * is generic (Render<T extends Entity) and this method has signature public
	 * void doRender(T entity, double d, double d1, double d2, float f, float
	 * f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4,
			double par6, float par8, float par9) {
		doRenderLiving((EntityHasRenderer) par1Entity, par2, par4, par6, par8,
				par9);
	}
}
