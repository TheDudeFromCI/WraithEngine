package wraith.library.WindowUtil.GUI;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GuiButton extends GuiImage{
	private Runnable onClick;
	public GuiButton(GuiContainer parent, BufferedImage buf, boolean stretch, Runnable onClick){
		super(parent, buf, stretch);
		this.onClick=onClick;
	}
	@Override public void dispose(){
		super.dispose();
		onClick=null;
	}
	@Override public void mouseReleased(MouseEvent e){ if(isWithinBounds(e.getPoint()))onClick.run(); }
}