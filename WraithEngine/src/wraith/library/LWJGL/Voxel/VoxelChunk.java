package wraith.library.LWJGL.Voxel;

public class VoxelChunk{
	private final VoxelBlock[][][] blocks = new VoxelBlock[8][8][8];
	public final int chunkX, chunkY, chunkZ;
	private int hidden = 512;
	public VoxelChunk(int chunkX, int chunkY, int chunkZ){
		this.chunkX=chunkX;
		this.chunkY=chunkY;
		this.chunkZ=chunkZ;
	}
	public void createBlock(int x, int y, int z, BlockRenderer blockRenderer){
		VoxelBlock block = new VoxelBlock(this, x, y, z, blockRenderer);
		blocks[block.chunkX][block.chunkY][block.chunkZ]=block;
		removeHidden();
	}
	public void update(float delta, long time){
		if(isHidden())return;
		int x, y, z;
		for(x=0; x<8; x++){
			for(y=0; y<8; y++){
				for(z=0; z<8; z++){
					if(blocks[x][y][z]==null)continue;
					blocks[x][y][z].update(delta, time);
				}
			}
		}
	}
	public void render(){
		if(isHidden())return;
		int x, y, z;
		for(x=0; x<8; x++){
			for(y=0; y<8; y++){
				for(z=0; z<8; z++){
					if(blocks[x][y][z]==null)continue;
					blocks[x][y][z].render();
				}
			}
		}
	}
	public void addHidden(){ hidden++; }
	public void removeHidden(){ hidden--; }
	public VoxelBlock getBlock(int x, int y, int z){ return blocks[x&7][y&7][z&7]; }
	public boolean isHidden(){ return hidden==512; }
}