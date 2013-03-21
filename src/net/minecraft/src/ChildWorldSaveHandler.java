package net.minecraft.src;

import java.io.File;
import java.io.FileInputStream;

public abstract class ChildWorldSaveHandler<WI extends WorldInfo> extends
		SaveHandler {

	@Override
	public IChunkLoader getChunkLoader(WorldProvider par1WorldProvider) {
		File file = getSaveDirectory();
		return new AnvilChunkLoader(file);
	}

	public ChildWorldSaveHandler(SaveHandler parentSaveHandler, String par2Str) {
		super(parentSaveHandler.getSaveDirectory(), par2Str, false);
	}

	abstract protected WI createInstance(NBTTagCompound nbttagcompound);

	@Override
	public WI loadWorldInfo() {
		File saveDirectory = getSaveDirectory();
		File file = new File(saveDirectory, "level.dat");

		if (file.exists()) {
			try {
				NBTTagCompound nbttagcompound = CompressedStreamTools
						.readCompressed(new FileInputStream(file));
				NBTTagCompound nbttagcompound2 = nbttagcompound
						.getCompoundTag("Data");
				return createInstance(nbttagcompound2);
			} catch (Exception exception) {
				exception.printStackTrace();
			}
		}

		file = new File(saveDirectory, "level.dat_old");

		if (file.exists()) {
			try {
				NBTTagCompound nbttagcompound1 = CompressedStreamTools
						.readCompressed(new FileInputStream(file));
				NBTTagCompound nbttagcompound3 = nbttagcompound1
						.getCompoundTag("Data");
				return createInstance(nbttagcompound3);
			} catch (Exception exception1) {
				exception1.printStackTrace();
			}
		}

		return null;
	}

}
