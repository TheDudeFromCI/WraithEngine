package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public class WorldRenderer extends RenderGroup{
	public final Camera cam;
	public void render(float delta){
		update(delta);
		cam.translateInvertMatrix();
		draw();
	}
	public WorldRenderer(float fov, float aspect, float near, float far){ cam=new Camera(fov, aspect, near, far); }
	public static void preLoop(){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
}