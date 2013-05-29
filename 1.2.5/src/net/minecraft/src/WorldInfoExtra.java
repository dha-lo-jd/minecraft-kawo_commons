package net.minecraft.src;

import java.util.List;

public abstract class WorldInfoExtra extends WorldInfo {

	public WorldInfoExtra(NBTTagCompound par1nbtTagCompound) {
		super(par1nbtTagCompound);
		readExtraNBTTagCompound(par1nbtTagCompound);
	}

	public WorldInfoExtra(WorldInfo worldInfo) {
		super(worldInfo);
	}

	@Override
	public NBTTagCompound getNBTTagCompound() {
		return writeExtraNBTTagCompound(super.getNBTTagCompound());
	}

	@Override
	public NBTTagCompound getNBTTagCompoundWithPlayers(List par1List) {
		return writeExtraNBTTagCompound(super
				.getNBTTagCompoundWithPlayers(par1List));
	}

	abstract protected NBTTagCompound writeExtraNBTTagCompound(
			NBTTagCompound tagCompound);

	abstract protected void readExtraNBTTagCompound(NBTTagCompound tagCompound);
}
