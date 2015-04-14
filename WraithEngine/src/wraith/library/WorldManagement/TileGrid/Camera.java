package wraith.library.WorldManagement.TileGrid;

public class Camera{
	public int x, z;
	public int scale = 16;
	public int width=320, height=240;
	public int rawScale = 16;
	public int realWidth=320, realHeight=240;
	public float stretch=1;
	@Override public String toString(){ return "x="+x+", z="+z+", scale="+scale+", width="+width+", height="+height+", rawScale="+rawScale+", realWidth="+realWidth+", realHeight="+realHeight+", stretch="+stretch; }
}