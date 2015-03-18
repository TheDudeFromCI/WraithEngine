package wraith.library.AI;

import java.util.ArrayList;
import java.util.Random;
import wraith.library.RandomGeneration.NoiseGenerator;

public class EvolutionaryNoiseGenerator{
	private double[] y, by;
	private BasicEvolutionProgressLog progressLog;
	private final NoiseGenerator noiseGenerator;
	private final EvolutionaryLearningSystem learningSystem;
	private final ArrayList<StandardHeight> standardHeights = new ArrayList<>();
	public EvolutionaryNoiseGenerator(){
		noiseGenerator=new NoiseGenerator(new Random().nextLong(), 1, 1, 0);
		learningSystem=new EvolutionaryLearningSystem(1, 10000, false);
		progressLog=new BasicEvolutionProgressLog(learningSystem);
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
			v[i]=random(edge, false);
		}
		return i(v, fracs, 0);
	}
	public double bestNoise(double... x){
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
			v[i]=random(edge, true);
		}
		return i(v, fracs, 0);
	}
	private double random(int[] x, boolean best){
		for(StandardHeight s : standardHeights)if(s.equals(x))return s.h;
		final long s = 4294967291L;
		long t = 0;
		for(int a : x)t=t*s+a;
		for(int a : x)t+=a*a*s;
		double d = new Random(t).nextDouble();
		return (d+(best?bestRN(x):rn(x)))%1;
	}
	private double rn(int[] x){
		float[] f = new float[x.length+y.length];
		for(int i = 0; i<f.length; i++){
			if(i<x.length)f[i]=x[i];
			else f[i]=(float)y[i-x.length];
		}
		return noiseGenerator.noise(f);
	}
	private double bestRN(int[] x){
		float[] f = new float[x.length+by.length];
		for(int i = 0; i<f.length; i++){
			if(i<x.length)f[i]=x[i];
			else f[i]=(float)by[i-x.length];
		}
		return noiseGenerator.noise(f);
	}
	private double i(double[] v, double[] fracs, int stage){
		if(v.length==2)return c(v[0], v[1], fracs[stage]);
		double[] k = new double[v.length/2];
		for(int i = 0; i<k.length; i++)k[i]=c(v[i*2], v[i*2+1], fracs[stage]);
		return i(k, fracs, stage+1);
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
	private void assignValues(){
		y=learningSystem.next();
		by=learningSystem.getBest();
	}
	public BasicEvolutionProgressLog getProgressLog(){ return progressLog; }
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