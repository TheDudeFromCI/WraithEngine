package wraith.library.LWJGL;

import org.lwjgl.opengl.GL11;

public enum MipmapQuality{
	LOW(GL11.GL_LINEAR_MIPMAP_NEAREST),
	HIGH(GL11.GL_LINEAR_MIPMAP_LINEAR);
	private final int q;
	private MipmapQuality(int q){ this.q=q; }
	public int getQuality(){ return q; }
}