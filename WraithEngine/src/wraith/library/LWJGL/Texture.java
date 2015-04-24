package wraith.library.LWJGL;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Hashtable;
import javax.imageio.ImageIO;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Texture{
	private int textureId;
	public Texture(File file){
		try{
			FileInputStream fis = new FileInputStream(file);
			textureId=getTexture(fis);
			fis.close();
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(1);
		}
	}
	public void bind(){ GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId); }
	private static int getTexture(InputStream in){
		try{
			ColorModel glAlphaColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8, 8}, true, false, Transparency.TRANSLUCENT, DataBuffer.TYPE_BYTE);
			ColorModel glColorModel = new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_sRGB), new int[]{8, 8, 8, 0}, false, false, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);
			IntBuffer intbuf = BufferUtils.createIntBuffer(1);
			GL11.glGenTextures(intbuf);
			int id = intbuf.get(0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
			BufferedImage bi = ImageIO.read(in);
			int format = bi.getColorModel().hasAlpha()?GL11.GL_RGBA:GL11.GL_RGB;
			ByteBuffer texData;
			WritableRaster raster;
			BufferedImage texImage;
			int texWidth = 2;
			int texHeight = 2;
			while(texWidth<bi.getWidth())texWidth*=2;
			while(texHeight<bi.getHeight())texHeight*=2;
			if(bi.getColorModel().hasAlpha()){
				raster=Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 4, null);
				texImage=new BufferedImage(glAlphaColorModel, raster, false, new Hashtable<String, Object>());
			}else{
				raster=Raster.createInterleavedRaster(DataBuffer.TYPE_BYTE, texWidth, texHeight, 3, null);
				texImage=new BufferedImage(glColorModel, raster, false, new Hashtable<String, Object>());
			}
			Graphics g = texImage.getGraphics();
			g.setColor(new Color(0f, 0f, 0f, 0f));
			g.fillRect(0, 0, texWidth, texHeight);
			g.drawImage(bi, 0, 0, null);
			byte[] data = ((DataBufferByte)texImage.getRaster().getDataBuffer()).getData();
			texData = ByteBuffer.allocateDirect(data.length);
			texData.order(ByteOrder.nativeOrder());
			texData.put(data, 0, data.length);
			texData.flip();
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, bi.getWidth(), bi.getHeight(), 0, format, GL11.GL_UNSIGNED_BYTE, texData);
			return id;
		}catch(Exception exception){
			exception.printStackTrace();
			System.exit(1);
			return 0;
		}
	}
}