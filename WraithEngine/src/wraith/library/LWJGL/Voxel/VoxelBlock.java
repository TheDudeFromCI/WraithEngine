package wraith.library.LWJGL.Voxel;

public class VoxelBlock{
	public final int x, y, z;
	public final int chunkX, chunkY, chunkZ;
	private boolean hidden;
	private VoxelChunk chunk;
	private BlockRenderer cube;
	public VoxelBlock(VoxelChunk chunk, int x, int y, int z, BlockRenderer blockRenderer){
		this.x=x;
		this.y=y;
		this.z=z;
		chunkX=x&7;
		chunkY=y&7;
		chunkZ=z&7;
		this.chunk=chunk;
		cube=blockRenderer;
		cube.x=x;
		cube.y=y;
		cube.z=z;
	}
	public void setHidden(boolean hidden){
		if(this.hidden==hidden)return;
		this.hidden=hidden;
		if(hidden)chunk.addHidden();
		else chunk.removeHidden();
	}
	public void render(){
		if(hidden)return;
		cube.renderObject();
	}
	public void hideSide(boolean xUp, boolean xDown, boolean yUp, boolean yDown, boolean zUp, boolean zDown){
		if(!xUp&&!xDown&&!yUp&&!yDown&&!zUp&&!zDown)setHidden(true);
		cube.renderXUp=xUp;
		cube.renderXDown=xDown;
		cube.renderYUp=yUp;
		cube.renderYDown=yDown;
		cube.renderZUp=zUp;
		cube.renderZDown=zDown;
	}
	public void update(float delta, long time){ cube.update(delta, time); }
	public boolean isHidden(){ return hidden; }
	public BlockRenderer getCube(){ return cube; }
	public VoxelChunk getChunk(){ return chunk; }
}