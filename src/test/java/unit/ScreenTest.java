package unit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import net.whg.we.main.Screen;
import net.whg.we.window.WindowSettings;

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
        window.settings = new WindowSettings();
        window.settings.setSize(1600, 900);

        Screen screen = new Screen(window);
        window.listener.onWindowUpdated(window);

        assertEquals(1600, screen.getWidth());
        assertEquals(900, screen.getHeight());
        assertEquals(16f / 9f, screen.getAspect(), 0.0001f);
    }
}
