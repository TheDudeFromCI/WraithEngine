package unit;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import net.whg.we.main.Screen;
import net.whg.we.rendering.IRenderingEngine;
import net.whg.we.window.IWindow;
import net.whg.we.window.IWindowListener;
import net.whg.we.window.WindowSettings;

public class ScreenTest
{
    private class FakeWindow implements IWindow
    {
        IWindowListener listener;

        @Override
        public void dispose()
        {}

        @Override
        public boolean isDisposed()
        {
            return false;
        }

        @Override
        public void setProperties(WindowSettings settings)
        {}

        @Override
        public WindowSettings getProperties()
        {
            WindowSettings settings = new WindowSettings();
            settings.setWidth(1600);
            settings.setHeight(900);
            return settings;
        }

        @Override
        public IRenderingEngine getRenderingEngine()
        {
            return null;
        }

        @Override
        public void addWindowListener(IWindowListener listener)
        {
            this.listener = listener;
        }

        @Override
        public void removeWindowListener(IWindowListener listener)
        {}

        @Override
        public void pollEvents()
        {}

        @Override
        public long getWindowId()
        {
            return 1;
        }
    }

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
        Screen screen = new Screen(window);

        window.listener.onWindowUpdated(window);

        assertEquals(1600, screen.getWidth());
        assertEquals(900, screen.getHeight());
        assertEquals(16f / 9f, screen.getAspect(), 0.0001f);
    }
}
