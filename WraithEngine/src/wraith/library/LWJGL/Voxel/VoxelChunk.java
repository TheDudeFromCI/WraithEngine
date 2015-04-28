package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;
import wraith.library.LWJGL.Cube;
import wraith.library.LWJGL.Texture;

public class VoxelChunk{
	private boolean open;
	private int hidden = 4096;
	private final VoxelBlock[][][] blocks = new VoxelBlock[16][16][16];
	public final int chunkX, chunkY, chunkZ;
	public final int startX, startY, startZ, endX, endY, endZ;
	public final ArrayList<QuadBatch> batches = new ArrayList(1);
	private final VoxelWorld world;
	public VoxelChunk(VoxelWorld world, int chunkX, int chunkY, int chunkZ){
		this.world=world;
		this.chunkX=chunkX;
		this.chunkY=chunkY;
		this.chunkZ=chunkZ;
		startX=chunkX*16;
		startY=chunkY*16;
		startZ=chunkZ*16;
		endX=startX+15;
		endY=startY+15;
		endZ=startZ+15;
	}
	private VoxelBlock createBlock(int x, int y, int z, BlockType type){
		int xPart = x&15;
		int yPart = y&15;
		int zPart = z&15;
		blocks[xPart][yPart][zPart]=new VoxelBlock(this, x, y, z, type);
		removeHidden();
		return blocks[xPart][yPart][zPart];
	}
	public void optimize(){
		int x, y, z;
		for(x=0; x<16; x++)for(y=0; y<16; y++)for(z=0; z<16; z++)optimizeBlock(blocks[x][y][z]);
	}
	public void optimizeSide(int side){
		if(side==0){
			int y, z;
			for(y=0; y<16; y++)for(z=0; z<16; z++)optimizeBlock(blocks[15][y][z], 0);
		}
		if(side==1){
			int y, z;
			for(y=0; y<16; y++)for(z=0; z<16; z++)optimizeBlock(blocks[0][y][z], 1);
		}
		if(side==2){
			int x, z;
			for(x=0; x<16; x++)for(z=0; z<16; z++)optimizeBlock(blocks[x][15][z], 2);
		}
		if(side==3){
			int x, z;
			for(x=0; x<16; x++)for(z=0; z<16; z++)optimizeBlock(blocks[x][0][z], 3);
		}
		if(side==4){
			int x, y;
			for(x=0; x<16; x++)for(y=0; y<16; y++)optimizeBlock(blocks[x][y][15], 4);
		}
		if(side==5){
			int x, y;
			for(x=0; x<16; x++)for(y=0; y<16; y++)optimizeBlock(blocks[x][y][0], 5);
		}
	}
	public void optimizeBlock(VoxelBlock block){
		optimizeBlock(block, 0);
		optimizeBlock(block, 1);
		optimizeBlock(block, 2);
		optimizeBlock(block, 3);
		optimizeBlock(block, 4);
		optimizeBlock(block, 5);
	}
	private void optimizeBlock(VoxelBlock block, int side){
		if(block==null)return;
		open=isNeighborOpen(block, side);
		if(open!=block.isSideShown(side)){
			block.showSide(side, open);
			if(open)getBatch(block.getType().getTexture(side)).addQuad(Cube.generateQuad(side, block.x, block.y, block.z, block.getType().getRotation(side)));
			else getBatch(block.getType().getTexture(side)).removeQuad(Cube.generateQuad(side, block.x, block.y, block.z, block.getType().getRotation(side)));
		}
	}
	private void removeBlock(int x, int y, int z){
		if(blocks[x][y][z]==null)return;
		removeBlockQuads(blocks[x][y][z]);
		blocks[x][y][z]=null;
		VoxelChunk chunk;
		if(x>0)optimizeBlock(blocks[x-1][y][z], 0);
		else{
			chunk=world.getChunk(chunkX-1, chunkY, chunkZ, false);
			if(chunk!=null)chunk.optimizeBlock(chunk.blocks[15][y][z], 0);
		}
		if(x<15)optimizeBlock(blocks[x+1][y][z], 1);
		else{
			chunk=world.getChunk(chunkX+1, chunkY, chunkZ, false);
			if(chunk!=null)chunk.optimizeBlock(chunk.blocks[0][y][z], 1);
		}
		if(y>0)optimizeBlock(blocks[x][y-1][z], 2);
		else{
			chunk=world.getChunk(chunkX, chunkY-1, chunkZ, false);
			if(chunk!=null)chunk.optimizeBlock(chunk.blocks[x][15][z], 2);
		}
		if(y<15)optimizeBlock(blocks[x][y+1][z], 3);
		else{
			chunk=world.getChunk(chunkX, chunkY+1, chunkZ, false);
			if(chunk!=null)chunk.optimizeBlock(chunk.blocks[x][0][z], 3);
		}
		if(z>0)optimizeBlock(blocks[x][y][z-1], 4);
		else{
			chunk=world.getChunk(chunkX, chunkY, chunkZ-1, false);
			if(chunk!=null)chunk.optimizeBlock(chunk.blocks[x][y][15], 4);
		}
		if(z<15)optimizeBlock(blocks[x][y][z+1], 5);
		else{
			chunk=world.getChunk(chunkX, chunkY, chunkZ+1, false);
			if(chunk!=null)chunk.optimizeBlock(chunk.blocks[x][y][0], 5);
		}
	}
	public VoxelBlock setBlock(int x, int y, int z, BlockType type){
		removeBlock(x&15, y&15, z&15);
		if(type!=null)return createBlock(x, y, z, type);
		return null;
	}
	private boolean isNeighborOpen(VoxelBlock block, int side){
		if(side==0)return getQuickBlock(block.x+1, block.y, block.z)==null;
		if(side==1)return getQuickBlock(block.x-1, block.y, block.z)==null;
		if(side==2)return getQuickBlock(block.x, block.y+1, block.z)==null;
		if(side==3)return (world.hidesNegativeY()?block.y>0:true)&&getQuickBlock(block.x, block.y-1, block.z)==null;
		if(side==4)return getQuickBlock(block.x, block.y, block.z+1)==null;
		if(side==5)return getQuickBlock(block.x, block.y, block.z-1)==null;
		return true;
	}
	private QuadBatch getBatch(Texture texture){
		for(QuadBatch batch : batches)if(batch.getTexture()==texture)return batch;
		QuadBatch batch = new QuadBatch();
		batch.setTexture(texture);
		batches.add(batch);
		world.setNeedsRebatch();
		return batch;
	}
	private VoxelBlock getQuickBlock(int x, int y, int z){
		if(x<startX||y<startY||z<startZ||x>endX||y>endY||z>endZ)return world.getBlock(x, y, z, false);
		return getBlock(x, y, z);
	}
	public void addHidden(){ hidden++; }
	public void removeHidden(){ hidden--; }
	public VoxelBlock getBlock(int x, int y, int z){ return blocks[x&15][y&15][z&15]; }
	public VoxelBlock getSubBlock(int x, int y, int z){ return blocks[x][y][z]; }
	public boolean isHidden(){ return hidden==4096; }
	public void render(){ for(QuadBatch batch : batches)batch.renderBatch(); }
	private void removeBlockQuads(VoxelBlock block){ for(int i = 0; i<6; i++)getBatch(block.getType().getTexture(i)).removeQuad(Cube.generateQuad(i, block.x, block.y, block.z, block.getType().getRotation(i))); }
}