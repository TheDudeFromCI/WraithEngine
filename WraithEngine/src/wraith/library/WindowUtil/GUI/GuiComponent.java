package wraith.library.WindowUtil.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.image.BufferedImage;
import wraith.library.MiscUtil.ImageUtil;
import wraith.library.WindowUtil.UserInputListener;

public abstract class GuiComponent implements UserInputListener{
	protected int x, y;
	protected int width, height;
	private boolean disposed;
	protected GuiContainer parent;
	protected boolean needsRepaint = true;
	private BufferedImage staticImage;
	private Graphics2D g;
	protected final int bufferWidth, bufferHeight;
	private final Object THREAD_LOCK = new Object();
	public GuiComponent(GuiContainer parent, int bufferWidth, int bufferHeight){
		this.parent=parent;
		this.bufferWidth=bufferWidth;
		this.bufferHeight=bufferHeight;
		staticImage=ImageUtil.getBestFormat(bufferWidth, bufferHeight);
		g=staticImage.createGraphics();
		g.setBackground(new Color(0, 0, 0, 0));
	}
	public void setSizeAndLocation(int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		setNeedsRepaint();
	}
	public void addListeners(Component c){
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
		c.addMouseWheelListener(this);
		c.addKeyListener(this);
	}
	public void removeListeners(Component c){
		c.removeMouseListener(this);
		c.removeMouseMotionListener(this);
		c.removeMouseWheelListener(this);
		c.removeKeyListener(this);
	}
	public void dispose(){
		if(disposed)return;
		disposed=true;
		if(parent!=null){
			parent.components.remove(this);
			parent=null;
		}
		g.dispose();
		g=null;
		staticImage=null;
	}
	public void setNeedsRepaint(){
		needsRepaint=true;
		if(parent!=null)parent.setNeedsRepaint();
	}
	public void update(){
		synchronized(THREAD_LOCK){
			if(!needsRepaint)return;
			g.clearRect(0, 0, width, height);
			render(g);
			setRepainted();
		}
	}
	public boolean isWithinBounds(Point p){
		Point off = getOffset();
		if(off==null)return p.x>=x&&p.y>=y&&p.x<width+x&&p.y<height+y;
		return p.x-off.x>=x&&p.y-off.y>=y&&p.x-off.x<width+x&&p.y-off.y<height+y;
	}
	public Point getOffset(){ return parent==null?null:parent.getOffset(); }
	public BufferedImage getPane(){ synchronized(THREAD_LOCK){ return staticImage; } }
	public void setRepainted(){ needsRepaint=false; }
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public boolean isDisposed(){ return disposed; }
	public boolean needsRepaint(){ return needsRepaint; }
	public abstract void render(Graphics2D g);
	public void keyPressed(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	public void mouseWheelMoved(MouseWheelEvent e){}
	public void mouseDragged(MouseEvent e){}
	public void mouseMoved(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
}