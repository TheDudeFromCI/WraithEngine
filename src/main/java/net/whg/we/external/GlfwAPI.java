package net.whg.we.external;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;
import java.io.PrintStream;
import java.util.List;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.glfw.GLFWException;
import net.whg.we.window.glfw.IGlfw;

/**
 * This class acts as the bridge between GLFW and WraithEngine.
 */
public final class GlfwAPI implements IGlfw
{
    @Override
    public void terminate()
    {
        glfwTerminate();
    }

    @Override
    public void destroyWindow(long windowId)
    {
        glfwDestroyWindow(windowId);
    }

    @Override
    public void init()
    {
        if (!glfwInit())
            throw new GLFWException("Unable to initialize GLFW");
    }

    @Override
    public void setErrorCallback(PrintStream printStream)
    {
        try (GLFWErrorCallback callback = GLFWErrorCallback.createPrint(printStream))
        {
            callback.set();
        }
    }

    @Override
    public void setWindowHints(boolean resizable, int openGlMajor, int openGlMinor, int samples)
    {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
        glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, openGlMajor);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, openGlMinor);
        glfwWindowHint(GLFW_SAMPLES, samples);
        glfwWindowHint(GLFW_DOUBLEBUFFER, GLFW_TRUE);
    }

    @Override
    public long createWindow(int width, int height, String title, boolean fullscreen)
    {
        long windowId = glfwCreateWindow(width, height, title, fullscreen ? glfwGetPrimaryMonitor() : NULL, NULL);

        if (windowId == NULL)
            throw new GLFWException("Failed to create GLFW window!");

        return windowId;
    }

    @Override
    public int[] getScreenSize()
    {
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        return new int[] {vidmode.width(), vidmode.height()};
    }

    @Override
    public void setWindowPosition(long windowId, int x, int y)
    {
        glfwSetWindowPos(windowId, x, y);
    }

    @Override
    public void setContextThread(long windowId)
    {
        glfwMakeContextCurrent(windowId);
    }

    @Override
    public void setVSync(int swapRate)
    {
        glfwSwapInterval(swapRate);
    }

    @Override
    public void showWindow(long windowId)
    {
        glfwShowWindow(windowId);
    }

    @Override
    public void setWindowSize(long windowId, int width, int height)
    {
        glfwSetWindowSize(windowId, width, height);
    }

    @Override
    public void setWindowTitle(long windowId, String title)
    {
        glfwSetWindowTitle(windowId, title);
    }

    @Override
    public void setWindowResizable(long windowId, boolean resizable)
    {
        glfwSetWindowAttrib(windowId, GLFW_RESIZABLE, resizable ? GLFW_TRUE : GLFW_FALSE);
    }

    @Override
    public void swapBuffers(long windowId)
    {
        glfwSwapBuffers(windowId);
    }

    @Override
    public void pollEvents()
    {
        glfwPollEvents();
    }

    @Override
    public boolean shouldWindowClose(long windowId)
    {
        return glfwWindowShouldClose(windowId);
    }

    @Override
    public void addMousePosListener(IWindow window, List<IWindowListener> listeners)
    {
        glfwSetCursorPosCallback(window.getWindowId(), (win, xpos, ypos) ->
        {
            for (IWindowListener listener : listeners)
                listener.onMouseMove(window, (float) xpos, (float) ypos);
        });
    }

    @Override
    public void addMouseButtonListener(IWindow window, List<IWindowListener> listeners)
    {
        glfwSetMouseButtonCallback(window.getWindowId(), (win, button, action, mods) ->
        {
            if (action == GLFW_PRESS)
            {
                for (IWindowListener listener : listeners)
                    listener.onMousePressed(window, button);
            }
            else if (action == GLFW_RELEASE)
            {
                for (IWindowListener listener : listeners)
                    listener.onMouseReleased(window, button);
            }
        });
    }

    @Override
    public void addMouseWheelListener(IWindow window, List<IWindowListener> listeners)
    {
        glfwSetScrollCallback(window.getWindowId(), (win, xoff, yoff) ->
        {
            for (IWindowListener listener : listeners)
                listener.onMouseWheel(window, (float) xoff, (float) yoff);
        });
    }

    @Override
    public void addKeyListener(IWindow window, List<IWindowListener> listeners)
    {
        glfwSetKeyCallback(window.getWindowId(), (win, key, scancode, action, mods) ->
        {
            if (action == GLFW_PRESS)
            {
                for (IWindowListener listener : listeners)
                    listener.onKeyPressed(window, key);
            }
            else if (action == GLFW_RELEASE)
            {
                for (IWindowListener listener : listeners)
                    listener.onKeyReleased(window, key);
            }
        });
    }

    @Override
    public void addWindowResizeListener(IWindow window, List<IWindowListener> listeners)
    {
        glfwSetWindowSizeCallback(window.getWindowId(), (win, width, height) ->
        {
            for (IWindowListener listener : listeners)
                listener.onWindowResized(window, width, height);
        });
    }
}
