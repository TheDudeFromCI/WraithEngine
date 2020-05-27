package net.whg.we.window;

/**
 * The screen object is an object which can be used to determine various
 * information about the state of the game screen, as well as modify the screen
 * in certain ways. Each screen is bound to a single window.
 */
public class Screen extends WindowReceiver
{
    /**
     * A private listener class which received events from the window to store
     * within the screen object.
     */
    private class ScreenListener extends IWindowAdapter
    {
        @Override
        public void onWindowResized(IWindow window, int width, int height)
        {
            Screen.this.width = width;
            Screen.this.height = height;
        }

        @Override
        public void onWindowUpdated(IWindow window)
        {
            WindowSettings settings = window.getProperties();
            Screen.this.width = settings.getWidth();
            Screen.this.height = settings.getHeight();
        }
    }

    private int width;
    private int height;

    /**
     * Creates a new screen object and binds to the given window.
     * 
     * @param window
     *     - The window to bind to.
     */
    public Screen(IWindow window)
    {
        var listener = new ScreenListener();
        listenTo(window, listener);

        listener.onWindowUpdated(window);
    }

    /**
     * Gets the current width of the window.
     * 
     * @return The width.
     */
    public int getWidth()
    {
        checkDisposed();
        return width;
    }

    /**
     * Gets the current height of the window.
     * 
     * @return The height.
     */
    public int getHeight()
    {
        checkDisposed();
        return height;
    }

    /**
     * Gets the current aspect of the window.
     * 
     * @return The aspect ratio.
     */
    public float getAspect()
    {
        checkDisposed();
        return (float) width / height;
    }

    /**
     * Checks if this screen is currently disposed or not.
     * 
     * @throws IllegalStateException
     *     If this screen is disposed.
     */
    private void checkDisposed()
    {
        if (isDisposed())
            throw new IllegalStateException("Screen already disposed!");
    }
}
