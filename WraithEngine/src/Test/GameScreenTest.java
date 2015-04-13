package Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import wraith.library.WindowUtil.UserInputAdapter;
import wraith.library.WindowUtil.GameRenderer;
import wraith.library.WindowUtil.GameScreen;

public class GameScreenTest{
	public static void main(String[] args){
		GameScreen game = new GameScreen("Game Screen Test", new GameRenderer(){
			public void render(Graphics2D g, int x, int y, int width, int height){
				g.setColor(Color.lightGray);
				g.fillRect(x, y, width, height);
				g.setColor(Color.black);
				g.drawString(x+", "+y, x+3, y+13);
				g.drawString(width+", "+height, x+3, y+26);
			}
		}, new UserInputAdapter(){
			@Override public void keyPressed(KeyEvent e){ if(e.getKeyCode()==KeyEvent.VK_F12)System.exit(0); }
		});
		game.setRenderSize(320, 240, true);
	}
}