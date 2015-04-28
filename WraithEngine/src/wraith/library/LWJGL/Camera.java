package wraith.library.LWJGL;

import com.sun.javafx.geom.Vec3f;
import wraith.library.LWJGL.Voxel.VoxelBlock;
import wraith.library.LWJGL.Voxel.VoxelChunk;
import wraith.library.LWJGL.Voxel.VoxelWorld;
import wraith.library.MiscUtil.Plotter;
import wraith.library.MiscUtil.Vec3i;

public class Camera extends Object3D{
	public float goalX, goalY, goalZ, goalRX, goalRY, goalRZ, goalSX=1, goalSY=1, goalSZ=1;
	public float cameraSpeed = 1;
	private Vec3f direction = new Vec3f();
	private Vec3f position = new Vec3f();
	public final Frustum frustum = new Frustum();
	private final Plotter plotter = new Plotter(0, 0, 0, 1, 1, 1);
	public Camera(float fov, float aspect, float near, float far, boolean ortho){
		if(ortho)MatrixUtils.setupOrtho(fov, aspect, near, far);
		else MatrixUtils.setupPerspective(fov, aspect, near, far);
	}
	public void update(float delta, long time){
		delta*=cameraSpeed;
		x+=delta*(goalX-x);
		y+=delta*(goalY-y);
		z+=delta*(goalZ-z);
		rx+=(((((goalRX-rx)%360)+540)%360)-180)*delta;
		ry+=(((((goalRY-ry)%360)+540)%360)-180)*delta;
		rz+=(((((goalRZ-rz)%360)+540)%360)-180)*delta;
		sx+=delta*(goalSX-sx);
		sy+=delta*(goalSY-sy);
		sz+=delta*(goalSZ-sz);
		translateInvertMatrix();
		frustum.calculateFrustum();
	}
	public Vec3f getDirection(){
		direction.x=(float)(Math.cos(Math.toRadians(ry-90))*Math.cos(Math.toRadians(-rx)));
		direction.y=(float)Math.sin(Math.toRadians(-rx));
		direction.z=(float)(Math.sin(Math.toRadians(ry-90))*Math.cos(Math.toRadians(-rx)));
		return direction;
	}
	public Vec3f getPosition(){
		position.x=x;
		position.y=y;
		position.z=z;
		return position;
	}
	public VoxelBlock getTargetBlock(VoxelWorld world, int range, boolean load){
		VoxelBlock block;
		VoxelChunk lastChunk = null;
		plotter.plot(getPosition(), getDirection(), range);
		Vec3i v;
		int chunkX, chunkY, chunkZ;
		while(plotter.next()){
			v=plotter.get();
			chunkX=v.x>>4;
			chunkY=v.y>>4;
			chunkZ=v.z>>4;
			if(lastChunk==null||lastChunk.chunkX!=chunkX||lastChunk.chunkY!=chunkY||lastChunk.chunkZ!=chunkZ)lastChunk=world.getChunk(chunkX, chunkY, chunkZ, load);
			if(lastChunk!=null&&(block=lastChunk.getSubBlock(v.x&15, v.y&15, v.z&15))!=null)return block;
		}
		return null;
	}
}