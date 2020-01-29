package net.whg.we.resource.assimp;

/**
 * Represents a mesh which loaded from Assimp.
 */
public interface IAssimpMesh
{
    /**
     * Counts the number of bones in this mesh.
     * 
     * @return The number of bones.
     */
    int countBones();

    /**
     * Counts the number of vertices in this mesh.
     * 
     * @return The number of vertices.
     */
    int countVertices();

    /**
     * Counts the number of triangles in this mesh.
     * 
     * @return The number of triangles.
     */
    int countTriangles();

    /**
     * Counts the number of UV layers in this mesh.
     * 
     * @return The number of UV layers.
     */
    int countUVLayers();

    /**
     * Gets the vertex position at the specified index, and places it into the data
     * array at the given position. A vertex position uses exactly three floats.
     * These are written to the array in order.
     * 
     * @param data
     *     - The data array to write the vertex position to.
     * @param pos
     *     - The position within the array to write the vertex position to.
     * @param index
     *     - The index of the vertex to get.
     */
    void getVertexPosition(float[] data, int pos, int index);

    /**
     * Gets the vertex normal at the specified index, and places it into the data
     * array at the given position. A vertex normal uses exactly three floats. These
     * are written to the array in order.
     * 
     * @param data
     *     - The data array to write the vertex normal to.
     * @param pos
     *     - The position within the array to write the vertex normal to.
     * @param index
     *     - The index of the vertex to get.
     */
    void getVertexNormal(float[] data, int pos, int index);

    /**
     * Gets the vertex tangent at the specified index, and places it into the data
     * array at the given position. A vertex tangent uses exactly three floats.
     * These are written to the array in order.
     * 
     * @param data
     *     - The data array to write the vertex tangent to.
     * @param pos
     *     - The position within the array to write the vertex tangent to.
     * @param index
     *     - The index of the vertex to get.
     */
    void getVertexTangent(float[] data, int pos, int index);

    /**
     * Gets the vertex bitangent at the specified index, and places it into the data
     * array at the given position. A vertex bitangent uses exactly three floats.
     * These are written to the array in order.
     * 
     * @param data
     *     - The data array to write the vertex bitangent to.
     * @param pos
     *     - The position within the array to write the vertex bitangent to.
     * @param index
     *     - The index of the vertex to get.
     */
    void getVertexBitangent(float[] data, int pos, int index);

    /**
     * Gets the vertex uv at the specified index and layer, and places it into the
     * data array at the given position. A vertex uv uses exactly two floats. These
     * are written to the array in order.
     * 
     * @param data
     *     - The data array to write the vertex bitangent to.
     * @param pos
     *     - The position within the array to write the vertex uv to.
     * @param index
     *     - The index of the vertex to get.
     * @param uvLayer
     *     - The layer of the vertex uv to get.
     */
    void getVertexUV(float[] data, int pos, int index, int uvLayer);

    /**
     * Gets the triangle at the specified index, and places it into the data array
     * at the given position. A triangle uses exactly three shorts, representing
     * pointers to vertex indices. These are written to the array in order.
     * 
     * @param data
     *     - The data array to write the triangle to.
     * @param pos
     *     - The position within the array to write the triangle to.
     * @param index
     *     - The index of the triangle to get.
     */
    void getTriangle(short[] data, int pos, int index);
}
