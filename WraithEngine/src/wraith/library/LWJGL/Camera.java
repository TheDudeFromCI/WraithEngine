package wraith.library.LWJGL;

public class Camera extends Object3D{
	public float goalX, goalY, goalZ, goalRX, goalRY, goalRZ, goalSX=1, goalSY=1, goalSZ=1;
	public float cameraSpeed = 1;
	public final Frustum frustum = new Frustum();
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
}