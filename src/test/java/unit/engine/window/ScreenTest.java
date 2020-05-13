package unit.engine.window;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import net.whg.we.window.Screen;

public class ScreenTest
{
    @Test
    public void resizeScreen()
    {
        FakeWindow window = new FakeWindow();
        Screen screen = new Screen(window);

        window.listener.onWindowResized(window, 320, 240);

        assertEquals(320, screen.getWidth());
        assertEquals(240, screen.getHeight());
        assertEquals(4f / 3f, screen.getAspect(), 0.0001f);
    }

    @Test
    public void resizeScreen_updateTrigger()
    {
        FakeWindow window = new FakeWindow();
        window.settings.setSize(1600, 900);

        Screen screen = new Screen(window);
        window.listener.onWindowUpdated(window);

        assertEquals(1600, screen.getWidth());
        assertEquals(900, screen.getHeight());
        assertEquals(16f / 9f, screen.getAspect(), 0.0001f);
    }

    @Test(expected = IllegalStateException.class)
    public void getWidth_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Screen screen = new Screen(window);
        screen.dispose();

        screen.getWidth();
    }

    @Test(expected = IllegalStateException.class)
    public void getHeight_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Screen screen = new Screen(window);
        screen.dispose();

        screen.getHeight();
    }

    @Test(expected = IllegalStateException.class)
    public void getAspect_alreadyDisposed()
    {
        FakeWindow window = new FakeWindow();
        Screen screen = new Screen(window);
        screen.dispose();

        screen.getAspect();
    }

    @Test
    public void dipose()
    {
        FakeWindow window = new FakeWindow();

        Screen screen = new Screen(window);
        screen.dispose();
        screen.dispose(); // To make sure nothing happens when you dispose twice

        assertNull(window.listener);
    }

    @Test
    public void initializeOnCreation()
    {
        FakeWindow window = new FakeWindow();
        window.settings.setSize(400, 300);

        Screen screen = new Screen(window);

        assertEquals(400, screen.getWidth());
        assertEquals(300, screen.getHeight());
    }
}
