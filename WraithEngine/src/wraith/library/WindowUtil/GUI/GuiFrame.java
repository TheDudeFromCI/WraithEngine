package wraith.library.WindowUtil.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import wraith.library.MiscUtil.ImageUtil;

public class GuiFrame extends GuiContainer{
	private BufferedImage staticImage;
	private Graphics2D g;
	public GuiFrame(int bufferWidth, int bufferHeight){
		super(null);
		staticImage=ImageUtil.getBestFormat(bufferWidth, bufferHeight);
		g=staticImage.createGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
		setSizeAndLocation(0, 0, bufferWidth, bufferHeight);
	}
	public void update(){
		if(!needsRepaint)return;
		needsRepaint=false;
		g.clearRect(0, 0, width, height);
		for(int i = 0; i<components.size(); i++)components.get(i).render(g);
	}
	@Override public void dispose(){
		super.dispose();
		g.dispose();
		g=null;
		staticImage=null;
	}
	public void render(Graphics2D g){}
	public BufferedImage getStaticImage(){ return staticImage; }
}