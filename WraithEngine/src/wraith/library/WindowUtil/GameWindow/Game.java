package wraith.library.WindowUtil.GameWindow;

import java.io.File;

public abstract class Game{
	private GameDataFolder gameDataFolder;
	private GameScreen screen;
	private GamePanel panel;
	private GameThread thread;
	public Game(String title, File dataFolder){
		gameDataFolder=new GameDataFolder(dataFolder);
		screen=new GameScreen(title, gameDataFolder.getIcon(), panel, panel);
		thread=new GameThread();
	}
	public GameScreen getScreen(){ return screen; }
	public GameDataFolder getGameDataFolder(){ return gameDataFolder; }
	public GamePanel getPanel(){ return panel; }
	public GameThread getGameThread(){ return thread; }
}