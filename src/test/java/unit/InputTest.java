package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.main.Input;
import net.whg.we.main.UserControlsUpdater;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

public class InputTest
{
    private IWindow window()
    {
        IWindow window = mock(IWindow.class);
        when(window.getProperties()).thenReturn(new WindowSettings());

        return window;
    }

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
    public void keyPressed()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onKeyPressed(window, 35);

        assertTrue(Input.isKeyDown(35));
        assertTrue(Input.isKeyJustDown(35));
        assertFalse(Input.isKeyJustUp(35));
    }

    @Test
    public void keyReleased()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onKeyPressed(window, 35);
        Input.endFrame();
        listener.onKeyReleased(window, 35);

        assertFalse(Input.isKeyDown(35));
        assertFalse(Input.isKeyJustDown(35));
        assertTrue(Input.isKeyJustUp(35));
    }

    @Test
    public void keyHeld()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onKeyPressed(window, 35);
        Input.endFrame();

        assertTrue(Input.isKeyDown(35));
        assertFalse(Input.isKeyJustDown(35));
        assertFalse(Input.isKeyJustUp(35));
    }

    @Test
    public void keyIdle()
    {
        Input.clear();
        Input.endFrame();

        assertFalse(Input.isKeyDown(35));
        assertFalse(Input.isKeyJustDown(35));
        assertFalse(Input.isKeyJustUp(35));
    }

    @Test
    public void mousePressed()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onMousePressed(window, 2);

        assertTrue(Input.isMouseButtonDown(2));
        assertTrue(Input.isMouseButtonJustDown(2));
        assertFalse(Input.isMouseButtonJustUp(2));
    }

    @Test
    public void mouseReleased()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onMousePressed(window, 2);
        Input.endFrame();
        listener.onMouseReleased(window, 2);

        assertFalse(Input.isMouseButtonDown(2));
        assertFalse(Input.isMouseButtonJustDown(2));
        assertTrue(Input.isMouseButtonJustUp(2));
    }

    @Test
    public void mouseHeld()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onMousePressed(window, 2);
        Input.endFrame();

        assertTrue(Input.isMouseButtonDown(2));
        assertFalse(Input.isMouseButtonJustDown(2));
        assertFalse(Input.isMouseButtonJustUp(2));
    }

    @Test
    public void mouseIdle()
    {
        Input.clear();
        Input.endFrame();

        assertFalse(Input.isMouseButtonDown(1));
        assertFalse(Input.isMouseButtonJustDown(1));
        assertFalse(Input.isMouseButtonJustUp(1));
    }

    @Test
    public void mouseMove()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onMouseMove(window, 120f, 108f);

        assertEquals(120f, Input.getMouseX(), 0f);
        assertEquals(108f, Input.getMouseY(), 0f);
    }

    @Test
    public void moveMouse_delta()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onMouseMove(window, 120f, 108f);
        Input.endFrame();
        listener.onMouseMove(window, 125f, 118f);

        assertEquals(5f, Input.getMouseDeltaX(), 0.0001f);
        assertEquals(10f, Input.getMouseDeltaY(), 0.0001f);
    }

    @Test
    public void scrollWheel()
    {
        Input.clear();

        IWindow window = window();
        IWindowListener listener = listener(window);

        listener.onMouseWheel(window, 0f, 3.5f);

        assertEquals(3.5f, Input.getScrollWheelDelta(), 0f);
    }
}
