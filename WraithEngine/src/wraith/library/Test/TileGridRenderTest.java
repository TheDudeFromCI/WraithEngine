package wraith.library.Test;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wraith.library.WorldManagement.TileGrid.Map;

public class TileGridRenderTest{
	public static void main(String[] args){
		JFrame frame = new JFrame();
		frame.setTitle("Conquest RTS");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WorldPopTest worldPopulator = new WorldPopTest();
		final Map map = new Map(15, 2, 15, worldPopulator);
		map.setCameraScale(32);
		final JPanel panel = new JPanel(){
			@Override public void paint(Graphics g){
				map.render((Graphics2D)g);
				g.dispose();
			}
		};
		frame.add(panel);
		panel.setPreferredSize(new Dimension(480, 480));
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		while(true)frame.repaint();
	}
}