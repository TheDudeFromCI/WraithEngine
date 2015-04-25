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
	public boolean isHidden(){ return hidden; }
	public BlockType getType(){ return type; }
	public VoxelChunk getChunk(){ return chunk; }
}