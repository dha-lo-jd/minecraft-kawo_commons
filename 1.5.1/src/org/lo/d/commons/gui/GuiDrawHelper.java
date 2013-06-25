package org.lo.d.commons.gui;

import net.minecraft.client.renderer.RenderEngine;

import org.lo.d.commons.coords.Point2D;

public class GuiDrawHelper {
	private final Point2D mousePoint;
	private final float renderParticleTicks;
	private final RenderEngine renderEngine;

	public GuiDrawHelper(Point2D mousePoint, float renderParticleTicks, RenderEngine renderEngine) {
		this.mousePoint = mousePoint;
		this.renderParticleTicks = renderParticleTicks;
		this.renderEngine = renderEngine;
	}

	public Point2D getMousePoint() {
		return mousePoint;
	}

	public RenderEngine getRenderEngine() {
		return renderEngine;
	}

	public float getRenderParticleTicks() {
		return renderParticleTicks;
	}
}
