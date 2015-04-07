package wraith.library.WorldManagement.TileGrid;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapImageLayer{
	private BufferedImage staticImage;
	private final Map map;
	private final int y;
	private final ArrayList<Tile> animatedTiles = new ArrayList<>();
	public MapImageLayer(Map map, int y){
		this.map=map;
		this.y=y;
		repaint();
	}
	public void repaint(){
		staticImage=new BufferedImage(map.getSizeX()*map.getCameraScale(), map.getSizeZ()*map.getCameraScale(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = staticImage.createGraphics();
		animatedTiles.clear();
		Tile tile;
		TileMaterial tileMaterial;
		BufferedImage img;
		for(int x = 0; x<map.getSizeX(); x++){
			for(int z = 0; z<map.getSizeZ(); z++){
				tile=map.getTileAt(x, y, z);
				if(tile==null)continue;
				tileMaterial=tile.getMaterial();
				if(tileMaterial.isAnimated())animatedTiles.add(tile);
				else{
					img=tileMaterial.getImage();
					g.drawImage(img, x*map.getCameraScale(), z*map.getCameraScale(), (x+1)*map.getCameraScale(), (z+1)*map.getCameraScale(), 0, 0, img.getWidth(), img.getHeight(), null);
				}
			}
		}
		g.dispose();
	}
	public void render(Graphics2D g){
		g.drawImage(staticImage, -map.getCameraX(), -map.getCameraZ(), null);
		BufferedImage img;
		for(Tile t : animatedTiles){
			img=t.getMaterial().getImage();
			g.drawImage(img, t.getX()*map.getCameraScale()-map.getCameraX(), t.getZ()*map.getCameraScale()-map.getCameraZ(), (t.getX()+1)*map.getCameraScale()-map.getCameraX(), (t.getZ()+1)*map.getCameraScale()-map.getCameraZ(), 0, 0, img.getWidth(), img.getHeight(), null);
		}
	}
	public ArrayList<Tile> getAnimatedTiles(){ return animatedTiles; }
}