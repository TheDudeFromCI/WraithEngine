package wraith.library.RandomGeneration;

public class TriangleGrid<T extends Object,K extends Object>{
	private final int height, width;
	private final int cornerWidth, cornerHeight;
	private final int totalCornerWidth;
	private final Object[] grid;
	private final Object[] Corners;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public TriangleGrid(int width, int height){
		this.height=height;
		this.width=width;
		this.cornerWidth=width+1;
		this.cornerHeight=height+1;
		totalCornerWidth=cornerWidth+width;
		grid=new Object[width*height*4];
		Corners=new Object[cornerWidth*cornerHeight+width*height];
	}
	public void setTile(int x, int y, int s, T t){ grid[getTileIndex(x, y, s)]=t; }
	public T getTile(int x, int y, int s){ return (T)grid[getTileIndex(x, y, s)]; }
	public void setCorner(int x, int y, K k){ Corners[getCornerIndex(x, y)]=k; }
	public K getCorner(int x, int y){ return (K)Corners[getCornerIndex(x, y)]; }
	private int getTileIndex(int x, int y, int s){ return (x*height+y)*4+s; }
	private int getCornerIndex(int x, int y){ return y%2==0?x+totalCornerWidth*(y/2):x+cornerWidth+totalCornerWidth*(y/2); }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public int getCornerWidth(){ return cornerWidth; }
	public int getCornerHeight(){ return cornerHeight; }
}