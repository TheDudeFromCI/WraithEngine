package Test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wraith.library.RandomGeneration.IslandSquare;
import wraith.library.RandomGeneration.IslandGenerator;

public class IslandGeneratorTest{
	public static void main(String[] args){
		final int size = 300;
		final IslandGenerator gen = new IslandGenerator(IslandGenerator.generateSeeds(), size, size);
		JFrame frame = new JFrame();
		JPanel panel = new JPanel(){
			@Override public void paintComponent(Graphics g){
				IslandSquare s;
				for(int x = 0; x<size; x++){
					for(int y = 0; y<size; y++){
						s=gen.getSquare(x, y);
						g.setColor(s.isLand?new Color(0, 125+(int)(130.0*s.height), 0):Color.BLUE);
						g.fillRect(x*2, y*2, 2, 2);
					}
				}
				g.dispose();
			}
		};
		panel.setPreferredSize(new Dimension(size*2, size*2));
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setTitle("Island Generator Test");
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}