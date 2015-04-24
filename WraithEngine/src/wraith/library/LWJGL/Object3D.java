package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public abstract class Object3D{
	public float x, y, z, rx, ry, rz, sx=1, sy=1, sz=1;
	public void translateMatrix(){
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(rx, 1, 0, 0);
		GL11.glRotatef(ry, 0, 1, 0);
		GL11.glRotatef(rz, 0, 0, 1);
		GL11.glScalef(sx, sy, sz);
	}
	public void translateInvertMatrix(){
		GL11.glRotatef(-rx, 1, 0, 0);
		GL11.glRotatef(-ry, 0, 1, 0);
		GL11.glRotatef(-rz, 0, 0, 1);
		GL11.glTranslatef(-x, -y, -z);
		GL11.glScalef(sx, sy, sz);
	}
	public abstract void update(float delta, long time);
}