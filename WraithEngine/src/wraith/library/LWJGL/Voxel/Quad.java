package wraith.library.LWJGL.Voxel;

import static org.lwjgl.opengl.GL11.*;

public class Quad{
	private int rotation;
	final float[] colors;
	final int[] texturePoints;
	final float[] loc;
	boolean centerPoint;
	public Quad(float[] points, float[] colors, int rotation){
		loc=points;
		this.colors=colors;
		this.rotation=rotation;
		if(rotation==0)texturePoints=new int[]{0, 0, 0, 1, 1, 1, 1, 0};
		else if(rotation==1)texturePoints=new int[]{0, 1, 1, 1, 1, 0, 0, 0};
		else if(rotation==2)texturePoints=new int[]{1, 1, 1, 0, 0, 0, 0, 1};
		else texturePoints=new int[]{1, 0, 0, 0, 0, 1, 1, 1};
	}
	public Quad(){
		loc=new float[15];
		colors=new float[15];
		this.rotation=0;
		texturePoints=new int[]{0, 0, 0, 1, 1, 1, 1, 0};
	}
	public void renderPart(){
		if(centerPoint){
			renderEdge(0);
			renderEdge(1);
			renderEdge(4);
			renderEdge(1);
			renderEdge(2);
			renderEdge(4);
			renderEdge(2);
			renderEdge(3);
			renderEdge(4);
			renderEdge(3);
			renderEdge(0);
			renderEdge(4);
		}else{
			glColor3f(colors[0], colors[1], colors[2]);
			glTexCoord2f(texturePoints[0], texturePoints[1]);
			glVertex3f(loc[0], loc[1], loc[2]);
			glColor3f(colors[3], colors[4], colors[5]);
			glTexCoord2f(texturePoints[2], texturePoints[3]);
			glVertex3f(loc[3], loc[4], loc[5]);
			glColor3f(colors[6], colors[7], colors[8]);
			glTexCoord2f(texturePoints[4], texturePoints[5]);
			glVertex3f(loc[6], loc[7], loc[8]);
			glColor3f(colors[0], colors[1], colors[2]);
			glTexCoord2f(texturePoints[0], texturePoints[1]);
			glVertex3f(loc[0], loc[1], loc[2]);
			glColor3f(colors[6], colors[7], colors[8]);
			glTexCoord2f(texturePoints[4], texturePoints[5]);
			glVertex3f(loc[6], loc[7], loc[8]);
			glColor3f(colors[9], colors[10], colors[11]);
			glTexCoord2f(texturePoints[6], texturePoints[7]);
			glVertex3f(loc[9], loc[10], loc[11]);
		}
	}
	private void renderEdge(int corner){
		if(corner==0){
			glColor3f(colors[0], colors[1], colors[2]);
			glTexCoord2f(texturePoints[0], texturePoints[1]);
			glVertex3f(loc[0], loc[1], loc[2]);
		}
		if(corner==1){
			glColor3f(colors[3], colors[4], colors[5]);
			glTexCoord2f(texturePoints[2], texturePoints[3]);
			glVertex3f(loc[3], loc[4], loc[5]);
		}
		if(corner==2){
			glColor3f(colors[6], colors[7], colors[8]);
			glTexCoord2f(texturePoints[4], texturePoints[5]);
			glVertex3f(loc[6], loc[7], loc[8]);
		}
		if(corner==3){
			glColor3f(colors[9], colors[10], colors[11]);
			glTexCoord2f(texturePoints[6], texturePoints[7]);
			glVertex3f(loc[9], loc[10], loc[11]);
		}
		if(corner==4){
			glColor3f(colors[12], colors[13], colors[14]);
			glTexCoord2f(0.5f, 0.5f);
			glVertex3f(loc[12], loc[13], loc[14]);
		}
	}
	public boolean matches(Quad q){
		if(q.rotation!=rotation)return false;
		for(int i = 0; i<12; i++){
			if(q.loc[i]!=loc[i])return false;
			if(q.colors[i]!=colors[i])return false;
		}
		return true;
	}
	public boolean matchesLight(Quad q){
		for(int i = 0; i<12; i++)if(q.loc[i]!=loc[i])return false;
		return true;
	}
	public void shift(float x, float y, float z){
		loc[0]+=x;
		loc[3]+=x;
		loc[6]+=x;
		loc[9]+=x;
		loc[12]+=x;
		loc[1]+=y;
		loc[4]+=y;
		loc[7]+=y;
		loc[10]+=y;
		loc[13]+=y;
		loc[2]+=z;
		loc[5]+=z;
		loc[8]+=z;
		loc[11]+=z;
		loc[14]+=z;
	}
	public void setColors(int corner, float r, float g, float b){
		if(corner==0){
			colors[0]=r;
			colors[1]=g;
			colors[2]=b;
		}
		if(corner==1){
			colors[3]=r;
			colors[4]=g;
			colors[5]=b;
		}
		if(corner==2){
			colors[6]=r;
			colors[7]=g;
			colors[8]=b;
		}
		if(corner==3){
			colors[9]=r;
			colors[10]=g;
			colors[11]=b;
		}
		if(corner==4){
			colors[12]=r;
			colors[13]=g;
			colors[14]=b;
		}
	}
	public void setRotation(int r){
		rotation=r;
		if(rotation==0){
			texturePoints[0]=0;
			texturePoints[1]=0;
			texturePoints[2]=0;
			texturePoints[3]=1;
			texturePoints[4]=1;
			texturePoints[5]=1;
			texturePoints[6]=1;
			texturePoints[7]=0;
		}
		if(rotation==1){
			texturePoints[0]=0;
			texturePoints[1]=1;
			texturePoints[2]=1;
			texturePoints[3]=1;
			texturePoints[4]=1;
			texturePoints[5]=0;
			texturePoints[6]=0;
			texturePoints[7]=0;
		}
		if(rotation==2){
			texturePoints[0]=1;
			texturePoints[1]=1;
			texturePoints[2]=1;
			texturePoints[3]=0;
			texturePoints[4]=0;
			texturePoints[5]=0;
			texturePoints[6]=0;
			texturePoints[7]=1;
		}
		if(rotation==3){
			texturePoints[0]=1;
			texturePoints[1]=0;
			texturePoints[2]=0;
			texturePoints[3]=0;
			texturePoints[4]=0;
			texturePoints[5]=1;
			texturePoints[6]=1;
			texturePoints[7]=1;
		}
	}
}