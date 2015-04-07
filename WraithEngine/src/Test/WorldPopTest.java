package Test;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import wraith.library.WorldManagement.TileGrid.Tile;
import wraith.library.WorldManagement.TileGrid.TileMaterial;
import wraith.library.WorldManagement.TileGrid.WorldPopulator;

public class WorldPopTest implements WorldPopulator{
	private TileMaterial grass;
	private TileMaterial waterfall;
	private TileMaterial rock;
	private BufferedImage grassImage, w1, w2, w3, w4, r;
	public WorldPopTest(){
		try{
			grassImage=ImageIO.read(new File("C:/Users/Phealoon/Desktop/grass.jpg"));
			w1=ImageIO.read(new File("C:/Users/Phealoon/Desktop/waterfall1.png"));
			w2=ImageIO.read(new File("C:/Users/Phealoon/Desktop/waterfall2.png"));
			w3=ImageIO.read(new File("C:/Users/Phealoon/Desktop/waterfall3.png"));
			w4=ImageIO.read(new File("C:/Users/Phealoon/Desktop/waterfall4.png"));
			r=ImageIO.read(new File("C:/Users/Phealoon/Desktop/rock.png"));
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(1);
		}
		grass=new TileMaterial(){
			public BufferedImage getImage(){ return grassImage; }
			public boolean isAnimated(){ return false; }
		};
		waterfall=new TileMaterial(){
			@Override public BufferedImage getImage(){
				int frame = (int)((System.currentTimeMillis()/100)%4);
				if(frame==0)return w1;
				if(frame==1)return w2;
				if(frame==2)return w3;
				return w4;
			}
			@Override public boolean isAnimated(){ return true; }
		};
		rock=new TileMaterial(){
			public BufferedImage getImage(){ return r; }
			public boolean isAnimated(){ return false; }
		};
	}
	public void generate(Tile[][][] tiles){ for(int x = 0; x<tiles.length; x++)for(int y = 0; y<tiles[x].length; y++)for(int z = 0; z<tiles[x][y].length; z++)tiles[x][y][z]=getIdealTile(x, y, z); }
	private Tile getIdealTile(int x, int y, int z){ return y==1?(x==3&&z==3?new Tile(rock, x, y, z):null):new Tile(x>=4&&x<11?waterfall:grass, x, y, z); }
}