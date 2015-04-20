package wraith.library.WindowUtil.GUI;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class GuiImage extends GuiComponent{
	protected Dimension size = new Dimension();
	protected Point location = new Point();
	private BufferedImage buf;
	private boolean stretch;
	public GuiImage(GuiContainer parent, BufferedImage buf, boolean stretch){
		super(parent, buf.getWidth(), buf.getHeight());
		this.buf=buf;
		this.stretch=stretch;
		calculateAdjustments();
	}
	private void calculateAdjustments(){
		if(stretch){
			size.width=bufferWidth;
			size.height=bufferHeight;
			location.x=0;
			location.y=0;
		}else{
			if(Math.abs(bufferWidth-buf.getWidth())>Math.abs(bufferHeight-buf.getHeight())){
				double scaleSize = bufferHeight/(double)buf.getHeight();
				size.height=bufferHeight;
				size.width*=scaleSize;
			}else{
				double scaleSize = bufferWidth/(double)buf.getWidth();
				size.width=bufferWidth;
				size.height*=scaleSize;
			}
			location.x=(bufferWidth-size.width)/2;
			location.y=(bufferHeight-size.height)/2;
		}
	}
	@Override public void setSizeAndLocation(int x, int y, int width, int height){
		super.setSizeAndLocation(x, y, width, height);
		calculateAdjustments();
	}
	@Override public boolean isWithinBounds(Point p){
		return p.x>=x&&p.y>=y&&p.x<x+width&&p.y<y+height;
	}
	@Override public void dispose(){
		super.dispose();
		buf=null;
		size=null;
		location=null;
	}
	public void render(Graphics2D g){ g.drawImage(buf, location.x, location.y, size.width, size.height, null); }
}