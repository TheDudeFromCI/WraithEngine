package wraith.library.RandomGeneration;

import java.util.Random;

public class NoiseGenerator{
	private int[] reals, edge, c;
	private float[] fracs, v;
	private final float maxHeight;
	private final long seed;
	private final float smoothness;
	private final int detail;
	public NoiseGenerator(long seed, float smoothness, int detail){
		this.seed=seed;
		this.smoothness=smoothness;
		this.detail=detail+1;
		maxHeight=findMaxHeight(this.detail);
	}
	public float noise(float... x){
		if(reals==null||x.length!=reals.length){
			reals=new int[x.length];
			fracs=new float[x.length];
			v=new float[(int)Math.pow(2, x.length)];
			edge=new int[x.length+1];
			c=new int[x.length];
			for(int i = 0; i<c.length; i++)c[i]=(int)Math.pow(2, i);
		}
		float total = 0;
		int pow;
		for(int k = 0; k<detail; k++){
			pow=(int)Math.pow(2, k);
			total+=layerNoise(x, pow, k)/pow;
		}
		return (total/maxHeight+1)*0.5f;
	}
	private float layerNoise(float[] x, int pow, int k){
		edge[x.length]=new Random(seed+k).nextInt();
		float a;
		int i, j;
		for(i=0; i<x.length; i++){
			a=x[i]/smoothness*pow;
			reals[i]=floor(a);
			fracs[i]=a-reals[i];
		}
		for(i=0; i<v.length; i++){
			for(j=0; j<x.length; j++)edge[j]=(i&c[j])==c[j]?reals[j]+1:reals[j];
			v[i]=random(edge);
		}
		return compress(v, fracs, 0);
	}
	private float compress(float[] v, float[] fracs, int stage){
		if(v.length==2)return interpolate(v[0], v[1], fracs[stage]);
		float[] k = new float[v.length/2];
		for(int i = 0; i<k.length; i++)k[i]=interpolate(v[i*2], v[i*2+1], fracs[stage]);
		return compress(k, fracs, stage+1);
	}
	private float random(int[] x){
		final long s = 4294967291L;
		long t = seed;
		for(int a : x)t=t*s+a;
		for(int a : x)t+=a*a*s;
		return new Random(t).nextFloat()*2-1;
	}
	private static float findMaxHeight(int detail){
		float m = 0;
		for(int i = 0; i<detail; i++)m+=Math.pow(2, -i);
		return m;
	}
	private static float interpolate(float a, float b, float c){
		c=(float)((1-Math.cos(c*Math.PI))/2);
		return (a*(1-c)+b*c);
	}
	private static int floor(float x){ return x>=0?(int)x:(int)x-1; }
}