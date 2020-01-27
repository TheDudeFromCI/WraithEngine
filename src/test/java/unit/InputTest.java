package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
}
