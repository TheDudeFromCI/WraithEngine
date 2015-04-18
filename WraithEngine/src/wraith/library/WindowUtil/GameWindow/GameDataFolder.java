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
	public BufferedImage getIcon(){
		try{ return ImageIO.read(new File(dataFolder, "Icon.png"));
		}catch(Exception exception){
			exception.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not load icon.");
			System.exit(1);
		}
		return null;
	}
}