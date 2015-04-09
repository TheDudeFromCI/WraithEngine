package wraith.library.WorldManagement.TileGrid;

import java.awt.Graphics2D;

public class Map{
	private Panoramic panoramic;
	private final Camera camera = new Camera();
	private final MapImageLayer[] imageLayers;
	private final int xSize, ySize, zSize;
	private final Tile[][][] tiles;
	public Map(int xSize, int ySize, int zSize, WorldPopulator populator){
		if(xSize<1)throw new IllegalArgumentException("Area cannot be less then 1 block thick!");
		if(ySize<1)throw new IllegalArgumentException("Area cannot be less then 1 block thick!");
		if(zSize<1)throw new IllegalArgumentException("Area cannot be less then 1 block thick!");
		if(populator==null)throw new IllegalArgumentException("World populator cannot be null!");
		this.xSize=xSize;
		this.ySize=ySize;
		this.zSize=zSize;
		tiles=new Tile[xSize][ySize][zSize];
		populator.generate(tiles);
		imageLayers=new MapImageLayer[ySize];
		for(int i = 0; i<ySize; i++)imageLayers[i]=new MapImageLayer(this, i);
	}
	public Tile getTileAt(int x, int y, int z){
		if(x<0)throw new IllegalArgumentException("Area out of bounds!");
		if(y<0)throw new IllegalArgumentException("Area out of bounds!");
		if(z<0)throw new IllegalArgumentException("Area out of bounds!");
		if(x>=xSize)throw new IllegalArgumentException("Area out of bounds!");
		if(y>=ySize)throw new IllegalArgumentException("Area out of bounds!");
		if(z>=zSize)throw new IllegalArgumentException("Area out of bounds!");
		return tiles[x][y][z];
	}
	public void setCameraPosition(int x, int z){
		camera.x=x;
		camera.z=z;
	}
	public void setCameraScale(int scale){
		camera.scale=scale;
	}
	public void setCameraDimensions(int width, int height){
		camera.width=width;
		camera.height=height;
	}
	public void placeTileGroup(TileGroup tileGroup, int x, int y, int z){
		tileGroup.placeAt(tiles, x, y, z);
		imageLayers[y].repaint();
	}
	public void render(Graphics2D g, int width, int height){
		if(panoramic!=null)panoramic.render(g, width, height);
		for(int i = 0; i<ySize; i++)imageLayers[i].render(g);
	}
	public void updateImageCompilation(){ for(int i = 0; i<ySize; i++)imageLayers[i].repaint(); }
	public int getSizeX(){ return xSize; }
	public int getSizeY(){ return ySize; }
	public int getSizeZ(){ return zSize; }
	public int getCameraScale(){ return camera.scale; }
	public int getCameraX(){ return camera.x; }
	public int getCameraZ(){ return camera.z; }
	public Panoramic getPanoramic(){ return panoramic; }
	public void setPanoramic(Panoramic panoramic){ this.panoramic=panoramic; }
	public int getCameraWidth(){ return camera.width; }
	public int getCameraHeight(){ return camera.height; }
}