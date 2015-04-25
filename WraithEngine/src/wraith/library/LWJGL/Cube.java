package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public abstract class Cube extends RenderableObject3D{
	public boolean renderXUp, renderXDown, renderYUp, renderYDown, renderZUp, renderZDown;
	public CubeTextures textures;
	protected void draw(){
		if(renderXUp){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.xUp!=null){
				textures.xUp.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glEnd();
			}else{
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glEnd();
			}
		}
		if(renderXDown){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.xDown!=null){
				textures.xDown.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glEnd();
			}else{
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glEnd();
			}
		}
		if(renderYUp){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.yUp!=null){
				textures.yUp.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glEnd();
			}else{
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glEnd();
			}
		}
		if(renderYDown){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.yDown!=null){
				textures.yDown.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glEnd();
			}else{
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glEnd();
			}
		}
		if(renderZUp){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.zUp!=null){
				textures.zUp.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glEnd();
			}else{
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glEnd();
			}
		}
		if(renderZDown){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.zDown!=null){
				textures.zDown.bind();
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glEnd();
			}else{
				GL11.glBegin(GL11.GL_QUADS);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glEnd();
			}
		}
	}
}