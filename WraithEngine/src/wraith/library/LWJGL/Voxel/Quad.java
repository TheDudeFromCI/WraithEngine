package wraith.library.LWJGL.Voxel;

import static org.lwjgl.opengl.GL11.*;

public class Quad{
	public float r, g, b;
	public final int[] texturePoints;
	public final float[] loc;
	private final int rotation;
	public Quad(float[] points, int rotation){
		loc=points;
		this.rotation=rotation;
		if(rotation==0)texturePoints=new int[]{0, 0, 0, 1, 1, 1, 1, 0};
		else if(rotation==1)texturePoints=new int[]{0, 1, 1, 1, 1, 0, 0, 0};
		else if(rotation==2)texturePoints=new int[]{1, 1, 1, 0, 0, 0, 0, 1};
		else texturePoints=new int[]{1, 0, 0, 0, 0, 1, 1, 1};
	}
	public Quad(){
		loc=new float[12];
		this.rotation=0;
		texturePoints=new int[]{0, 0, 0, 1, 1, 1, 1, 0};
	}
	public void renderPart(){
		glColor3f(r, g, b);
		glTexCoord2f(texturePoints[0], texturePoints[1]);
		glVertex3f(loc[0], loc[1], loc[2]);
		glColor3f(r, g, b);
		glTexCoord2f(texturePoints[2], texturePoints[3]);
		glVertex3f(loc[3], loc[4], loc[5]);
		glColor3f(r, g, b);
		glTexCoord2f(texturePoints[4], texturePoints[5]);
		glVertex3f(loc[6], loc[7], loc[8]);
		glColor3f(r, g, b);
		glTexCoord2f(texturePoints[0], texturePoints[1]);
		glVertex3f(loc[0], loc[1], loc[2]);
		glColor3f(r, g, b);
		glTexCoord2f(texturePoints[4], texturePoints[5]);
		glVertex3f(loc[6], loc[7], loc[8]);
		glColor3f(r, g, b);
		glTexCoord2f(texturePoints[6], texturePoints[7]);
		glVertex3f(loc[9], loc[10], loc[11]);
	}
	public boolean matches(Quad q){
		if(r!=q.r||g!=q.g||b!=q.b||q.rotation!=rotation)return false;
		for(int i = 0; i<12; i++)if(q.loc[i]!=loc[i])return false;
		return true;
	}
}