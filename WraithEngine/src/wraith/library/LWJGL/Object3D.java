package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public abstract class Object3D{
	public float x, y, z, pitch, yaw, roll, xSize=1, ySize=1, zSize=1;
	public void translateMatrix(){
		GL11.glTranslatef(x, y, z);
		GL11.glRotatef(pitch, 1, 0, 0);
		GL11.glRotatef(yaw, 0, 1, 0);
		GL11.glRotatef(roll, 0, 0, 1);
		GL11.glScalef(xSize, ySize, zSize);
	}
	public void translateInvertMatrix(){
		GL11.glRotatef(-pitch, 1, 0, 0);
		GL11.glRotatef(-yaw, 0, 1, 0);
		GL11.glRotatef(-roll, 0, 0, 1);
		GL11.glTranslatef(-x, -y, -z);
		GL11.glScalef(xSize, ySize, zSize);
	}
}