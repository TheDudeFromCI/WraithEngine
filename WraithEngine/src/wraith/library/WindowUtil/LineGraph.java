package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;

public class LineGraph extends JPanel{
	private String[] rowNames = new String[0];
	private String[] colNames = new String[0];
	private double[][] values = new double[0][];
	private String[][] valueNames = new String[0][];
	private String[] key = new String[0];
	private static Color[] PRIME_COLORS = {
		Color.RED,
		Color.BLUE,
		Color.GREEN,
		Color.ORANGE,
		Color.YELLOW,
		Color.CYAN,
		Color.MAGENTA,
		Color.PINK,
		new Color(151, 35, 201),
		new Color(152, 235, 29),
		new Color(75, 81, 255)
	};
	private static final int COLOR_GENERATOR = 2;
	private static final int PIXEL_TEXT_BUFFER = 10;
	public void setValues(double[][] values, String[][] valueNames){
		this.values=values;
		this.valueNames=valueNames;
		repaint();
	}
	@Override public void paint(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		g.setColor(Color.gray);
		g.fillRect(0, 0, getWidth(), getHeight());
		FontMetrics fm = g.getFontMetrics();
		int rowNameBuffer = calculateRowNameBuffer(fm)+PIXEL_TEXT_BUFFER;
		int colNameBuffer = fm.getHeight()+PIXEL_TEXT_BUFFER;
		int keyBuffer = calculateKeyBuffer(fm)+PIXEL_TEXT_BUFFER;
		int width = getWidth()-rowNameBuffer-keyBuffer;
		int height = getHeight()-colNameBuffer;
		g.setColor(Color.black);
		for(int i = 0; i<rowNames.length; i++)g.drawString(rowNames[i], (rowNameBuffer-fm.stringWidth(rowNames[i]))/2, (int)(height/(double)rowNames.length*((rowNames.length-i))));
		for(int i = 0; i<colNames.length; i++)g.drawString(colNames[i], (int)(width/(double)colNames.length*(i+0.5)-fm.stringWidth(colNames[i])/2), getHeight()-colNameBuffer/2+fm.getAscent()/2);
		g.setColor(Color.darkGray);
		for(int i = 0; i<=rowNames.length; i++)g.drawLine(rowNameBuffer, (int)(height/(double)rowNames.length*i), getWidth()-keyBuffer, (int)(height/(double)rowNames.length*i));
		for(int i = 0; i<colNames.length; i++)g.drawLine((int)(width/(double)colNames.length*(i+0.5)), 0, (int)(width/(double)colNames.length*(i+0.5)), height);
		Color c;
		Random colorGen = new Random(COLOR_GENERATOR);
		for(int a = 0; a<values.length; a++){
			c=(values.length<PRIME_COLORS.length?PRIME_COLORS[a]:new Color(colorGen.nextFloat(), colorGen.nextFloat(), colorGen.nextFloat()));
			g.setColor(c);
			if(values[a].length==1){
				g.drawLine(rowNameBuffer, (int)(height*(1-values[a][0])), getWidth()-keyBuffer, (int)(height*(1-values[a][0])));
				g.setColor(Color.white);
				g.drawString(valueNames[a][0], (width-fm.stringWidth(valueNames[a][0]))/2, (int)(height*(1-values[a][0])));
				g.setColor(c);
			}else{
				for(int b = 0; b<values[a].length; b++){
					if(b==0){
						g.setColor(Color.white);
						g.drawString(valueNames[a][b], (int)(width/(double)colNames.length*(b+0.5))-fm.stringWidth(valueNames[a][b])/2,  (int)(height*(1-values[a][b]))-2);
						g.setColor(c);
						continue;
					}
					g.drawLine((int)(width/(double)colNames.length*(b-0.5)),  (int)(height*(1-values[a][b-1])), (int)(width/(double)colNames.length*(b+0.5)),  (int)(height*(1-values[a][b])));
					g.setColor(Color.white);
					g.drawString(valueNames[a][b], (int)(width/(double)colNames.length*(b+0.5))-fm.stringWidth(valueNames[a][b])/2,  (int)(height*(1-values[a][b]))-2);
					g.setColor(c);
				}
			}
		}
		Random keyGen = new Random(COLOR_GENERATOR);
		int sampleX = getWidth()-keyBuffer+PIXEL_TEXT_BUFFER/2;
		for(int i = 0; i<key.length; i++){
			c=(values.length<PRIME_COLORS.length?PRIME_COLORS[i]:new Color(keyGen.nextFloat(), keyGen.nextFloat(), keyGen.nextFloat()));
			g.setColor(c);
			g.fillRect(sampleX, i*(fm.getHeight()+5)+PIXEL_TEXT_BUFFER/2, 10, 10);
			g.setColor(Color.black);
			g.drawRect(sampleX, i*(fm.getHeight()+5)+PIXEL_TEXT_BUFFER/2, 10, 10);
			g.setColor(Color.white);
			g.drawString(key[i], sampleX+10+PIXEL_TEXT_BUFFER/2, i*(fm.getHeight()+5)+PIXEL_TEXT_BUFFER/2+10);
		}
		g.dispose();
	}
	private int calculateRowNameBuffer(FontMetrics fm){
		int l = 0;
		int c;
		for(String s : rowNames)if((c=fm.stringWidth(s))>l)l=c;
		return l;
	}
	public void setRowNames(String[] rowNames){
		this.rowNames=rowNames;
		repaint();
	}
	public void setColNames(String[] colNames){
		this.colNames=colNames;
		repaint();
	}
	public void setKey(String[] key){
		this.key=key;
		repaint();
	}
	private int calculateKeyBuffer(FontMetrics fm){
		int l = 0;
		int c;
		for(String s : key)if((c=fm.stringWidth(s))>l)l=c;
		return l+10+PIXEL_TEXT_BUFFER/2;
	}
}