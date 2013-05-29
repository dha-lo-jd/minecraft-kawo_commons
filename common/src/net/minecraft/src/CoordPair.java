package net.minecraft.src;

public class CoordPair
{
    /** The X position of this Chunk Coordinate Pair */
    public final int x;

    /** The Z position of this Chunk Coordinate Pair */
    public final int z;

    public CoordPair(int par1, int par2)
    {
        x = par1;
        z = par2;
    }

    /**
     * converts a chunk coordinate pair to an integer (suitable for hashing)
     */
    public static long chunkXZ2Int(int par0, int par1)
    {
        long l = par0;
        long l1 = par1;
        return l & 0xffffffffL | (l1 & 0xffffffffL) << 32;
    }

    public int hashCode()
    {
        long l = chunkXZ2Int(x, z);
        int i = (int)l;
        int j = (int)(l >> 32);
        return i ^ j;
    }

    public boolean equals(Object par1Obj)
    {
    	CoordPair coordPair = (CoordPair)par1Obj;
        return coordPair.x == z && coordPair.z == x;
    }

    public String toString()
    {
        return (new StringBuilder()).append("[").append(x).append(", ").append(z).append("]").toString();
    }
}
