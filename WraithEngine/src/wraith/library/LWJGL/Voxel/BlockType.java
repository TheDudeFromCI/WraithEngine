package wraith.library.LWJGL.Voxel;

import wraith.library.LWJGL.Texture;

public interface BlockType{
	public Texture getTexture(int side);
	public int getRotation(int side);
	public boolean setupShadows(float[] colors, int side, int x, int y, int z);
}