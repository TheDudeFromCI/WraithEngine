package net.whg.we.rendering.opengl;

import net.whg.we.rendering.CullingMode;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.ITexture;
import net.whg.we.rendering.Color;

/**
 * This is the basic OpenGL implementation of the rendering engine. It uses
 * OpenGL 3.3, and renders on the main thread.
 */
public class OpenGLRenderingEngine implements IRenderingEngine
{
    private final IOpenGL opengl;
    private final BindStates bindStates;
    private final IScreenClearHandler screenClearHandler;
    private boolean depthTesting = true;
    private CullingMode cullingMode = CullingMode.BACK_FACING;
    private boolean disposed;

    public OpenGLRenderingEngine(IOpenGL opengl)
    {
        this.opengl = opengl;
        bindStates = new BindStates(opengl);
        screenClearHandler = new GLScreenClear(opengl);
    }

    @Override
    public void init()
    {
        opengl.init();
        opengl.setCullingMode(cullingMode);
        opengl.setDepthTesting(depthTesting);

        screenClearHandler.setClearColor(new Color(0f, 0f, 0f));
        screenClearHandler.setClearDepth(true);
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        opengl.dispose();
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
        return new GLMesh(bindStates, opengl);
    }

    @Override
    public IShader createShader()
    {
        return new GLShader(bindStates, opengl);
    }

    @Override
    public void setDepthTesting(boolean depthTesting)
    {
        if (this.depthTesting == depthTesting)
            return;

        this.depthTesting = depthTesting;
        opengl.setDepthTesting(depthTesting);
    }

    @Override
    public void setCullingMode(CullingMode cullingMode)
    {
        if (this.cullingMode == cullingMode)
            return;

        this.cullingMode = cullingMode;
        opengl.setCullingMode(cullingMode);
    }

    @Override
    public ITexture createTexture()
    {
        return new GLTexture(opengl, bindStates);
    }
}
