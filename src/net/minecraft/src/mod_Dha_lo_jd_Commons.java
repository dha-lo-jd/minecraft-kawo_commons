package net.minecraft.src;

import java.util.Map;
import java.util.logging.Level;

public class mod_Dha_lo_jd_Commons extends BaseMod {

	public static boolean enableModNamePlate = false;

	public static final MaidManager MAID_MANAGER;
	static {
		try {
			enableModNamePlate = Class
					.forName("net.minecraft.src.mod_Nameplate") != null;
		} catch (ClassNotFoundException e) {
		}
		MAID_MANAGER = new MaidManager();
	}

	public mod_Dha_lo_jd_Commons() {
	}

	@Override
	public String getVersion() {
		return "1.2.5-1";
	}

	@Override
	public void load() {

	}

	@Override
	public void addRenderer(Map map) {
	}

	private void loggingExeption(Throwable thrown) {
		ModLoader.getLogger().log(Level.INFO, thrown.getMessage(), thrown);
	}

	@Override
	public void modsLoaded() {
	}
}
