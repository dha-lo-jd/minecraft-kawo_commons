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
	 * 
	 * @param processor
	 */
	public static void safetyGLProcess(Processor processor) {
		SafetyGL safetyGL = new SafetyGL();
		try {
			safetyGL.preBindedTexture2DId = GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D);
			safetyGL.setRepairPreBindedTexture2D(true);
		} catch (Exception e) {
			safetyGL.setRepairPreBindedTexture2D(false);
		}
		processor.process(safetyGL);
		safetyGL.repair();
	}

	private int pushCount = 0;

	private int preBindedTexture2DId;
	private boolean repairPreBindedTexture2D;

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

	public boolean isRepairPreBindedTexture2D() {
		return repairPreBindedTexture2D;
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

		if (repairPreBindedTexture2D) {
			try {
				repairPreBindedTexture2D();
			} catch (Exception e) {
				e.printStackTrace();// こういうコードは良くないという見本
			}
		}
	}

	/**
	 * SafetyGL処理が始まる前にバインドされていたテクスチャをバインドし直す
	 */
	public void repairPreBindedTexture2D() throws Exception {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, preBindedTexture2DId);
	}

	public void setRepairPreBindedTexture2D(boolean repairPreBindedTexture2D) {
		this.repairPreBindedTexture2D = repairPreBindedTexture2D;
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
