package net.whg.we.main;

/**
 * The screen object is a public static interface which can be used to determine
 * various information about the state of the game screen, as well as modidy the
 * screen in certain ways.
 */
public final class Screen
{
    private static int width;
    private static int height;

    /**
     * Assigns the current window size. This should be called every time the window
     * is resized.
     * 
     * @param width
     *     - The width of the window.
     * @param height
     *     - The height of the window.
     */
    static void updateWindowSize(int width, int height)
    {
        Screen.width = width;
        Screen.height = height;
    }

    /**
     * Gets the current width of the window.
     * 
     * @return The width.
     */
    public static int getWidth()
    {
        return width;
    }

    /**
     * Gets the current height of the window.
     * 
     * @return The height.
     */
    public static int getHeight()
    {
        return height;
    }

    /**
     * Gets the current aspect of the window.
     * 
     * @return The aspect ratio.
     */
    public static float getAspect()
    {
        return (float) width / height;
    }

    private Screen()
    {}
}
