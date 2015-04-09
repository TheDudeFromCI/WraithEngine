package wraith.library.WindowUtil;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameScreen{
	public GameScreen(String name, final JPanel panel, UserInputAdapter adapter){
		JFrame frame = new JFrame();
		frame.setTitle(name);
		frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		frame.addMouseListener(adapter);
		frame.addMouseMotionListener(adapter);
		frame.addMouseWheelListener(adapter);
		frame.addKeyListener(adapter);
		frame.setVisible(true);
		new Timer().schedule(new TimerTask(){
			public void run(){ panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight()); }
		}, 16, 16);
	}
}