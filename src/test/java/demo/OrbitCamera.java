package demo;

import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IUpdateable;
import net.whg.we.main.Timer;
import net.whg.we.main.Transform3D;
import net.whg.we.rendering.Camera;
import net.whg.we.window.Input;

public class OrbitCamera extends AbstractBehavior implements IUpdateable
{
    private Camera camera;
    private Input input;
    private float distance = 10f;
    private float sensitivity = 0.01f;
    private float yaw = 0f;
    private float pitch = 0f;
    private Vector3f offset = new Vector3f();

    public OrbitCamera(Camera camera, Input input)
    {
        this.camera = camera;
        this.input = input;

        updatePosition();
    }

    @Override
    public void update(Timer timer)
    {
        if (!input.isMouseButtonDown(0))
            return;

        yaw -= input.getMouseDeltaX() * sensitivity;
        pitch -= input.getMouseDeltaY() * sensitivity;

        yaw %= Math.PI * 2;
        pitch = (float) Math.max(-Math.PI / 2f, Math.min(Math.PI / 2f, pitch));

        updatePosition();
    }

    private void updatePosition()
    {
        Transform3D transform = camera.getTransform();

        Quaternionf rot = transform.getRotation();
        rot.identity()
           .rotateLocalX(pitch)
           .rotateLocalY(yaw);

        transform.getPosition()
                 .set(0f, 0f, distance)
                 .rotate(rot);
    }

    public float getDistance()
    {
        return distance;
    }

    public void setDistance(float distance)
    {
        this.distance = distance;
    }

    public float getSensitivity()
    {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity)
    {
        this.sensitivity = sensitivity;
    }

    public float getYaw()
    {
        return yaw;
    }

    public float getPitch()
    {
        return pitch;
    }

    public Vector3f getOffset()
    {
        return offset;
    }
}
