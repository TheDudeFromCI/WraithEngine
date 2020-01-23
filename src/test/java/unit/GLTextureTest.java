package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.mockito.InOrder;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.TextureData;
import net.whg.we.rendering.TextureData.SampleMode;
import net.whg.we.rendering.TextureData.WrapMode;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;

public class GLTextureTest
{
    private IOpenGL opengl()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        when(opengl.generateTexture()).thenReturn(1);

        return opengl;
    }

    @Test
    public void createTexture()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        assertNotNull(renderingEngine.createTexture());
    }

    @Test
    public void bind()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        ITexture texture = renderingEngine.createTexture();
        texture.update(new TextureData(16, 16));
        texture.bind(2);

        InOrder order = inOrder(opengl);
        order.verify(opengl)
             .bindTextureSlot(2);
        order.verify(opengl)
             .bindTexture(1);
    }

    @Test
    public void update()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(8, 8);

        ITexture texture = renderingEngine.createTexture();
        assertFalse(texture.isCreated());

        texture.update(data);
        assertTrue(texture.isCreated());

        verify(opengl).uploadTexture2DDataRGBA8(any(), eq(8), eq(8));
    }

    @Test(expected = IllegalStateException.class)
    public void bind_isDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        ITexture texture = renderingEngine.createTexture();
        texture.dispose();

        texture.bind(0);
    }

    @Test(expected = IllegalStateException.class)
    public void bind_notCreated()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        ITexture texture = renderingEngine.createTexture();
        texture.bind(0);
    }

    @Test(expected = IllegalStateException.class)
    public void update_isDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        ITexture texture = renderingEngine.createTexture();
        texture.dispose();

        texture.update(new TextureData(2, 2));
    }

    @Test
    public void update_sampleMode_Nearest()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setSampleMode(SampleMode.NEAREST);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DNearestInterpolation(false);
    }

    @Test
    public void update_sampleMode_Nearest_WithMipmap()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setSampleMode(SampleMode.NEAREST);
        data.setMipmap(true);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DNearestInterpolation(true);
    }

    @Test
    public void update_sampleMode_Bilinear()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setSampleMode(SampleMode.BILINEAR);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DBilinearInterpolation(false);
    }

    @Test
    public void update_sampleMode_Bilinear_WithMipmap()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setSampleMode(SampleMode.BILINEAR);
        data.setMipmap(true);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DBilinearInterpolation(true);
    }

    @Test
    public void update_sampleMode_Trilinear()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setSampleMode(SampleMode.TRILINEAR);
        data.setMipmap(true);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DTrilinearpolation();
    }

    @Test
    public void update_sampleMode_Trilinear_NoMipmap()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setSampleMode(SampleMode.TRILINEAR);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DBilinearInterpolation(false);
    }

    @Test
    public void update_wrapMode_clamp()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setWrapMode(WrapMode.CLAMP);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DClampWrapMode();
    }

    @Test
    public void update_wrapMode_repeat()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        TextureData data = new TextureData(2, 2);
        data.setWrapMode(WrapMode.REPEAT);

        ITexture texture = renderingEngine.createTexture();
        texture.update(data);

        verify(opengl).setTexture2DRepeatWrapMode();
    }

    @Test
    public void dispose()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        ITexture texture = renderingEngine.createTexture();
        texture.update(new TextureData(2, 2));

        texture.dispose();
        texture.dispose();

        verify(opengl, times(1)).deleteTexture(anyInt());
        assertTrue(texture.isDisposed());
    }

    @Test
    public void dispose_noData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        ITexture texture = renderingEngine.createTexture();

        texture.dispose();

        verify(opengl, never()).deleteTexture(anyInt());
        assertTrue(texture.isDisposed());
    }
}
