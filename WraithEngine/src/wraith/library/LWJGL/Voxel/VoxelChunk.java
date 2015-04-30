package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;
import wraith.library.LWJGL.Texture;

public class VoxelChunk{
	private boolean open;
	private int hidden = 4096;
	private final VoxelBlock[][][] blocks = new VoxelBlock[16][16][16];
	public final int chunkX, chunkY, chunkZ;
	public final int startX, startY, startZ, endX, endY, endZ;
	final ArrayList<QuadBatch> batches = new ArrayList(1);
	public final VoxelWorld world;
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
	public VoxelBlock createBlock(int x, int y, int z, BlockType type){
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
		world.setNeedsRebatch();
	}
	public void optimizeSide(int side){
		if(side==0){
			int y, z;
			for(y=0; y<16; y++)for(z=0; z<16; z++)optimizeBlock(blocks[15][y][z], 0, true);
		}
		if(side==1){
			int y, z;
			for(y=0; y<16; y++)for(z=0; z<16; z++)optimizeBlock(blocks[0][y][z], 1, true);
		}
		if(side==2){
			int x, z;
			for(x=0; x<16; x++)for(z=0; z<16; z++)optimizeBlock(blocks[x][15][z], 2, true);
		}
		if(side==3){
			int x, z;
			for(x=0; x<16; x++)for(z=0; z<16; z++)optimizeBlock(blocks[x][0][z], 3, true);
		}
		if(side==4){
			int x, y;
			for(x=0; x<16; x++)for(y=0; y<16; y++)optimizeBlock(blocks[x][y][15], 4, true);
		}
		if(side==5){
			int x, y;
			for(x=0; x<16; x++)for(y=0; y<16; y++)optimizeBlock(blocks[x][y][0], 5, true);
		}
		world.setNeedsRebatch();
	}
	public void optimizeBlock(VoxelBlock block){
		if(block==null)return;
		optimizeBlock(block, 0, true);
		optimizeBlock(block, 1, true);
		optimizeBlock(block, 2, true);
		optimizeBlock(block, 3, true);
		optimizeBlock(block, 4, true);
		optimizeBlock(block, 5, true);
	}
	public void optimizeBlock(VoxelBlock block, int side, boolean updateShadows){
		if(block==null)return;
		open=block.chunk.isNeighborOpen(block, side);
		if(open!=block.isSideShown(side)){
			block.showSide(side, open);
			if(open)block.chunk.getBatch(block.type.getTexture(side)).addQuad(block.getQuad(side));
			else block.chunk.getBatch(block.type.getTexture(side)).removeQuad(block.getQuad(side));
			world.setNeedsRebatch();
		}
		if(updateShadows)block.quads[side].centerPoint=block.type.setupShadows(block.quads[side].colors, side, block.x, block.y, block.z);
	}
	private void optimizeAroundBlock(int x, int y, int z){
		int startX = x-1;
		int startY = y-1;
		int startZ = z-1;
		int endX = x+1;
		int endY = y+1;
		int endZ = z+1;
		int sx, sy, sz;
		for(sx=startX; sx<=endX; sx++){
			for(sy=startY; sy<=endY; sy++){
				for(sz=startZ; sz<=endZ; sz++){
					if(sx==x&&sy==y&&sz==z)continue;
					optimizeBlock(getQuickBlock(sx, sy, sz));
				}
			}
		}
	}
	private void removeBlock(int x, int y, int z){
		if(blocks[x][y][z]==null)return;
		removeBlockQuads(blocks[x][y][z]);
		blocks[x][y][z]=null;
	}
	public VoxelBlock setBlock(int x, int y, int z, BlockType type){
		world.setNeedsRebatch();
		removeBlock(x&15, y&15, z&15);
		if(type!=null){
			VoxelBlock block = createBlock(x, y, z, type);
			optimizeBlock(block);
			optimizeAroundBlock(x, y, z);
			return block;
		}
		optimizeAroundBlock(x, y, z);
		return null;
	}
	private boolean isNeighborOpen(VoxelBlock block, int side){
		if(side==0)return block.x<world.bounds.endX&&getQuickBlock(block.x+1, block.y, block.z)==null;
		if(side==1)return block.x>world.bounds.startX&&getQuickBlock(block.x-1, block.y, block.z)==null;
		if(side==2)return block.y<world.bounds.endY&&getQuickBlock(block.x, block.y+1, block.z)==null;
		if(side==3)return block.y>world.bounds.startY&&getQuickBlock(block.x, block.y-1, block.z)==null;
		if(side==4)return block.z<world.bounds.endZ&&getQuickBlock(block.x, block.y, block.z+1)==null;
		if(side==5)return block.z>world.bounds.startZ&&getQuickBlock(block.x, block.y, block.z-1)==null;
		return true;
	}
	private QuadBatch getBatch(Texture texture){
		QuadBatch batch;
		for(int i = 0; i<batches.size(); i++){
			batch=batches.get(i);
			if(batch.getTexture()==texture)return batch;
		}
		batch=new QuadBatch();
		batch.setTexture(texture);
		batches.add(batch);
		return batch;
	}
	private VoxelBlock getQuickBlock(int x, int y, int z){
		if(x<startX||y<startY||z<startZ||x>endX||y>endY||z>endZ)return world.getBlock(x, y, z, false);
		return getBlock(x, y, z);
	}
	void addHidden(){ hidden++; }
	void removeHidden(){ hidden--; }
	public VoxelBlock getBlock(int x, int y, int z){ return blocks[x&15][y&15][z&15]; }
	public VoxelBlock getSubBlock(int x, int y, int z){ return blocks[x][y][z]; }
	public boolean isHidden(){ return hidden==4096; }
	private void removeBlockQuads(VoxelBlock block){ for(int i = 0; i<6; i++)getBatch(block.type.getTexture(i)).removeQuad(block.getQuad(i)); }
}