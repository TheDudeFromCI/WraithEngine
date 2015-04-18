package wraith.library.WorldManagement.TileGrid;

import java.awt.image.BufferedImage;

public class ChipsetTileMaterial implements TileMaterial{
	private final Chipset chipset;
	private final int index;
	public ChipsetTileMaterial(Chipset chipset, int index){
		this.chipset=chipset;
		this.index=index;
	}
	public BufferedImage getImage(){ return chipset.getTile(index); }
}