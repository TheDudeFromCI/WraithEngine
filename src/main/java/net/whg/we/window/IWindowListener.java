package net.whg.we.window;

/**
 * The window listener object receives events from the window handler object as
 * these events occur. It is important to note that these events may not occur
 * on the main thread, and must be synchronized manually.
 */
public interface IWindowListener
{
    /**
     * Called when the settings of a window have been updated in any way. This
     * includes actions such as requesting a title update, or toggling fullscreen.
     * This event is also called if the user forces an update to occur, such as by
     * resizing the window.
     * 
     * @param window
     *     - The window which was updated.
     */
    void onWindowUpdated(IWindow window);

    /**
     * Called when the window has been disposed. This is called after the disposing
     * process is finished.
     * 
     * @param window
     *     - The window which was destroyed.
     */
    void onWindowDestroyed(IWindow window);

    /**
     * Called when a request was made by the system to close the window.
     * 
     * @param window
     *     - The window in which this event occured.
     */
    void onWindowRequestClose(IWindow window);

    /**
     * Called when the user moves their mouse over the window.
     * 
     * @param window
     *     - The window in which this event occured.
     * @param newX
     *     - The new x position of the mouse relative to the window.
     * @param newX
     *     - The new y position of the mouse relative to the window.
     */
    void onMouseMove(IWindow window, float newX, float newY);

    /**
     * Called when the user presses a key while the window is focused.
     * 
     * @param window
     *     - The window in which this event occured.
     * @param keyCode
     *     - The key code for the key which was pressed.
     */
    void onKeyPressed(IWindow window, int keyCode);

    /**
     * Called when the user releases a key while the window is focused.
     * 
     * @param window
     *     - The window in which this event occured.
     * @param keyCode
     *     - The key code for the key which was released.
     */
    void onKeyReleased(IWindow window, int keyCode);

    /**
     * Called when the user presses a mouse button while the window is focused.
     * 
     * @param window
     *     - The window in which this event occured.
     * @param mouseButton
     *     - The button index on the mouse which was pressed.
     */
    void onMousePressed(IWindow window, int mouseButton);

    /**
     * Called when the user releases a mouse button while the window is focused.
     * 
     * @param window
     *     - The window in which this event occured.
     * @param mouseButton
     *     - The button index on the mouse which was released.
     */
    void onMouseReleased(IWindow window, int mouseButton);

    /**
     * Called when the user scrolls the verticle or horizontal mouse wheel while the
     * window is focused.
     * 
     * @param window
     *     - The window in which this event occured.
     * @param scrollX
     *     - How much the mouse wheel was scrolled in the x direction.
     * @param scrollY
     *     - How much the mouse wheel was scrolled in the y direction.
     */
    void onMouseWheel(IWindow window, float scrollX, float scrollY);
}
