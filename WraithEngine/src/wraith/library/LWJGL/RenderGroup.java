package wraith.library.LWJGL;

import java.util.ArrayList;

public class RenderGroup extends RenderableObject3D{
	public final ArrayList<RenderableObject3D> objects = new ArrayList();
	protected void draw(){ for(int i = 0; i<objects.size(); i++)objects.get(i).draw(); }
	public void update(float delta, long time){ for(int i = 0; i<objects.size(); i++)objects.get(i).update(delta, time); }
}