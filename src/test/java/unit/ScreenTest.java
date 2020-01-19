package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.main.Screen;
import net.whg.we.main.UserControlsUpdater;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

public class ScreenTest
{
    private IWindowListener listener(IWindow window)
    {
        IWindowListener[] l = new IWindowListener[1];

        doAnswer(a ->
        { l[0] = a.getArgument(0); return null; }).when(window)
                                                  .addWindowListener(any());

        UserControlsUpdater.bind(window);
        return l[0];
    }

    @Test
    public void resizeScreen()
    {
        IWindow window = mock(IWindow.class);
        when(window.getProperties()).thenReturn(new WindowSettings());

        IWindowListener listener = listener(window);

        listener.onWindowResized(window, 320, 240);

        assertEquals(320, Screen.getWidth());
        assertEquals(240, Screen.getHeight());
        assertEquals(4f / 3f, Screen.getAspect(), 0.0001f);
    }

    @Test
    public void resizeScreen_updateTrigger()
    {
        IWindow window = mock(IWindow.class);
        WindowSettings settings = new WindowSettings();
        settings.setWidth(1600);
        settings.setHeight(900);
        when(window.getProperties()).thenReturn(settings);

        IWindowListener listener = listener(window);

        listener.onWindowUpdated(window);

        assertEquals(1600, Screen.getWidth());
        assertEquals(900, Screen.getHeight());
        assertEquals(16f / 9f, Screen.getAspect(), 0.0001f);
    }
}
