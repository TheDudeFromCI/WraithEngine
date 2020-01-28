package net.whg.we.window;

import net.whg.we.util.IDisposable;

/**
 * The screen object is an object which can be used to determine various
 * information about the state of the game screen, as well as modidy the screen
 * in certain ways. Each screen is bound to a single window.
 */
public class Screen implements IDisposable
{
    private static final String SCREEN_DISPOSED = "Screen already disposed!";

    /**
     * A private listener class which recieved events from the window to store
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

    private final IWindowListener listener = new ScreenListener();
    private IWindow window;
    private int width;
    private int height;
    private boolean disposed;

    /**
     * Creates a new screen object and binds to the given window.
     * 
     * @param window
     *     - The window to bind to.
     */
    public Screen(IWindow window)
    {
        this.window = window;
        window.addWindowListener(listener);

        listener.onWindowUpdated(window);
    }

    /**
     * Gets the current width of the window.
     * 
     * @return The width.
     */
    public int getWidth()
    {
        if (isDisposed())
            throw new IllegalStateException(SCREEN_DISPOSED);

        return width;
    }

    /**
     * Gets the current height of the window.
     * 
     * @return The height.
     */
    public int getHeight()
    {
        if (isDisposed())
            throw new IllegalStateException(SCREEN_DISPOSED);

        return height;
    }

    /**
     * Gets the current aspect of the window.
     * 
     * @return The aspect ratio.
     */
    public float getAspect()
    {
        if (isDisposed())
            throw new IllegalStateException(SCREEN_DISPOSED);

        return (float) width / height;
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        window.removeWindowListener(listener);
        window = null;
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }
}
