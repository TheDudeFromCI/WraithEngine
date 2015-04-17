package wraith.library.MiscUtil;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ImageUtil{
	public static BufferedImage[] splitImage(BufferedImage img, int rows, int cols){
		if(img==null)throw new IllegalArgumentException("Image cannot be null!");
		if(rows<1||cols<1)throw new IllegalArgumentException("Rows and columns must be at least 1!");
		BufferedImage[] bufs = new BufferedImage[rows*cols];
		int width = img.getWidth()/cols;
		int height = img.getHeight()/rows;
		for(int i = 0; i<bufs.length; i++)bufs[i]=img.getSubimage(i%cols*width, i/cols*height, width, height);
		return bufs;
	}
	public static void saveImage(File file, BufferedImage img){
		try{
			if(!file.exists()){
				new File(file.getParent()).mkdirs();
				file.createNewFile();
			}
			ImageIO.write(img, StringUtil.getFileExtension(file.getName()), file);
		}catch(Exception exception){ exception.printStackTrace(); }
	}
	public static BufferedImage[][] splitImageAndScale(BufferedImage img, int rows, int cols, int scales){
		BufferedImage[][] bufs = new BufferedImage[scales][];
		int i, j;
		for(i=0; i<scales; i++){
			bufs[i]=splitImage(img, rows, cols);
			if(i>0)for(j=0; j<bufs[i].length; j++)bufs[i][j]=scaleImage(bufs[i][j], (float)Math.pow(2, -i));
		}
		return bufs;
	}
	public static BufferedImage scaleImage(BufferedImage img, float scale){
		BufferedImage newBuf = getBestFormat((int)(img.getWidth()*scale), (int)(img.getHeight()*scale));
		Graphics2D g = newBuf.createGraphics();
		g.drawImage(img, 0, 0, newBuf.getWidth(), newBuf.getHeight(), null);
		g.dispose();
		return newBuf;
	}
	public static BufferedImage getBestFormat(int width, int height){ return GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT); }
}