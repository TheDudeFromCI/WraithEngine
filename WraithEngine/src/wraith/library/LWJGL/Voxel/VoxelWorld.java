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
	public VoxelBlock createBlock(int x, int y, int z, BlockRenderer blockRenderer){
		if(y<0)return null;
		VoxelChunk chunk = getContainingChunk(x, y, z);
		chunk.createBlock(x, y, z, blockRenderer);
		return chunk.getBlock(x, y, z);
	}
	public void optimizeBlocks(int startX, int startY, int startZ, int endX, int endY, int endZ){
		int x, y, z;
		VoxelBlock block;
		for(x=startX; x<endX; x++){
			for(y=startY; y<endY; y++){
				for(z=startZ; z<endZ; z++){
					block=getBlock(x, y, z);
					if(block!=null){
						block.hideSide(
							getBlock(x+1, y, z)==null,
							getBlock(x-1, y, z)==null,
							getBlock(x, y+1, z)==null,
							y==0||getBlock(x, y-1, z)==null,
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
		if(block.getCube().renderXUp)getBatch(block.getCube().textures.xUp).addQuad(block.getCube().generateQuad(Cube.X_UP_SIDE));
		if(block.getCube().renderXDown)getBatch(block.getCube().textures.xDown).addQuad(block.getCube().generateQuad(Cube.X_DOWN_SIDE));
		if(block.getCube().renderYUp)getBatch(block.getCube().textures.yUp).addQuad(block.getCube().generateQuad(Cube.Y_UP_SIDE));
		if(block.getCube().renderYDown)getBatch(block.getCube().textures.yDown).addQuad(block.getCube().generateQuad(Cube.Y_DOWN_SIDE));
		if(block.getCube().renderZUp)getBatch(block.getCube().textures.zUp).addQuad(block.getCube().generateQuad(Cube.Z_UP_SIDE));
		if(block.getCube().renderZDown)getBatch(block.getCube().textures.zDown).addQuad(block.getCube().generateQuad(Cube.Z_DOWN_SIDE));
	}
	private QuadBatch getBatch(Texture texture){
		for(QuadBatch batch : batches)if(batch.getTexture()==texture)return batch;
		QuadBatch batch = new QuadBatch();
		batch.setTexture(texture);
		batches.add(batch);
		return batch;
	}
	public VoxelChunk getContainingChunk(int x, int y, int z){ return getChunk(x>>3, y>>3, z>>3, true); }
	public VoxelChunk getChunk(int chunkX, int chunkY, int chunkZ){ return getChunk(chunkX, chunkY, chunkZ, true); }
	public void update(float delta, long time){ for(int i = 0; i<chunks.size(); i++)chunks.get(i).update(delta, time); }
	public void render(){
//		for(int i = 0; i<chunks.size(); i++)chunks.get(i).render();
		for(QuadBatch batch : batches)batch.renderBatch();
	}
}