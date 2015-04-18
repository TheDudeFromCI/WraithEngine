package wraith.library.WindowUtil.GameWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import wraith.library.WindowUtil.ImageWindow;
import wraith.library.WindowUtil.UserInputAdapter;

public class Game{
	private GameDataFolder gameDataFolder;
	private GameScreen screen;
	private GameThread thread;
	private UserInputAdapter inputAdapter;
	private GameRenderer gameRenderer;
	public Game(String title, File dataFolder){
		gameDataFolder=new GameDataFolder(dataFolder);
		BufferedImage icon = gameDataFolder.getIcon();
		ImageWindow imageWindow = new ImageWindow(gameDataFolder.getImageWindow());
		try{ Thread.sleep(2500);
		}catch(Exception e){}
		imageWindow.dispose();
		screen=new GameScreen(title, icon, gameRenderer, inputAdapter);
		thread=new GameThread();
	}
	public void setInputAdapter(UserInputAdapter inputAdapter){
		this.inputAdapter=inputAdapter;
		screen.setUserInputAdapter(inputAdapter);
	}
	public void setGameRenderer(GameRenderer gameRenderer){
		this.gameRenderer=gameRenderer;
		screen.setGameRenderer(gameRenderer);
	}
	public GameScreen getScreen(){ return screen; }
	public GameDataFolder getGameDataFolder(){ return gameDataFolder; }
	public GameThread getGameThread(){ return thread; }
	public UserInputAdapter getInputAdapter(){ return inputAdapter; }
	public GameRenderer getGameRenderer(){ return gameRenderer; }
}