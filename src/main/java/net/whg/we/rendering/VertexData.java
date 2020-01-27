package net.whg.we.rendering;

import java.util.Arrays;

/**
 * Represents a structure of triangles and vertices which are used to contruct a
 * mesh. Vertices may have shader attributes applied to them as well.
 *
 * @author TheDudeFromCI
 */
public class VertexData
{
    private float[] data;
    private short[] triangles;
    private ShaderAttributes attributes;

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
     * @throws IllegalArgumentException
     *     If the provided data, triangles, or shader attributes does not represent
     *     a valid data structure. (I.e too few verts, triangles out of bounds, no
     *     shader attributes, etc.)
     */
    public VertexData(float[] data, short[] triangles, ShaderAttributes attributes)
    {
        this.data = data;
        this.triangles = triangles;
        this.attributes = attributes;

        validate();
    }

    private void validate()
    {
        if (triangles.length % 3 != 0)
            throw new IllegalArgumentException("Triangle array length must be a multiple of 3!");

        int vertSize = attributes.getVertexSize();

        if (vertSize == 0)
            throw new IllegalArgumentException("Shader attributes must contain at least one attribute!");

        if (data.length % vertSize != 0)
            throw new IllegalArgumentException(
                    "Data array length does not match vertex size and count! Expected: multiple of" + vertSize
                            + ", Actual: " + data.length);

        int vertCount = triangles.length;

        for (short s : triangles)
            if (s < 0 || s >= vertCount)
                throw new IllegalArgumentException(
                        "Triangles point to non-existant vertices! Index: " + s + ", Vertex Count: " + vertCount);
    }

    /**
     * Returns the array of floats used to specify vertices within this vertex data.
     * The array contains all vertices formated as specified by the shader
     * attributes object. This should not be modified.
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
     * @see VertexData#getVertexSize()
     */
    public int getVertexByteSize()
    {
        return attributes.getVertexByteSize();
    }

    /**
     * Gets an array of all triangles and the indices of the vertices they point to.
     * A single triangle is 3 consecutive indice, where each indice is a pointer to
     * the corrosponding index of the vertex in question. This should not be
     * modified.
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

    /**
     * Gets the shader attributes object. This should not be modified.
     * 
     * @return The shader attributes object.
     */
    public ShaderAttributes getAttributeSizes()
    {
        return attributes;
    }

    @Override
    public int hashCode()
    {
        final int prime = 8597;

        int value = 1;
        value = value * prime + Arrays.hashCode(data);
        value = value * prime + Arrays.hashCode(triangles);
        value = value * prime + attributes.hashCode();
        return value;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        VertexData o = (VertexData) obj;
        return Arrays.equals(data, o.data) && Arrays.equals(triangles, o.triangles) && attributes.equals(o.attributes);
    }
}
