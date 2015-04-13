package wraith.library.WindowUtil;

import java.awt.Graphics2D;

public interface GameRenderer{
	public void render(Graphics2D g, int x, int y, int width, int height);
}