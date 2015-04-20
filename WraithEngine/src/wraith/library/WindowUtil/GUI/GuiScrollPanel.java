package wraith.library.WindowUtil.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
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
		if(totalScreen.getHeight()<bufferHeight){
			g.setColor(Color.white);
			g.fillRect(0, 0, bufferWidth, bufferHeight);
		}
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
	@Override public void mouseWheelMoved(MouseWheelEvent e){
		int move = e.getScrollAmount();
		if(move<0)scrollPosition=Math.max(scrollPosition-move, 0);
		else scrollPosition=Math.max(totalScreen.getHeight()-bufferHeight-(scrollPosition+move), 0);
	}
}