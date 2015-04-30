package wraith.library.LWJGL.Voxel;

import wraith.library.LWJGL.Texture;

public interface BlockType{
	public Texture getTexture(int side);
	public int getRotation(int side);
	public void setupShadows(float[] colors, int side, boolean[] shadowPoints);
	public void findShadowPoints(int x, int y, int z, boolean[] shadowPoints);
}