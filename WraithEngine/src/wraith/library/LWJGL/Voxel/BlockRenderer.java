package wraith.library.LWJGL.Voxel;

import wraith.library.LWJGL.Cube;
import wraith.library.LWJGL.CubeTextures;

public class BlockRenderer extends Cube{
	public BlockRenderer(CubeTextures textures){ this.textures=textures; }
	public void update(float delta, long time){}
	protected void draw(){}
}