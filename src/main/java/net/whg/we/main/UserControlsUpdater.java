package net.whg.we.main;

import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

/**
 * This class acts as a utility class for updating the user control classes in
 * the engine, such as Screen and Input. This binds to a window to continously
 * update the settings.
 */
public final class UserControlsUpdater implements IWindowListener
{
    private static UserControlsUpdater updater = new UserControlsUpdater();

    /**
     * Binds this updater to the current window. Replaces the binding with previous
     * window, if previously binded. This updater is automaically unbound when the
     * window is destroyed.
     * 
     * @param window
     *     - The window to bind with, or null to disable current bindings.
     */
    public static void bind(IWindow window)
    {
        updater.setWindow(window);
    }

    private IWindow window;

    private UserControlsUpdater()
    {}

    /**
     * Assigns the target window to bind to.
     * 
     * @param window
     *     - The new target window.
     */
    private void setWindow(IWindow window)
    {
        if (this.window == window)
            return;

        if (this.window != null)
            this.window.removeWindowListener(this);

        this.window = window;

        if (this.window != null)
        {
            this.window.addWindowListener(this);

            WindowSettings settings = this.window.getProperties();
            Screen.updateWindowSize(settings.getWidth(), settings.getHeight());
        }
    }

    @Override
    public void onWindowUpdated(IWindow window)
    {
        WindowSettings settings = window.getProperties();
        Screen.updateWindowSize(settings.getWidth(), settings.getHeight());
    }

    @Override
    public void onWindowDestroyed(IWindow window)
    {
        setWindow(null);
    }

    @Override
    public void onWindowRequestClose(IWindow window)
    {}

    @Override
    public void onMouseMove(IWindow window, float newX, float newY)
    {}

    @Override
    public void onKeyPressed(IWindow window, int keyCode)
    {}

    @Override
    public void onKeyReleased(IWindow window, int keyCode)
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
}
