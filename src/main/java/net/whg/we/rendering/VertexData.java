package net.whg.we.rendering;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/**
 * Represents a structure of triangles and vertices which are used to contruct a
 * mesh. Vertices may have shader attributes applied to them as well.
 *
 * @author TheDudeFromCI
 */
public class VertexData implements Externalizable
{
    private float[] data;
    private short[] triangles;
    private ShaderAttributes attributes;

    /**
     * Creates a new, empty, vertex data object.
     */
    public VertexData()
    {
        // This constructor exists for externalizable to work

        this(new float[0], new short[0], new ShaderAttributes());
    }

    /**
     * Creates a new vertex data object based on the given input information.
     *
     * @param data
     *     - The vertex data array. The vertices are stored as a list of floats as
     *     specified by the given shader attribute object.
     * @param triangles
     *     - An array of vertex pointers to determine the individual triangles of
     *     this vertex data. A single triangle is 3 continuous steps along this
     *     array, with each index pointing to a vertex data at that corner of the
     *     triangle.
     * @param attributes
     *     - The shader attribute object that is used to determine how vertices are
     *     layed out within the vertex float array.
     */
    public VertexData(float[] data, short[] triangles, ShaderAttributes attributes)
    {
        this.data = data;
        this.triangles = triangles;
        this.attributes = attributes;
    }

    /**
     * Returns the array of floats used to specify vertices within this vertex data.
     * The array contains all vertices formated as specified by the shader
     * attributes object.
     *
     * @return The array of vertices within this vertex data object.
     */
    public float[] getDataArray()
    {
        return data;
    }

    /**
     * Gets the size of each vertex based on the number of floats required to
     * specify a single vertex, as defined by the shader attributes object.
     *
     * @return The size of each vertex.
     */
    public int getVertexSize()
    {
        return attributes.getVertexSize();
    }

    /**
     * Gets the size of each vertex in bytes.
     *
     * @return The size of each vertex in bytes.
     * @see {@link #getVertexSize()}
     */
    public int getVertexByteSize()
    {
        return attributes.getVertexByteSize();
    }

    /**
     * Gets an array of all triangles and the indices of the vertices they point to.
     * A single triangle is 3 consecutive indice, where each indice is a pointer to
     * the corrosponding index of the vertex in question.
     *
     * @return The array of all indices within this vertex data, used to construct
     *     triangles.
     */
    public short[] getTriangles()
    {
        return triangles;
    }

    /**
     * Gets the number of triangles within the given data.
     *
     * @return The number of triangles.
     */
    public int getTriangleCount()
    {
        return triangles.length / 3;
    }

    /**
     * Gets the number of vertices within the given data.
     *
     * @return The number of vertices.
     */
    public int getVertexCount()
    {
        return data.length / getVertexSize();
    }

    public ShaderAttributes getAttributeSizes()
    {
        return attributes;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(data);
        out.writeObject(triangles);
        out.writeObject(attributes);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        data = (float[]) in.readObject();
        triangles = (short[]) in.readObject();
        attributes = (ShaderAttributes) in.readObject();
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(data) ^ Arrays.hashCode(triangles);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof VertexData))
            return false;

        VertexData o = (VertexData) obj;
        return Arrays.equals(data, o.data) && Arrays.equals(triangles, o.triangles) && attributes.equals(o.attributes);
    }
}
