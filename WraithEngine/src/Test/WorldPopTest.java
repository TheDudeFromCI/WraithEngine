package Test;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import wraith.library.WorldManagement.TileGrid.Chipset;
import wraith.library.WorldManagement.TileGrid.Tile;
import wraith.library.WorldManagement.TileGrid.TileMaterial;
import wraith.library.WorldManagement.TileGrid.WorldPopulator;

public class WorldPopTest implements WorldPopulator{
	private TileMaterial grass;
	private TileMaterial waterfall;
	private TileMaterial rock;
	private Chipset chipset;
	public WorldPopTest(){
		try{
			BufferedImage img = ImageIO.read(new File("C:/Users/Phealoon/Desktop/chipset.png"));
			chipset=new Chipset(img, 16);
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(1);
		}
		grass=new TileMaterial(){
			public BufferedImage getImage(){ return chipset.getTile(1, 10); }
		};
		waterfall=new TileMaterial(){
			@Override public BufferedImage getImage(){
				int frame = (int)((System.currentTimeMillis()/100)%4);
				if(frame==0)return chipset.getTile(4, 4);
				if(frame==1)return chipset.getTile(4, 5);
				if(frame==2)return chipset.getTile(4, 6);
				return chipset.getTile(4, 7);
			}
		};
		rock=new TileMaterial(){
			public BufferedImage getImage(){ return chipset.getTile(26, 10); }
		};
	}
	public void generate(Tile[][][] tiles){ for(int x = 0; x<tiles.length; x++)for(int y = 0; y<tiles[x].length; y++)for(int z = 0; z<tiles[x][y].length; z++)tiles[x][y][z]=getIdealTile(x, y, z); }
	private Tile getIdealTile(int x, int y, int z){ return y==1?(x==3&&z==3?new Tile(rock, x, y, z):null):new Tile(x>=4&&x<11?waterfall:grass, x, y, z); }
}