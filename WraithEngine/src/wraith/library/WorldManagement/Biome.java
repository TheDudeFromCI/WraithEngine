package wraith.library.WorldManagement;

public class Biome{
	public final String name;
	public float temperature, humidity, mapHeight, heightRoughness, plantLife, rarity;
	public float similarity(float temperature, float humidity){
		float a = 0;
		a+=this.temperature==temperature?1:Math.min(this.temperature, temperature)/Math.max(this.temperature, temperature);
		a+=this.humidity==humidity?1:Math.min(this.humidity, humidity)/Math.max(this.humidity, humidity);
		a+=this.heightRoughness==heightRoughness?1:Math.min(this.heightRoughness, heightRoughness)/Math.max(this.heightRoughness, heightRoughness);
		return a/3;
	}
	public Biome(String name){ this.name=name; }
	public static Biome randomize(String name){
		Biome biome = new Biome(name);
		biome.temperature=(float)Math.random();
		biome.humidity=(float)Math.random();
		biome.mapHeight=(float)Math.random();
		biome.heightRoughness=(float)Math.random();
		biome.plantLife=(float)Math.random();
		return biome;
	}
}