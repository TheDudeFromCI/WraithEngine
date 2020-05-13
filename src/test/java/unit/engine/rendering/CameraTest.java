package unit.engine.rendering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.junit.Test;
import net.whg.we.main.Transform3D;
import net.whg.we.rendering.Camera;
import net.whg.we.window.Screen;

public class CameraTest
{
    @Test
    public void defaultProperties()
    {
        Camera camera = new Camera(mock(Screen.class));

        assertEquals(0.1f, camera.getNearClip(), 0f);
        assertEquals(1000f, camera.getFarClip(), 0f);
        assertEquals(Math.PI / 2f, camera.getFov(), 0.0001f);

        assertEquals(new Vector3f(), camera.getTransform()
                                           .getPosition());
        assertEquals(new Quaternionf(), camera.getTransform()
                                              .getRotation());
        assertEquals(new Vector3f(1f), camera.getTransform()
                                             .getSize());
    }

    @Test
    public void setProperties()
    {
        Camera camera = new Camera(mock(Screen.class));

        camera.setClippingDistance(15f, 30f);
        assertEquals(15f, camera.getNearClip(), 0f);
        assertEquals(30f, camera.getFarClip(), 0f);

        camera.setFov(0.5f);
        assertEquals(0.5f, camera.getFov(), 0f);
    }

    @Test
    public void getProjectionMatrix()
    {
        Screen screen = mock(Screen.class);
        when(screen.getAspect()).thenReturn(4f / 3f);
        Camera camera = new Camera(screen);

        Matrix4f mat = new Matrix4f();
        mat.perspective((float) Math.PI / 2f, 4f / 3f, 0.1f, 1000f);

        assertTrue(mat.equals(camera.getProjectionMatrix(), 0.0001f));
    }

    @Test
    public void externalTransform()
    {
        Transform3D transform = new Transform3D();
        Camera camera = new Camera(transform, mock(Screen.class));

        assertTrue(transform == camera.getTransform());
    }
}
