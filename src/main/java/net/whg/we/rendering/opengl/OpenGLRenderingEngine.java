package net.whg.we.rendering.opengl;

import org.lwjgl.opengl.GL;
import net.whg.we.rendering.IRenderingEngine;

public class OpenGLRenderingEngine implements IRenderingEngine
{
    private boolean disposed;

    public OpenGLRenderingEngine()
    {
        GL.createCapabilities();
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
}
