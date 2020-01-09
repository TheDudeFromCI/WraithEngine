package net.whg.we.rendering.opengl;

import org.lwjgl.opengl.GL11;
import net.whg.we.rendering.IScreenClearHandler;
import net.whg.we.util.Color;

public class GLScreenClear implements IScreenClearHandler
{
    private Color clearColor;
    private boolean clearDepth;

    GLScreenClear()
    {
        setClearColor(new Color(0.2f, 0.4f, 0.8f));
        setClearDepth(true);
    }

    @Override
    public void clearScreen()
    {
        int mask = 0;

        if (clearColor != null)
            mask |= GL11.GL_COLOR_BUFFER_BIT;

        if (clearDepth)
            mask |= GL11.GL_DEPTH_BUFFER_BIT;

        GL11.glClear(mask);
    }

    @Override
    public void setClearColor(Color clearColor)
    {
        this.clearColor = clearColor;

        if (clearColor != null)
            GL11.glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
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
