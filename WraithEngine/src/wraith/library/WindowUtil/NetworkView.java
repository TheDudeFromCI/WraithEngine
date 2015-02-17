package wraith.library.WindowUtil;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JComponent;

public class NetworkView extends JComponent{
	private ArrayList<NetworkNode> nodes = new ArrayList<>();
	private int[][] nodeCords = new int[0][];
	private Color connectorColor = Color.black;
	private Color nodeColor = Color.red;
	private int nodeSize = 20;
	@Override public void paint(Graphics g1){
		recalculateNodeCords();
		Graphics2D g = (Graphics2D)g1;
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(connectorColor);
		synchronized(nodes){
			for(int i = 0; i<nodes.size(); i++)drawNodeConnections(g, nodes.get(i), nodeCords[i][0], nodeCords[i][1]);
			for(int i = 0; i<nodes.size(); i++)drawNode(g, nodes.get(i), nodeCords[i][0], nodeCords[i][1]);
		}
		g.dispose();
	}
	private void drawNode(Graphics2D g, NetworkNode node, int x, int y){
		g.setColor(node.isActive()?nodeColor:Color.gray);
		g.fillOval(x-nodeSize/2, y-nodeSize/2, nodeSize, nodeSize);
		g.setColor(getForeground());
		FontMetrics fm = g.getFontMetrics();
		String name = node.getName();
		String des = node.getDescription();
		g.drawString(name, Math.round((nodeSize-fm.stringWidth(name))/2f+x-nodeSize/2f), Math.round(y-nodeSize/2f-fm.getHeight()));
		g.drawString(des, Math.round((nodeSize-fm.stringWidth(des))/2f+x-nodeSize/2f), Math.round(y-nodeSize/2f));
	}
	private void drawNodeConnections(Graphics2D g, NetworkNode node, int x, int y){
		int index = 0;
		for(NetworkNode n : node.getConnections()){
			index=nodes.indexOf(n);
			if(index==-1)continue;
			g.drawLine(nodeCords[index][0], nodeCords[index][1], x, y);
		}
	}
	public void addNetworkNode(NetworkNode node){
		synchronized(nodes){
			nodes.add(node);
		}
		repaint();
	}
	public void removeNetworkNode(NetworkNode node){
		synchronized(nodes){
			nodes.remove(node);
		}
		repaint();
	}
	private void recalculateNodeCords(){
		nodeCords=new int[nodes.size()][];
		double angle = 2*Math.PI/nodes.size();
		double horizontalRadius = (getWidth()*0.9-nodeSize*2)*0.5;
		double verticalRadius = (getHeight()*0.9-nodeSize*2)*0.5;
		for(int i = 0; i<nodeCords.length; i++){
			nodeCords[i]=new int[]{
				(int)Math.round(Math.cos(angle*i)*horizontalRadius+getWidth()/2.0),
				(int)Math.round(Math.sin(angle*i)*verticalRadius+getHeight()/2.0)
			};
		}
	}
	public NetworkView(){ setPreferredSize(new Dimension(20, 20)); }
	public Color getConnectorColor(){ return connectorColor; }
	public void setConnectorColor(Color connectorColor){ this.connectorColor=connectorColor; }
	public int getNodeSize(){ return nodeSize; }
	public void setNodeSize(int nodeSize){ this.nodeSize=nodeSize; }
	public Color getNodeColor(){ return nodeColor; }
	public void setNodeColor(Color nodeColor){ this.nodeColor=nodeColor; }
}