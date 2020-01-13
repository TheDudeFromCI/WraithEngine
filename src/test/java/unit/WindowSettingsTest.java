package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import net.whg.we.window.WindowSettings;

public class WindowSettingsTest
{
    @Test
    public void properties_getAndSet()
    {
        WindowSettings settings = new WindowSettings();

        settings.setTitle("New Title");
        assertEquals("New Title", settings.getTitle());

        settings.setWidth(100);
        assertEquals(100, settings.getWidth());

        settings.setHeight(120);
        assertEquals(120, settings.getHeight());

        settings.setFullscreen(true);
        assertEquals(true, settings.isFullscreen());

        settings.setVsync(true);
        assertEquals(true, settings.isVsync());

        settings.setResizable(true);
        assertEquals(true, settings.isResizable());

        settings.setSamples(6);
        assertEquals(6, settings.getSamples());
    }

    @Test
    public void properties_getDefaults()
    {
        WindowSettings settings = new WindowSettings();

        assertEquals("Untitled", settings.getTitle());
        assertEquals(800, settings.getWidth());
        assertEquals(600, settings.getHeight());
        assertEquals(false, settings.isFullscreen());
        assertEquals(false, settings.isVsync());
        assertEquals(false, settings.isResizable());
        assertEquals(1, settings.getSamples());
    }

    @Test
    public void equals_sameInstance()
    {
        WindowSettings settings = new WindowSettings();
        settings.setHeight(345);
        settings.setResizable(true);
        settings.setSamples(23);

        assertEquals(settings, settings);
    }

    @Test
    public void equals_diffInstance()
    {
        WindowSettings settings1 = new WindowSettings();
        settings1.setWidth(4323);
        settings1.setFullscreen(true);

        WindowSettings settings2 = new WindowSettings();
        settings2.setWidth(4323);
        settings2.setFullscreen(true);

        assertEquals(settings1, settings2);
        assertEquals(settings1.hashCode(), settings2.hashCode());
    }

    @Test
    public void notEquals_diffInstance_diffSettings()
    {
        WindowSettings settings1 = new WindowSettings();
        settings1.setWidth(243);

        WindowSettings settings2 = new WindowSettings();
        settings2.setWidth(395);

        assertNotEquals(settings1, settings2);
        assertNotEquals(settings1.hashCode(), settings2.hashCode());
    }
}
