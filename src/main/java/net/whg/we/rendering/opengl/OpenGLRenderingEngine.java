package net.whg.we.rendering.opengl;

import org.lwjgl.opengl.GL;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.IShader;

/**
 * This is the basic OpenGL implementation of the rendering engine. It uses
 * OpenGL 3.3, and renders on the main thread.
 */
public class OpenGLRenderingEngine implements IRenderingEngine
{
    private final BindStates bindStates = new BindStates();
    private final IScreenClearHandler screenClearHandler;
    private boolean disposed;

    public OpenGLRenderingEngine()
    {
        GL.createCapabilities();

        screenClearHandler = new GLScreenClear();
    }

    @Override
    public void dispose()
    {
        GL.destroy();
        disposed = true;
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    @Override
    public IScreenClearHandler getScreenClearHandler()
    {
        return screenClearHandler;
    }

    @Override
    public IMesh createMesh()
    {
        return new GLMesh();
    }

    @Override
    public IShader createShader()
    {
        return new GLShader(bindStates);
    }
}
