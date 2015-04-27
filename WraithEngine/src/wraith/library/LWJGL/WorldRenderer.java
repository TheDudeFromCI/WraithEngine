package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public class WorldRenderer extends RenderGroup{
	public Camera cam;
	public void render(float delta, long time){
		update(delta, time);
		cam.translateInvertMatrix();
		draw();
	}
	public void preLoop(float fov, float aspect, float near, float far){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
		cam=new Camera(fov, aspect, near, far, true);
	}
}