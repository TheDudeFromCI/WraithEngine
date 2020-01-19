package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.junit.Assert;
import org.junit.Test;
import net.whg.we.util.Transform3D;

public class Transform3DTest
{
    @Test
    public void setPosition_FromFloat()
    {
        Transform3D t = new Transform3D();
        t.setPosition(0, 1, 2);

        Vector3f pos = t.getPosition();
        Assert.assertEquals(0f, pos.x, 0f);
        Assert.assertEquals(1f, pos.y, 0f);
        Assert.assertEquals(2f, pos.z, 0f);
    }

    @Test
    public void setPosition_FromVector()
    {
        Transform3D t = new Transform3D();
        t.setPosition(new Vector3f(10f, 20f, 30f));

        Vector3f pos = t.getPosition();
        Assert.assertEquals(10f, pos.x, 0f);
        Assert.assertEquals(20f, pos.y, 0f);
        Assert.assertEquals(30f, pos.z, 0f);
    }

    @Test
    public void default_position()
    {
        Transform3D t = new Transform3D();

        Vector3f pos = t.getPosition();
        Assert.assertEquals(0f, pos.x, 0f);
        Assert.assertEquals(0f, pos.y, 0f);
        Assert.assertEquals(0f, pos.z, 0f);
    }

    @Test
    public void setRotation_FromQuaternion()
    {
        Transform3D t = new Transform3D();
        t.setRotation(new Quaternionf(1, 2, 3, 4));

        Quaternionf rot = t.getRotation();
        Assert.assertEquals(1f, rot.x, 0f);
        Assert.assertEquals(2f, rot.y, 0f);
        Assert.assertEquals(3f, rot.z, 0f);
        Assert.assertEquals(4f, rot.w, 0f);
    }

    @Test
    public void default_rotation()
    {
        Transform3D t = new Transform3D();

        Quaternionf rot = t.getRotation();
        Assert.assertEquals(0f, rot.x, 0f);
        Assert.assertEquals(0f, rot.y, 0f);
        Assert.assertEquals(0f, rot.z, 0f);
        Assert.assertEquals(1f, rot.w, 0f);
    }

    @Test
    public void setScale_From1Float()
    {
        Transform3D t = new Transform3D();
        t.setSize(2f);

        Vector3f size = t.getSize();
        Assert.assertEquals(2f, size.x, 0f);
        Assert.assertEquals(2f, size.y, 0f);
        Assert.assertEquals(2f, size.z, 0f);
    }

    @Test
    public void setScale_From3Floats()
    {
        Transform3D t = new Transform3D();
        t.setSize(1f, 2f, 3f);

        Vector3f size = t.getSize();
        Assert.assertEquals(1f, size.x, 0f);
        Assert.assertEquals(2f, size.y, 0f);
        Assert.assertEquals(3f, size.z, 0f);
    }

    @Test
    public void setScale_Vector3f()
    {
        Transform3D t = new Transform3D();
        t.setSize(new Vector3f(10f, 20f, 30f));

        Vector3f size = t.getSize();
        Assert.assertEquals(10f, size.x, 0f);
        Assert.assertEquals(20f, size.y, 0f);
        Assert.assertEquals(30f, size.z, 0f);
    }

    @Test
    public void default_size()
    {
        Transform3D t = new Transform3D();

        Vector3f size = t.getSize();
        Assert.assertEquals(1f, size.x, 0f);
        Assert.assertEquals(1f, size.y, 0f);
        Assert.assertEquals(1f, size.z, 0f);
    }

    @Test
    public void getLocalMatrix()
    {
        Transform3D t = new Transform3D();

        t.setPosition(10f, 30f, 50f);
        t.getRotation()
         .rotateLocalX((float) Math.toRadians(90f));
        t.setSize(4f);

        Matrix4f mat = new Matrix4f();
        t.getLocalMatrix(mat);

        Matrix4f mat2 = new Matrix4f();
        mat2.translate(10f, 30f, 50f);
        mat2.rotate(new Quaternionf().rotateLocalX((float) Math.toRadians(90f)));
        mat2.scale(4f);

        Assert.assertEquals(mat2, mat);
    }

    @Test
    public void getFullMatrix()
    {
        Transform3D parent = new Transform3D();
        parent.setPosition(10f, 30f, 50f);
        parent.setRotation(new Quaternionf().rotateLocalX((float) Math.toRadians(90f)));
        parent.setSize(4f);

        Transform3D t = new Transform3D();

        t.setParent(parent);
        t.setPosition(-5f, -17f, -5f);
        t.getRotation()
         .rotateLocalX((float) Math.toRadians(45f));
        t.setSize(0.5f);

        Matrix4f mat = new Matrix4f();
        t.getFullMatrix(mat);

        Matrix4f mat2 = new Matrix4f();
        mat2.translate(-5f, -17f, -5f);
        mat2.rotate(new Quaternionf().rotateLocalX((float) Math.toRadians(45f)));
        mat2.scale(0.5f);

        Matrix4f tempMat2 = new Matrix4f();
        parent.getLocalMatrix(tempMat2);
        mat2 = tempMat2.mul(mat2);

        Assert.assertEquals(mat2, mat);
    }

    @Test
    public void getFullMatrix_NullParent()
    {
        Transform3D t = new Transform3D();
        t.setPosition(10f, 30f, 50f);
        t.getRotation()
         .rotateLocalX((float) Math.toRadians(90f));
        t.setSize(4f);

        Matrix4f mat = new Matrix4f();
        t.getFullMatrix(mat);

        Matrix4f mat2 = new Matrix4f();
        mat2.translate(10f, 30f, 50f);
        mat2.rotate(new Quaternionf().rotateLocalX((float) Math.toRadians(90f)));
        mat2.scale(4f);

        Assert.assertEquals(mat2, mat);
    }

    @Test
    public void equals_sameInstance()
    {
        Transform3D t = new Transform3D();
        t.setPosition(1, 2, 4);
        t.setSize(13f);

        assertEquals(t, t);
        assertEquals(t.hashCode(), t.hashCode());
    }

    @Test
    public void equals_diffInstance()
    {
        Transform3D t1 = new Transform3D();
        t1.setPosition(1, 2, 4);
        t1.setRotation(new Quaternionf(4, 3, 2, 1));
        t1.setSize(13f);

        Transform3D t2 = new Transform3D();
        t2.setPosition(1, 2, 4);
        t2.setRotation(new Quaternionf(4, 3, 2, 1));
        t2.setSize(13f);

        assertEquals(t1, t2);
        assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    public void equals_diffInstance_diffProperties()
    {
        Transform3D t1 = new Transform3D();
        t1.setPosition(1, 2, 4);
        t1.setRotation(new Quaternionf(4, 3, 30, 1));
        t1.setSize(13f);

        Transform3D t2 = new Transform3D();
        t2.setPosition(1, 2, 4);
        t2.setRotation(new Quaternionf(4, 3, 2, 1));
        t2.setSize(13f);

        assertNotEquals(t1, t2);
        assertNotEquals(t1.hashCode(), t2.hashCode());
    }
}
