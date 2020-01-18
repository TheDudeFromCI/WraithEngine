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
}
