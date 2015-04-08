package wraith.library.WorldManagement.TileGrid;

import java.awt.image.BufferedImage;
import wraith.library.MiscUtil.ImageUtil;

public class Chipset{
	private TileMaterial[] materials;
	private final int rows, cols;
	private final BufferedImage[] tiles;
	public Chipset(BufferedImage img, int rows, int cols){
		if(img==null)throw new IllegalArgumentException("Image cannot be null!");
		if(rows<1||cols<1)throw new IllegalArgumentException("Rows and cols must be at least 1!");
		tiles=ImageUtil.splitImage(img, rows, cols);
		this.cols=cols;
		this.rows=rows;
	}
	public Chipset(BufferedImage img, int size){
		if(img==null)throw new IllegalArgumentException("Image cannot be null!");
		if(size<1)throw new IllegalArgumentException("Size cannot be less then 1!");
		this.cols=img.getWidth()/size;
		this.rows=img.getHeight()/size;
		tiles=ImageUtil.splitImage(img, rows, cols);
	}
	public void generateTileMaterials(){
		materials=new TileMaterial[tiles.length];
		for(int i = 0; i<tiles.length; i++)materials[i]=new StaticTileMaterial(tiles[i]);
	}
	public BufferedImage getTile(int x, int y){ return tiles[y*cols+x]; }
	public TileMaterial getTileMaterial(int x, int y){ return materials[y*cols+x]; }
	public int getRows(){ return rows; }
	public int getCols(){ return cols; }
}