package net.whg.we.resource;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.whg.we.rendering.TextureData;

public final class TextureLoader
{
    private TextureLoader()
    {}

    /**
     * Loads a texture from a file image file. The texture is loaded with default
     * settings applied. This operation supports the image formats of:
     * <ul>
     * <li>JPEG</li>
     * <li>PNG</li>
     * <li>BMP</li>
     * <li>WBMP</li>
     * <li>GIF</li>
     * </ul>
     * For GIF file formats, only the first frame is sampled.
     * 
     * @param file
     *     - The image file to load.
     * @return The loaded texture data file.
     * @throws FileNotFoundException
     *     If the file cannot be found.
     * @throws IOException
     *     If the file cannot be read.
     */
    public static TextureData loadTexture(File file) throws IOException
    {
        if (!file.exists())
            throw new FileNotFoundException(file.getAbsolutePath());

        BufferedImage image = ImageIO.read(file);

        int width = image.getWidth();
        int height = image.getHeight();

        TextureData data = new TextureData(width, height);

        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                data.setPixel(x, y, image.getRGB(x, y));

        return data;
    }
}
