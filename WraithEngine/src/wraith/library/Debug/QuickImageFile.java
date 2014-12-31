package wraith.library.Debug;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class QuickImageFile{
	private BufferedImage b;
	private String name;
	private Graphics2D g;
	private boolean disposed = false;
	public QuickImageFile(String name, int w, int h){
		this.name=name;
		b=new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		g=b.createGraphics();
	}
	public void save(){
		if(disposed)throw new IllegalStateException("Object already disposed!");
		disposed=true;
		g.dispose();
		try{
			File f = new File(name+".png");
			ImageIO.write(b, "PNG", f);
		}catch(Exception exception){ exception.printStackTrace(); }
		g=null;
		b=null;
	}
	public void color(Color c){ g.setColor(c); }
	public void line(int x1, int y1, int x2, int y2){ g.drawLine(x1, y1, x2, y2); }
	public void set(int x, int y){ g.fillRect(x, y, 1, 1); }
	public void text(String text, int x, int y){ g.drawString(text, x, y); }
	public void fill(int x, int y, int w, int h){ g.fillRect(x, y, w, h); }
}