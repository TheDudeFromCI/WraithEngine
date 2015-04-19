package wraith.library.WindowUtil.GUI;

import java.awt.Graphics2D;

public class GuiFrame extends GuiContainer{
	public GuiFrame(int bufferWidth, int bufferHeight){
		super(null, bufferWidth, bufferHeight);
		setSizeAndLocation(0, 0, bufferWidth, bufferHeight);
	}
	public void render(Graphics2D g){}
}