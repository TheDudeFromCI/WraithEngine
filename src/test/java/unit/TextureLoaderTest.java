package unit;

import static org.junit.Assert.assertEquals;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Test;
import net.whg.we.rendering.Color;
import net.whg.we.rendering.TextureData;
import net.whg.we.resource.TextureLoader;

public class TextureLoaderTest
{
    @Test
    public void loadTexture() throws IOException
    {
        TextureData data = TextureLoader.loadTexture(new File("src/test/res/grass.png"));

        assertEquals(16, data.getWidth());
        assertEquals(16, data.getHeight());
        assertEquals(new Color(0.1882353f, 0.32156864f, 0.19215687f), data.getPixelAsColor(6, 8));
    }

    @Test(expected = FileNotFoundException.class)
    public void loadTexture_fileNotFound() throws IOException
    {
        TextureLoader.loadTexture(new File("./not-a-real-file"));
    }
}
