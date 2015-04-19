package wraith.library.WindowUtil.GUI;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class GuiImage extends GuiComponent{
	protected final Dimension size = new Dimension();
	protected final Point location = new Point();
	private final BufferedImage buf;
	private final boolean stretch;
	public GuiImage(GuiContainer parent, BufferedImage buf, boolean stretch){
		super(parent, buf.getWidth(), buf.getHeight());
		this.buf=buf;
		this.stretch=stretch;
		calculateAdjustments();
	}
	private void calculateAdjustments(){
		if(stretch){
			size.width=width;
			size.height=height;
			location.x=x;
			location.y=y;
		}else{
			if(Math.abs(width-buf.getWidth())>Math.abs(height-buf.getHeight())){
				double scaleSize = height/(double)buf.getHeight();
				size.height=height;
				size.width*=scaleSize;
			}else{
				double scaleSize = width/(double)buf.getWidth();
				size.width=width;
				size.height*=scaleSize;
			}
			location.x=(width-size.width)/2;
			location.y=(height-size.height)/2;
		}
	}
	@Override public void setSizeAndLocation(int x, int y, int width, int height){
		super.setSizeAndLocation(x, y, width, height);
		calculateAdjustments();
	}
	@Override public boolean isWithinBounds(Point p){
		if(stretch)return super.isWithinBounds(p);
		return p.x>=x+location.x&&p.y>=y+location.y&&p.x<x+location.x+size.width&&p.y<y+location.y+size.height;
	}
	public void render(Graphics2D g){ g.drawImage(buf, location.x, location.y, size.width, size.height, null); }
}