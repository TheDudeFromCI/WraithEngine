package wraith.library.LWJGL.Voxel;

import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import wraith.library.LWJGL.Texture;

public class QuadBatch{
	private int i;
	private Texture texture;
	private final ArrayList<Quad> quads;
	public void renderBatch(){
		texture.bind();
		GL11.glBegin(GL11.GL_TRIANGLES);
		renderPart();
		GL11.glEnd();
	}
	public void removeQuadLight(Quad q){
		for(int i = 0; i<quads.size(); i++){
			if(quads.get(i).matchesLight(q)){
				quads.remove(i);
				return;
			}
		}
	}
	public void removeQuad(Quad q){
		for(i=0; i<quads.size(); i++){
			if(quads.get(i)==q){
				quads.remove(i);
				return;
			}
		}
	}
	public QuadBatch(int buffer){ quads=new ArrayList(buffer); }
	public QuadBatch(){ quads=new ArrayList(); }
	public void renderPart(){ for(i=0; i<quads.size(); i++)quads.get(i).renderPart(); }
	public void addQuad(Quad q){ if(!quads.contains(q))quads.add(q); }
	public void setTexture(Texture texture){ this.texture=texture; }
	public Texture getTexture(){ return texture; }
	public int getSize(){ return quads.size(); }
}