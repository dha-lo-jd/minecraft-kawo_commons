package org.lo.d.commons.gl;

import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

public class SafetyGL {

	public interface Processor {
		public void work(SafetyGL safetyGL);
	}

	public static void safetyGLProcess(Processor processor) {
		SafetyGL safetyGL = new SafetyGL();
		processor.work(safetyGL);
		safetyGL.repair();
	}

	private int pushCount = 0;

	private final Map<Integer, Boolean> states = Maps.newHashMap();

	public void disable(int cap) {
		save(cap);
		GL11.glDisable(cap);
	}

	public void disableStandardItemLighting() {
		disable(GL11.GL_LIGHTING);
		disable(GL11.GL_LIGHT0);
		disable(GL11.GL_LIGHT1);
		disable(GL11.GL_COLOR_MATERIAL);
	}

	public void enable(int cap) {
		save(cap);
		GL11.glEnable(cap);
	}

	public void pushMatrix() {
		GL11.glPushMatrix();
		pushCount++;
	}

	public void repair() {
		for (Entry<Integer, Boolean> entry : states.entrySet()) {
			set(entry.getKey(), entry.getValue());
		}
		states.clear();

		for (int i = 0; i < pushCount; i++) {
			GL11.glPopMatrix();
		}
	}

	private void save(int cap) {
		if (states.containsKey(cap)) {
			return;
		}
		states.put(cap, GL11.glIsEnabled(cap));
	}

	private void set(int cap, boolean flag) {
		if (flag) {
			GL11.glEnable(cap);
		} else {
			GL11.glDisable(cap);
		}
	}
}
