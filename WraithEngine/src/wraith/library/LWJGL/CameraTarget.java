package wraith.library.LWJGL;

import wraith.library.LWJGL.Voxel.VoxelBlock;
import wraith.library.LWJGL.Voxel.VoxelChunk;
import wraith.library.LWJGL.Voxel.VoxelWorld;
import wraith.library.MiscUtil.Plotter;
import wraith.library.MiscUtil.Vec3i;

public class CameraTarget{
	private int chunkX, chunkY, chunkZ;
	private VoxelBlock block;
	private VoxelChunk lastChunk;
	private final Camera cam;
	private final Plotter plotter = new Plotter(0, 0, 0, 1, 1, 1);
	private final Vec3i v = plotter.get();
	public VoxelBlock getTargetBlock(VoxelWorld world, int range, boolean load){
		lastChunk=null;
		plotter.plot(cam.getPosition(), cam.getDirection(), range);
		while(plotter.next()){
			chunkX=v.x>>4;
			chunkY=v.y>>4;
			chunkZ=v.z>>4;
			if(lastChunk==null||lastChunk.chunkX!=chunkX||lastChunk.chunkY!=chunkY||lastChunk.chunkZ!=chunkZ)lastChunk=world.getChunk(chunkX, chunkY, chunkZ, load);
			if(lastChunk!=null&&(block=lastChunk.getSubBlock(v.x&15, v.y&15, v.z&15))!=null)return block;
		}
		return null;
	}
	public CameraTarget(Camera cam){ this.cam=cam; }
}