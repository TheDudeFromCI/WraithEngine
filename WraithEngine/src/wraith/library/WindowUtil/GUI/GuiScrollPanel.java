package wraith.library.WindowUtil.GUI;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
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
		int panelHeight = Math.min(bufferHeight, totalScreen.getHeight());
		g.drawImage(totalScreen, 0, 0, bufferWidth, panelHeight, 0, scrollPosition, bufferWidth, panelHeight+scrollPosition, null);
	}
	private void renderScreen(){
		int intenedSize = Math.max(entries.size()*entryHeight, 1);
		if(totalScreen==null||totalScreen.getHeight()!=intenedSize){
			if(graphics!=null)graphics.dispose();
			totalScreen=ImageUtil.getBestFormat(bufferWidth, intenedSize);
			graphics=totalScreen.createGraphics();
		}
		for(int i = 0; i<entries.size(); i++)entries.get(i).renderEntry(graphics, 0, i*entryHeight, totalScreen.getWidth()-1, entryHeight-1);
	}
	@Override public void mouseWheelMoved(MouseWheelEvent e){
		int move = e.getUnitsToScroll();
		scrollPosition+=move;
		if(scrollPosition<0)scrollPosition=0;
		if(scrollPosition>totalScreen.getHeight()-bufferHeight)scrollPosition=Math.max(totalScreen.getHeight()-bufferHeight, 0);
		setNeedsRepaint();
	}
	@Override public void mouseClicked(MouseEvent e){
		Point p = e.getPoint();
		if(!isWithinBounds(p))return;
		Point off = getOffset();
		if(off!=null)p.y-=off.y;
		p.y-=y;
		p.y+=scrollPosition;
		int index = p.y/entryHeight;
		if(index>=entries.size())return;
		entries.get(index).onEntryClick();
	}
	public void addScrollPanelEntry(ScrollPaneEntry entry){
		entries.add(entry);
		setNeedsRepaint();
	}
	public ScrollPaneEntry getScrollPaneEntry(int index){ return entries.get(index); }
	public int getIndexOfEntry(ScrollPaneEntry entry){ return entries.indexOf(entry); }
	public int getListSize(){ return entries.size(); }
}