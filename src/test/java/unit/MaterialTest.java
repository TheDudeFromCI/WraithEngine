package unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.joml.Matrix4f;
import org.junit.Test;
import net.whg.we.rendering.Material;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.ITexture;

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

        verify(shader).setUniformMat4(eq(Material.UNIFORM_MVP), any());
    }

    @Test
    public void setTextures()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        ITexture texture1 = mock(ITexture.class);
        ITexture texture2 = mock(ITexture.class);

        ITexture[] textures = {texture1, texture2};
        String[] uniforms = {"diffuse", "normal"};

        material.setTextures(textures, uniforms);

        assertArrayEquals(textures, material.getTextures());
        assertArrayEquals(uniforms, material.getTextureUniformNames());
    }

    @Test
    public void setTextures_isBound()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        ITexture texture1 = mock(ITexture.class);
        ITexture texture2 = mock(ITexture.class);

        ITexture[] textures = {texture1, texture2};
        String[] uniforms = {"diffuse", "normal"};

        material.setTextures(textures, uniforms);

        material.bind();

        verify(texture1).bind(0);
        verify(texture2).bind(1);
        verify(shader).setUniformInt("diffuse", 0);
        verify(shader).setUniformInt("normal", 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTextures_differentArrayLengths()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        ITexture texture1 = mock(ITexture.class);
        ITexture texture2 = mock(ITexture.class);

        ITexture[] textures = {texture1, texture2};
        String[] uniforms = {"specular"};

        material.setTextures(textures, uniforms);
    }

    @Test(expected = NullPointerException.class)
    public void setTextures_nullTextures()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        String[] uniforms = {"specular"};

        material.setTextures(null, uniforms);
    }

    @Test(expected = NullPointerException.class)
    public void setTextures_nullTextureNames()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        ITexture texture1 = mock(ITexture.class);
        ITexture texture2 = mock(ITexture.class);

        ITexture[] textures = {texture1, texture2};

        material.setTextures(textures, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTextures_tooManyElements()
    {
        IShader shader = mock(IShader.class);
        Material material = new Material(shader);

        ITexture[] textures = new ITexture[25];
        String[] uniforms = new String[25];

        material.setTextures(textures, uniforms);
    }
}
