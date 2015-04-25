package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;
import wraith.library.LWJGL.Cube;
import wraith.library.LWJGL.Texture;

public class VoxelWorld{
	private final ArrayList<VoxelChunk> chunks = new ArrayList();
	private ArrayList<QuadBatch> batches = new ArrayList(1);
	private VoxelChunk loadChunk(int chunkX, int chunkY, int chunkZ){
		VoxelChunk chunk = new VoxelChunk(chunkX, chunkY, chunkZ);
		chunks.add(chunk);
		return chunk;
	}
	public void unloadChunk(int chunkX, int chunkY, int chunkZ){
		VoxelChunk c = getChunk(chunkX, chunkY, chunkZ, false);
		if(c==null)return;
		chunks.remove(c);
	}
	private VoxelChunk getChunk(int chunkX, int chunkY, int chunkZ, boolean load){
		if(chunkY<0)return null;
		VoxelChunk c;
		for(int i = 0; i<chunks.size(); i++){
			c=chunks.get(i);
			if(c.chunkX==chunkX&&c.chunkY==chunkY&&c.chunkZ==chunkZ)return c;
		}
		return load?loadChunk(chunkX, chunkY, chunkZ):null;
	}
	public VoxelBlock getBlock(int x, int y, int z){
		if(y<0)return null;
		return getContainingChunk(x, y, z).getBlock(x, y, z);
	}
	public VoxelBlock createBlock(int x, int y, int z, BlockType type){
		if(y<0)return null;
		return getContainingChunk(x, y, z).createBlock(x, y, z, type);
	}
	public void optimizeBlocks(int startX, int startY, int startZ, int endX, int endY, int endZ){
		int x, y, z;
		VoxelBlock block;
		for(x=startX; x<=endX; x++){
			for(y=startY; y<=endY; y++){
				for(z=startZ; z<=endZ; z++){
					block=getBlock(x, y, z);
					if(block!=null){
						block.hideSide(
							getBlock(x+1, y, z)==null,
							getBlock(x-1, y, z)==null,
							getBlock(x, y+1, z)==null,
							y>0&&getBlock(x, y-1, z)==null,
							getBlock(x, y, z+1)==null,
							getBlock(x, y, z-1)==null
						);
						optimizeBlock(block);
					}
				}
			}
		}
	}
	private void optimizeBlock(VoxelBlock block){
		if(block.isHidden())return;
		if(block.xUp)getBatch(block.getType().getTexture(0)).addQuad(Cube.generateQuad(Cube.X_UP_SIDE, block.x, block.y, block.z));
		if(block.xDown)getBatch(block.getType().getTexture(1)).addQuad(Cube.generateQuad(Cube.X_DOWN_SIDE, block.x, block.y, block.z));
		if(block.yUp)getBatch(block.getType().getTexture(2)).addQuad(Cube.generateQuad(Cube.Y_UP_SIDE, block.x, block.y, block.z));
		if(block.yDown)getBatch(block.getType().getTexture(3)).addQuad(Cube.generateQuad(Cube.Y_DOWN_SIDE, block.x, block.y, block.z));
		if(block.zUp)getBatch(block.getType().getTexture(4)).addQuad(Cube.generateQuad(Cube.Z_UP_SIDE, block.x, block.y, block.z));
		if(block.zDown)getBatch(block.getType().getTexture(5)).addQuad(Cube.generateQuad(Cube.Z_DOWN_SIDE, block.x, block.y, block.z));
	}
	private QuadBatch getBatch(Texture texture){
		for(QuadBatch batch : batches)if(batch.getTexture()==texture)return batch;
		QuadBatch batch = new QuadBatch();
		batch.setTexture(texture);
		batches.add(batch);
		return batch;
	}
	public VoxelChunk getContainingChunk(int x, int y, int z){ return getChunk(x>>4, y>>4, z>>4, true); }
	public VoxelChunk getChunk(int chunkX, int chunkY, int chunkZ){ return getChunk(chunkX, chunkY, chunkZ, true); }
	public void render(){ for(QuadBatch batch : batches)batch.renderBatch(); }
	public int getChunkCount(){ return chunks.size(); }
}