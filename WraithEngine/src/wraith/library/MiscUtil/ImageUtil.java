package wraith.library.MiscUtil;

import java.awt.image.BufferedImage;

public class ImageUtil{
	public static BufferedImage[] splitImage(BufferedImage img, int rows, int cols){
		if(img==null)throw new IllegalArgumentException("Image cannot be null!");
		if(rows<1||cols<1)throw new IllegalArgumentException("Rows and columns must be at least 1!");
		BufferedImage[] bufs = new BufferedImage[rows*cols];
		float width = img.getWidth()/(float)cols;
		float height = img.getHeight()/(float)rows;
		for(int i = 0; i<bufs.length; i++)bufs[i]=img.getSubimage((int)(i/cols*width), (int)(i%cols*height), (int)width, (int)height);
		return bufs;
	}
}