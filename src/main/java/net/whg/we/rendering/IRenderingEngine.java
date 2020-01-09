package net.whg.we.rendering;

import net.whg.we.util.IDisposable;

/**
 * The rendering engine object is used to draw to the window, and is the heart
 * of all visuals within the engine.
 */
public interface IRenderingEngine extends IDisposable
{
    /**
     * Gets the handler in charge of clearing the screen.
     * 
     * @return The handler.
     */
    IScreenClearHandler getScreenClearHandler();
}
