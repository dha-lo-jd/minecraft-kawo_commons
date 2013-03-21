package net.minecraft.src;

import java.util.logging.Level;

public class LogSupport {

	public static void logging(Object... args) {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (Object object : args) {
			sb.append(sep);
			sb.append(object);
			sep = ", ";
		}
		ModLoader.getLogger().log(Level.INFO, sb.toString());
	}

}
