package wraith.library.RandomGeneration;

public class IslandEdge{
	public boolean coastLine;
	public final int x, y;
	public final int ox, oy, od;
	public IslandEdge(int x, int y, int d){
		this.ox=x;
		this.oy=y;
		this.od=d;
		if(d==0){
			this.x=x*2+1;
			this.y=y*2;
		}else if(d==1){
			this.x=x*2+2;
			this.y=y*2+1;
		}else if(d==2){
			this.x=x*2+1;
			this.y=y*2+2;
		}else{
			this.x=x*2;
			this.y=y*2+1;
		}
	}
	public double distanceSquared(int x, int y){ return Math.pow(this.x-x*2, 2)+Math.pow(this.y-y*2, 2); }
}