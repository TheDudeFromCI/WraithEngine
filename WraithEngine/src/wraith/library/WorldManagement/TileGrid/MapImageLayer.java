package wraith.library.WorldManagement.TileGrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class MapImageLayer{
	private BufferedImage staticImage;
	private Graphics2D g;
	private final Map map;
	private final int y;
	public MapImageLayer(Map map, int y){
		this.map=map;
		this.y=y;
		repaint();
	}
	public void repaint(){
		if(staticImage==null||staticImage.getWidth()!=map.getCameraWidth()||staticImage.getHeight()!=map.getCameraHeight()){
			if(g!=null)g.dispose();
			staticImage=new BufferedImage(map.getCameraWidth(), map.getCameraHeight(), BufferedImage.TYPE_INT_ARGB);
			g=staticImage.createGraphics();
			g.setBackground(new Color(0, 0, 0, 0));
		}else g.clearRect(0, 0, staticImage.getWidth(), staticImage.getHeight());
		Tile tile;
		TileMaterial tileMaterial;
		int tileX, tileZ;
		int imageWidth = staticImage.getWidth();
		int imageHeight = staticImage.getHeight();
		int lowX = Math.max(map.getCameraX()/map.getCameraScale(), 0);
		int lowZ = Math.max(map.getCameraZ()/map.getCameraScale(), 0);
		int highX = Math.min(lowX+imageWidth/map.getCameraScale(), map.getSizeX()-1)+1;
		int highZ = Math.min(lowZ+imageHeight/map.getCameraScale(), map.getSizeZ()-1);
		for(int x = lowX; x<=highX; x++){
			tileX=x*map.getCameraScale()-map.getCameraX();
			for(int z = lowZ; z<=highZ; z++){
				tile=map.getTileAt(x, y, z);
				if(tile==null)continue;
				tileZ=z*map.getCameraScale()-map.getCameraZ();
				tileMaterial=tile.getMaterial();
				g.drawImage(tileMaterial.getImage(), tileX, tileZ, map.getCameraScale(), map.getCameraScale(), null);
			}
		}
	}
	public void render(Graphics2D g){ g.drawImage(staticImage, 0, 0, null); }
}