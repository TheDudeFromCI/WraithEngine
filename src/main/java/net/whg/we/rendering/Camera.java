package net.whg.we.rendering;

import org.joml.Matrix4f;
import net.whg.we.util.Transform3D;

public class Camera
{
    private final Transform3D transform = new Transform3D();
    private final Matrix4f projectionMatrix = new Matrix4f();
    private float fov = (float) Math.toRadians(90f);
    private float nearClip = 0.1f;
    private float farClip = 1000f;

    public Camera()
    {
        rebuildProjectionMatrix();
    }

    private void rebuildProjectionMatrix()
    {
        // float aspect = (float) Screen.width() / Screen.height();
        float aspect = 4f / 3f;

        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspect, nearClip, farClip);
    }

    public Matrix4f getProjectionMatrix()
    {
        return projectionMatrix;
    }

    public float getFov()
    {
        return fov;
    }

    public void setFov(float radians)
    {
        fov = radians;
        rebuildProjectionMatrix();
    }

    public float getNearClip()
    {
        return nearClip;
    }

    public float getFarClip()
    {
        return farClip;
    }

    public void setClippingDistance(float near, float far)
    {
        nearClip = near;
        farClip = far;

        rebuildProjectionMatrix();
    }

    public Transform3D getTransform()
    {
        return transform;
    }
}
