package wraith.library.WindowUtil;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.Timer;

public class GameScreen{
	public GameScreen(String name, final GameRenderer renderer, UserInputAdapter adapter){
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		final BufferedImage buffer = new BufferedImage(screenSize.width, screenSize.height, BufferedImage.TYPE_INT_RGB);
		final Graphics2D bufferGraphics = buffer.createGraphics();
		bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		final JFrame frame = new JFrame(){
			@Override public void paint(Graphics g){
				((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g.drawImage(buffer, 0, 0, screenSize.width, screenSize.height, null);
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
				renderer.render(bufferGraphics, screenSize.width, screenSize.height);
				frame.repaint();
			}
		}).start();
	}
}