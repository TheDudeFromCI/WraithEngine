package wraith.library.WorldManagement.TileGrid;

import java.awt.Point;
import java.util.Arrays;

public class TileGroup{
	private Point[] points;
	private Point[] tiles;
	private Chipset chipset;
	public TileGroup(Chipset chipset, Point[] points, Point[] tiles){
		this.points=Arrays.copyOf(points, points.length);
		this.tiles=Arrays.copyOf(tiles, tiles.length);
		this.chipset=chipset;
	}
	public void placeAt(Tile[][][] map, int x, int y, int z){
		for(int i = 0; i<points.length; i++){
			try{ map[x+points[i].x][y][z+points[i].y]=new Tile(chipset.getTileMaterial(tiles[i].x, tiles[i].y), x+points[i].x, y, z+points[i].y);
			}catch(ArrayIndexOutOfBoundsException exception){}
		}
	}
	public int getPointCount(){ return points.length; }
}