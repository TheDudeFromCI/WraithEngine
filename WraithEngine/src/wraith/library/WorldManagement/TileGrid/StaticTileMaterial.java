package wraith.library.WorldManagement.TileGrid;

import java.awt.image.BufferedImage;

public class StaticTileMaterial implements TileMaterial{
	private BufferedImage img;
	public StaticTileMaterial(BufferedImage img){ this.img=img; }
	public BufferedImage getImage(){ return img; }
	public boolean isAnimated(){ return false; }
}