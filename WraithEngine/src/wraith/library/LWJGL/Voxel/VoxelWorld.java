package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;
import java.util.Comparator;
import org.lwjgl.opengl.GL11;
import wraith.library.LWJGL.Texture;

public class VoxelWorld{
	private boolean needsRebatch;
	private final ArrayList<QuadBatch> tempQuads = new ArrayList();
	private final ArrayList<VoxelChunk> chunks = new ArrayList();
	private final VoxelWorldListener worldListener;
	public VoxelChunk loadChunk(int chunkX, int chunkY, int chunkZ){
		VoxelChunk chunk = new VoxelChunk(this, chunkX, chunkY, chunkZ);
		chunks.add(chunk);
		worldListener.loadChunk(chunk);
		setNeedsRebatch();
		return chunk;
	}
	public void unloadChunk(int chunkX, int chunkY, int chunkZ){
		VoxelChunk c = getChunk(chunkX, chunkY, chunkZ, false);
		if(c==null)return;
		chunks.remove(c);
		worldListener.unloadChunk(c);
		setNeedsRebatch();
	}
	public void unloadChunk(VoxelChunk chunk){
		chunks.remove(chunk);
		worldListener.unloadChunk(chunk);
	}
	public VoxelChunk getChunk(int chunkX, int chunkY, int chunkZ, boolean load){
		VoxelChunk c;
		for(int i = 0; i<chunks.size(); i++){
			c=chunks.get(i);
			if(c.chunkX==chunkX&&c.chunkY==chunkY&&c.chunkZ==chunkZ)return c;
		}
		return load?loadChunk(chunkX, chunkY, chunkZ):null;
	}
	public VoxelBlock getBlock(int x, int y, int z, boolean load){
		VoxelChunk c = getContainingChunk(x, y, z, load);
		if(c==null)return null;
		return c.getBlock(x, y, z);
	}
	public void render(){
		if(needsRebatch){
			tempQuads.clear();
			for(VoxelChunk chunk : chunks)if(worldListener.isChunkVisible(chunk))tempQuads.addAll(chunk.batches);
			tempQuads.sort(new Comparator<QuadBatch>(){
				public int compare(QuadBatch a, QuadBatch b){ return a.getTexture()==b.getTexture()?0:a.getTexture().getId()>b.getTexture().getId()?1:-1; }
			});
			needsRebatch=false;
		}
		Texture bound = null;
		for(QuadBatch batch : tempQuads){
			if(bound!=batch.getTexture()){
				if(bound!=null)GL11.glEnd();
				bound=batch.getTexture();
				bound.bind();
				GL11.glBegin(GL11.GL_TRIANGLES);
			}
			batch.renderPart();
		}
		GL11.glEnd();
	}
	public VoxelWorld(VoxelWorldListener worldListener){ this.worldListener=worldListener; }
	public VoxelChunk getContainingChunk(int x, int y, int z){ return getChunk(x>>4, y>>4, z>>4, true); }
	public VoxelChunk getContainingChunk(int x, int y, int z, boolean load){ return getChunk(x>>4, y>>4, z>>4, load); }
	public VoxelChunk getChunk(int chunkX, int chunkY, int chunkZ){ return getChunk(chunkX, chunkY, chunkZ, true); }
	public int getChunkCount(){ return chunks.size(); }
	public void optimizeAll(boolean negativeY){ for(int i = 0; i<chunks.size(); i++)chunks.get(i).optimize(negativeY); }
	public VoxelChunk getChunk(int index){ return chunks.get(index); }
	public VoxelBlock getBlock(int x, int y, int z){ return getContainingChunk(x, y, z).getBlock(x, y, z); }
	public VoxelBlock createBlock(int x, int y, int z, BlockType type){ return getContainingChunk(x, y, z).createBlock(x, y, z, type); }
	public void setNeedsRebatch(){ needsRebatch=true; }
}