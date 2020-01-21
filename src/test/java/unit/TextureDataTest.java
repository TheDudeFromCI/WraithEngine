package unit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.TextureData.NormalMapMode;
import net.whg.we.rendering.TextureData.SampleMode;
import net.whg.we.rendering.TextureData.WrapMode;
import net.whg.we.util.Color;

public class TextureDataTest
{
    @Test
    public void createTexture_defaultValues()
    {
        TextureData data = new TextureData(16, 16);

        assertEquals(16, data.getWidth());
        assertEquals(16, data.getHeight());
        assertEquals(0, data.getPixel(8, 8));

        assertEquals(SampleMode.BILINEAR, data.getSampleMode());
        assertEquals(NormalMapMode.NON_NORMALMAP, data.getNormalMapMode());
        assertEquals(WrapMode.REPEAT, data.getWrapMode());
        assertEquals(1, data.getAnisotropicFiltering());
        assertEquals(false, data.hasMipmap());
        assertEquals(true, data.issRGB());
    }

    @Test
    public void assignValues()
    {
        TextureData data = new TextureData(8, 24);

        data.setSampleMode(SampleMode.TRILINEAR);
        assertEquals(SampleMode.TRILINEAR, data.getSampleMode());

        data.setNormalMapMode(NormalMapMode.OPENGL_STYLE);
        assertEquals(NormalMapMode.OPENGL_STYLE, data.getNormalMapMode());

        data.setWrapMode(WrapMode.CLAMP);
        assertEquals(WrapMode.CLAMP, data.getWrapMode());

        data.setAnisotropicFiltering(4);
        assertEquals(4, data.getAnisotropicFiltering());

        data.setMipmap(true);
        assertEquals(true, data.hasMipmap());

        data.setsRGB(false);
        assertEquals(false, data.issRGB());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTexture_negativeSizeX()
    {
        new TextureData(-11, 10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTexture_negativeSizeY()
    {
        new TextureData(5, -4);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSampleMode_null()
    {
        new TextureData(1, 1).setSampleMode(null);
        ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNormalMapMode_null()
    {
        new TextureData(1, 1).setNormalMapMode(null);
        ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWrapMode_null()
    {
        new TextureData(1, 1).setWrapMode(null);
        ;
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAniostropicFiltering_negative()
    {
        new TextureData(1, 1).setAnisotropicFiltering(-2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setAniostropicFiltering_tooHigh()
    {
        new TextureData(1, 1).setAnisotropicFiltering(19);
    }

    @Test
    public void setPixel()
    {
        TextureData data = new TextureData(8, 8);

        data.setPixel(5, 3, 370);
        assertEquals(370, data.getPixel(5, 3));
    }

    @Test
    public void setPixel_asColor()
    {
        TextureData data = new TextureData(8, 8);

        data.setPixelAsColor(5, 3, new Color(0.2f, 0.4f, 0.8f));
        assertEquals(new Color(0.2f, 0.4f, 0.8f), data.getPixelAsColor(5, 3));
    }
}
