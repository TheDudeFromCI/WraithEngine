package net.whg.we.rendering.opengl;

import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.util.Color;

public class GLScreenClear implements IScreenClearHandler
{
    private final IOpenGL opengl;
    private Color clearColor;
    private boolean clearDepth;

    /**
     * Creates a new GLScreenClear object.
     * 
     * @param opengl
     *     - The OpenGL instance to send screen clear requests to.
     */
    GLScreenClear(IOpenGL opengl)
    {
        this.opengl = opengl;
    }

    @Override
    public void clearScreen()
    {
        if (clearColor == null && !clearDepth)
            return;

        opengl.clearScreen(clearColor != null, clearDepth);
    }

    @Override
    public void setClearColor(Color clearColor)
    {
        this.clearColor = clearColor;

        if (clearColor != null)
            opengl.setClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(),
                    clearColor.getAlpha());
    }

    @Override
    public void setClearDepth(boolean clearDepth)
    {
        this.clearDepth = clearDepth;
    }

    @Override
    public Color getClearColor()
    {
        return clearColor;
    }

    @Override
    public boolean getClearDepth()
    {
        return clearDepth;
    }
}
