package wraith.library.RandomGeneration;

import java.util.Random;

public class BiomeGenerator{
	private NoiseGenerator[] n;
	public BiomeGenerator(long[] seeds){
		n=new NoiseGenerator[seeds.length];
		Random r;
		for(int a = 0; a<seeds.length; a++){
			r=new Random(seeds[a]+100);
			n[a]=new NoiseGenerator(seeds[a], r.nextFloat()*40+10, 1, false, r.nextInt(5)+1);
		}
		//Test
	}
	public int biomeAt(float... cords){
		int highest = 0;
		float val = -10;
		float f;
		for(int a = 0; a<n.length; a++){
			f=n[a].noise(cords);
			if(f>val){
				val=f;
				highest=a;
			}
		}
		return highest;
	}
	public BiomeGenerator(int biomes){ this(randomSeeds(biomes)); }
	public BiomeGenerator(NoiseGenerator[] biomes){ n=biomes; }
	public static long[] randomSeeds(int l){
		long[] a = new long[l];
		for(int i = 0; i<l; i++)a[i]=(long)(Math.random()*Long.MAX_VALUE);
		return a;
	}
}