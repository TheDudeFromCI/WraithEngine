package net.whg.we.window.glfw;

import java.io.PrintStream;
import java.util.List;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;

/**
 * This interface is a wrapper for interacting with the GLFW library. This is
 * used to add a layer of abstraction between the library and the
 * implementation.
 */
public interface IGlfw
{
    /**
     * Destroys this GLFW instances and all stored information.
     */
    void terminate();

    /**
     * Destroys the given window.
     * 
     * @param windowId
     *     - The ID of the window.
     */
    void destroyWindow(long windowId);

    /**
     * Initializes this GLFW instance.
     */
    void init();

    /**
     * Sets up the error callback for this GLFW instance.
     * 
     * @param printStream
     *     - The error stream to write to.
     */
    void setErrorCallback(PrintStream printStream);

    /**
     * Sets the given window hints for displaying the next window.
     * 
     * @param resizable
     *     - Whether or not the window is resizable.
     * @param openGlMajor
     *     - The OpenGL major version.
     * @param openGlMinor
     *     - The OpenGL minor version.
     * @param samples
     *     - The number of samples to use per pixel when rendering.
     */
    void setWindowHints(boolean resizable, int openGlMajor, int openGlMinor, int samples);

    /**
     * Creates an invisible window object.
     * 
     * @param width
     *     - The width of the window.
     * @param height
     *     - The height of the window.
     * @param title
     *     - The title of the window.
     * @param fullscreen
     *     - Whether or not the window is fullscreen.
     * @return The ID of the newly created window.
     */
    long createWindow(int width, int height, String title, boolean fullscreen);

    /**
     * Gets the size of the primary monitor.
     * 
     * @return The screen size as an int array with a length of 2, where int[0] is
     *     the width, and int[1] is the height.
     */
    int[] getScreenSize();

    /**
     * Sets the position of the window to the given x and y position, on the primary
     * monitor.
     * 
     * @param windowId
     *     - The ID of the window to move.
     * @param x
     *     - The x position of the window.
     * @param y
     *     - The y position of the window.
     */
    void setWindowPosition(long windowId, int x, int y);

    /**
     * Assigns the current thread as the context thread for GLFW and Rendering
     * calls.
     * 
     * @param windowId
     *     - The window Id to set the context for.
     */
    void setContextThread(long windowId);

    /**
     * Sets the VSync swap interval for rendering contexts.
     * 
     * @param swapRate
     *     - The swap rate for vSync, or 0 to disable.
     */
    void setVSync(int swapRate);

    /**
     * Calls the given window to be displayed.
     * 
     * @param windowId
     *     - The window to display.
     */
    void showWindow(long windowId);

    /**
     * Sets the width of the given window.
     * 
     * @param windowId
     *     - The ID of the window to edit.
     * @param width
     *     - The new window width.
     * @param height
     *     - The new window height.
     */
    void setWindowSize(long windowId, int width, int height);

    /**
     * Sets a new title for the given window.
     * 
     * @param windowId
     *     - The ID of the window to edit.
     * @param title
     *     - The new title.
     */
    void setWindowTitle(long windowId, String title);

    /**
     * Sets whether or not the window should be resizable.
     * 
     * @param windowId
     *     - The ID of the window to edit.
     * @param resizable
     *     - Whether or not the window is resizable.
     */
    void setWindowResizable(long windowId, boolean resizable);

    /**
     * Swaps the frame buffers for the given window.
     * 
     * @param windowId
     *     - The ID of the window to display changes for.
     */
    void swapBuffers(long windowId);

    /**
     * Polls all pending GLFW events.
     */
    void pollEvents();

    /**
     * Checks whether or not the window is requesting to close.
     * 
     * @param windowId
     *     - The ID of the window to check.
     * @return True if the window is requesting to closed. False otherwise.
     */
    boolean shouldWindowClose(long windowId);

    /**
     * Attaches a callback for mouse move events, and passes them to the given list
     * of listeners.
     * 
     * @param window
     *     - The ID of the window to attach the callback to.
     * @param listeners
     *     - The list of listeners to forward the event to.
     */
    void addMousePosListener(IWindow window, List<IWindowListener> listeners);

    /**
     * Attaches a callback for mouse button events, and passes them to the given
     * list of listeners.
     * 
     * @param window
     *     - The ID of the window to attach the callback to.
     * @param listeners
     *     - The list of listeners to forward the event to.
     */
    void addMouseButtonListener(IWindow window, List<IWindowListener> listeners);

    /**
     * Attaches a callback for mouse wheel events, and passes them to the given list
     * of listeners.
     * 
     * @param window
     *     - The ID of the window to attach the callback to.
     * @param listeners
     *     - The list of listeners to forward the event to.
     */
    void addMouseWheelListener(IWindow window, List<IWindowListener> listeners);

    /**
     * Attaches a callback for key press and release events, and passes them to the
     * given list of listeners.
     * 
     * @param window
     *     - The ID of the window to attach the callback to.
     * @param listeners
     *     - The list of listeners to forward the event to.
     */
    void addKeyListener(IWindow window, List<IWindowListener> listeners);

    /**
     * Attaches a callback for window resize events, and passes them to the given
     * list of listeners.
     * 
     * @param window
     *     - The ID of the window to attach the callback to.
     * @param listeners
     *     - The list of listeners to forward the event to.
     */
    void addWindowResizeListener(IWindow window, List<IWindowListener> listeners);
}
