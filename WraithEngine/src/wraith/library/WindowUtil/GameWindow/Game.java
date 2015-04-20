package wraith.library.WindowUtil.GameWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import wraith.library.WindowUtil.ImageWindow;
import wraith.library.WindowUtil.UserInputListener;

public class Game{
	private GameDataFolder gameDataFolder;
	private GameScreen screen;
	private GameThread thread;
	private UserInputListener inputListener;
	private GameRenderer gameRenderer;
	public Game(String title, File dataFolder){
		gameDataFolder=new GameDataFolder(dataFolder);
		BufferedImage icon = gameDataFolder.getIcon();
		ImageWindow imageWindow = new ImageWindow(gameDataFolder.getImageWindow());
		imageWindow.setIconImage(icon);
		imageWindow.setTitle(title);
		try{ Thread.sleep(3000);
		}catch(Exception e){}
		imageWindow.dispose();
		screen=new GameScreen(title, icon, gameRenderer, inputListener);
		thread=new GameThread();
	}
	public void setInputAdapter(UserInputListener inputListener){
		this.inputListener=inputListener;
		screen.setUserInputAdapter(inputListener);
	}
	public void setGameRenderer(GameRenderer gameRenderer){
		this.gameRenderer=gameRenderer;
		screen.setGameRenderer(gameRenderer);
	}
	public GameScreen getScreen(){ return screen; }
	public GameDataFolder getGameDataFolder(){ return gameDataFolder; }
	public GameThread getGameThread(){ return thread; }
	public UserInputListener getInputListener(){ return inputListener; }
	public GameRenderer getGameRenderer(){ return gameRenderer; }
	public int getScreenWidth(){ return screen.getRenderSize().width; }
	public int getScreenHeight(){ return screen.getRenderSize().height; }
}