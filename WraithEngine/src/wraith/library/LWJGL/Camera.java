package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public class Camera extends Object3D{
	private float fov, aspect, near, far;
	public Camera(float fov, float aspect, float near, float far){
		this.fov=fov;
		this.aspect=aspect;
		this.near=near;
		this.far=far;
		init();
	}
	public void init(){
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		double fov = Math.tan(Math.toRadians(this.fov))/2;
		GL11.glFrustum(-aspect*near*fov, aspect*near*fov, -fov, fov, near, far);
	}
	public void translateMatrix(){
		GL11.glRotatef(pitch, 1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);
		GL11.glRotatef(roll, 0, 0, 1);
		GL11.glTranslatef(x, y, z);
		GL11.glScalef(xSize, ySize, zSize);
	}
}