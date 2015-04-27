package wraith.library.LWJGL;

import wraith.library.LWJGL.Voxel.Quad;

public abstract class Cube extends RenderableObject3D{
	public boolean renderXUp, renderXDown, renderYUp, renderYDown, renderZUp, renderZDown;
	public CubeTextures textures;
	private static final float[] X_UP_QUAD = {
		0.5f, -0.5f, -0.5f,
		0.5f, 0.5f, -0.5f,
		0.5f, 0.5f, 0.5f,
		0.5f, -0.5f, 0.5f,
	};
	private static final float[] X_DOWN_QUAD = {
		-0.5f, 0.5f, -0.5f,
		-0.5f, -0.5f, -0.5f,
		-0.5f, -0.5f, 0.5f,
		-0.5f, 0.5f, 0.5f,
	};
	private static final float[] Y_UP_QUAD = {
		0.5f, 0.5f, -0.5f,
		-0.5f, 0.5f, -0.5f,
		-0.5f, 0.5f, 0.5f,
		0.5f, 0.5f, 0.5f,
	};
	private static final float[] Y_DOWN_QUAD = {
		0.5f, -0.5f, -0.5f,
		0.5f, -0.5f, 0.5f,
		-0.5f, -0.5f, 0.5f,
		-0.5f, -0.5f, -0.5f,
	};
	private static final float[] Z_UP_QUAD = {
		0.5f, 0.5f, 0.5f,
		-0.5f, 0.5f, 0.5f,
		-0.5f, -0.5f, 0.5f,
		0.5f, -0.5f, 0.5f,
	};
	private static final float[] Z_DOWN_QUAD = {
		0.5f, 0.5f, -0.5f,
		0.5f, -0.5f, -0.5f,
		-0.5f, -0.5f, -0.5f,
		-0.5f, 0.5f, -0.5f,
	};
	public static final int X_UP_SIDE = 0;
	public static final int X_DOWN_SIDE = 1;
	public static final int Y_UP_SIDE = 2;
	public static final int Y_DOWN_SIDE = 3;
	public static final int Z_UP_SIDE = 4;
	public static final int Z_DOWN_SIDE = 5;
	public static Quad generateQuad(int side, int x, int y, int z, int r){
		Quad q = null;
		if(side==0)q=new Quad(clone(X_UP_QUAD), r);
		if(side==1)q=new Quad(clone(X_DOWN_QUAD), r);
		if(side==2)q=new Quad(clone(Y_UP_QUAD), r);
		if(side==3)q=new Quad(clone(Y_DOWN_QUAD), r);
		if(side==4)q=new Quad(clone(Z_UP_QUAD), r);
		if(side==5)q=new Quad(clone(Z_DOWN_QUAD), r);
		if(q!=null){
			q.r=q.g=q.b=1;
			shiftQuad(q, x, y, z);
		}
		return q;
	}
	private static void shiftQuad(Quad q, int x, int y, int z){
		q.loc[0]+=x;
		q.loc[3]+=x;
		q.loc[6]+=x;
		q.loc[9]+=x;
		q.loc[1]+=y;
		q.loc[4]+=y;
		q.loc[7]+=y;
		q.loc[10]+=y;
		q.loc[2]+=z;
		q.loc[5]+=z;
		q.loc[8]+=z;
		q.loc[11]+=z;
	}
	private static float[] clone(float[] f){
		float[] f2 = new float[f.length];
		for(int i = 0; i<f2.length; i++)f2[i]=f[i];
		return f2;
	}
}