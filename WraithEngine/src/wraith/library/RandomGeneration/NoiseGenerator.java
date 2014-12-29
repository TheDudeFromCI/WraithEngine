package wraith.library.RandomGeneration;

import java.util.Random;

public class NoiseGenerator{
	private long seed;
	private float smoothness;
	private int detail;
	private float maxHeight;
	public NoiseGenerator(long seed, float smoothness, int detail){
		if(smoothness<=0)throw new IllegalArgumentException("Smoothness must be greater then 0!");
		if(detail<1)throw new IllegalArgumentException("Detail must be at least 1!");
		this.seed=seed;
		this.smoothness=smoothness;
		this.detail=detail;
		maxHeight=m(detail);
	}
	public float noise(float... x){
		if(x.length==0)return 0;
		float t = 0;
		for(int i = 0; i<detail; i++){
			t+=n(x, (float)Math.pow(2, i), i)/(i+1);
		}
		return t/maxHeight;
	}
	private float n(float[] x, float pow, int k){
		float total = 0;
		for(int i = 0; i<x.length; i++)total+=e(x[i]*pow, (i+1)*k<<34^10*(k+1000)*k+3453+k^6);
		return total/=x.length;
	}
	private float e(float x, int k){
		x/=smoothness;
		int floor = floor(x);
		int ceil = floor+1;
		return cosInterpolation(random(floor, k), random(ceil, k), x-floor);
	}
	private float cosInterpolation(float a, float b, float c){
		c=(float)((1-Math.cos(c*Math.PI))/2f);
		return(a*(1-c)+b*c);
	}
	private float m(int i){
		float f = 0;
		for(int a = 0; a<i; a++)f+=1f/(float)Math.pow(2, a);
		return f;
	}
	private float random(int x, int k){ return new Random(seed*(x^k)*x+seed*k*k+x<<k+seed^k).nextFloat()*2-1; }
	private int floor(float x){ return x>=0?(int)x:(int)x-1; }
}