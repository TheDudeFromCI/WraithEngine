package wraith.library.AI;

import java.util.ArrayList;
import java.util.Random;

public class EvolutionaryNoiseGenerator{
	private long[] y;
	private final EvolutionaryLearningSystem learningSystem;
	private final ArrayList<StandardHeight> standardHeights = new ArrayList<>();
	public EvolutionaryNoiseGenerator(){
		learningSystem=new EvolutionaryLearningSystem(-1, 1, false);
		assignValues();
	}
	public double noise(double... x){
		int[] f = new int[x.length];
		double[] fracs = new double[x.length];
		for(int i = 0; i<x.length; i++){
			f[i]=f(x[i]);
			fracs[i]=x[i]-f[i];
		}
		double[] v = new double[(int)Math.pow(2, x.length)];
		for(int i = 0; i<v.length; i++){
			int[] edge = new int[x.length];
			for(int j = 0; j<x.length; j++){
				int c = (int)Math.pow(2, j);
				edge[j]=(i&c)==c?f[j]+1:f[j];
			}
			v[i]=random(edge);
		}
		return i(v, fracs, 0);
	}
	private double random(int[] x){
		for(StandardHeight s : standardHeights)if(s.equals(x))return s.h;
		final long s = 4294967291L;
		long t = 0;
		for(int a : x)t=t*s+a;
		for(int a : x)t+=a*a*s;
		for(long l : y)t+=l*t+s;
		return new Random(t).nextDouble();
	}
	private double i(double[] v, double[] fracs, int stage){
		if(v.length==2)return c(v[0], v[1], fracs[stage]);
		double[] k = new double[v.length/2];
		for(int i = 0; i<k.length; i++)k[i]=c(v[i*2], v[i*2+1], fracs[stage]);
		return i(k, fracs, stage+1);
	}
	private void assignValues(){
		double[] d = learningSystem.next();
		y=new long[d.length];
		for(int i = 0; i<y.length; i++)y[i]=Math.round(d[i]);
	}
	public void score(long score){
		learningSystem.score(score);
		assignValues();
	}
	public void setStandardHeight(double h, int... x){
		for(int i = 0; i<standardHeights.size(); i++){
			if(standardHeights.get(i).equals(x)){
				standardHeights.get(i).h=h;
				return;
			}
		}
		standardHeights.add(new StandardHeight(x, h));
	}
	private static double c(double a, double b, double c){
		c=(float)((1-Math.cos(c*Math.PI))/2);
		return (a*(1-c)+b*c);
	}
	private static int f(double x){ return x>=0?(int)x:(int)x-1; }
	private class StandardHeight{
		private double h;
		private int[] x;
		private StandardHeight(int[] x, double h){
			this.x=x;
			this.h=h;
		}
		@Override public boolean equals(Object o){
			if(o instanceof int[]){
				int[] y = (int[])o;
				if(y.length!=x.length)return false;
				for(int i = 0; i<x.length; i++)if(x[i]!=y[i])return false;
				return true;
			}
			if(o instanceof StandardHeight){
				if(h!=((StandardHeight)o).h)return false;
				int[] y = ((StandardHeight)o).x;
				if(y.length!=x.length)return false;
				for(int i = 0; i<x.length; i++)if(x[i]!=y[i])return false;
				return true;
			}
			return false;
		}
	}
}