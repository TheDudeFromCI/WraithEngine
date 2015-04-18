package wraith.library.WindowUtil.GameWindow;

import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class GameDataFolder{
	private File dataFolder;
	public GameDataFolder(File dataFolder){
		if(!dataFolder.exists()){
			JOptionPane.showMessageDialog(null, "Game assets not found.");
			System.exit(1);
		}
		this.dataFolder=dataFolder;
	}
	public BufferedImage getImage(String name){
		try{ return ImageIO.read(getFile(name));
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	public File getFile(String name){ return new File(dataFolder, name); }
	public BufferedImage getIcon(){ return getImage("Icon.png"); }
}