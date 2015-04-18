package wraith.library.WindowUtil.GameWindow;

import java.io.File;
import wraith.library.WindowUtil.UserInputAdapter;

public abstract class Game{
	private GameDataFolder gameDataFolder;
	private GameScreen screen;
	private GameThread thread;
	private UserInputAdapter inputAdapter;
	private GameRenderer gameRenderer;
	public Game(String title, File dataFolder){
		gameDataFolder=new GameDataFolder(dataFolder);
		screen=new GameScreen(title, gameDataFolder.getIcon(), gameRenderer, inputAdapter);
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