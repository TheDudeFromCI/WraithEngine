package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

public class BulletPointList extends JComponent{
	private String[] bullets;
	private Color bulletColor;
	public BulletPointList(String[] bullets){
		super();
		this.bullets=bullets;
		findSize();
	}
	@Override public void paint(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(getFont());
		int size = g.getFont().getSize();
		int height = g.getFontMetrics().getHeight();
		for(int i = 0; i<bullets.length; i++){
			g.setColor(bulletColor==null?getForeground():bulletColor);
			g.fillOval(2, 2+i*height, size, size);
			g.setColor(getForeground());
			g.drawString(bullets[i], 4+size, 2+i*height+size);
		}
		g.dispose();
	}
	private void findSize(){
		Font f = getFont();
		FontMetrics fm = getFontMetrics(f==null?f=new Font("Tahoma", Font.PLAIN, 11):f);
		int maxWidth = 0;
		for(int i = 0; i<bullets.length; i++)maxWidth=Math.max(maxWidth, fm.stringWidth(bullets[i]+4+f.getSize()));
		setPreferredSize(new Dimension(maxWidth, fm.getHeight()*bullets.length));
	}
	public void setBullets(String[] bullets){
		this.bullets=bullets;
		findSize();
	}
	public void setBulletColor(Color c){ bulletColor=c; }
}