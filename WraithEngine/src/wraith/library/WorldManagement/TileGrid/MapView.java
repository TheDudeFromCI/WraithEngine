package wraith.library.WorldManagement.TileGrid;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class MapView{
	private boolean debug;
	private boolean disposed;
	private JPanel panel;
	private Map map;
	public MapView(Map m){
		map=m;
		panel=new JPanel(){
			@Override public void paint(Graphics g){
				map.render((Graphics2D)g, panel.getWidth(), panel.getHeight());
				g.dispose();
			}
		};
		panel.setPreferredSize(new Dimension(map.getCameraScale()*map.getSizeX(), map.getCameraScale()*map.getSizeZ()));
		panel.setFocusable(true);
		new Thread(new Runnable(){
			public void run(){
				int step = 0;
				long time = System.nanoTime();
				long temp;
				while(!disposed){
					panel.paintImmediately(0, 0, panel.getWidth(), panel.getHeight());
					if(debug){
						step++;
						if(step==100){
							step=0;
							temp=System.nanoTime();
							double seconds = (temp-time)/100.0/1000000000.0;
							System.out.println((int)(100/seconds)+" Fps");
							time=temp;
						}
					}
				}
			}
		}).start();
	}
	public void dispose(){
		disposed=true;
		panel=null;
		map=null;
	}
	public JPanel getPanel(){ return panel; }
	public boolean isDebugMode(){ return debug; }
	public void setDebugMode(boolean debug){ this.debug=debug; }
}