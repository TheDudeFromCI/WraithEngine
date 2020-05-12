package unit.engine.rendering;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.rendering.CullingMode;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;

public class OpenGLRenderingEngineTest
{
    @Test
    public void init()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);

        renderingEngine.init();

        verify(opengl).init();
        verify(opengl).setDepthTesting(true);
        verify(opengl).setCullingMode(CullingMode.BACK_FACING);
    }

    @Test
    public void dispose()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        renderingEngine.dispose();
        renderingEngine.dispose();

        assertTrue(renderingEngine.isDisposed());
        verify(opengl, times(1)).dispose();
    }

    @Test
    public void setCullingMode_none()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        renderingEngine.setCullingMode(CullingMode.NONE);
        renderingEngine.setCullingMode(CullingMode.NONE);

        verify(opengl).init();
        verify(opengl, times(1)).setCullingMode(CullingMode.NONE);
    }

    @Test
    public void setDepthTesting_false()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        renderingEngine.setDepthTesting(false);
        renderingEngine.setDepthTesting(false);

        verify(opengl).init();
        verify(opengl, times(1)).setDepthTesting(false);
    }
}
