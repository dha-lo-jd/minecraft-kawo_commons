package net.minecraft.src;

import java.io.File;

import net.minecraft.client.Minecraft;

public class FileStrageManager {

	public static class WorldSaveProxy extends WorldSavedData {

		private final FileStrageManager proxy;

		public WorldSaveProxy(FileStrageManager proxy) {
			super("WorldSaveProxy");
			this.proxy = proxy;
		}

		@Override
		public void readFromNBT(NBTTagCompound nbttagcompound) {
			proxy.onLoadWorldSavedData();
		}

		@Override
		public void writeToNBT(NBTTagCompound nbttagcompound) {
			proxy.onSaveWorldSavedData();
		}
	}

	private void onLoadWorldSavedData() {
		Minecraft mc = ModLoader.getMinecraftInstance();
		World world = mc.theWorld;

	}

	private void onSaveWorldSavedData() {
		Minecraft mc = ModLoader.getMinecraftInstance();
		World world = mc.theWorld;
		File dir = FileStrageSupport.getTopLevelWorldSaveDirectory(world);

	}

}
