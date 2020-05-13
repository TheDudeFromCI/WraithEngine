package unit.engine.rendering;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.junit.Test;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;

public class GLMeshTest
{
    private VertexData vertexData()
    {
        float[] data = {0f, 1f, 0f, 1f, 0f, 0f, 0f, 0f, 1f};
        short[] triangles = {0, 1, 2};

        ShaderAttributes attributes = new ShaderAttributes();
        attributes.addAttribute("pos", 3);

        return new VertexData(data, triangles, attributes);
    }

    private IOpenGL opengl()
    {
        int[] bufferCount = {1};
        IOpenGL opengl = mock(IOpenGL.class);
        when(opengl.generateBuffer()).thenAnswer(a -> bufferCount[0]++);
        when(opengl.generateVertexArray()).thenReturn(1);

        return opengl;
    }

    @Test
    public void createMesh()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        assertNotNull(mesh);
    }

    @Test
    public void render_noData_ignore()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.render();

        verify(opengl, never()).drawElements(anyInt());
    }

    @Test
    public void dispose()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.dispose();
        mesh.dispose();

        assertTrue(mesh.isDisposed());

        verify(opengl, never()).deleteBuffer(anyInt());
        verify(opengl, never()).deleteVertexArray(anyInt());
    }

    @Test
    public void dispose_safelyDisposeData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.update(vertexData());

        mesh.dispose();

        verify(opengl, times(2)).deleteBuffer(anyInt());
        verify(opengl, times(1)).deleteVertexArray(anyInt());
    }

    @Test
    public void createData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.update(vertexData());

        verify(opengl, times(1)).generateVertexArray();
        verify(opengl, times(2)).generateBuffer();

        verify(opengl, times(1)).uploadBufferData((FloatBuffer) any());
        verify(opengl, times(1)).uploadBufferData((ShortBuffer) any());
    }

    @Test
    public void updateData()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.update(vertexData());
        reset(opengl);

        mesh.update(vertexData());

        verify(opengl, never()).generateVertexArray();
        verify(opengl, never()).generateBuffer();

        verify(opengl, times(1)).uploadBufferData((FloatBuffer) any());
        verify(opengl, times(1)).uploadBufferData((ShortBuffer) any());
    }

    @Test
    public void render()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.update(vertexData());

        mesh.render();

        verify(opengl).drawElements(3);
    }

    @Test(expected = IllegalStateException.class)
    public void render_alreadyDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.dispose();

        mesh.render();
    }

    @Test(expected = IllegalStateException.class)
    public void update_alreadyDisposed()
    {
        IOpenGL opengl = opengl();
        OpenGLRenderingEngine renderingEngine = new OpenGLRenderingEngine(opengl);
        renderingEngine.init();

        IMesh mesh = renderingEngine.createMesh();
        mesh.dispose();

        mesh.update(vertexData());
    }
}
