package net.whg.we.window.glfw;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.whg.we.external.LoggerAPI;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.util.OutputStreamWrapper;
import net.whg.we.util.OutputStreamWrapper.LogLevel;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowAdapter;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

/**
 * This class represents the GLFW implementation of the window handler object.
 * It uses an OpenGL 3.3 rendering engine, and supports a single window at a
 * time.
 */
public final class GlfwWindow implements IWindow
{
    private static GlfwWindow window;

    /**
     * Sets the current active window.
     * 
     * @param window
     *     - The new active window. If an active window is already open, it is
     *     disposed.
     */
    private synchronized void setWindow(GlfwWindow window)
    {
        if (GlfwWindow.window != null && !GlfwWindow.window.isDisposed())
            GlfwWindow.window.dispose();

        GlfwWindow.window = window;
    }

    /**
     * Gets the active GLFW window.
     * 
     * @return The active GLFW window, or null if no window is open.
     */
    public static GlfwWindow getActiveWindow()
    {
        return window;
    }

    private final List<IWindowListener> listeners = new CopyOnWriteArrayList<>();
    private final WindowSettings settings = new WindowSettings();
    private final IRenderingEngine renderingEngine;
    private final IGlfw glfw;
    private boolean disposed;
    private long windowId;

    /**
     * Creates a new GLFW window.
     * 
     * @param glfw
     *     - The GLFW implementation to build this window on.
     * @param renderingEngine
     *     - The rendering engine to build this window with.
     * @param settings
     *     - The settings to initialize the window with.
     * @throws IllegalStateException
     *     If another GLFW already exists and has not yet been disposed.
     */
    public GlfwWindow(IGlfw glfw, IRenderingEngine renderingEngine, WindowSettings settings)
    {
        if (doesWindowExist())
            throw new IllegalStateException("A GLFW window has already been created!");

        this.glfw = glfw;
        this.renderingEngine = renderingEngine;

        glfw.init();
        glfw.setErrorCallback(new PrintStream(new OutputStreamWrapper(LogLevel.ERROR, new LoggerAPI())));

        buildWindow(settings);
        renderingEngine.init();

        addWindowListener(new IWindowAdapter()
        {
            @Override
            public void onWindowResized(IWindow window, int width, int height)
            {
                settings.setWidth(width);
                settings.setHeight(height);
            }
        });
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        try
        {
            destroyWindow();
            renderingEngine.dispose();
            glfw.terminate();
        }
        finally
        {
            disposed = true;
            setWindow(null);

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

        for (IWindowListener listener : listeners)
            listener.onWindowUpdated(this);
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
        if (getActiveWindow() != null)
            return true;

        setWindow(this);
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
        if (this.settings.isFullscreen() != settings.isFullscreen())
            return false;

        return this.settings.getSamples() == settings.getSamples();
    }

    /**
     * Destroys the window object and disposes GLFW resources. Window may be rebuilt
     * again afterwards. Must be called only when a window currently exists.
     */
    private void destroyWindow()
    {
        glfw.destroyWindow(windowId);
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
        this.settings.set(settings);

        glfw.setWindowHints(settings.isResizable(), 3, 3, settings.getSamples());
        windowId = glfw.createWindow(settings.getWidth(), settings.getHeight(), settings.getTitle(),
                settings.isFullscreen());

        int[] screenSize = glfw.getScreenSize();
        glfw.setWindowPosition(windowId, (screenSize[0] - settings.getWidth()) / 2,
                (screenSize[1] - settings.getHeight()) / 2);

        createCallbacks();

        glfw.setContextThread(windowId);
        glfw.setVSync(settings.isVsync() ? 1 : 0);
        glfw.showWindow(windowId);
    }

    /**
     * This method is used to attach all callbacks to the window, and send
     * corresponding events to the window listeners.
     */
    private void createCallbacks()
    {
        glfw.addMousePosListener(this, listeners);
        glfw.addMouseButtonListener(this, listeners);
        glfw.addMouseWheelListener(this, listeners);

        glfw.addKeyListener(this, listeners);

        glfw.addWindowResizeListener(this, listeners);
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
        if (!this.settings.getTitle()
                          .equals(settings.getTitle()))
        {
            this.settings.setTitle(settings.getTitle());
            glfw.setWindowTitle(windowId, settings.getTitle());
        }

        if (this.settings.getWidth() != settings.getWidth() || this.settings.getHeight() != settings.getHeight())
        {
            this.settings.setWidth(settings.getWidth());
            this.settings.setHeight(settings.getHeight());
            glfw.setWindowSize(windowId, settings.getWidth(), settings.getHeight());
        }

        if (this.settings.isVsync() != settings.isVsync())
        {
            this.settings.setVsync(settings.isVsync());
            glfw.setVSync(settings.isVsync() ? 1 : 0);
        }

        if (this.settings.isResizable() != settings.isResizable())
        {
            this.settings.setResizable(settings.isResizable());
            glfw.setWindowResizable(windowId, settings.isResizable());
        }
    }

    @Override
    public void addWindowListener(IWindowListener listener)
    {
        if (listener == null)
            return;

        if (!listeners.contains(listener))
            listeners.add(listener);
    }

    @Override
    public void removeWindowListener(IWindowListener listener)
    {
        if (listener == null)
            return;

        listeners.remove(listener);
    }

    @Override
    public void pollEvents()
    {
        glfw.swapBuffers(windowId);
        glfw.pollEvents();

        if (glfw.shouldWindowClose(windowId))
        {
            for (IWindowListener listener : listeners)
                listener.onWindowRequestClose(this);
        }
    }

    @Override
    public long getWindowId()
    {
        if (isDisposed())
            return -1;

        return windowId;
    }
}
