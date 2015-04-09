package wraith.library.WorldManagement.TileGrid;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MapImageLayer{
	private BufferedImage staticImage;
	private Graphics2D g;
	private final Map map;
	private final int y;
	private final ArrayList<Tile> animatedTiles = new ArrayList<>();
	public MapImageLayer(Map map, int y){
		this.map=map;
		this.y=y;
		repaint();
	}
	public void repaint(){
		if(staticImage==null||staticImage.getWidth()!=map.getCameraWidth()||staticImage.getHeight()!=map.getCameraHeight()){
			staticImage=new BufferedImage(map.getCameraWidth(), map.getCameraHeight(), BufferedImage.TYPE_INT_ARGB);
			g=staticImage.createGraphics();
		}
		animatedTiles.clear();
		Tile tile;
		TileMaterial tileMaterial;
		BufferedImage img;
		int lowX = Math.max(map.getCameraX()/map.getCameraScale(), 0);
		int lowZ = Math.max(map.getCameraZ()/map.getCameraScale(), 0);
		int highX = (map.getCameraWidth()-map.getCameraX())/map.getCameraScale();
		int highZ = (map.getCameraHeight()-map.getCameraZ())/map.getCameraScale();
		for(int x = lowX; x<highX; x++){
			for(int z = lowZ; z<highZ; z++){
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