package net.whg.we.util;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Represents a standard tranformation object in 3D space. It is used to allow
 * simple transformations, (position, rotation, and scale) to be used and
 * calculated as needed into a transformation matrix.
 */
public class Transform3D
{
    private Transform3D parent;

    private Vector3f position = new Vector3f();
    private Quaternionf rotation = new Quaternionf();
    private Vector3f size = new Vector3f(1f, 1f, 1f);

    private Matrix4f matrixBuffer = new Matrix4f();
    private Vector3f vectorBuffer = new Vector3f();
    private Quaternionf quaternionBuffer = new Quaternionf();

    /**
     * Gets the position of this transform in 3D space.
     *
     * @return The position vector for this transform.
     */
    public Vector3f getPosition()
    {
        return position;
    }

    /**
     * Sets the position of this transform based on the values given by the input.
     *
     * @param position
     *     - The position vector to copy the values from.
     */
    public void setPosition(Vector3f position)
    {
        this.position.set(position);
    }

    /**
     * Sets the position of this transform based on the values given by the input.
     *
     * @param x
     *     - The x position.
     * @param y
     *     - The y position.
     * @param z
     *     - The z position.
     */
    public void setPosition(float x, float y, float z)
    {
        position.set(x, y, z);
    }

    /**
     * Gets the size/scaling vector for this transform.
     *
     * @return The size vector for this transform.
     */
    public Vector3f getSize()
    {
        return size;
    }

    /**
     * Gets the size of this transform based on the values given by the inout.
     *
     * @param size
     *     - The size vector to copt the values from.
     */
    public void setSize(Vector3f size)
    {
        this.size.set(size);
    }

    /**
     * Gets the size of this transform based on the values given by the inout.
     *
     * @param size
     *     - The uniform scaling value.
     */
    public void setSize(float size)
    {
        this.size.set(size, size, size);
    }

    /**
     * Gets the size of this transform based on the values given by the inout.
     *
     * @param x
     *     - The x scale.
     * @param y
     *     - The y scale.
     * @param z
     *     - The z scale.
     */
    public void setSize(float x, float y, float z)
    {
        size.set(x, y, z);
    }

    /**
     * Gets the current rotation of this transform.
     *
     * @return The rotation of this transform.
     */
    public Quaternionf getRotation()
    {
        return rotation;
    }

    /**
     * Sets the rotation of this transform.
     *
     * @param rot
     *     - The quaternion to copy the rotation from.
     */
    public void setRotation(Quaternionf rot)
    {
        rotation.set(rot);
    }

    /**
     * Calculates the local matrix for this transform, and stores it in the output
     * matrix parameter.
     *
     * @param out
     *     - The matrix to store the output into.
     */
    public void getLocalMatrix(Matrix4f out)
    {
        out.identity();
        out.translate(position);
        out.rotate(rotation);
        out.scale(size);
    }

    /**
     * Calculates the full matrix for this transform and stores it in the output
     * matrix parameter. A full transform is considered:
     * <p>
     * <code>out = parent * local</code>
     * <p>
     * for the given transforms. If this transform does not have a parent, then this
     * method returns the local transformation matrix.
     *
     * @param out
     *     - The matrix to store the output into.
     */
    public void getFullMatrix(Matrix4f out)
    {
        if (parent == null)
        {
            getLocalMatrix(out);
            return;
        }

        parent.getFullMatrix(matrixBuffer);
        out.set(matrixBuffer);

        getLocalMatrix(matrixBuffer);
        out.mul(matrixBuffer);
    }

    /**
     * Calculates the inverse of this local matrix.
     *
     * @param out
     *     - The matrix to write the output to.
     */
    public void getInverseMatrix(Matrix4f out)
    {
        out.identity();
        out.rotate(rotation.invert(quaternionBuffer));
        out.translate(position.negate(vectorBuffer));
        out.scale(size.negate(vectorBuffer));
    }

    @Override
    public int hashCode()
    {
        return position.hashCode() ^ rotation.hashCode() ^ size.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        Transform3D o = (Transform3D) obj;
        return position.equals(o.position) && rotation.equals(o.rotation) && size.equals(o.size);
    }

    /**
     * Gets the parent transformation for this object. When calculating the
     * transformation matrix, this object's transformation is influenced by the
     * parent's transformation.
     * 
     * @return The parent transformation object, or null if this transformation has
     *     no parent.
     */
    public Transform3D getParent()
    {
        return parent;
    }

    /**
     * Assigned a new parent transformation for this object.
     * 
     * @param parent
     *     - The new parent transformation object, or null to remove this
     *     transformation's parent.
     */
    public void setParent(Transform3D parent)
    {
        this.parent = parent;
    }
}
