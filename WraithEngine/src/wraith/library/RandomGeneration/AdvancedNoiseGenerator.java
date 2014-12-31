package wraith.library.RandomGeneration;

public class AdvancedNoiseGenerator{
	private NoiseGenerator[] n;
	private int buffer;
	public AdvancedNoiseGenerator(int blending, boolean onlyPositive, float smoothness, float detail, int buffer){
		if(blending<1)throw new IllegalArgumentException("Blending must be at least 1!");
		if(buffer<1)throw new IllegalArgumentException("Buffer must be at least 1!");
		n=new NoiseGenerator[blending*buffer];
		this.buffer=buffer;
		for(int i = 0; i<n.length; i++){
			n[i]=new NoiseGenerator(onlyPositive);
			n[i].setSmoothness(Math.max(n[i].getSmoothness()*smoothness, 0.0001f));
			n[i].setDetail(Math.max(Math.round(n[i].getDetail()*detail), 1));
		}
	}
	public float noise(float... x){
		float total = 0;
		for(NoiseGenerator a : n)total+=a.noise(x);
		return total/n.length;
	}
	public float[] noiseArray(float... x){
		float[] f = new float[n.length/buffer];
		for(int i = 0; i<f.length; i++)for(int j = 0; j<buffer; j++)f[i]+=n[i*buffer+j].noise(x);
		return f;
	}
	public AdvancedNoiseGenerator(int blending, boolean onlyPositive, float smoothness, float detail){ this(blending, onlyPositive, smoothness, detail, 1); }
	public AdvancedNoiseGenerator(int blending, boolean onlyPositive, float smoothness){ this(blending, onlyPositive, smoothness, 1, 1); }
	public AdvancedNoiseGenerator(int blending, boolean onlyPositive){ this(blending, onlyPositive, 1, 1, 1); }
	public AdvancedNoiseGenerator(int blending){ this(blending, false, 1, 1, 1); }
	public AdvancedNoiseGenerator(){ this(1, false, 1, 1, 1); }
	public AdvancedNoiseGenerator(int blending, int buffer){ this(blending, false, 1, 1, buffer); }
	public AdvancedNoiseGenerator(int blending, int buffer, float smoothness){ this(blending, false, smoothness, 1, buffer); }
	public AdvancedNoiseGenerator(int blending, int buffer, float smoothness, boolean onlyPositive){ this(blending, onlyPositive, smoothness, 1, buffer); }
}