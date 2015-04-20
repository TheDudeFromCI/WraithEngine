package wraith.library.WindowUtil.GUI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import wraith.library.MiscUtil.ImageUtil;

public class GuiScrollPanel extends GuiComponent{
	private int scrollPosition;
	private BufferedImage totalScreen;
	private Graphics2D graphics;
	private int entryHeight;
	private final ArrayList<ScrollPaneEntry> entries = new ArrayList();
	public GuiScrollPanel(GuiContainer parent, int bufferWidth, int bufferHeight, int entryHeight){
		super(parent, bufferWidth, bufferHeight);
		this.entryHeight=entryHeight;
	}
	public void render(Graphics2D g){
		renderScreen();
		g.drawImage(totalScreen, 0, 0, bufferWidth, Math.min(bufferHeight, totalScreen.getHeight()), 0, scrollPosition, bufferWidth, Math.min(bufferHeight, totalScreen.getHeight()), null);
	}
	private void renderScreen(){
		if(totalScreen==null||totalScreen.getHeight()!=entries.size()*entryHeight){
			if(graphics!=null)graphics.dispose();
			totalScreen=ImageUtil.getBestFormat(bufferWidth, entries.size()*entryHeight);
			graphics=totalScreen.createGraphics();
		}
		for(int i = 0; i<entries.size(); i++)entries.get(i).renderEntry(graphics, 0, i*entryHeight, totalScreen.getWidth(), entryHeight);
	}
}