package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class SpreadSheet extends JPanel{
	private String[] columnNames;
	private double[] columnSizes;
	private byte[] alignments;
	private int rowHeight;
	private final ArrayList<Object[]> data = new ArrayList<>();
	private static final Color ALT_COLOR = new Color(230, 255, 230);
	public SpreadSheet(String[] columnNames, double[] columnSizes, byte[] alignments, int rowHeight){
		this.columnNames=columnNames;
		this.columnSizes=columnSizes;
		this.alignments=alignments;
		this.rowHeight=rowHeight;
	}
	public void addRow(Object[] row){
		data.add(row);
		setPreferredSize(new Dimension(10, (data.size()+1)*rowHeight));
		repaint();
	}
	public void removeRow(Object[] row){
		data.remove(row);
		setPreferredSize(new Dimension(10, (data.size()+1)*rowHeight));
		repaint();
	}
	@Override public void paint(Graphics g1){
		Graphics2D g = (Graphics2D)g1;
		int width = getWidth();
		int height = getHeight();
		int rowsShown = height/rowHeight;
		FontMetrics fm = g.getFontMetrics();
		int textY = (int)(fm.getAscent()+(rowHeight-(fm.getAscent()+fm.getDescent()))*0.5);
		for(int i = 0; i<=rowsShown; i++){
			g.setColor(i==0?Color.lightGray:i%2==1?Color.white:ALT_COLOR);
			g.fillRect(0, rowHeight*i, width, rowHeight);
			g.setColor(Color.black);
			g.drawLine(0, rowHeight*i, width, rowHeight*i);
			if(i==0){
				Font oldFont = getFont();
				g.setFont(new Font(oldFont.getName(), Font.BOLD, oldFont.getSize()));
				for(int j = 0; j<columnNames.length; j++)g.drawString(columnNames[j], (int)(((((j==columnSizes.length-1?1:columnSizes[j+1])-columnSizes[j])*width)-fm.stringWidth(columnNames[j].toString()))*0.5+width*columnSizes[j]), rowHeight*i+textY);
				g.setFont(oldFont);
			}else{
				if(i-1<data.size()){
					for(int j = 0; j<columnSizes.length; j++){
						if(alignments[j]==-1)g.drawString(data.get(i-1)[j].toString(), (int)(width*columnSizes[j])+3, rowHeight*i+textY);
						else if(alignments[j]==0)g.drawString(data.get(i-1)[j].toString(), (int)(((((j==columnSizes.length-1?1:columnSizes[j+1])-columnSizes[j])*width)-fm.stringWidth(data.get(i-1)[j].toString()))*0.5+width*columnSizes[j]), rowHeight*i+textY);
						else g.drawString(data.get(i-1)[j].toString(), (int)(width*columnSizes[j])+3, rowHeight*i+textY);
					}
				}
			}
		}
		g.setColor(Color.black);
		for(int i = 0; i<columnSizes.length-1; i++)g.drawLine((int)(width*columnSizes[i+1]), 0, (int)(width*columnSizes[i+1]), height);
		g.dispose();
	}
	public void setRows(ArrayList<Object[]> rows){
		data.clear();
		data.addAll(rows);
		repaint();
	}
	public void setNames(String[] columnNames){
		this.columnNames=columnNames;
		repaint();
	}
	public void updateColumnData(String[] columnNames, double[] columnSizes, byte[] alignments, int rowHeight){
		this.columnNames=columnNames;
		this.columnSizes=columnSizes;
		this.rowHeight=rowHeight;
		this.alignments=alignments;
		data.clear();
	}
}