package net.whg.we.window.glfw;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.rendering.opengl.OpenGLRenderingEngine;
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
    private static boolean windowState = false;

    /**
     * Sets the current state of this window.
     * 
     * @param open
     *     - Wether or not this window is currently open.
     */
    private static void setWindowState(boolean open)
    {
        windowState = open;
    }

    /**
     * Gets the current state of this window.
     * 
     * @return True if this window is open. False otherwise.
     */
    private static boolean getWindowState()
    {
        return windowState;
    }

    private final List<IWindowListener> listeners = new CopyOnWriteArrayList<>();
    private final WindowSettings settings = new WindowSettings();
    private boolean disposed;
    private IRenderingEngine renderingEngine;
    private long windowId;

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

        destroyWindow();
        destroyRenderingEngine();

        glfwTerminate();
        glfwSetErrorCallback(null).free();

        disposed = true;
        setWindowState(false);

        for (IWindowListener listener : listeners)
            listener.onWindowDestroyed(this);
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
        if (getWindowState())
            return true;

        setWindowState(true);
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

        return this.settings.getSamples() != settings.getSamples();
    }

    /**
     * Destroys the window object and disposes GLFW resources. Window may be rebuilt
     * again afterwards. Must be called only when a window currently exists.
     */
    private void destroyWindow()
    {
        glfwDestroyWindow(windowId);
    }

    /**
     * Disposes and cleans up the renhdering engine being used by this window.
     * Called once after the window has been destroyed for the last time.
     */
    private void destroyRenderingEngine()
    {
        renderingEngine.dispose();
    }

    /**
     * Initializes the rendering engine to be used by this window. Called once after
     * the window has been built for the first time.
     */
    private void initRenderingEngine()
    {
        renderingEngine = new OpenGLRenderingEngine();
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

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFWErrorCallback.createPrint(System.err)
                         .set();

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, settings.isResizable() ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_SAMPLES, settings.getSamples());
        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);

        windowId = glfwCreateWindow(settings.getWidth(), settings.getHeight(), settings.getTitle(),
                settings.isFullscreen() ? glfwGetPrimaryMonitor() : NULL, NULL);
        if (windowId == NULL)
            throw new GLFWException("Failed to create GLFW window!");

        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(windowId, (vidmode.width() - settings.getWidth()) / 2,
                (vidmode.height() - settings.getHeight()) / 2);

        createCallbacks();
        glfwMakeContextCurrent(windowId);
        glfwSwapInterval(settings.isVsync() ? 1 : 0);
        glfwShowWindow(windowId);
    }

    /**
     * This method is used to attach all callbacks to the window, and send
     * corresponding events to the window listeners.
     */
    private void createCallbacks()
    {
        createMouseCallbacks();
        createKeyboardCallbacks();
        createWindowCallbacks();
    }

    /**
     * This method is used to attach all mouse-based events to the window object.
     */
    private void createMouseCallbacks()
    {
        glfwSetCursorPosCallback(windowId, (window, xpos, ypos) ->
        {
            for (IWindowListener listener : listeners)
                listener.onMouseMove(this, (float) xpos, (float) ypos);
        });

        glfwSetMouseButtonCallback(windowId, (window, button, action, mods) ->
        {
            if (action == GLFW_PRESS)
            {
                for (IWindowListener listener : listeners)
                    listener.onMousePressed(this, button);
            }
            else if (action == GLFW_RELEASE)
            {
                for (IWindowListener listener : listeners)
                    listener.onMouseReleased(this, button);
            }
        });

        glfwSetScrollCallback(windowId, (window, xoff, yoff) ->
        {
            for (IWindowListener listener : listeners)
                listener.onMouseWheel(this, (float) xoff, (float) yoff);
        });
    }

    /**
     * This method is used to attach all keyboard-based events to the window object.
     */
    private void createKeyboardCallbacks()
    {
        glfwSetKeyCallback(windowId, (window, key, scancode, action, mods) ->
        {
            if (action == GLFW_PRESS)
            {
                for (IWindowListener listener : listeners)
                    listener.onKeyPressed(this, key);
            }
            else if (action == GLFW_RELEASE)
            {
                for (IWindowListener listener : listeners)
                    listener.onKeyReleased(this, key);
            }
        });
    }

    /**
     * This method is used to attach all window-based events to the window object.
     */
    private void createWindowCallbacks()
    {
        glfwSetWindowSizeCallback(windowId, (window, width, height) ->
        {
            settings.setWidth(width);
            settings.setHeight(height);

            for (IWindowListener listener : listeners)
                listener.onWindowUpdated(this);
        });
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
            glfwSetWindowTitle(windowId, settings.getTitle());
        }

        if (this.settings.getWidth() != settings.getWidth() || this.settings.getHeight() != settings.getHeight())
        {
            this.settings.setWidth(settings.getWidth());
            this.settings.setHeight(settings.getHeight());
            glfwSetWindowSize(windowId, settings.getWidth(), settings.getHeight());
        }

        if (this.settings.isVsync() != settings.isVsync())
        {
            this.settings.setVsync(settings.isVsync());
            glfwSwapInterval(settings.isVsync() ? 1 : 0);
        }

        if (this.settings.isResizable() != settings.isResizable())
        {
            this.settings.setResizable(settings.isResizable());
            glfwSetWindowAttrib(windowId, GLFW_RESIZABLE, settings.isResizable() ? GLFW_TRUE : GLFW_FALSE);
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
        glfwSwapBuffers(windowId);
        glfwPollEvents();

        if (glfwWindowShouldClose(windowId))
        {
            for (IWindowListener listener : listeners)
                listener.onWindowRequestClose(this);
        }
    }
}
