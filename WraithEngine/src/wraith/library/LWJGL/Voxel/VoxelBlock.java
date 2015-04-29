package wraith.library.LWJGL.Voxel;

public class VoxelBlock{
	public final int x, y, z;
	public boolean xUp, xDown, yUp, yDown, zUp, zDown;
	private boolean hidden;
	public final VoxelChunk chunk;
	public final BlockType type;
	public VoxelBlock(VoxelChunk chunk, int x, int y, int z, BlockType type){
		this.x=x;
		this.y=y;
		this.z=z;
		this.chunk=chunk;
		this.type=type;
	}
	private void setHidden(boolean hidden){
		if(this.hidden==hidden)return;
		this.hidden=hidden;
		if(hidden)chunk.addHidden();
		else chunk.removeHidden();
	}
	public boolean isSideShown(int side){
		if(side==0)return xUp;
		if(side==1)return xDown;
		if(side==2)return yUp;
		if(side==3)return yDown;
		if(side==4)return zUp;
		if(side==5)return zDown;
		return false;
	}
	void showSide(int side, boolean show){
		if(side==0)xUp=show;
		if(side==1)xDown=show;
		if(side==2)yUp=show;
		if(side==3)yDown=show;
		if(side==4)zUp=show;
		if(side==5)zDown=show;
		if(!xUp&&!xDown&&!yUp&&!yDown&&!zUp&&!zDown)setHidden(true);
		else setHidden(false);
	}
	public VoxelBlock getNearbyBlock(int side, boolean load){
		if(side==0)return getBlockByOffset(1, 0, 0, load);
		if(side==1)return getBlockByOffset(-1, 0, 0, load);
		if(side==2)return getBlockByOffset(0, 1, 0, load);
		if(side==3)return getBlockByOffset(0, -1, 0, load);
		if(side==4)return getBlockByOffset(0, 0, 1, load);
		if(side==5)return getBlockByOffset(0, 0, -1, load);
		return null;
	}
	public VoxelBlock getBlockByOffset(int x, int y, int z, boolean load){ return chunk.world.getBlock(this.x+x, this.y+y, this.z+z, load); }
	public boolean isHidden(){ return hidden; }
}