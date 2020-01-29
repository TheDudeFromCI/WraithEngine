package unit;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import org.junit.Test;
import net.whg.we.main.GameLoop;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowCloseHandler;

public class WindowCloseHandlerTest
{
    @Test
    public void bindToWindow()
    {
        IWindow window = mock(IWindow.class);
        GameLoop loop = mock(GameLoop.class);

        WindowCloseHandler.bindToWindow(window, loop);

        verify(window).addWindowListener(any());
    }

    @Test
    public void dispose()
    {
        IWindow window = mock(IWindow.class);
        GameLoop loop = mock(GameLoop.class);

        WindowCloseHandler handler = WindowCloseHandler.bindToWindow(window, loop);

        handler.dispose();
        handler.dispose();

        verify(window, times(1)).removeWindowListener(any());
    }

    @Test
    public void onClose()
    {
        IWindow window = mock(IWindow.class);
        GameLoop loop = mock(GameLoop.class);

        IWindowListener[] l = new IWindowListener[1];
        doAnswer(a ->
        { l[0] = a.getArgument(0); return null; }).when(window)
                                                  .addWindowListener(any());

        WindowCloseHandler.bindToWindow(window, loop);

        l[0].onWindowRequestClose(window);

        verify(loop).stop();
    }
}
