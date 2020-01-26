package unit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.lwjgl.BufferUtils;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.RawShaderCode;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;

public class GLShaderTest
{
    private RawShaderCode shaderCode()
    {
        return new RawShaderCode("", "");
    }

    private IOpenGL opengl()
    {
        IOpenGL opengl = mock(IOpenGL.class);
        when(opengl.createFragementShader(anyString())).thenReturn(1);
        when(opengl.createVertexShader(anyString())).thenReturn(1);
        when(opengl.createShaderProgram()).thenReturn(1);

        return opengl;
    }

    @Test
    public void createShader()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        assertNotNull(shader);
    }

    @Test(expected = IllegalStateException.class)
    public void bind_noData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.bind();
    }

    @Test
    public void dispose_noData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.dispose();
        shader.dispose();

        verify(opengl, never()).deleteShaderProgram(anyInt());
    }

    @Test
    public void compile()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        assertFalse(shader.isCreated());

        shader.compile(shaderCode());

        verify(opengl, never()).deleteShaderProgram(anyInt());

        verify(opengl).createVertexShader(anyString());
        verify(opengl).createFragementShader(anyString());
        verify(opengl).createShaderProgram();
        verify(opengl).linkShader(anyInt());

        assertTrue(shader.isCreated());
    }

    @Test
    public void recompile()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.compile(shaderCode());
        reset(opengl);

        shader.compile(shaderCode());

        verify(opengl).deleteShaderProgram(anyInt());

        verify(opengl).createVertexShader(anyString());
        verify(opengl).createFragementShader(anyString());
        verify(opengl).createShaderProgram();
        verify(opengl).linkShader(anyInt());
    }

    @Test
    public void dispose_safelyDisposeData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.compile(shaderCode());
        reset(opengl);

        shader.dispose();
        shader.dispose();

        verify(opengl, times(1)).deleteShaderProgram(anyInt());
    }

    @Test(expected = IllegalStateException.class)
    public void setUniformMat4_noData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.setUniformMat4("any_uni", BufferUtils.createFloatBuffer(16));
    }

    @Test
    public void setUniformMat4_sampleLocationOnce()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.compile(shaderCode());

        shader.setUniformMat4("any_uni", BufferUtils.createFloatBuffer(16));
        shader.setUniformMat4("any_uni", BufferUtils.createFloatBuffer(16));

        verify(opengl, times(1)).bindShader(1);
        verify(opengl, times(1)).getUniformLocation(anyInt(), anyString());
    }

    @Test(expected = IllegalStateException.class)
    public void compile_alreadyDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.dispose();
        shader.compile(shaderCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void compile_nullShaderCode()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.compile(null);
    }

    @Test(expected = IllegalStateException.class)
    public void bind_alreadyDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.dispose();
        shader.bind();
    }

    @Test
    public void dispose_boundShader()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.compile(shaderCode());
        shader.bind();

        shader.dispose();

        verify(opengl).bindShader(0);
    }

    @Test(expected = IllegalStateException.class)
    public void setUniformMat4_alreadyDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.dispose();

        shader.setUniformMat4("cam", BufferUtils.createFloatBuffer(16));
    }

    @Test
    public void setUniformInt()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.compile(shaderCode());
        shader.setUniformInt("texture", 1);

        verify(opengl).getUniformLocation(anyInt(), eq("texture"));
        verify(opengl).setUniformInt(anyInt(), eq(1));
    }

    @Test(expected = IllegalStateException.class)
    public void setUniformInt_alreadyDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IShader shader = renderingEngine.createShader();
        shader.dispose();

        shader.setUniformInt("texture", 0);
    }
}
