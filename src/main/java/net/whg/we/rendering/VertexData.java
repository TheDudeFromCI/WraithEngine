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
    private static final int FILE_VERSION = 1;

    private float[] _data;
    private short[] _triangles;
    private ShaderAttributes _attributes;

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
        _data = data;
        _triangles = triangles;
        _attributes = attributes;
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
        return _data;
    }

    /**
     * Gets the size of each vertex based on the number of floats required to
     * specify a single vertex, as defined by the shader attributes object.
     *
     * @return The size of each vertex.
     */
    public int getVertexSize()
    {
        return _attributes.getVertexSize();
    }

    /**
     * Gets the size of each vertex in bytes.
     *
     * @return The size of each vertex in bytes.
     * @see {@link #getVertexSize()}
     */
    public int getVertexByteSize()
    {
        return _attributes.getVertexByteSize();
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
        return _triangles;
    }

    /**
     * Gets the number of triangles within the given data.
     *
     * @return The number of triangles.
     */
    public int getTriangleCount()
    {
        return _triangles.length / 3;
    }

    /**
     * Gets the number of vertices within the given data.
     *
     * @return The number of vertices.
     */
    public int getVertexCount()
    {
        return _data.length / getVertexSize();
    }

    public ShaderAttributes getAttributeSizes()
    {
        return _attributes;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(FILE_VERSION);
        out.writeObject(_data);
        out.writeObject(_triangles);
        out.writeObject(_attributes);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        int fileVersion = in.readInt();

        switch (fileVersion)
        {
            case 1:
                _data = (float[]) in.readObject();
                _triangles = (short[]) in.readObject();
                _attributes = (ShaderAttributes) in.readObject();
                return;

            default:
                throw new IllegalStateException("Unknown file version: " + fileVersion + "!");
        }
    }

    @Override
    public int hashCode()
    {
        return _data.hashCode() ^ _triangles.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof VertexData))
            return false;

        VertexData o = (VertexData) obj;
        return Arrays.equals(_data, o._data) && Arrays.equals(_triangles, o._triangles)
                && _attributes.equals(o._attributes);
    }
}
