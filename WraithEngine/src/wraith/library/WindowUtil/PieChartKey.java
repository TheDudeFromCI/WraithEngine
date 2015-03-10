package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PieChartKey extends JComponent{
	private PieChart pieChart;
	private static final int COLOR_SAMPLE_SIZE = 10;
	private static final int LIST_INDENT = 5;
	private static final int TEXT_LINE_HEIGHT = 15;
	private static final int SAMPLE_TO_TEXT_SPACE = 5;
	public PieChartKey(final PieChart pieChart){
		this.pieChart=pieChart;
		setPreferredSize(new Dimension(150, pieChart.getDataSize()*TEXT_LINE_HEIGHT));
		pieChart.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setPreferredSize(new Dimension(150, pieChart.getDataSize()*TEXT_LINE_HEIGHT));
				repaint();
			}
		});
	}
	@Override public void paint(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setFont(getFont());
		for(int i = 0; i<pieChart.getDataSize(); i++){
			g.setColor(pieChart.getCurrentColors()[i]);
			g.fillRect(LIST_INDENT, i*TEXT_LINE_HEIGHT+(TEXT_LINE_HEIGHT-COLOR_SAMPLE_SIZE)/2, COLOR_SAMPLE_SIZE, COLOR_SAMPLE_SIZE);
			g.setColor(Color.black);
			g.drawRect(LIST_INDENT, i*TEXT_LINE_HEIGHT+(TEXT_LINE_HEIGHT-COLOR_SAMPLE_SIZE)/2, COLOR_SAMPLE_SIZE, COLOR_SAMPLE_SIZE);
			g.setColor(getForeground());
			g.drawString(pieChart.getCurrentDataNames()[i], COLOR_SAMPLE_SIZE+LIST_INDENT+SAMPLE_TO_TEXT_SPACE, i*TEXT_LINE_HEIGHT+(TEXT_LINE_HEIGHT-COLOR_SAMPLE_SIZE)/2+COLOR_SAMPLE_SIZE);
		}
		g.dispose();
	}
}