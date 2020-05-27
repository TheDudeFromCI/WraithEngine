package net.whg.we.window;

import net.whg.we.util.IDisposable;

/**
 * A simple handler for allowing a window receiver to connect to a window
 * seamlessly and listen for all window events as they occur.
 */
public abstract class WindowReceiver implements IDisposable
{
    private IWindow window;
    private IWindowListener listener;
    private boolean disposed = true;

    /**
     * Marks this receiver to listen to the given window. If this object is
     * currently disposed, it will be reset. If it is already listening to another
     * window, it will be removed from that window.
     * 
     * @param window
     *     - The window to listen to.
     * @param listener
     *     - The listener for accepting events.
     */
    protected void listenTo(IWindow window, IWindowListener listener)
    {
        if (!isDisposed())
            dispose();

        this.window = window;
        this.listener = listener;
        disposed = false;

        window.addWindowListener(listener);
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        window.removeWindowListener(listener);
        window = null;
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }
}
