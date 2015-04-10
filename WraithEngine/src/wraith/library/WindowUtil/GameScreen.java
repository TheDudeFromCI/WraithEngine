package wraith.library.WindowUtil;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GameScreen{
	public GameScreen(String name, final GameRenderer renderer, UserInputAdapter adapter){
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final JFrame frame = new JFrame(){
			@Override public void paint(Graphics g){
				renderer.render((Graphics2D)g, screenSize.width, screenSize.height);
				g.dispose();
			}
		};
		frame.setTitle(name);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setUndecorated(true);
		frame.setSize(screenSize);
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.addMouseListener(adapter);
		frame.addMouseMotionListener(adapter);
		frame.addMouseWheelListener(adapter);
		frame.addKeyListener(adapter);
		frame.setVisible(true);
		new Timer(20, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.repaint();
			}
		}).start();
	}
}