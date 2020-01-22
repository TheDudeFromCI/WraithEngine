package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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

    @Test
    public void cloneWindow()
    {
        WindowSettings settings1 = new WindowSettings();
        settings1.setFullscreen(true);
        settings1.setTitle("My Window");

        WindowSettings settings2 = new WindowSettings(settings1);

        assertEquals(settings1, settings2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTitle_null()
    {
        new WindowSettings().setTitle(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWidth_negative()
    {
        new WindowSettings().setWidth(-5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHeight_zero()
    {
        new WindowSettings().setHeight(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSamples_zero()
    {
        new WindowSettings().setSamples(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setWidth_tooHigh()
    {
        new WindowSettings().setSamples(50);
    }

    @Test
    public void equalsTests()
    {
        WindowSettings settings = new WindowSettings();

        assertTrue(settings.equals(settings));
        assertFalse(settings.equals(null));
        assertFalse(settings.equals(new Object()));

        WindowSettings settings2 = new WindowSettings();
        assertTrue(settings.equals(settings2));
        assertEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setFullscreen(true);
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setTitle("New Title");
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setResizable(true);
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setWidth(1000);
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setHeight(1000);
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setSamples(7);
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());

        settings2 = new WindowSettings();
        settings2.setVsync(true);
        assertFalse(settings.equals(settings2));
        assertNotEquals(settings.hashCode(), settings2.hashCode());
    }

    @Test
    public void setAspectRatio()
    {
        WindowSettings settings;

        settings = new WindowSettings();
        settings.setSize(320, 240);
        assertEquals(4f / 3f, settings.getAspectRatio(), 0.00001f);

        settings = new WindowSettings();
        settings.setSize(1600, 900);
        assertEquals(16f / 9f, settings.getAspectRatio(), 0.00001f);

        settings = new WindowSettings();
        settings.setSize(1000, 1000);
        assertEquals(1f, settings.getAspectRatio(), 0.00001f);
    }
}
