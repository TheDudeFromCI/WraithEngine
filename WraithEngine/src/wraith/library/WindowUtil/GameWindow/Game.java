package wraith.library.WindowUtil.GameWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import wraith.library.WindowUtil.ImageWindow;

public class Game{
	private GameDataFolder gameDataFolder;
	private GameScreen screen;
	private GameThread thread;
	private GameRenderer gameRenderer;
	public Game(String title, File dataFolder){
		gameDataFolder=new GameDataFolder(dataFolder);
		BufferedImage icon = gameDataFolder.getIcon();
		ImageWindow imageWindow = new ImageWindow(gameDataFolder.getImageWindow());
		imageWindow.setIconImage(icon);
		imageWindow.setTitle(title);
		imageWindow.addFadeTimer(40, 70, 40, 20);
		try{ Thread.sleep(3000);
		}catch(Exception e){}
		screen=new GameScreen(title, icon, gameRenderer, null);
		thread=new GameThread();
	}
	public void setGameRenderer(GameRenderer gameRenderer){
		this.gameRenderer=gameRenderer;
		screen.setGameRenderer(gameRenderer);
	}
	public GameScreen getScreen(){ return screen; }
	public GameDataFolder getFolder(){ return gameDataFolder; }
	public GameThread getGameThread(){ return thread; }
	public GameRenderer getGameRenderer(){ return gameRenderer; }
	public int getScreenWidth(){ return screen.getRenderSize().width; }
	public int getScreenHeight(){ return screen.getRenderSize().height; }
	public int getRenderX(){ return screen.getRenderX(); }
	public int getRenderY(){ return screen.getRenderY(); }
}