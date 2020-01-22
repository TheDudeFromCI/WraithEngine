package net.whg.we.rendering;

import net.whg.we.util.IDisposable;

/**
 * The rendering engine object is used to draw to the window, and is the heart
 * of all visuals within the engine.
 */
public interface IRenderingEngine extends IDisposable
{
    /**
     * Initializes this rendering engine.
     */
    void init();

    /**
     * Gets the handler in charge of clearing the screen.
     * 
     * @return The handler.
     */
    IScreenClearHandler getScreenClearHandler();

    /**
     * This function is used to create a new empty mesh object.
     * 
     * @return A new empty mesh.
     */
    IMesh createMesh();

    /**
     * Creates a new shader object which can be compiled to render materials to the
     * screen.
     * 
     * @return A new shader object.
     */
    IShader createShader();

    /**
     * Sets the depth testing state to use when rendering objects.
     * 
     * @param depthTesting
     *     - Whether or not depth testing should be used.
     */
    void setDepthTesting(boolean depthTesting);

    /**
     * Sets the culling mode to use when rendering triangles.
     * 
     * @param cullingMode
     *     - The new culling mode.
     */
    void setCullingMode(CullingMode cullingMode);

    /**
     * Creates a new texture object which can have pixel data uploaded to it.
     * 
     * @return A new texture object.
     */
    ITexture createTexture();
}
