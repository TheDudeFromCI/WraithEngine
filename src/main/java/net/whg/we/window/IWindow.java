package net.whg.we.window;

import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.util.IDisposable;

/**
 * The window class is in charge of managing a window which is displayed to the
 * screen. This can be modified and closed at any time. Windows have their own
 * rendering engine attached which can be used to render 2D or 3D graphics with.
 * When disposing this window, the rendering engine is automatically disposed as
 * well. The window is also in charge of receiving input events from the user.
 */
public interface IWindow extends IDisposable
{
    /**
     * Sets the properties of this window. This method calls for the window to be
     * rebuilt or modified to match the given window settings.
     * 
     * @param settings
     *     - The new settings the window should have.
     * @throws UnsupportedOperationException
     *     If the window settings contains a change which is not supported by the
     *     window handler.
     */
    void setProperties(WindowSettings settings);

    /**
     * Gets the current properties of this window.
     * 
     * @return The properties of this window.
     */
    WindowSettings getProperties();

    /**
     * Gets the rendering engine that is used by this window.
     * 
     * @return The rendering engine.
     */
    IRenderingEngine getRenderingEngine();

    /**
     * Adds a window listener to this window, to receive events. This method does
     * nothing if the listener is null, or is already attached to this window.
     * 
     * @param listener
     *     - The listener to add.
     */
    void addWindowListener(IWindowListener listener);

    /**
     * Removes a window listener from this window. This method does nothing if the
     * listener is null, or is not attached to this window.
     * 
     * @param listener
     *     - The listener to remove.
     */
    void removeWindowListener(IWindowListener listener);

    /**
     * This function is used to poll events currently queued by the window. Calling
     * this method will trigger all events and pass them to all attached window
     * listeners. This method must be called on the main thread at the end of each
     * frame.
     */
    void pollEvents();

    /**
     * Gets the ID of the window.
     * 
     * @return The active window ID, or -1 if this window is disposed.
     */
    long getWindowId();
}
