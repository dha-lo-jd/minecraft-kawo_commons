package org.lo.d.commons.gui;

import net.minecraft.client.renderer.texture.TextureManager;

import org.lo.d.commons.coords.Point2D;

public class GuiDrawHelper {
	private final Point2D mousePoint;
	private final float renderParticleTicks;
	private final TextureManager renderEngine;

	public GuiDrawHelper(Point2D mousePoint, float renderParticleTicks, TextureManager renderEngine) {
		this.mousePoint = mousePoint;
		this.renderParticleTicks = renderParticleTicks;
		this.renderEngine = renderEngine;
	}

	public Point2D getMousePoint() {
		return mousePoint;
	}

	public TextureManager getRenderEngine() {
		return renderEngine;
	}

	public float getRenderParticleTicks() {
		return renderParticleTicks;
	}
}
