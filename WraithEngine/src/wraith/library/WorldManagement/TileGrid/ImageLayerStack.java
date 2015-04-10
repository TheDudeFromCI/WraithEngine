package wraith.library.WorldManagement.TileGrid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageLayerStack{
	private BufferedImage staticImage;
	private Graphics2D g;
	private final Map map;
	private final Object LOCK = true;
	public ImageLayerStack(Map map){
		this.map=map;
		repaint();
	}
	public void repaint(){
		for(int i = 0; i<map.getImageLayers().length; i++)map.getImageLayers()[i].repaint();
		boolean newGraphics = false;
		if(staticImage==null||staticImage.getWidth()!=map.getCameraWidth()||staticImage.getHeight()!=map.getCameraHeight()){
			if(g!=null)g.dispose();
			staticImage=new BufferedImage(map.getCameraWidth(), map.getCameraHeight(), BufferedImage.TYPE_INT_ARGB);
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
	public void render(Graphics2D g){ synchronized(LOCK){ g.drawImage(staticImage, 0, 0, null); } }
}