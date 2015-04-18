package wraith.library.WindowUtil.GUI;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class GuiFrame{
	private int x, y;
	private int width, height;
	private GuiLayout layout;
	private final ArrayList<GuiComponent> components = new ArrayList();
	public void setSize(int width, int height){
		this.width=width;
		this.height=height;
		validate();
	}
	public void setLocation(int x, int y){
		this.x=x;
		this.y=y;
	}
	private void validate(){
		if(layout!=null){
			layout.setParentDimensions(width, height);
			layout.validateComponents(components);
		}
	}
	public void addComponent(GuiComponent component){
		components.add(component);
		validate();
	}
	public void removeComponent(GuiComponent component){
		components.remove(component);
		validate();
	}
	public void render(Graphics2D g){ for(int i = 0; i<components.size(); i++)components.get(i).render(g, x, y); }
}