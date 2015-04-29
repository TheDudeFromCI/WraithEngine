package wraith.library.LWJGL.Voxel;

public class VoxelWorldBounds{
	public final int startX, startY, startZ;
	public final int endX, endY, endZ;
	public final int chunkStartX, chunkStartY, chunkStartZ;
	public final int chunkEndX, chunkEndY, chunkEndZ;
	public VoxelWorldBounds(int startX, int startY, int startZ, int endX, int endY, int endZ){
		this.startX=startX;
		this.startY=startY;
		this.startZ=startZ;
		this.endX=endX;
		this.endY=endY;
		this.endZ=endZ;
		chunkStartX=startX>>4;
		chunkStartY=startY>>4;
		chunkStartZ=startZ>>4;
		chunkEndX=endX>>4;
		chunkEndY=endY>>4;
		chunkEndZ=endZ>>4;
	}
}