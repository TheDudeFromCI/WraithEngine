package wraith.library.LWJGL.Voxel;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import wraith.library.LWJGL.Texture;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class QuadBatch{
	private int i;
	private int elementCount;
	private FloatBuffer vertexBuffer;
	private FloatBuffer colorBuffer;
	private FloatBuffer textureCoordBuffer;
	private ShortBuffer indexBuffer;
	private final int vertexBufferId;
	private final int colorBufferId;
	private final int textureCoordBufferId;
	private final int indexBufferId;
	final Texture texture;
	private final ArrayList<Quad> quads = new ArrayList();
	private static final long ZERO = 0;
	private static final int FLOAT_SIZE = 4;
	public QuadBatch(Texture texture){
		this.texture=texture;
		vertexBufferId=glGenBuffers();
		colorBufferId=glGenBuffers();
		textureCoordBufferId=glGenBuffers();
		indexBufferId=glGenBuffers();
	}
	public void removeQuad(Quad q){
		for(i=0; i<quads.size(); i++){
			if(quads.get(i)==q){
				quads.remove(i);
				return;
			}
		}
	}
	void recompileBuffer(){
		int points = 0;
		int indices = 0;
		for(int i = 0; i<quads.size(); i++){
			if(quads.get(i).centerPoint){
				points+=5;
				indices+=12;
			}else{
				points+=4;
				indices+=6;
			}
		}
		vertexBuffer=BufferUtils.createFloatBuffer(points*3);
		colorBuffer=BufferUtils.createFloatBuffer(points*3);
		textureCoordBuffer=BufferUtils.createFloatBuffer(points*2);
		indexBuffer=BufferUtils.createShortBuffer(indices);
		elementCount=0;
		Quad q;
		try{
			for(int i = 0; i<quads.size(); i++){
				q=quads.get(i);
				if(q.centerPoint){
					addEdge(q, 0);
					addEdge(q, 1);
					addEdge(q, 2);
					addEdge(q, 3);
					addEdge(q, 4);
					addIndex(0);
					addIndex(1);
					addIndex(4);
					addIndex(1);
					addIndex(2);
					addIndex(4);
					addIndex(2);
					addIndex(3);
					addIndex(4);
					addIndex(3);
					addIndex(0);
					addIndex(4);
					elementCount+=5;
				}else{
					addEdge(q, 0);
					addEdge(q, 1);
					addEdge(q, 2);
					addEdge(q, 3);
					addIndex(0);
					addIndex(1);
					addIndex(2);
					addIndex(0);
					addIndex(2);
					addIndex(3);
					elementCount+=4;
				}
			}
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(1);
		}
		vertexBuffer.flip();
		colorBuffer.flip();
		textureCoordBuffer.flip();
		indexBuffer.flip();
	}
	private void addEdge(Quad q, int edge){
		if(edge==0){
			vertexBuffer.put(q.loc[0]).put(q.loc[1]).put(q.loc[2]);
			colorBuffer.put(q.colors[0]).put(q.colors[1]).put(q.colors[2]);
			textureCoordBuffer.put(q.texturePoints[0]).put(q.texturePoints[1]);
		}
		if(edge==1){
			vertexBuffer.put(q.loc[3]).put(q.loc[4]).put(q.loc[5]);
			colorBuffer.put(q.colors[3]).put(q.colors[4]).put(q.colors[5]);
			textureCoordBuffer.put(q.texturePoints[2]).put(q.texturePoints[3]);
		}
		if(edge==2){
			vertexBuffer.put(q.loc[6]).put(q.loc[7]).put(q.loc[8]);
			colorBuffer.put(q.colors[6]).put(q.colors[7]).put(q.colors[8]);
			textureCoordBuffer.put(q.texturePoints[4]).put(q.texturePoints[5]);
		}
		if(edge==3){
			vertexBuffer.put(q.loc[9]).put(q.loc[10]).put(q.loc[11]);
			colorBuffer.put(q.colors[9]).put(q.colors[10]).put(q.colors[11]);
			textureCoordBuffer.put(q.texturePoints[6]).put(q.texturePoints[7]);
		}
		if(edge==4){
			vertexBuffer.put(q.loc[12]).put(q.loc[13]).put(q.loc[14]);
			colorBuffer.put(q.colors[12]).put(q.colors[13]).put(q.colors[14]);
			textureCoordBuffer.put(0.5f).put(0.5f);
		}
	}
	public void renderPart(){
		glBindBuffer(GL_ARRAY_BUFFER, vertexBufferId);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_DYNAMIC_DRAW);
		glVertexPointer(3, GL_FLOAT, FLOAT_SIZE*3, ZERO);
		glBindBuffer(GL_ARRAY_BUFFER, colorBufferId);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_DYNAMIC_DRAW);
		glColorPointer(3, GL_FLOAT, FLOAT_SIZE*3, ZERO);
		glBindBuffer(GL_ARRAY_BUFFER, textureCoordBufferId);
		glBufferData(GL_ARRAY_BUFFER, textureCoordBuffer, GL_DYNAMIC_DRAW);
		glTexCoordPointer(2, GL_FLOAT, FLOAT_SIZE*2, ZERO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBufferId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_DYNAMIC_DRAW);
		glDrawElements(GL_TRIANGLES, indexBuffer.limit(), GL_UNSIGNED_SHORT, ZERO);
	}
	public void cleanUp(){
		glDeleteBuffers(vertexBufferId);
		glDeleteBuffers(colorBufferId);
		glDeleteBuffers(textureCoordBufferId);
		glDeleteBuffers(indexBufferId);
	}
	public void addQuad(Quad q){ if(!quads.contains(q))quads.add(q); }
	public Texture getTexture(){ return texture; }
	public int getSize(){ return quads.size(); }
	private void addIndex(int offset){ indexBuffer.put((short)(elementCount+offset)); }
}