package net.whg.we.rendering;

import net.whg.we.util.IDisposable;

/**
 * A mesh is a collection of vertices and indices which make up a triangle mesh
 * on the graphics card. A mesh is used to trigger a draw-call to the graphics
 * card to render the given mesh with the currently bound material.
 */
public interface IMesh extends IDisposable
{
    /**
     * This is used to call for the mesh to be rendered. If this mesh has not yet
     * been bound with any vertex data, this function does nothing.
     */
    void render();

    /**
     * Populates or updates this mesh to bind to the given vertex data. If this mesh
     * has not yet been created, this function creates the mesh with the given data.
     * If the mesh already exists, then this function updates the mesh to use the
     * new vertex data instead.
     * 
     * @param vertexData
     *     - The vertex data to update this mesh with.
     */
    void update(VertexData vertexData);
}
