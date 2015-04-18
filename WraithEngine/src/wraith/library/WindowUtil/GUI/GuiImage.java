package wraith.library.WindowUtil.GUI;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class GuiImage extends GuiComponent{
	private final Dimension size = new Dimension();
	private final Point location = new Point();
	private final BufferedImage buf;
	private final boolean stretch;
	public GuiImage(GuiContainer parent, BufferedImage buf, boolean stretch){
		super(parent);
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
	public void render(Graphics2D g){ g.drawImage(buf, location.x, location.y, size.width, size.height, null); }
}