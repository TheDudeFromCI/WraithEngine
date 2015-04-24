package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public abstract class RenderableObject3D extends Object3D{
	public void renderObject(){
		GL11.glPushMatrix();
		translateMatrix();
		draw();
		GL11.glPopMatrix();
	}
	protected abstract void draw();
}