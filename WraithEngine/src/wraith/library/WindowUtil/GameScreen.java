package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameScreen{
	private int x, y, nx, ny;
	private Color backgroundColor = Color.black;
	private final Dimension renderSize;
	private final Dimension screenSize;
	public GameScreen(String name, final GameRenderer renderer, UserInputAdapter adapter){
		screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		renderSize=new Dimension(screenSize);
		final JFrame frame = new JFrame();
		JPanel panel = new JPanel(){
			@Override public void paintComponent(Graphics g){
				g.setColor(backgroundColor);
				g.fillRect(0, 0, x, screenSize.height);
				g.fillRect(x, 0, renderSize.width, y);
				g.fillRect(x, ny, renderSize.width, y);
				g.fillRect(nx, 0, x, screenSize.height);
				renderer.render((Graphics2D)g, x, y, renderSize.width, renderSize.height);
				g.dispose();
			}
		};
		frame.add(panel);
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
		new Timer().scheduleAtFixedRate(new TimerTask(){
			public void run(){ frame.repaint(); }
		}, 20, 20);
	}
	public void setRenderSize(int width, int height, boolean scale){
		if(scale){
			if(Math.abs(screenSize.width-width)>Math.abs(screenSize.height-height)){
				double scaleSize = screenSize.height/(double)height;
				height=screenSize.height;
				width*=scaleSize;
			}else{
				double scaleSize = screenSize.width/(double)width;
				width=screenSize.width;
				height*=scaleSize;
			}
		}
		renderSize.width=width;
		renderSize.height=height;
		x=(screenSize.width-width)/2;
		y=(screenSize.height-height)/2;
		nx=x+renderSize.width;
		ny=y+renderSize.height;
	}
	public void setBackgroundColor(Color color){ backgroundColor=color; }
}