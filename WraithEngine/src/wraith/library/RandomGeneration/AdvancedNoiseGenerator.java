package wraith.library.RandomGeneration;

public class AdvancedNoiseGenerator{
	private NoiseGenerator[] n;
	public AdvancedNoiseGenerator(int blending, boolean onlyPositive){
		if(blending<1)throw new IllegalArgumentException("Blending must be at least 1!");
		n=new NoiseGenerator[blending];
		for(int i = 0; i<blending; i++)n[i]=new NoiseGenerator(onlyPositive);
	}
	public float noise(float... x){
		float total = 0;
		for(NoiseGenerator a : n)total+=a.noise(x);
		return total/n.length;
	}
	public float[] noiseArray(float... x){
		float[] f = new float[n.length];
		for(int i = 0; i<f.length; i++)f[i]=n[i].noise(x);
		return f;
	}
}