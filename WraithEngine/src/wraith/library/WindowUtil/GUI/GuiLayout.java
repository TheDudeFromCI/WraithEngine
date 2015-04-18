package wraith.library.WindowUtil.GUI;

import java.util.ArrayList;

public interface GuiLayout{
	public void setParentDimensions(int x, int y, int width, int height);
	public void validateComponents(ArrayList<GuiComponent> components);
	public int getMaxChildren();
}