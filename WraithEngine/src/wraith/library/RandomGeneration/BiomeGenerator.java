package wraith.library.RandomGeneration;

import java.util.ArrayList;
import wraith.library.WorldManagement.Biome;
import wraith.library.WorldManagement.BiomeMerge;

public class BiomeGenerator{
	private NoiseGenerator n1;
	private NoiseGenerator n2;
	private ArrayList<Biome> biomes = new ArrayList<>();
	public BiomeGenerator(long[] seeds, float scale){
		if(scale<=0)throw new IllegalArgumentException("Scale must be greater then 0!");
		n1=new NoiseGenerator(seeds[0], true);
		n2=new NoiseGenerator(seeds[1], true);
		n1.setSmoothness(n1.getSmoothness()*scale);
		n2.setSmoothness(n2.getSmoothness()*scale);
	}
	public Biome getBiomeMerge(float... cords){
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
	public BiomeMerge getBiomeMerge(int smoothing, float... cords){
		if(smoothing<0)throw new IllegalArgumentException("Smoothing cannot be negative!");
		if(biomes.size()==0)throw new IllegalStateException("No biomes loaded!");
		float[] d = new float[cords.length];
		for(int a = 0; a<d.length; a++)d[a]=cords[a];
		BiomeMerge biomeMerge = new BiomeMerge();
		biomeMerge.biomes=biomes.toArray(new Biome[biomes.size()]);
		biomeMerge.percents=new float[biomes.size()];
		double s = 0;
		for(int bio = 0; bio<biomes.size(); bio++){
			for(int a = 0; a<d.length; a++){
				for(int b = -smoothing; b<=smoothing; b++){
					d[a]=cords[a]+b;
					biomeMerge.percents[bio]+=biomeMerge.biomes[bio].similarity(n1.noise(d), n2.noise(d))*biomeMerge.biomes[bio].rarity;
				}
				d[a]=cords[a];
			}
			biomeMerge.percents[bio]/=(smoothing*2+1)*d.length;
			s+=biomeMerge.percents[bio]*biomeMerge.percents[bio];
		}
		s=Math.sqrt(s);
		if(s>0)for(int a = 0; a<biomeMerge.percents.length; a++)biomeMerge.percents[a]/=s;
		return biomeMerge;
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