package wraith.library.RandomGeneration;

import java.util.ArrayList;
import wraith.library.WorldManagement.Biome;

public class BiomeGenerator{
	private NoiseGenerator n1;
	private NoiseGenerator n2;
	private ArrayList<Biome> biomes = new ArrayList<>();
	public BiomeGenerator(long[] seeds, float scale){
		n1=new NoiseGenerator(seeds[0], true);
		n2=new NoiseGenerator(seeds[1], true);
		n1.setSmoothness(n1.getSmoothness()*scale);
		n2.setSmoothness(n2.getSmoothness()*scale);
	}
	public Biome biome(float... cords){
		if(biomes.size()==0)throw new IllegalStateException("No biomes loaded!");
		float a1 = n1.noise(cords);
		float a2 = n2.noise(cords);
		Biome c = null;
		float s = -1;
		for(Biome b : biomes){
			float f = b.similarity(a1, a2)*b.rarity;
			if(f>s){
				s=f;
				c=b;
			}
		}
		return c;
	}
	public BiomeGenerator(float scale){ this(randomizeSeeds(), scale); }
	public void addBiome(Biome biome){ biomes.add(biome); }
	public void removeBiome(Biome biome){ biomes.remove(biome); }
	public void clearBiomes(){ biomes.clear(); }
	public int getBiomeCount(){ return biomes.size(); }
	public int indexOf(Biome b){ return biomes.indexOf(b); }
	public ArrayList<Biome> getBiomes(){ return biomes; }
	private static long[] randomizeSeeds(){
		long[] l = new long[2];
		for(int i = 0; i<l.length; i++)l[i]=(long)(Math.random()*Long.MAX_VALUE);
		return l;
	}
}