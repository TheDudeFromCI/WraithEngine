package unit;

import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

public class FakeWindow implements IWindow
{
    public IWindowListener listener;
    public WindowSettings settings;

    @Override
    public void dispose()
    {}

    @Override
    public boolean isDisposed()
    {
        return false;
    }

    @Override
    public void setProperties(WindowSettings settings)
    {}

    @Override
    public WindowSettings getProperties()
    {
        return settings;
    }

    @Override
    public IRenderingEngine getRenderingEngine()
    {
        return null;
    }

    @Override
    public void addWindowListener(IWindowListener listener)
    {
        this.listener = listener;
    }

    @Override
    public void removeWindowListener(IWindowListener listener)
    {
        this.listener = null;
    }

    @Override
    public void pollEvents()
    {}

    @Override
    public long getWindowId()
    {
        return 1;
    }
}
