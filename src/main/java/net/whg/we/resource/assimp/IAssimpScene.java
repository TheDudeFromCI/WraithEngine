package net.whg.we.resource.assimp;

import net.whg.we.util.IDisposable;

/**
 * An assimp scene is a collection of resources which are loaded together in a
 * single file.
 */
public interface IAssimpScene extends IDisposable
{
    /**
     * Counts the number of meshes in this scene.
     * 
     * @return The number of meshes.
     */
    int countMeshes();

    /**
     * Gets the mesh at the specified index. This method creates a new mesh object
     * with each call.
     * 
     * @param index
     *     - The index of the mesh.
     * @return A new instance of the mesh.
     * @throws ArrayIndexOutOfBoundsException
     *     If the index is less than 0, or greater than or equal to the mesh count.
     */
    IAssimpMesh getMesh(int index);
}
