package net.minecraft.src;

import java.io.File;
import java.util.List;

import net.minecraft.client.AnvilConverterException;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.ISaveHandler;
import net.minecraft.world.storage.SaveFormatComparator;
import net.minecraft.world.storage.SaveHandler;

public class FileStrageSupport {

	public static File getTopLevelWorldSaveDirectory(ISaveHandler saveHandler) {
		if (saveHandler instanceof SaveHandler) {
			return getTopLevelWorldSaveDirectory((SaveHandler) saveHandler);
		}
		return null;
	}

	public static File getTopLevelWorldSaveDirectory(SaveHandler saveHandler) {
		File dir = saveHandler.getWorldDirectory();
		Minecraft mc = ModLoader.getMinecraftInstance();
		File baseSaveDirectory = new File(Minecraft.getMinecraftDir(), "saves");
		ISaveFormat isaveformat = mc.getSaveLoader();
		List<SaveFormatComparator> saveList;
		try {
			saveList = isaveformat.getSaveList();
		} catch (AnvilConverterException e) {
			e.printStackTrace();
			return null;
		}
		for (SaveFormatComparator save : saveList) {
			File file = new File(baseSaveDirectory, save.getFileName());
			if (dir.getAbsolutePath().startsWith(file.getAbsolutePath())) {
				return file;
			}
		}
		return null;
	}

	public static File getTopLevelWorldSaveDirectory(World world) {
		if (world == null) {
			return null;
		}
		return getTopLevelWorldSaveDirectory(world.getSaveHandler());
	}

	// public static File getWorldSaveDirectory(WorldInfo worldInfo) {
	// String worldName = worldInfo.getWorldName();
	// Minecraft mc = ModLoader.getMinecraftInstance();
	// File saveDirectory = new File(Minecraft.getMinecraftDir(), "saves");
	// return saveDirectory;
	// }

	public static ISaveHandler getWorldSaveHandler(String saveDirName) {
		Minecraft mc = ModLoader.getMinecraftInstance();
		ISaveFormat isaveformat = mc.getSaveLoader();
		List<SaveFormatComparator> saveList;
		try {
			saveList = isaveformat.getSaveList();
			for (SaveFormatComparator save : saveList) {
				if (save.getFileName().equals(saveDirName)) {
					return isaveformat.getSaveLoader(save.getFileName(), false);
				}
			}
		} catch (AnvilConverterException e) {
			e.printStackTrace();
		}
		throw new RuntimeException();
	}
}
