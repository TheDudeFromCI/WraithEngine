package wraith.library.WindowUtil.GUI;

import java.awt.Graphics2D;

public abstract class GuiComponent{
	protected int x, y;
	protected int width, height;
	public void setLocation(int x, int y){
		this.x=x;
		this.y=y;
	}
	public void setSize(int width, int height){
		this.width=width;
		this.height=height;
	}
	public void setSizeAndLocation(int x, int y, int width, int height){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
	}
	public int getX(){ return x; }
	public int getY(){ return y; }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public abstract void render(Graphics2D g);
}