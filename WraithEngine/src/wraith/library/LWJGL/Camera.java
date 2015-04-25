package wraith.library.LWJGL;

public class Camera extends Object3D{
	public float goalX, goalY, goalZ, goalRX, goalRY, goalRZ, goalSX, goalSY, goalSZ;
	public float cameraSpeed = 1;
	public Camera(float fov, float aspect, float near, float far){ MatrixUtils.setupPerspective(fov, aspect, near, far); }
	public Camera(float scale, float near, float far){ MatrixUtils.setupOrtho(scale, near, far); }
	public void update(float delta, long time){
		delta*=cameraSpeed;
		x=x+delta*(goalX-x);
		y=y+delta*(goalY-y);
		z=z+delta*(goalZ-z);
		rx=(((((goalRX-rx)%360)+540)%360)-180)*delta;
		ry=(((((goalRY-ry)%360)+540)%360)-180)*delta;
		rz=(((((goalRZ-rz)%360)+540)%360)-180)*delta;
		sx=sx+delta*(goalSX-sx);
		sy=sy+delta*(goalSY-sy);
		sz=sz+delta*(goalSZ-sz);
	}
}