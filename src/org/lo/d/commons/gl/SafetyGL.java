package org.lo.d.commons.gl;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.renderer.RenderHelper;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

/**
 * ある程度安全にOpenGLの座標マトリックスやフラグ操作を扱えるようにする
 *
 * @author dha_lo_jd
 *
 */
public class SafetyGL {

	public interface Command {
		public void repair();
	}

	public interface Processor {
		public void process(SafetyGL safetyGL);
	}

	/**
	 * Processorのprocess実装内で行った、SafetyGL引数経由の操作をProcessorのprocess実行終了後にすべてもとに戻す
	 * @param processor
	 */
	public static void safetyGLProcess(Processor processor) {
		SafetyGL safetyGL = new SafetyGL();
		processor.process(safetyGL);
		safetyGL.repair();
	}

	private int pushCount = 0;

	private final Map<Integer, Boolean> states = Maps.newHashMap();

	private Command itemLightingState = null;

	public void disable(int cap) {
		save(cap);
		GL11.glDisable(cap);
	}

	/**
	 * GUI上のアイテム描画時のライティング設定
	 */
	public void disableStandardItemLighting() {
		if (itemLightingState == null) {
			itemLightingState = new Command() {
				@Override
				public void repair() {
					RenderHelper.enableStandardItemLighting();
				}
			};
		}
		RenderHelper.disableStandardItemLighting();
	}

	public void enable(int cap) {
		save(cap);
		GL11.glEnable(cap);
	}

	public void enableStandardItemLighting() {
		if (itemLightingState == null) {
			itemLightingState = new Command() {
				@Override
				public void repair() {
					RenderHelper.disableStandardItemLighting();
				}
			};
		}
		RenderHelper.enableStandardItemLighting();
	}

	public void pushMatrix() {
		GL11.glPushMatrix();
		pushCount++;
	}

	/**
	 * このSafetyGLインスタンス経由でプッシュしたマトリックスや変更したフラグを一括でもとに戻す
	 */
	public void repair() {
		for (Entry<Integer, Boolean> entry : states.entrySet()) {
			set(entry.getKey(), entry.getValue());
		}
		states.clear();
		if (itemLightingState != null) {
			itemLightingState.repair();
			itemLightingState = null;
		}

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
