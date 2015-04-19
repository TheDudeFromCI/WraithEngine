package wraith.library.WindowUtil.GUI;

public class GuiFrame extends GuiPanel{
	public GuiFrame(int bufferWidth, int bufferHeight){
		super(null, bufferWidth, bufferHeight);
		setSizeAndLocation(0, 0, bufferWidth, bufferHeight);
	}
}