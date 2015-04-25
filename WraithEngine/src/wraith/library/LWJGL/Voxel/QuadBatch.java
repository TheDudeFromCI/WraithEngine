package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import wraith.library.LWJGL.Texture;

public class QuadBatch{
	private ArrayList<Quad> quads = new ArrayList();
	private Texture texture;
	public void renderBatch(){
		texture.bind();
		GL11.glBegin(GL11.GL_TRIANGLES);
		for(Quad q : quads)q.renderPart();
		GL11.glEnd();
	}
	public void addQuad(Quad q){ quads.add(q); }
	public void setTexture(Texture texture){ this.texture=texture; }
	public Texture getTexture(){ return texture; }
}