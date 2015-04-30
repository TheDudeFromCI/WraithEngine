package wraith.library.LWJGL;

import wraith.library.LWJGL.Voxel.Quad;

public abstract class Cube extends RenderableObject3D{
	public boolean renderXUp, renderXDown, renderYUp, renderYDown, renderZUp, renderZDown;
	public CubeTextures textures;
	private static final float[] X_UP_QUAD = {
		1, 1, 1,
		1, 0, 1,
		1, 0, 0,
		1, 1, 0,
	};
	private static final float[] X_DOWN_QUAD = {
		0, 0, 0,
		0, 0, 1,
		0, 1, 1,
		0, 1, 0,
	};
	private static final float[] Y_UP_QUAD = {
		0, 1, 0,
		0, 1, 1,
		1, 1, 1,
		1, 1, 0,
	};
	private static final float[] Y_DOWN_QUAD = {
		1, 0, 1,
		0, 0, 1,
		0, 0, 0,
		1, 0, 0,
	};
	private static final float[] Z_UP_QUAD = {
		1, 1, 1,
		0, 1, 1,
		0, 0, 1,
		1, 0, 1,
	};
	private static final float[] Z_DOWN_QUAD = {
		0, 0, 0,
		0, 1, 0,
		1, 1, 0,
		1, 0, 0,
	};
	public static final int X_UP_SIDE = 0;
	public static final int X_DOWN_SIDE = 1;
	public static final int Y_UP_SIDE = 2;
	public static final int Y_DOWN_SIDE = 3;
	public static final int Z_UP_SIDE = 4;
	public static final int Z_DOWN_SIDE = 5;
	public static Quad generateQuad(int side, int x, int y, int z, int r, float[] colors){
		Quad q = null;
		if(side==0)q=new Quad(clone(X_UP_QUAD), colors, r);
		if(side==1)q=new Quad(clone(X_DOWN_QUAD), colors, r);
		if(side==2)q=new Quad(clone(Y_UP_QUAD), colors, r);
		if(side==3)q=new Quad(clone(Y_DOWN_QUAD), colors, r);
		if(side==4)q=new Quad(clone(Z_UP_QUAD), colors, r);
		if(side==5)q=new Quad(clone(Z_DOWN_QUAD), colors, r);
		if(q!=null)q.shift(x, y, z);
		return q;
	}
	private static float[] clone(float[] f){
		float[] f2 = new float[f.length];
		for(int i = 0; i<f2.length; i++)f2[i]=f[i];
		return f2;
	}
}