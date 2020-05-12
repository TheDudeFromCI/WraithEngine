package unit.engine.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import net.whg.we.window.Input;

public class InputTest
{
    @Test
    public void keyPressed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onKeyPressed(window, 35);

        assertTrue(input.isKeyDown(35));
        assertTrue(input.isKeyJustDown(35));
        assertFalse(input.isKeyJustUp(35));
    }

    @Test
    public void keyReleased()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onKeyPressed(window, 35);
        input.endFrame();
        window.listener.onKeyReleased(window, 35);

        assertFalse(input.isKeyDown(35));
        assertFalse(input.isKeyJustDown(35));
        assertTrue(input.isKeyJustUp(35));
    }

    @Test
    public void keyHeld()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onKeyPressed(window, 35);
        input.endFrame();

        assertTrue(input.isKeyDown(35));
        assertFalse(input.isKeyJustDown(35));
        assertFalse(input.isKeyJustUp(35));
    }

    @Test
    public void keyIdle()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        input.endFrame();

        assertFalse(input.isKeyDown(35));
        assertFalse(input.isKeyJustDown(35));
        assertFalse(input.isKeyJustUp(35));
    }

    @Test
    public void mousePressed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onMousePressed(window, 2);

        assertTrue(input.isMouseButtonDown(2));
        assertTrue(input.isMouseButtonJustDown(2));
        assertFalse(input.isMouseButtonJustUp(2));
    }

    @Test
    public void mouseReleased()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onMousePressed(window, 2);
        input.endFrame();
        window.listener.onMouseReleased(window, 2);

        assertFalse(input.isMouseButtonDown(2));
        assertFalse(input.isMouseButtonJustDown(2));
        assertTrue(input.isMouseButtonJustUp(2));
    }

    @Test
    public void mouseHeld()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onMousePressed(window, 2);
        input.endFrame();

        assertTrue(input.isMouseButtonDown(2));
        assertFalse(input.isMouseButtonJustDown(2));
        assertFalse(input.isMouseButtonJustUp(2));
    }

    @Test
    public void mouseIdle()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        input.endFrame();

        assertFalse(input.isMouseButtonDown(1));
        assertFalse(input.isMouseButtonJustDown(1));
        assertFalse(input.isMouseButtonJustUp(1));
    }

    @Test
    public void mouseMove()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onMouseMove(window, 120f, 108f);

        assertEquals(120f, input.getMouseX(), 0f);
        assertEquals(108f, input.getMouseY(), 0f);
    }

    @Test
    public void moveMouse_delta()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onMouseMove(window, 120f, 108f);
        input.endFrame();
        window.listener.onMouseMove(window, 125f, 118f);

        assertEquals(5f, input.getMouseDeltaX(), 0.0001f);
        assertEquals(10f, input.getMouseDeltaY(), 0.0001f);
    }

    @Test
    public void scrollWheel()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);

        window.listener.onMouseWheel(window, 0f, 3.5f);

        assertEquals(3.5f, input.getScrollWheelDelta(), 0f);
    }

    @Test(expected = IllegalStateException.class)
    public void endFrame_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.endFrame();
    }

    @Test(expected = IllegalStateException.class)
    public void isKeyDown_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.isKeyDown(0);
    }

    @Test(expected = IllegalStateException.class)
    public void isKeyJustDown_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.isKeyJustDown(0);
    }

    @Test(expected = IllegalStateException.class)
    public void isKeyJustUp_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.isKeyJustUp(0);
    }

    @Test(expected = IllegalStateException.class)
    public void getMouseX_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.getMouseX();
    }

    @Test(expected = IllegalStateException.class)
    public void getMouseY_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.getMouseY();
    }

    @Test(expected = IllegalStateException.class)
    public void getMouseDeltaX_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.getMouseDeltaX();
    }

    @Test(expected = IllegalStateException.class)
    public void getMouseDeltaY_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.getMouseDeltaY();
    }

    @Test(expected = IllegalStateException.class)
    public void isMouseButtonDown_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.isMouseButtonDown(0);
    }

    @Test(expected = IllegalStateException.class)
    public void isMouseButtonJustDown_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.isMouseButtonJustDown(0);
    }

    @Test(expected = IllegalStateException.class)
    public void isMouseButtonJustUp_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.isMouseButtonJustUp(0);
    }

    @Test(expected = IllegalStateException.class)
    public void getScrollWheelDelta_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();

        input.getScrollWheelDelta();
    }

    @Test
    public void dispose()
    {
        FakeWindow window = new FakeWindow();
        Input input = new Input(window);
        input.dispose();
        input.dispose();

        assertNull(window.listener);
    }
}
