package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PieChart extends JComponent{
	private double total;
	private int[] data = new int[0];
	private String[] names = new String[0];
	private ArrayList<ChangeListener> changeListeners = new ArrayList<>();
	private Color[] colors = new Color[0];
	private static final Color[] COLORS = {
		new Color(255, 0, 0),
		new Color(0, 255, 0),
		new Color(0, 0, 255),
		new Color(255, 255, 0),
		new Color(0, 255, 255),
		new Color(255, 0, 255),
		new Color(255, 125, 0),
		new Color(0, 125, 255),
		new Color(125, 0, 255),
		new Color(125, 0, 125),
		new Color(0, 125, 125),
		new Color(125, 125, 0),
		new Color(125, 125, 125),
		new Color(204, 0, 204),
		new Color(102, 51, 0),
		new Color(0, 0, 102),
		new Color(0, 102, 0),
		new Color(102, 0, 0),
		new Color(255, 153, 204)
	};
	@Override public void paint(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		if(data.length>0){
			double currentValue = 0;
			int startAngle, arcAngle;
			for(int i = 0; i<data.length; i++){
				if(data[i]==0)continue;
				startAngle=(int)(currentValue*360.0/total);
				arcAngle=(int)(data[i]*360.0/total);
				g.setColor(colors[i]);
				g.fillArc(0, 0, getWidth(), getHeight(), startAngle, Math.max(arcAngle+1, 1));
				currentValue+=data[i];
			}
		}
		g.dispose();
	}
	public void setData(String[] n, int[] d){
		names=n;
		total=0;
		data=d;
		for(int l : d)total+=l;
		randomizeColors();
		fireChangeListener();
		repaint();
	}
	private void fireChangeListener(){
		ChangeEvent event = new ChangeEvent(this);
		for(ChangeListener c : changeListeners)c.stateChanged(event);
	}
	private void randomizeColors(){
		colors=new Color[data.length];
		Random r = new Random(238947520);
		for(int i = 0; i<colors.length; i++){
			if(i<COLORS.length)colors[i]=COLORS[i];
			else colors[i]=new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
		}
	}
	public PieChart(){ setPreferredSize(new Dimension(50, 50)); }
	public int[] getCurrentData(){ return data; }
	public String[] getCurrentDataNames(){ return names; }
	public int getDataSize(){ return data.length; }
	public void addChangeListener(ChangeListener c){ changeListeners.add(c); }
	public void removeChangeListener(ChangeListener c){ changeListeners.remove(c); }
	public Color[] getCurrentColors(){ return colors; }
}