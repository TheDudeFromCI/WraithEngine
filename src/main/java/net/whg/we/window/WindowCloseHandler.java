package net.whg.we.window;

import net.whg.we.main.GameLoop;
import net.whg.we.util.IDisposable;

/**
 * This class is a simple utility which listens for when a window close request
 * is triggered and stops the connected game loop, allowing the window to close.
 * <p>
 * The window binding can be removed by disposing this object. This object will
 * also automatially be disposed after stopping the game loop.
 */
public class WindowCloseHandler implements IDisposable
{
    /**
     * Creates a new window close handler. This will automatically bind the listener
     * to the window.
     * 
     * @param window
     *     - The window to bind to.
     * @param gameLoop
     *     - The game loop to stop when the window requests to close.
     */
    public static WindowCloseHandler bindToWindow(IWindow window, GameLoop gameLoop)
    {
        return new WindowCloseHandler(window, gameLoop);
    }

    /**
     * A simple listener which listens for a window close request.
     */
    private class WindowCloseListener extends IWindowAdapter
    {
        @Override
        public void onWindowRequestClose(IWindow window)
        {
            gameLoop.stop();
            dispose();
        }
    }

    private final WindowCloseListener listener = new WindowCloseListener();
    private final IWindow window;
    private final GameLoop gameLoop;
    private boolean disposed;

    /**
     * Creates a new window close handler. This constructor handles the window
     * binding automatically.
     * 
     * @param window
     *     - The window to bind to.
     * @param gameLoop
     *     - The game loop to stop when the window requests to close.
     */
    private WindowCloseHandler(IWindow window, GameLoop gameLoop)
    {
        this.window = window;
        this.gameLoop = gameLoop;

        window.addWindowListener(listener);
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        window.removeWindowListener(listener);
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }
}
