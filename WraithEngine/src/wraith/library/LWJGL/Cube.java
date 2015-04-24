package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public class Cube extends RenderableObject3D{
	public boolean renderXUp, renderXDown, renderYUp, renderYDown, renderZUp, renderZDown;
	public CubeTextures textures;
	protected void draw(){
		GL11.glBegin(GL11.GL_QUADS);
		if(renderXUp){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.xUp!=null){
				textures.xUp.bind();
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
			}else{
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
			}
		}
		if(renderXDown){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.xDown!=null){
				textures.xDown.bind();
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
			}else{
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
			}
		}
		if(renderYUp){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.yUp!=null){
				textures.yUp.bind();
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
			}else{
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
			}
		}
		if(renderYDown){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.yDown!=null){
				textures.yDown.bind();
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
			}else{
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
			}
		}
		if(renderZUp){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.zUp!=null){
				textures.zUp.bind();
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
			}else{
				GL11.glVertex3f(0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, 0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, 0.5f);
				GL11.glVertex3f(0.5f, -0.5f, 0.5f);
			}
		}
		if(renderZDown){
			GL11.glColor3f(1, 1, 1);
			if(textures!=null&&textures.zDown!=null){
				textures.zDown.bind();
				GL11.glTexCoord2f(0, 0);
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(0, 1);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glTexCoord2f(1, 1);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glTexCoord2f(1, 0);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
			}else{
				GL11.glVertex3f(0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
				GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
				GL11.glVertex3f(0.5f, 0.5f, -0.5f);
			}
		}
		GL11.glEnd();
	}
}