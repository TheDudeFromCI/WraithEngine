package wraith.library.LWJGL;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;

public class Frustum{
	private float[][] frustum = new float[6][4];
	private FloatBuffer modelBuffer;
	private FloatBuffer projectionBuffer;
	private float[] projectionMatrix;
	private float[] modelMatrix;
	private float[] clipMatrix;
	private int i;
	private static final int RIGHT = 0;
	private static final int LEFT = 1;
	private static final int BOTTOM = 2;
	private static final int TOP = 3;
	private static final int BACK = 4;
	private static final int FRONT = 5;
	private static final int A = 0;
	private static final int B = 1;
	private static final int C = 2;
	private static final int D = 3;
	public Frustum(){
		modelBuffer=BufferUtils.createFloatBuffer(16);
		projectionBuffer=BufferUtils.createFloatBuffer(16);
	}
	private void normalizePlane(float[][] frustum, int side){
		float magnitude = (float)Math.sqrt(frustum[side][A]*frustum[side][A]+frustum[side][B]*frustum[side][B]+frustum[side][C]*frustum[side][C]);
		frustum[side][A]/=magnitude;
		frustum[side][B]/=magnitude;
		frustum[side][C]/=magnitude;
		frustum[side][D]/=magnitude;
	}
	public void calculateFrustum(){
		projectionMatrix=new float[16];
		modelMatrix=new float[16];
		clipMatrix=new float[16];
		projectionBuffer.rewind();
		glGetFloat(GL_PROJECTION_MATRIX, projectionBuffer);
		projectionBuffer.rewind();
		projectionBuffer.get(projectionMatrix);
		modelBuffer.rewind();
		glGetFloat(GL_MODELVIEW_MATRIX, modelBuffer);
		modelBuffer.rewind();
		modelBuffer.get(modelMatrix);
		clipMatrix[0]=modelMatrix[0]*projectionMatrix[0]+modelMatrix[1]*projectionMatrix[4]+modelMatrix[2]*projectionMatrix[8]+modelMatrix[3]*projectionMatrix[12];
		clipMatrix[1]=modelMatrix[0]*projectionMatrix[1]+modelMatrix[1]*projectionMatrix[5]+modelMatrix[2]*projectionMatrix[9]+modelMatrix[3]*projectionMatrix[13];
		clipMatrix[2]=modelMatrix[0]*projectionMatrix[2]+modelMatrix[1]*projectionMatrix[6]+modelMatrix[2]*projectionMatrix[10]+modelMatrix[3]*projectionMatrix[14];
		clipMatrix[3]=modelMatrix[0]*projectionMatrix[3]+modelMatrix[1]*projectionMatrix[7]+modelMatrix[2]*projectionMatrix[11]+modelMatrix[3]*projectionMatrix[15];
		clipMatrix[4]=modelMatrix[4]*projectionMatrix[0]+modelMatrix[5]*projectionMatrix[4]+modelMatrix[6]*projectionMatrix[8]+modelMatrix[7]*projectionMatrix[12];
		clipMatrix[5]=modelMatrix[4]*projectionMatrix[1]+modelMatrix[5]*projectionMatrix[5]+modelMatrix[6]*projectionMatrix[9]+modelMatrix[7]*projectionMatrix[13];
		clipMatrix[6]=modelMatrix[4]*projectionMatrix[2]+modelMatrix[5]*projectionMatrix[6]+modelMatrix[6]*projectionMatrix[10]+modelMatrix[7]*projectionMatrix[14];
		clipMatrix[7]=modelMatrix[4]*projectionMatrix[3]+modelMatrix[5]*projectionMatrix[7]+modelMatrix[6]*projectionMatrix[11]+modelMatrix[7]*projectionMatrix[15];
		clipMatrix[8]=modelMatrix[8]*projectionMatrix[0]+modelMatrix[9]*projectionMatrix[4]+modelMatrix[10]*projectionMatrix[8]+modelMatrix[11]*projectionMatrix[12];
		clipMatrix[9]=modelMatrix[8]*projectionMatrix[1]+modelMatrix[9]*projectionMatrix[5]+modelMatrix[10]*projectionMatrix[9]+modelMatrix[11]*projectionMatrix[13];
		clipMatrix[10]=modelMatrix[8]*projectionMatrix[2]+modelMatrix[9]*projectionMatrix[6]+modelMatrix[10]*projectionMatrix[10]+modelMatrix[11]*projectionMatrix[14];
		clipMatrix[11]=modelMatrix[8]*projectionMatrix[3]+modelMatrix[9]*projectionMatrix[7]+modelMatrix[10]*projectionMatrix[11]+modelMatrix[11]*projectionMatrix[15];
		clipMatrix[12]=modelMatrix[12]*projectionMatrix[0]+modelMatrix[13]*projectionMatrix[4]+modelMatrix[14]*projectionMatrix[8]+modelMatrix[15]*projectionMatrix[12];
		clipMatrix[13]=modelMatrix[12]*projectionMatrix[1]+modelMatrix[13]*projectionMatrix[5]+modelMatrix[14]*projectionMatrix[9]+modelMatrix[15]*projectionMatrix[13];
		clipMatrix[14]=modelMatrix[12]*projectionMatrix[2]+modelMatrix[13]*projectionMatrix[6]+modelMatrix[14]*projectionMatrix[10]+modelMatrix[15]*projectionMatrix[14];
		clipMatrix[15]=modelMatrix[12]*projectionMatrix[3]+modelMatrix[13]*projectionMatrix[7]+modelMatrix[14]*projectionMatrix[11]+modelMatrix[15]*projectionMatrix[15];
		frustum[LEFT][A]=clipMatrix[3]+clipMatrix[0];
		frustum[LEFT][B]=clipMatrix[7]+clipMatrix[4];
		frustum[LEFT][C]=clipMatrix[11]+clipMatrix[8];
		frustum[LEFT][D]=clipMatrix[15]+clipMatrix[12];
		normalizePlane(frustum, LEFT);
		frustum[RIGHT][A]=clipMatrix[3]-clipMatrix[0];
		frustum[RIGHT][B]=clipMatrix[7]-clipMatrix[4];
		frustum[RIGHT][C]=clipMatrix[11]-clipMatrix[8];
		frustum[RIGHT][D]=clipMatrix[15]-clipMatrix[12];
		normalizePlane(frustum, RIGHT);
		frustum[BOTTOM][A]=clipMatrix[3]+clipMatrix[1];
		frustum[BOTTOM][B]=clipMatrix[7]+clipMatrix[5];
		frustum[BOTTOM][C]=clipMatrix[11]+clipMatrix[9];
		frustum[BOTTOM][D]=clipMatrix[15]+clipMatrix[13];
		normalizePlane(frustum, BOTTOM);
		frustum[TOP][A]=clipMatrix[3]-clipMatrix[1];
		frustum[TOP][B]=clipMatrix[7]-clipMatrix[5];
		frustum[TOP][C]=clipMatrix[11]-clipMatrix[9];
		frustum[TOP][D]=clipMatrix[15]-clipMatrix[13];
		normalizePlane(frustum, TOP);
		frustum[FRONT][A]=clipMatrix[3]+clipMatrix[2];
		frustum[FRONT][B]=clipMatrix[7]+clipMatrix[6];
		frustum[FRONT][C]=clipMatrix[11]+clipMatrix[10];
		frustum[FRONT][D]=clipMatrix[15]+clipMatrix[14];
		normalizePlane(frustum, FRONT);
		frustum[BACK][A]=clipMatrix[3]-clipMatrix[2];
		frustum[BACK][B]=clipMatrix[7]-clipMatrix[6];
		frustum[BACK][C]=clipMatrix[11]-clipMatrix[10];
		frustum[BACK][D]=clipMatrix[15]-clipMatrix[14];
		normalizePlane(frustum, BACK);
	}
	public boolean cubeInFrustum(float x, float y, float z, float size){
		for(i=0; i<6; i++){
			if(frustum[i][A]*(x-size)+frustum[i][B]*(y-size)+frustum[i][C]*(z-size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x+size)+frustum[i][B]*(y-size)+frustum[i][C]*(z-size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x-size)+frustum[i][B]*(y+size)+frustum[i][C]*(z-size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x+size)+frustum[i][B]*(y+size)+frustum[i][C]*(z-size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x-size)+frustum[i][B]*(y-size)+frustum[i][C]*(z+size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x+size)+frustum[i][B]*(y-size)+frustum[i][C]*(z+size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x-size)+frustum[i][B]*(y+size)+frustum[i][C]*(z+size)+frustum[i][D]>0)continue;
			if(frustum[i][A]*(x+size)+frustum[i][B]*(y+size)+frustum[i][C]*(z+size)+frustum[i][D]>0)continue;
			return false;
		}
		return true;
	}
}