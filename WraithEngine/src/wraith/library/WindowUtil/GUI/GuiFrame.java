package wraith.library.WindowUtil.GUI;

import java.awt.Point;

public class GuiFrame extends GuiPanel{
	private Point offset = new Point();
	public GuiFrame(int bufferWidth, int bufferHeight){
		super(null, bufferWidth, bufferHeight);
		setSizeAndLocation(0, 0, bufferWidth, bufferHeight);
	}
	@Override public Point getOffset(){ return offset; }
}