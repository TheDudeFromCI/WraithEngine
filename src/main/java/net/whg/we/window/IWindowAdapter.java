package net.whg.we.window;

public abstract class IWindowAdapter implements IWindowListener
{
    @Override
    public void onKeyPressed(IWindow window, int keyCode)
    {}

    @Override
    public void onKeyReleased(IWindow window, int keyCode)
    {}

    @Override
    public void onMouseMove(IWindow window, float newX, float newY)
    {}

    @Override
    public void onMousePressed(IWindow window, int mouseButton)
    {}

    @Override
    public void onMouseReleased(IWindow window, int mouseButton)
    {}

    @Override
    public void onMouseWheel(IWindow window, float scrollX, float scrollY)
    {}

    @Override
    public void onWindowDestroyed(IWindow window)
    {}

    @Override
    public void onWindowRequestClose(IWindow window)
    {}

    @Override
    public void onWindowResized(IWindow window, int width, int height)
    {}

    @Override
    public void onWindowUpdated(IWindow window)
    {}
}
