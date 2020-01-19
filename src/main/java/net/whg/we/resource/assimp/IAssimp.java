package net.whg.we.resource.assimp;

import java.io.File;

/**
 * This interface is used as a wrapper for communicating with Assimp. It is used
 * to add an extra layer of abstraction between WraithEngine and Assimp, to
 * allow the code to be more testable.
 */
public interface IAssimp
{
    /**
     * Loads the scene within the given file.
     * 
     * @param file
     *     - The file to load.
     * @return The loaded scene, or null if the scene could not be loaded.
     */
    IAssimpScene loadScene(File file);
}
