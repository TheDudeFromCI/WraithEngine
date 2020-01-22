package unit;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;
import net.whg.we.rendering.Color;

public class GLScreenClearTest
{
    @Test
    public void clearScreen_defaultValues()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IScreenClearHandler screenClear = renderingEngine.getScreenClearHandler();
        screenClear.clearScreen();

        verify(opengl).setClearColor(0f, 0f, 0f, 1f);
        verify(opengl).clearScreen(true, true);
    }

    @Test
    public void clearScreen_colorRed_withDepth()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IScreenClearHandler screenClear = renderingEngine.getScreenClearHandler();
        screenClear.setClearColor(new Color(1f, 0f, 0f));
        screenClear.clearScreen();

        verify(opengl).setClearColor(1f, 0f, 0f, 1f);
        verify(opengl).clearScreen(true, true);
    }

    @Test
    public void clearScreen_noColor_noDepth()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        reset(opengl);

        IScreenClearHandler screenClear = renderingEngine.getScreenClearHandler();
        screenClear.setClearColor(null);
        screenClear.setClearDepth(false);
        screenClear.clearScreen();

        verify(opengl, never()).setClearColor(anyFloat(), anyFloat(), anyFloat(), anyFloat());
        verify(opengl, never()).clearScreen(anyBoolean(), anyBoolean());
    }
}
