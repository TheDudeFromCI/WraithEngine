package wraith.library.LWJGL.Voxel;

public class VoxelWorldBounds{
	public final int startX, startY, startZ;
	public final int endX, endY, endZ;
	public VoxelWorldBounds(int startX, int startY, int startZ, int endX, int endY, int endZ){
		this.startX=startX;
		this.startY=startY;
		this.startZ=startZ;
		this.endX=endX;
		this.endY=endY;
		this.endZ=endZ;
	}
}