package wraith.library.WorldManagement.TileGrid;

public class Tile{
	private final int x, y, z;
	private final TileMaterial material;
	public Tile(TileMaterial material, int x, int y, int z){
		this.material=material;
		this.x=x;
		this.y=y;
		this.z=z;
	}
	public TileMaterial getMaterial(){ return material; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getZ(){ return z; }
}