package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public class WorldRenderer{
	public static void preLoop(){
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}
	public void render(float delta){
	}
}