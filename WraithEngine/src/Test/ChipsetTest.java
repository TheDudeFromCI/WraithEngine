package Test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wraith.library.WorldManagement.TileGrid.Chipset;

public class ChipsetTest{
	public static void main(String[] args)throws Exception{
		JFrame frame = new JFrame();
		frame.setTitle("Chipset Test");
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Chipset chipset = new Chipset(ImageIO.read(new File("C:/Users/Phealoon/Desktop/chipset.png")), 16);
		JPanel panel = new JPanel(){
			@Override public void paint(Graphics g){
				for(int x = 0; x<chipset.getCols(); x++)for(int y = 0; y<chipset.getRows(); y++)g.drawImage(chipset.getTile(x, y), x*16, y*16, null);
				g.dispose();
			}
		};
		panel.setPreferredSize(new Dimension(16*chipset.getCols(), 16*chipset.getRows()));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}