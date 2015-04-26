package wraith.library.LWJGL.Voxel;

public class VoxelBlock{
	public final int x, y, z;
	public boolean xUp, xDown, yUp, yDown, zUp, zDown;
	private boolean hidden;
	private VoxelChunk chunk;
	private BlockType type;
	public VoxelBlock(VoxelChunk chunk, int x, int y, int z, BlockType type){
		this.x=x;
		this.y=y;
		this.z=z;
		this.chunk=chunk;
		this.type=type;
	}
	public void setHidden(boolean hidden){
		if(this.hidden==hidden)return;
		this.hidden=hidden;
		if(hidden)chunk.addHidden();
		else chunk.removeHidden();
	}
	public void hideSide(boolean xUp, boolean xDown, boolean yUp, boolean yDown, boolean zUp, boolean zDown){
		if(!xUp&&!xDown&&!yUp&&!yDown&&!zUp&&!zDown)setHidden(true);
		else setHidden(false);
		this.xUp=xUp;
		this.xDown=xDown;
		this.yUp=yUp;
		this.yDown=yDown;
		this.zUp=zUp;
		this.zDown=zDown;
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
	public void showSide(int side, boolean show){
		if(side==0)xUp=show;
		if(side==1)xDown=show;
		if(side==2)yUp=show;
		if(side==3)yDown=show;
		if(side==4)zUp=show;
		if(side==5)zDown=show;
		if(!xUp&&!xDown&&!yUp&&!yDown&&!zUp&&!zDown)setHidden(true);
		else setHidden(false);
	}
	public boolean isHidden(){ return hidden; }
	public BlockType getType(){ return type; }
	public VoxelChunk getChunk(){ return chunk; }
}