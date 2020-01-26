package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.junit.Test;
import net.whg.we.rendering.Camera;

public class CameraTest
{
    @Test
    public void defaultProperties()
    {
        Camera camera = new Camera();

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
        Camera camera = new Camera();

        camera.setClippingDistance(15f, 30f);
        assertEquals(15f, camera.getNearClip(), 0f);
        assertEquals(30f, camera.getFarClip(), 0f);

        camera.setFov(0.5f);
        assertEquals(0.5f, camera.getFov(), 0f);
    }

    @Test
    public void getProjectionMatrix()
    {
        Camera camera = new Camera();

        Matrix4f mat = new Matrix4f();
        mat.perspective((float) Math.PI / 2f, 4f / 3f, 0.1f, 1000f);

        assertTrue(mat.equals(camera.getProjectionMatrix(), 0.0001f));
    }
}
