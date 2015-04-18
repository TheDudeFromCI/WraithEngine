package wraith.library.WindowUtil.GUI;

import java.awt.Graphics2D;

public class GuiFrame extends GuiContainer{
	public void render(Graphics2D g){ for(int i = 0; i<components.size(); i++)components.get(i).render(g); }
}