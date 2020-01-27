package net.whg.we.main;

import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowAdapter;
import net.whg.we.window.WindowSettings;

/**
 * The screen object is an object which can be used to determine various
 * information about the state of the game screen, as well as modidy the screen
 * in certain ways. Each screen is bound to a single window.
 */
public class Screen
{
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
        window.addWindowListener(new ScreenListener());
    }

    /**
     * Gets the current width of the window.
     * 
     * @return The width.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Gets the current height of the window.
     * 
     * @return The height.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Gets the current aspect of the window.
     * 
     * @return The aspect ratio.
     */
    public float getAspect()
    {
        return (float) width / height;
    }
}
