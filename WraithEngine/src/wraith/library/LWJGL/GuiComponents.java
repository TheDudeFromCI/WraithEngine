package wraith.library.LWJGL;

import java.awt.Dimension;
import java.util.ArrayList;
import static org.lwjgl.opengl.GL11.*;

public class GuiComponents{
	public float scaleX = 1;
	public float scaleY = 1;
	private GuiImage img;
	private final Dimension screenSize;
	private final ArrayList<GuiImage> images = new ArrayList();
	public void render(){
		MatrixUtils.setupImageOrtho(screenSize.width*scaleX, screenSize.height*scaleY, -1, 1);
		for(int i = 0; i<images.size(); i++){
			img=images.get(i);
			img.texture.bind();
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex3f(screenSize.width*img.x*scaleX, screenSize.height*img.y*scaleY, img.z);
			glTexCoord2f(0, 1);
			glVertex3f(screenSize.width*(img.x*scaleX+img.w), screenSize.height*img.y*scaleY, img.z);
			glTexCoord2f(1, 1);
			glVertex3f(screenSize.width*(img.x*scaleX+img.w), screenSize.height*(img.y*scaleY+img.h), img.z);
			glTexCoord2f(1, 0);
			glVertex3f(screenSize.width*img.x*scaleX, screenSize.height*(img.y*scaleY+img.h), img.z);
			glEnd();
		}
	}
	public GuiComponents(Dimension screenSize){ this.screenSize=screenSize; }
	public void addComponent(GuiImage guiImage){ images.add(guiImage); }
	public void removeComponent(GuiImage guiImage){ images.remove(guiImage); }
	public void clearComponents(){ images.clear(); }
}