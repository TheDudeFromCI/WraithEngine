package wraith.library.LWJGL.Voxel;

import static org.lwjgl.opengl.GL11.*;

public class Quad{
	public float r, g, b;
	public final float[] loc;
	public void renderPart(){
		glColor3f(r, g, b);
		glTexCoord2f(0, 0);
		glVertex3f(loc[0], loc[1], loc[2]);
		glColor3f(r, g, b);
		glTexCoord2f(0, 1);
		glVertex3f(loc[3], loc[4], loc[5]);
		glColor3f(r, g, b);
		glTexCoord2f(1, 1);
		glVertex3f(loc[6], loc[7], loc[8]);
		glColor3f(r, g, b);
		glTexCoord2f(0, 0);
		glVertex3f(loc[0], loc[1], loc[2]);
		glColor3f(r, g, b);
		glTexCoord2f(1, 1);
		glVertex3f(loc[6], loc[7], loc[8]);
		glColor3f(r, g, b);
		glTexCoord2f(1, 0);
		glVertex3f(loc[9], loc[10], loc[11]);
	}
	public Quad(float[] points){ loc=points; }
	public Quad(){ loc=new float[12]; }
}