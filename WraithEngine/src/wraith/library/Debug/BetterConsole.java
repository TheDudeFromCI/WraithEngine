package wraith.library.Debug;

import javax.swing.JFrame;
import javax.swing.JTextPane;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

@SuppressWarnings("serial")
public class BetterConsole extends JFrame{
	private JTextPane textPanel;
	private JScrollPane scrollPane;
	private ArrayList<Object> values = new ArrayList<>();
	private Rectangle r;
	public BetterConsole(boolean exitOnClose){
		setTitle("Debug Console");
		setDefaultCloseOperation(exitOnClose?JFrame.EXIT_ON_CLOSE:JFrame.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		setLocationRelativeTo(null);
		textPanel=new JTextPane();
		textPanel.setContentType("log");
		textPanel.setEditable(false);
		textPanel.setBackground(Color.BLACK);
		textPanel.setForeground(Color.GREEN);
		textPanel.setFont(new Font("Courier New", Font.BOLD, 14));
		scrollPane=new JScrollPane(textPanel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		setVisible(true);
	}
	public void clear(){
		values.clear();
		textPanel.setText("");
	}
	public void updateText(){
		String a = "";
		for(Object v : values)a+=v.toString();
		textPanel.setText(a);
		r=scrollPane.getBounds();
		r.y=textPanel.getHeight()-r.height;
		scrollPane.scrollRectToVisible(r);
	}
	public void print(Object a){
		if(a==null)return;
		if(values.size()==0){
			values.add(a.toString());
			updateText();
		}else{
			Object o = values.get(values.size()-1);
			if(o instanceof String)values.set(values.size()-1, o.toString()+a.toString());
			else values.add(a.toString());
			updateText();
		}
	}
	public void printChanging(Object a){
		if(a==null)return;
		values.add(a);
		updateText();
	}
	public void printChangingLine(Object a){
		if(a==null)return;
		values.add(a);
		println();
	}
	public void println(Object a){
		if(a==null)return;
		print(a+"\n");
	}
	public void println(){ print("\n"); }
}