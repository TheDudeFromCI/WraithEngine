package wraith.library.WindowUtil.GUI;

import java.util.ArrayList;

public abstract class GuiContainer extends GuiComponent{
	private GuiLayout layout;
	protected ArrayList<GuiComponent> components = new ArrayList();
	private void validate(){
		if(layout!=null){
			layout.setParentDimensions(x, y, width, height);
			layout.validateComponents(components);
			setNeedsRepaint();
		}
	}
	public void addComponent(GuiComponent component){
		if(components.size()>=layout.getMaxChildren())return;
		components.add(component);
		validate();
	}
	public void removeComponent(GuiComponent component){
		components.remove(component);
		validate();
	}
	@Override public void setSizeAndLocation(int x, int y, int width, int height){
		super.setSizeAndLocation(x, y, width, height);
		validate();
	}
	@Override public void dispose(){
		super.dispose();
		components.clear();
		components=null;
		layout=null;
	}
	@Override public void setRepainted(){
		if(needsRepaint){
			super.setRepainted();
			for(int i = 0; i<components.size(); i++)components.get(i).setRepainted();
		}
	}
	public void clearChildren(){
		components.clear();
		validate();
	}
	public void setLayout(GuiLayout layout){
		this.layout=layout;
		validate();
	}
	public GuiContainer(GuiContainer parent, int bufferWidth, int bufferHeight){ super(parent, bufferWidth, bufferHeight); }
	public GuiLayout getLayout(){ return layout; }
}