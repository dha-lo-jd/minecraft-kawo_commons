package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class NamePlatePakage implements INamePlatePakage {

	private List<EntityNameplate> list = new ArrayList<EntityNameplate>();

	/*
	 * (非 Javadoc)
	 * 
	 * @see net.minecraft.src.INamePlatePakage#refreshNamePlateList()
	 */
	@Override
	public void refreshNamePlateList() {
		this.list = new ArrayList<EntityNameplate>(mod_Nameplate.NameplateList);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see net.minecraft.src.INamePlatePakage#getNamePlate(net.minecraft.src.
	 * EntityLittleMaid)
	 */
	@Override
	public EntityNameplate getNamePlate(EntityLittleMaid maid) {
		for (EntityNameplate nameplate : list) {
			if (nameplate.entityOwner == maid) {
				return nameplate;
			}
		}
		return null;
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see
	 * net.minecraft.src.INamePlatePakage#moveWorldNamePlate(net.minecraft.src
	 * .EntityLittleMaid, net.minecraft.src.EntityLittleMaid,
	 * net.minecraft.src.World, net.minecraft.src.World,
	 * net.minecraft.src.EntityPosition)
	 */
	@Override
	public void moveWorldNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid, World prevWorld, World newWorld,
			EntityPosition ep) {
		removeNamePlateAtWorld(prevWorld, srcMaid);
		addNamePlateAtWorld(newWorld, srcMaid, distMaid, ep);
		moveNamePlate(srcMaid, distMaid);
	}

	/*
	 * (非 Javadoc)
	 * 
	 * @see net.minecraft.src.INamePlatePakage#moveNamePlate(net.minecraft.src.
	 * EntityLittleMaid, net.minecraft.src.EntityLittleMaid)
	 */
	@Override
	public void moveNamePlate(EntityLittleMaid srcMaid,
			EntityLittleMaid distMaid) {
		EntityNameplate namePlate = getNamePlate(srcMaid);
		if (namePlate != null) {
			namePlate.setOwnerEntity(distMaid);
		}
	}

	private void addNamePlateAtWorld(World world, EntityLittleMaid srcMaid,
			EntityLittleMaid entityLittleMaid, EntityPosition ep) {
		EntityNameplate np = getNamePlate(srcMaid);
		if (np == null) {
			return;
		}
		np.worldObj = world;
		ep.setPositonAndRotationToEntity(np);
		world.spawnEntityInWorld(np);
		int i = MathHelper.floor_double(np.posX / 16D);
		int j = MathHelper.floor_double(np.posZ / 16D);
		Chunk chunk = world.getChunkFromChunkCoords(i, j);
		chunk.setChunkModified();
	}

	private void removeNamePlateAtWorld(World world,
			EntityLittleMaid entityLittleMaid) {
		EntityNameplate np = getNamePlate(entityLittleMaid);
		if (np == null) {
			return;
		}
		int i1 = np.chunkCoordX;
		int k1 = np.chunkCoordZ;
		Chunk chunk = world.getChunkFromChunkCoords(i1, k1);
		chunk.removeEntity(np);
		chunk.setChunkModified();
	}

}
