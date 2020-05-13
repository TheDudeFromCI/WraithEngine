package unit.library.behaviors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.main.Timer;
import net.whg.we.main.Transform3D;
import net.whg.we.rendering.Camera;
import net.whg.lib.behaviors.OrbitCamera;
import net.whg.we.window.Input;

public class OrbitCameraTest
{
    @Test
    public void createOrbit()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);

        OrbitCamera orbit = new OrbitCamera(camera, input);
        assertEquals(camera, orbit.getCamera());
    }

    @Test
    public void updateYaw()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);
        when(input.isMouseButtonDown(0)).thenReturn(true);
        when(input.getMouseDeltaX()).thenReturn(-1f);
        when(input.getMouseDeltaY()).thenReturn(0f);

        Timer timer = mock(Timer.class);

        OrbitCamera orbit = new OrbitCamera(camera, input);
        orbit.setSensitivity(0.05f);

        assertEquals(0f, orbit.getYaw(), 0.0001f);
        assertEquals(0f, orbit.getPitch(), 0.0001f);

        orbit.update(timer);
        assertEquals(0.05f, orbit.getYaw(), 0.0001f);
        assertEquals(0f, orbit.getPitch(), 0.0001f);

        orbit.update(timer);
        assertEquals(0.1f, orbit.getYaw(), 0.0001f);
        assertEquals(0f, orbit.getPitch(), 0.0001f);
    }

    @Test
    public void updatePitch()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);
        when(input.isMouseButtonDown(0)).thenReturn(true);
        when(input.getMouseDeltaX()).thenReturn(0f);
        when(input.getMouseDeltaY()).thenReturn(-1f);

        Timer timer = mock(Timer.class);

        OrbitCamera orbit = new OrbitCamera(camera, input);
        orbit.setSensitivity(0.05f);

        assertEquals(0f, orbit.getYaw(), 0.0001f);
        assertEquals(0f, orbit.getPitch(), 0.0001f);

        orbit.update(timer);
        assertEquals(0f, orbit.getYaw(), 0.0001f);
        assertEquals(0.05f, orbit.getPitch(), 0.0001f);

        orbit.update(timer);
        assertEquals(0f, orbit.getYaw(), 0.0001f);
        assertEquals(0.1f, orbit.getPitch(), 0.0001f);
    }

    @Test
    public void updateCameraTransform()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);

        OrbitCamera orbit = new OrbitCamera(camera, input);

        orbit.setDistance(10f);
        orbit.setRotation(0.8f, 1.2f);

        assertEquals(2.5993955f, transform.getPosition().x, 0.001f);
        assertEquals(-9.320391f, transform.getPosition().y, 0.001f);
        assertEquals(2.5245704f, transform.getPosition().z, 0.001f);

        assertEquals(0.5200701f, transform.getRotation().x, 0.001f);
        assertEquals(0.3214008f, transform.getRotation().y, 0.001f);
        assertEquals(-0.219882f, transform.getRotation().z, 0.001f);
        assertEquals(0.7601844f, transform.getRotation().w, 0.0001f);

        assertEquals(10f, orbit.getDistance(), 0f);
        assertEquals(0.8f, orbit.getYaw(), 0f);
        assertEquals(1.2f, orbit.getPitch(), 0f);
    }

    @Test
    public void update_mouseNotHeld()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);
        when(input.isMouseButtonDown(0)).thenReturn(false);
        when(input.getMouseDeltaX()).thenReturn(10f);
        when(input.getMouseDeltaY()).thenReturn(10f);

        OrbitCamera orbit = new OrbitCamera(camera, input);

        orbit.update(mock(Timer.class));
        assertEquals(0f, orbit.getYaw(), 0.0001f);
        assertEquals(0f, orbit.getPitch(), 0.0001f);
    }

    @Test
    public void setSensitivity()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);

        OrbitCamera orbit = new OrbitCamera(camera, input);

        orbit.setSensitivity(15f);
        assertEquals(15f, orbit.getSensitivity(), 0f);
    }

    @Test
    public void rotateAroundOffset()
    {
        Transform3D transform = new Transform3D();
        Camera camera = mock(Camera.class);
        when(camera.getTransform()).thenReturn(transform);

        Input input = mock(Input.class);

        OrbitCamera orbit = new OrbitCamera(camera, input);

        orbit.setDistance(10f);
        orbit.setRotation(0.8f, 1.2f);

        orbit.getOffset()
             .set(31f, 22f, 13f);
        orbit.updatePosition();

        assertEquals(33.5993955f, transform.getPosition().x, 0.001f);
        assertEquals(12.6796090f, transform.getPosition().y, 0.001f);
        assertEquals(15.5245704f, transform.getPosition().z, 0.001f);

        assertEquals(0.5200701f, transform.getRotation().x, 0.001f);
        assertEquals(0.3214008f, transform.getRotation().y, 0.001f);
        assertEquals(-0.219882f, transform.getRotation().z, 0.001f);
        assertEquals(0.7601844f, transform.getRotation().w, 0.0001f);

        assertEquals(10f, orbit.getDistance(), 0f);
        assertEquals(0.8f, orbit.getYaw(), 0f);
        assertEquals(1.2f, orbit.getPitch(), 0f);
    }
}
