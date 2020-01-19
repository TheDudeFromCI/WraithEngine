package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.main.UserControlsUpdater;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

public class UserControlsUpdaterTest
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
    public void unbindWindow()
    {
        IWindow window = mock(IWindow.class);
        when(window.getProperties()).thenReturn(new WindowSettings());
        IWindowListener listener = listener(window);

        listener.onWindowRequestClose(window);
        listener.onWindowDestroyed(window);

        assertNull(UserControlsUpdater.getBoundWindow());
    }

    @Test
    public void bindWindow_twice()
    {
        IWindow window = mock(IWindow.class);
        when(window.getProperties()).thenReturn(new WindowSettings());

        UserControlsUpdater.bind(window);
        UserControlsUpdater.bind(window);

        assertEquals(window, UserControlsUpdater.getBoundWindow());
        verify(window, never()).removeWindowListener(any());
    }
}
