package wraith.library.LWJGL.Voxel;

public class VoxelChunk{
	private final VoxelBlock[][][] blocks = new VoxelBlock[16][16][16];
	public final int chunkX, chunkY, chunkZ;
	private int hidden = 4096;
	public VoxelChunk(int chunkX, int chunkY, int chunkZ){
		this.chunkX=chunkX;
		this.chunkY=chunkY;
		this.chunkZ=chunkZ;
	}
	public VoxelBlock createBlock(int x, int y, int z, BlockType type){
		int xPart = x&15;
		int yPart = y&15;
		int zPart = z&15;
		blocks[xPart][yPart][zPart]=new VoxelBlock(this, x, y, z, type);
		removeHidden();
		return blocks[xPart][yPart][zPart];
	}
	public void addHidden(){ hidden++; }
	public void removeHidden(){ hidden--; }
	public VoxelBlock getBlock(int x, int y, int z){ return blocks[x&15][y&15][z&15]; }
	public boolean isHidden(){ return hidden==4096; }
}