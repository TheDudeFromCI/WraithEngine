package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;

public class VoxelWorld{
	private final ArrayList<VoxelChunk> chunks = new ArrayList();
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
	public VoxelChunk getContainingChunk(int x, int y, int z){ return getChunk(x>>3, y>>3, z>>3, true); }
	public VoxelChunk getChunk(int chunkX, int chunkY, int chunkZ){ return getChunk(chunkX, chunkY, chunkZ, true); }
	public void update(float delta, long time){ for(int i = 0; i<chunks.size(); i++)chunks.get(i).update(delta, time); }
	public void render(){ for(int i = 0; i<chunks.size(); i++)chunks.get(i).render(); }
}