package net.whg.we.window.glfw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.whg.we.window.IRenderingEngine;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

/**
 * This class represents the GLFW implementation of the window handler object.
 * It uses an OpenGL 3.3 rendering engine, and supports a single window at a
 * time.
 */
public final class GlfwWindow implements IWindow
{
    private final List<IWindowListener> listeners = Collections.synchronizedList(new ArrayList<>());
    private boolean disposed;
    private WindowSettings settings;
    private IRenderingEngine renderingEngine;

    /**
     * Creates a new GLFW window.
     * 
     * @param settings
     *     - The settings to initialize the window with.
     * @throws IllegalStateException
     *     If another GLFW already exists and has not yet been disposed.
     */
    public GlfwWindow(WindowSettings settings)
    {
        if (doesWindowExist())
            throw new IllegalStateException("A GLFW window has already been created!");

        buildWindow(settings);
        initRenderingEngine();
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        destroyWindow();
        destroyRenderingEngine();

        synchronized (listeners)
        {
            for (IWindowListener listener : listeners)
                listener.onWindowDestroyed(this);
        }
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    @Override
    public void setProperties(WindowSettings settings)
    {
        if (isTweakableChange(settings))
            tweakWindow(settings);
        else
        {
            destroyWindow();
            buildWindow(settings);
        }

        synchronized (listeners)
        {
            for (IWindowListener listener : listeners)
                listener.onWindowUpdated(this);
        }
    }

    @Override
    public WindowSettings getProperties()
    {
        return settings;
    }

    @Override
    public IRenderingEngine getRenderingEngine()
    {
        return renderingEngine;
    }

    /**
     * Checks if another GLFW window already exists or not.
     * 
     * @return True if another GLFW window exists and has not yet been disposed.
     *     False otherwise.
     */
    private boolean doesWindowExist()
    {
        // TODO
        return false;
    }

    /**
     * Checks if the new settings requested allow for simple tweaks to be made to an
     * existing window, or if a full new window object needs to be created instead.
     * 
     * @return True if the existing window can be modified cleanly. False if a new
     *     window must be created to apply changes.
     */
    private boolean isTweakableChange(WindowSettings settings)
    {
        // TODO
        return false;
    }

    /**
     * Destroys the window object and disposes GLFW resources. Window may be rebuilt
     * again afterwards. Must be called only when a window currently exists.
     */
    private void destroyWindow()
    {
        // TODO
    }

    /**
     * Disposes and cleans up the renhdering engine being used by this window.
     * Called once after the window has been destroyed for the last time.
     */
    private void destroyRenderingEngine()
    {
        // TODO
    }

    /**
     * Initializes the rendering engine to be used by this window. Called once after
     * the window has been built for the first time.
     */
    private void initRenderingEngine()
    {
        // TODO
    }

    /**
     * Builds a new window with the given settings. May be culled multiple times, as
     * long as the previous window is destroyed first.
     * 
     * @param settings
     *     - The settings to build the window with.
     */
    private void buildWindow(WindowSettings settings)
    {
        // TODO
    }

    /**
     * Applies minor changes to the existing window as specified by the window
     * settings object. All changes which require a full window rebuild are ignored.
     * 
     * @param settings
     *     - The new settings to apply to the window, where possible.
     */
    private void tweakWindow(WindowSettings settings)
    {
        // TODO
    }

    @Override
    public void addWindowListener(IWindowListener listener)
    {
        if (listener == null)
            return;

        synchronized (listeners)
        {
            if (!listeners.contains(listener))
                listeners.add(listener);
        }
    }

    @Override
    public void removeWindowListener(IWindowListener listener)
    {
        if (listener == null)
            return;

        listeners.remove(listener);
    }
}
