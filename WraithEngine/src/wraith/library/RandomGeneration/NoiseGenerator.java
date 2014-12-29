package wraith.library.RandomGeneration;

import java.util.Random;

public class NoiseGenerator{
	private long seed;
	private float smoothness;
	private int detail;
	private float maxHeight;
	private boolean onlyPositive;
	public NoiseGenerator(long seed, float smoothness, int detail, boolean onlyPositive){
		if(smoothness<=0)throw new IllegalArgumentException("Smoothness must be greater then 0!");
		if(detail<1)throw new IllegalArgumentException("Detail must be at least 1!");
		this.seed=seed;
		this.smoothness=smoothness;
		this.detail=detail;
		this.onlyPositive=onlyPositive;
		maxHeight=m(detail);
	}
	public float noise(float... x){
		if(x.length==0){
			if(onlyPositive)return new Random(seed).nextFloat();
			return new Random(seed).nextFloat()*2-1;
		}
		for(int i = 0; i<x.length; i++)x[i]/=smoothness;
		float t = 0;
		for(int i = 0; i<detail; i++)t+=(e(x, (float)Math.pow(2, i), (i+1)*1000)/(i+1))*1/Math.pow(2, i);
		t=t/maxHeight;
		if(onlyPositive)t=(t+1)/2;
		return t;
	}
	private float e(float[] x, float pow, int k){
		float[] a = new float[x.length];
		for(int i = 0; i<a.length; i++)a[i]=x[i]*pow;
		int w = new Random(k).nextInt();
		int[] f = new int[a.length];
		float[] fracs = new float[a.length];
		for(int i = 0; i<a.length; i++){
			f[i]=f(a[i]);
			fracs[i]=a[i]-f[i];
		}
		float[] v = new float[(int)Math.pow(2, a.length)];
		for(int i = 0; i<v.length; i++){
			int[] edge = new int[a.length+1];
			for(int j = 0; j<a.length; j++){
				int c = (int)Math.pow(2, j);
				edge[j]=(i&c)==c?f[j]+1:f[j];
			}
			edge[a.length]=w;
			v[i]=s(edge);
		}
		return i(v, fracs, 0);
	}
	private float i(float[] v, float[] fracs, int stage){
		if(v.length==2)return c(v[0], v[1], fracs[stage]);
		float[] k = new float[v.length/2];
		for(int i = 0; i<k.length; i++)k[i]=c(v[i*2], v[i*2+1], fracs[stage]);
		return i(k, fracs, stage+1);
	}
	private float c(float a, float b, float c){
		c=(float)((1-Math.cos(c*Math.PI))/2);
		return (a*(1-c)+b*c);
	}
	private float m(int i){
		float f = 0;
		for(int a = 0; a<i; a++)f+=1/Math.pow(2, a);
		return f;
	}
	private float s(int[] x){
		float total = 0;
		int[] n = new int[x.length];
		for(int i = 0; i<n.length; i++)n[i]=x[i];
		for(int a = 0; a<x.length; a++){
			for(int b = -1; b<=1; b++){
				n[a]=x[a]+b;
				total+=r(n);
			}
		}
		return total/(x.length*3);
	}
	private float r(int[] x){
		final long s = 4294967291L;
		long t = seed;
		for(int a : x)t=t*s+a;
		for(int a : x)t+=a*a*s;
		return new Random(t).nextFloat()*2-1;
	}
	public NoiseGenerator(boolean onlyPositive){ this((int)(Math.random()*Integer.MAX_VALUE), (float)(Math.random()*100), (int)(Math.random()*25)+1, onlyPositive); }
	private int f(float x){ return x>=0?(int)x:(int)x-1; }
}