package wraith.library.WorldManagement.TileGrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import wraith.library.MiscUtil.ImageUtil;

public class ImageLayerStack{
	private BufferedImage staticImage;
	private Graphics2D g;
	private final Map map;
	private final Object LOCK = true;
	public void repaint(){
		for(int i = 0; i<map.getImageLayers().length; i++)map.getImageLayers()[i].repaint();
		boolean newGraphics = false;
		if(staticImage==null||staticImage.getWidth()!=map.getCameraRealWidth()||staticImage.getHeight()!=map.getCameraRealHeight()){
			if(g!=null)g.dispose();
			staticImage=ImageUtil.getBestFormat(map.getCameraRealWidth(), map.getCameraRealHeight());
			g=staticImage.createGraphics();
			g.setBackground(new Color(0, 0, 0, 0));
			newGraphics=true;
		}
		synchronized(LOCK){
			if(newGraphics){
				if(map.getPanoramic()!=null)map.getPanoramic().render(g, staticImage.getWidth(), staticImage.getHeight());
			}else{
				if(map.getPanoramic()==null)g.clearRect(0, 0, staticImage.getWidth(), staticImage.getHeight());
				else map.getPanoramic().render(g, staticImage.getWidth(), staticImage.getHeight());
			}
			for(int i = 0; i<map.getImageLayers().length; i++)map.getImageLayers()[i].render(g);
		}
	}
	public ImageLayerStack(Map map){ this.map=map; }
	public void render(Graphics2D g, int x, int y, int width, int height){ synchronized(LOCK){ g.drawImage(staticImage, x, y, width, height, null); } }
	public BufferedImage getCurrentImage(){ return staticImage; }
}