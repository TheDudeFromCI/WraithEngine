package wraith.library.RandomGeneration;

import java.util.Random;

public class IslandGenerator{
	private final SquareGrid<IslandSquare,IslandEdge,IslandCorner> grid;
	public IslandGenerator(long[] seeds, int width, int height){
		grid=new SquareGrid(width, height);
		Random genSpecs = new Random(seeds[1]);
		NoiseGenerator noise = new NoiseGenerator(seeds[0], genSpecs.nextInt(15)+10, genSpecs.nextInt(5)+3, 0);
		IslandSquare s;
		for(int x = 0; x<width; x++){
			for(int y = 0; y<height; y++){
				grid.setSquare(x, y, s=new IslandSquare(x, y));
				s.isLand=noise.noise(x, y)>0.3;
			}
		}
		float maxHeight = 0;
		int x, y, w;
		IslandEdge e;
		int lastX = width-1;
		int lastY = height-1;
		for(x=0; x<width; x++){
			for(y=0; y<height; y++){
				if(x==lastX&&y==lastY){
					for(w=1; w<3; w++){
						e=grid.getEdge(x, y, w);
						if(e!=null)continue;
						e=new IslandEdge(x, y, w);
						grid.setEdge(x, y, w, e);
						e.coastLine=isCoastline(e);
					}
				}else if(x==lastX){
					e=grid.getEdge(x, y, 1);
					if(e!=null)continue;
					e=new IslandEdge(x, y, 1);
					grid.setEdge(x, y, 1, e);
					e.coastLine=isCoastline(e);
				}else if(y==lastY){
					e=grid.getEdge(x, y, 2);
					if(e!=null)continue;
					e=new IslandEdge(x, y, 2);
					grid.setEdge(x, y, 2, e);
					e.coastLine=isCoastline(e);
				}else{
					for(w=0; w<4; w+=3){
						e=grid.getEdge(x, y, w);
						if(e!=null)continue;
						e=new IslandEdge(x, y, w);
						grid.setEdge(x, y, w, e);
						e.coastLine=isCoastline(e);
					}
				}
			}
		}
		for(x=0; x<width; x++)for(y=0; y<height; y++)maxHeight=Math.max(calculateElevation(x, y), maxHeight);
		for(x=0; x<width; x++)for(y=0; y<height; y++)normalizeHeight(x, y, maxHeight);
	}
	private boolean isCoastline(IslandEdge e){
		if(e.od==0){
			if(e.oy==0)return false;
			return grid.getSquare(e.ox, e.oy).isLand!=grid.getSquare(e.ox, e.oy-1).isLand;
		}else if(e.od==1){
			if(e.ox==0)return false;
			return grid.getSquare(e.ox, e.oy).isLand!=grid.getSquare(e.ox-1, e.oy).isLand;
		}else if(e.od==2){
			if(e.oy==grid.getHeight()-1)return false;
			return grid.getSquare(e.ox, e.oy).isLand!=grid.getSquare(e.ox, e.oy+1).isLand;
		}else{
			if(e.ox==grid.getWidth()-1)return false;
			return grid.getSquare(e.ox, e.oy).isLand!=grid.getSquare(e.ox+1, e.oy).isLand;
		}
	}
	private float calculateElevation(int x, int y){
		IslandSquare s = grid.getSquare(x, y);
		if(!s.isLand)return 0;
		double distance = Double.MAX_VALUE;
		double tempDistance;
		int a, b, w;
		IslandEdge e;
		int lastX = grid.getWidth()-1;
		int lastY = grid.getHeight()-1;
		for(a=0; a<grid.getWidth(); a++){
			for(b=0; b<grid.getHeight(); b++){
				if(a==lastX&&b==lastY){
					for(w=1; w<3; w++){
						e=grid.getEdge(a, b, w);
						if(!e.coastLine)continue;
						tempDistance=e.distanceSquared(x, y);
						if(tempDistance<distance)distance=tempDistance;
					}
				}else if(a==lastX){
					e=grid.getEdge(a, b, 1);
					if(!e.coastLine)continue;
					tempDistance=e.distanceSquared(x, y);
					if(tempDistance<distance)distance=tempDistance;
				}else if(b==lastY){
					e=grid.getEdge(a, b, 2);
					if(!e.coastLine)continue;
					tempDistance=e.distanceSquared(x, y);
					if(tempDistance<distance)distance=tempDistance;
				}else{
					for(w=0; w<4; w+=3){
						e=grid.getEdge(a, b, w);
						if(!e.coastLine)continue;
						tempDistance=e.distanceSquared(x, y);
						if(tempDistance<distance)distance=tempDistance;
					}
				}
			}
		}
		return s.height=(float)Math.sqrt(distance);
	}
	private void normalizeHeight(int x, int y, float maxHeight){ grid.getSquare(x, y).height/=maxHeight; }
	public IslandSquare getSquare(int x, int y){ return grid.getSquare(x, y); }
	public static long[] generateSeeds(){
		long[] seeds = new long[2];
		for(int i = 0; i<seeds.length; i++)seeds[i]=(long)(Math.random()*Long.MAX_VALUE)*(Math.random()>0.5?1:-1);
		return seeds;
	}
}