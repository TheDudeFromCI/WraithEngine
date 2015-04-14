package wraith.library.WorldManagement.TileGrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import wraith.library.MiscUtil.ImageUtil;

public class MapImageLayer{
	private BufferedImage staticImage;
	private Graphics2D g;
	private final Map map;
	private final int y;
	public MapImageLayer(Map map, int y){
		this.map=map;
		this.y=y;
	}
	public void repaint(){
		if(staticImage==null||staticImage.getWidth()!=map.getCameraRealWidth()||staticImage.getHeight()!=map.getCameraRealHeight()){
			if(g!=null)g.dispose();
			staticImage=ImageUtil.getBestFormat(map.getCameraRealWidth(), map.getCameraRealHeight());
			g=staticImage.createGraphics();
			g.setBackground(new Color(0, 0, 0, 0));
		}else g.clearRect(0, 0, staticImage.getWidth(), staticImage.getHeight());
		Tile tile;
		int tileX;
		int imageWidth = staticImage.getWidth();
		int imageHeight = staticImage.getHeight();
		int lowX = Math.max(map.getCameraX()/map.getCameraRawScale(), 0);
		int lowZ = Math.max(map.getCameraZ()/map.getCameraRawScale(), 0);
		int highX = Math.min(lowX+imageWidth/map.getCameraRawScale()+1, map.getSizeX()-1);
		int highZ = Math.min(lowZ+imageHeight/map.getCameraRawScale(), map.getSizeZ()-1);
		for(int x = lowX; x<=highX; x++){
			tileX=x*map.getCameraRawScale()-map.getCameraX();
			for(int z = lowZ; z<=highZ; z++){
				tile=map.getTileAt(x, y, z);
				if(tile==null)continue;
				g.drawImage(tile.getMaterial().getImage(), tileX, z*map.getCameraRawScale()-map.getCameraZ(), null);
			}
		}
	}
	public void render(Graphics2D g){ g.drawImage(staticImage, 0, 0, null); }
}