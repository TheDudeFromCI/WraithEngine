package wraith.library.LWJGL;

import com.sun.javafx.geom.Vec3f;

public class Camera extends Object3D{
	public float goalX, goalY, goalZ, goalRX, goalRY, goalRZ, goalSX=1, goalSY=1, goalSZ=1;
	public float cameraSpeed = 1;
	public final Frustum frustum = new Frustum();
	private final Vec3f direction = new Vec3f();
	private final Vec3f position = new Vec3f();
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
}