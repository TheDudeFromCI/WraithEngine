package net.whg.we.window;

/**
 * A window settings object is a data container object for storing a set of
 * properties about a window which should be used to create a window or modify
 * an existing window. The window settings object contains a set of properties
 * which are common among windows handlers.
 */
public class WindowSettings
{
    private String title = "Untitled";
    private int width = 800;
    private int height = 600;
    private boolean fullscreen = false;
    private boolean vsync = false;
    private boolean resizable = false;
    private int samples = 1;

    /**
     * Creates a new window settings object with the default window properties
     * assigned.
     * <p>
     * <ul>
     * <li>Title: "Untitled"</li>
     * <li>Width: 800</li>
     * <li>Height: 600</li>
     * <li>Fullscreen: false</li>
     * <li>VSync: false</li>
     * <li>Resizable: false</li>
     * <li>Samples-Per-Pixel: 1</li>
     * </ul>
     */
    public WindowSettings()
    {}

    /**
     * Creates a new window settings container with the properties copied from
     * another object. Useful for cloning a window settings object.
     * 
     * @param toClone
     *     - The object to uuse the properties from.
     */
    public WindowSettings(WindowSettings toClone)
    {
        set(toClone);
    }

    /**
     * Gets the title property of this window settings container.
     * 
     * @return The title.
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title property of this window settings container.
     * <p>
     * The default value is "Untitled".
     * 
     * @param title
     *     - The new title.
     * @throws IllegalArgumentException
     *     If the title is null.
     */
    public void setTitle(String title)
    {
        if (title == null)
            throw new IllegalArgumentException("Title cannot be null!");

        this.title = title;
    }

    /**
     * Gets the width property of this window settings container.
     * 
     * @return The widith.
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Sets the width property of this window settings container.
     * <p>
     * The default value is 800.
     * 
     * @param width
     *     - The new width.
     * @throws IllegalArgumentException
     *     If the width is less than 1 pixel.
     */
    public void setWidth(int width)
    {
        if (width < 1)
            throw new IllegalArgumentException("Width must be at least 1 pixel!");

        this.width = width;
    }

    /**
     * Gets the height property of this window settings container.
     * 
     * @return The height.
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * Sets the height property of this window settings container.
     * <p>
     * The default value is 600.
     * 
     * @param height
     *     - The new height.
     * @throws IllegalArgumentException
     *     If the height is less than 1 pixel.
     */
    public void setHeight(int height)
    {
        if (height < 1)
            throw new IllegalArgumentException("Height must be at least 1 pixel!");

        this.height = height;
    }

    /**
     * Gets the fullscreen property of this window settings container.
     * 
     * @return Whether or not the window is fullscreen.
     */
    public boolean isFullscreen()
    {
        return fullscreen;
    }

    /**
     * Sets the fullscreen property of this window settings container.
     * <p>
     * The default value is false.
     * 
     * @param fullscreen
     *     - Whether or not the window is fullscreen.
     */
    public void setFullscreen(boolean fullscreen)
    {
        this.fullscreen = fullscreen;
    }

    /**
     * Gets the vSync property of this window settings container.
     * 
     * @return Whether or not the window has vSync enabled.
     */
    public boolean isVsync()
    {
        return vsync;
    }

    /**
     * Sets the vSync property of this window settings container.
     * <p>
     * The default value is false.
     * 
     * @param vsync
     *     - Whether or not the window has vSync enabled.
     */
    public void setVsync(boolean vsync)
    {
        this.vsync = vsync;
    }

    /**
     * Gets the samples-per-pixel property of this window settings container.
     * 
     * @return The number of samples to use per pixel when rendering.
     */
    public int getSamples()
    {
        return samples;
    }

    /**
     * Sets the samples-per-pixel property of this window settings container.
     * <p>
     * The default value is 1.
     * 
     * @param samples
     *     - The number of samples to use per pixel when rendering.
     * @throws IllegalArgumentException
     *     If the sample count is less than 1 or greater than 32.
     */
    public void setSamples(int samples)
    {
        if (samples < 1)
            throw new IllegalArgumentException("Sampling count must be at least 1!");

        if (samples >= 32)
            throw new IllegalArgumentException("Sampling count must be at most 32!");

        this.samples = samples;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + (fullscreen ? 1231 : 1237);
        result = prime * result + height;
        result = prime * result + samples;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + (vsync ? 1231 : 1237);
        result = prime * result + width;
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (getClass() != obj.getClass())
            return false;

        WindowSettings other = (WindowSettings) obj;
        if (fullscreen != other.fullscreen)
            return false;

        if (height != other.height)
            return false;

        if (samples != other.samples)
            return false;

        if (!title.equals(other.title))
            return false;

        if (vsync != other.vsync)
            return false;

        if (width != other.width)
            return false;

        return resizable == other.resizable;
    }

    /**
     * Gets the resizable property of this window settings container.
     * 
     * @return Whether or not the window is resizable.
     */
    public boolean isResizable()
    {
        return resizable;
    }

    /**
     * Sets the resizable property of this window settings container.
     * <p>
     * The default value is false.
     * 
     * @param resizable
     *     - Whether or not the window can be resized.
     */
    public void setResizable(boolean resizable)
    {
        this.resizable = resizable;
    }

    /**
     * Gets the current aspect ratio of this window.
     * 
     * @return The aspect ratio.
     */
    public float getAspectRation()
    {
        return (float) width / height;
    }

    /**
     * Sets the properties of this window settings container to match that of
     * another window settings container object.
     * 
     * @param settings
     *     - The object to copy the settings from.
     */
    public void set(WindowSettings settings)
    {
        title = settings.title;
        width = settings.width;
        height = settings.height;
        fullscreen = settings.fullscreen;
        vsync = settings.vsync;
        resizable = settings.resizable;
        samples = settings.samples;
    }
}
