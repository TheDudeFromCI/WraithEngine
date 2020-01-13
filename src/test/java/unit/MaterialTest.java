package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.joml.Matrix4f;
import org.junit.Test;
import net.whg.we.rendering.Material;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IShader;

public class MaterialTest
{
    @Test
    public void createMaterial_getShader()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        assertEquals(shader, material.getShader());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMaterial_nullShader()
    {
        new Material(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createMaterial_disposedShader()
    {
        IShader shader = mock(IShader.class);
        when(shader.isDisposed()).thenReturn(true);

        new Material(shader);
    }

    @Test
    public void bind_shaderIsBound()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        material.bind();

        verify(shader).bind();
    }

    @Test
    public void cameraMatrix()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        Camera camera = new Camera();
        Matrix4f matrix = new Matrix4f();

        material.setCameraMatrix(camera, matrix);

        verify(shader).setUniformMat4(Material.UNIFORM_MVP, any());
    }
}
