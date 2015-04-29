package wraith.library.LWJGL;

import wraith.library.LWJGL.Voxel.VoxelChunk;
import wraith.library.LWJGL.Voxel.VoxelWorld;
import wraith.library.MiscUtil.Plotter;
import wraith.library.MiscUtil.Vec3i;

public class CameraTarget{
	private int chunkX, chunkY, chunkZ;
	private VoxelChunk lastChunk;
	private final Camera cam;
	private final Plotter plotter = new Plotter(0, 0, 0, 1, 1, 1);
	private final Vec3i v = plotter.get();
	private final CameraTargetCallback callback = new CameraTargetCallback();
	public CameraTargetCallback getTargetBlock(VoxelWorld world, int range, boolean load){
		lastChunk=null;
		plotter.plot(cam.getPosition(), cam.getDirection(), range);
		while(plotter.next()){
			chunkX=v.x>>4;
			chunkY=v.y>>4;
			chunkZ=v.z>>4;
			if(lastChunk==null||lastChunk.chunkX!=chunkX||lastChunk.chunkY!=chunkY||lastChunk.chunkZ!=chunkZ)lastChunk=world.getChunk(chunkX, chunkY, chunkZ, load);
			if(lastChunk!=null&&(callback.block=lastChunk.getSubBlock(v.x&15, v.y&15, v.z&15))!=null){
				callback.side=plotter.getSideHit();
				return callback;
			}
		}
		callback.block=null;
		callback.side=-1;
		return callback;
	}
	public CameraTarget(Camera cam){ this.cam=cam; }
}