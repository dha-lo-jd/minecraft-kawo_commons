package net.minecraft.src;


import java.util.HashSet;
import java.util.Set;

public class TagCompoundSupport {

	public interface TagReader<T> {
		T read(String name, NBTTagCompound nbttagcompound);
	}

	public interface TagWriter<T> {
		void write(String name, T value, NBTTagCompound nbttagcompound);
	}

	public static NBTTagCompound getChunkCoordinatesTag(
			ChunkCoordinates coordinates) {
		NBTTagCompound tag = new NBTTagCompound();
		writeChunkCoordinates(tag, coordinates);
		return tag;
	}

	public static NBTTagCompound getVec3DTag(Vec3D vec3d) {
		NBTTagCompound tag = new NBTTagCompound();
		TagCompoundSupport.writeTagVec3D(tag, vec3d);
		return tag;
	}

	public static ChunkCoordinates readChunkCoordinates(
			NBTTagCompound nbttagcompound) {
		int x = nbttagcompound.getInteger("x");
		int y = nbttagcompound.getInteger("y");
		int z = nbttagcompound.getInteger("z");
		return new ChunkCoordinates(x, y, z);
	}

	public static <T> Set<T> readSet(NBTTagCompound nbttagcompound,
			TagReader<T> reader) {

		Set<T> set = new HashSet<T>();

		int length = nbttagcompound.getInteger("length");
		for (int i = 0; i < length; i++) {
			T e = reader.read(String.valueOf(i), nbttagcompound);
			set.add(e);
		}

		return set;
	}

	public static Vec3D readTagVec3D(NBTTagCompound nbttagcompound) {
		double x = nbttagcompound.getDouble("x");
		double y = nbttagcompound.getDouble("y");
		double z = nbttagcompound.getDouble("z");
		return Vec3D.createVectorHelper(x, y, z);
	}

	public static void writeChunkCoordinates(NBTTagCompound nbttagcompound,
			ChunkCoordinates coordinates) {
		nbttagcompound.setInteger("x", coordinates.posX);
		nbttagcompound.setInteger("y", coordinates.posY);
		nbttagcompound.setInteger("z", coordinates.posZ);
	}

	public static void writeChunkCoordinatesTa(NBTTagCompound nbttagcompound,
			String name, ChunkCoordinates coordinates) {
		nbttagcompound.setTag(name, getChunkCoordinatesTag(coordinates));
	}

	public static <T> void writeSet(Set<T> set, NBTTagCompound nbttagcompound,
			TagWriter<T> writer) {

		nbttagcompound.setInteger("length", set.size());
		int i = 0;
		for (T t : set) {
			writer.write(String.valueOf(i), t, nbttagcompound);
			i++;
		}
	}

	public static void writeTagVec3D(NBTTagCompound nbttagcompound, Vec3D vec3d) {
		nbttagcompound.setDouble("x", vec3d.xCoord);
		nbttagcompound.setDouble("y", vec3d.yCoord);
		nbttagcompound.setDouble("z", vec3d.zCoord);
	}

	public static void writeVec3DTag(NBTTagCompound nbttagcompound,
			String name, Vec3D vec3d) {
		nbttagcompound.setTag(name, getVec3DTag(vec3d));
	}
}
