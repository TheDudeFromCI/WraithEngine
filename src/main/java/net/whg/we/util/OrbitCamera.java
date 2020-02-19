package net.whg.we.util;

import org.joml.Math;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IUpdateable;
import net.whg.we.main.Timer;
import net.whg.we.rendering.Camera;
import net.whg.we.window.Input;

/**
 * An orbital camera is a camera switch can spin freely around an origin point
 * via use of the mouse. The camera is set to operate when mouse button 0 is
 * held down. This behavior can be attached to any game object to run in the
 * background.
 * <p>
 * This type of camera is useful when it comes to actions such as displaying a
 * rendered model.
 */
public class OrbitCamera extends AbstractBehavior implements IUpdateable
{
    private Camera camera;
    private Input input;
    private float distance = 10f;
    private float sensitivity = 0.01f;
    private float yaw = 0f;
    private float pitch = 0f;
    private Vector3f offset = new Vector3f();

    /**
     * Creates a new new orbit camera.
     * 
     * @param camera
     *     - The camera to move.
     * @param input
     *     - The input handler which controls the camera.
     */
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

        updatePosition();
    }

    /**
     * Updates the position of the camera based on the current yaw and pitch. This
     * method will also clamp the yaw and pitch into the acceptable range if
     * required.
     */
    private void updatePosition()
    {
        yaw %= Math.PI * 2;
        pitch = (float) Math.max(-Math.PI / 2f, Math.min(Math.PI / 2f, pitch));

        Quaternionf rot = camera.getTransform()
                                .getRotation();

        rot.identity()
           .rotateLocalX(pitch)
           .rotateLocalY(yaw);

        camera.getTransform()
              .getPosition()
              .set(0f, 0f, distance)
              .rotate(rot);
    }

    /**
     * Gets the distance this camera will hover away from the origin point.
     * 
     * @return The distance.
     */
    public float getDistance()
    {
        return distance;
    }

    /**
     * Sets the distance this camera should hover from the origin point.
     * 
     * @param distance
     *     - The new distance.
     */
    public void setDistance(float distance)
    {
        this.distance = distance;
        updatePosition();
    }

    /**
     * Gets the current mouse sensitivity in radians per mouse delta pixel.
     * 
     * @return The mouse sensitivity.
     */
    public float getSensitivity()
    {
        return sensitivity;
    }

    /**
     * Sets the mouse sensitivity in radians per mouse delta pixel.
     * 
     * @param sensitivity
     *     - The new mouse sensitivity.
     */
    public void setSensitivity(float sensitivity)
    {
        this.sensitivity = sensitivity;
    }

    /**
     * Gets the current yaw of the camera. This is the rotation of the camera around
     * the object around the y axis.
     * 
     * @return The yaw in radians.
     */
    public float getYaw()
    {
        return yaw;
    }

    /**
     * Gets the current pitch of the camera. This is the rotation of the camera
     * around the object around the x axis.
     * 
     * @return The pitch in radians.
     */
    public float getPitch()
    {
        return pitch;
    }

    /**
     * Gets the offset vector for the cameras hover. This can be used if the camera
     * should orbit around a location other than the origin.
     * 
     * @return The offset vector.
     */
    public Vector3f getOffset()
    {
        return offset;
    }

    /**
     * Assigns a new rotation value for where the camera should be located in its
     * orbit. This method will update the camera instantly.
     * 
     * @param yaw
     *     - The new yaw value in radians.
     * @param pitch
     *     - The new pitch value in radians.
     */
    public void setRotation(float yaw, float pitch)
    {
        this.yaw = yaw;
        this.pitch = pitch;

        updatePosition();
    }
}
