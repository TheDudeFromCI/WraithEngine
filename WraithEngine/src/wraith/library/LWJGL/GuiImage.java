package wraith.library.LWJGL;

import java.io.File;

public class GuiImage{
	Texture texture;
	public float x, y, z, w, h;
	public GuiImage(File file){ texture=new Texture(file, 0, null); }
}