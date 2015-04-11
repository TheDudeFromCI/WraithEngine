package wraith.library.RandomGeneration;

public class SquareGrid<S extends Object,E extends Object,C extends Object>{
	private final int width, height, maxWidth, maxHeight;
	private final Object[] data;
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;
	public SquareGrid(int width, int height){
		this.width=width;
		this.height=height;
		maxWidth=width*2+1;
		maxHeight=height*2+1;
		data=new Object[maxWidth*maxHeight];
	}
	private int edgeIndex(int x, int y, int d){
		int tx, ty;
		if(d==0){
			tx=x*2+1;
			ty=y*2;
		}else if(d==1){
			tx=x*2+2;
			ty=y*2+1;
		}else if(d==2){
			tx=x*2+1;
			ty=y*2+2;
		}else{
			tx=x*2;
			ty=y*2+1;
		}
		return tx*maxHeight+ty;
	}
	private int squareIndex(int x, int y){ return (x*2+1)*maxHeight+(y*2+1); }
	private int cornerIndex(int x, int y){ return x*2*maxHeight+y*2; }
	public S getSquare(int x, int y){ return (S)data[squareIndex(x, y)]; }
	public E getEdge(int x, int y, int d){ return (E)data[edgeIndex(x, y, d)]; }
	public C getCorner(int x, int y){ return (C)data[cornerIndex(x, y)]; }
	public void setSquare(int x, int y, S s){ data[squareIndex(x, y)]=s; }
	public void setEdge(int x, int y, int d, E s){ data[edgeIndex(x, y, d)]=s; }
	public void setCorner(int x, int y, C s){ data[cornerIndex(x, y)]=s; }
	public int getSquareCount(){ return width*height; }
	public int getEdgeCount(){ return (data.length-1)/2; }
	public int getCornerCount(){ return (width+1)*(height+1); }
	public int getWidth(){ return width; }
	public int getHeight(){ return height; }
	public S getSquare(int index){ return (S)data[index]; }
	public E getEdge(int index){ return (E)data[index]; }
	public C getCorner(int index){ return (C)data[index]; }
}