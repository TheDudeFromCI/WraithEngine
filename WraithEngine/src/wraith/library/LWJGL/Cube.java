package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public class Cube extends RenderableObject3D{
	public boolean renderXUp, renderXDown, renderYUp, renderYDown, renderZUp, renderZDown;
	protected void draw(){
		GL11.glBegin(GL11.GL_QUADS);
		if(renderXUp){
			GL11.glColor3f(0.5f, 0.0f, 0.5f);
			GL11.glVertex3f(0.5f, 0.5f, -0.5f);
			GL11.glVertex3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex3f(0.5f, -0.5f, 0.5f);
			GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		}
		if(renderXDown){
			GL11.glColor3f(0.0f, 0.0f, 0.5f);
			GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
			GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
			GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
			GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
		}
		if(renderYUp){
			GL11.glColor3f(0.5f, 0.5f, 0.0f);
			GL11.glVertex3f(0.5f, 0.5f, -0.5f);
			GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
			GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
			GL11.glVertex3f(0.5f, 0.5f, 0.5f);
		}
		if(renderYDown){
			GL11.glColor3f(0.5f, 0.5f, 0.0f);
			GL11.glVertex3f(0.5f, -0.5f, 0.5f);
			GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
			GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
			GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		}
		if(renderZUp){
			GL11.glColor3f(0.5f, 0.0f, 0.0f);
			GL11.glVertex3f(0.5f, 0.5f, 0.5f);
			GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
			GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
			GL11.glVertex3f(0.5f, -0.5f, 0.5f);
		}
		if(renderZDown){
			GL11.glColor3f(0.5f, 0.5f, 0.0f);
			GL11.glVertex3f(0.5f, -0.5f, -0.5f);
			GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
			GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
			GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		}
		GL11.glEnd();
	}
}