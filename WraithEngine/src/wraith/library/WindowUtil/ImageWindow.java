package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ImageWindow extends JFrame{
	private BufferedImage img;
	public ImageWindow(BufferedImage image){
		img=image;
		init();
		setVisible(true);
	}
	private void init(){
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(img.getWidth(), img.getHeight());
		setLocationRelativeTo(null);
		setBackground(new Color(0, 0, 0, 0));
		setAlwaysOnTop(true);
		add(new JPanel(){
			@Override public void paint(Graphics g){
				g.drawImage(img, 0, 0, this);
				g.dispose();
			}
		});
	}
}