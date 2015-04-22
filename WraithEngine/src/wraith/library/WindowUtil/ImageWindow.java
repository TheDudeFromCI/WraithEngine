package wraith.library.WindowUtil;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import wraith.library.MiscUtil.FadeListener;
import wraith.library.MiscUtil.FadeTimer;

@SuppressWarnings("serial")
public class ImageWindow extends JFrame{
	protected BufferedImage img;
	private boolean fadeTimer;
	protected float fade;
	protected JPanel panel;
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
		add(panel=createPanel());
	}
	public void addFadeTimer(int fadeIn, int fadeStay, int fadeOut, int pingDelay){
		if(this.fadeTimer)return;
		this.fadeTimer=true;
		final FadeTimer fadeTimer = new FadeTimer(fadeIn, fadeStay, fadeOut, pingDelay);
		fadeTimer.addListener(new FadeListener(){
			public void onComplete(){ dispose(); }
			public void onFadeOutTick(){ updateFadeLevel(fadeTimer.getFadeLevel()); }
			public void onFadeInTick(){ updateFadeLevel(fadeTimer.getFadeLevel()); }
			public void onFadeInComplete(){ updateFadeLevel(fadeTimer.getFadeLevel()); }
			public void onFadeOutComplete(){}
			public void onFadeStayTick(){}
			public void onFadeStayComplete(){}
		});
		fadeTimer.start();
	}
	public void updateFadeLevel(float fade){
		this.fade=fade;
		repaint();
	}
	protected JPanel createPanel(){
		return new JPanel(){
			@Override public void paintComponent(Graphics g){
				g.setColor(getBackground());
				g.clearRect(0, 0, getWidth(), getHeight());
				((Graphics2D)g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, fade));
				g.drawImage(img, 0, 0, this);
				g.dispose();
			}
		};
	}
}