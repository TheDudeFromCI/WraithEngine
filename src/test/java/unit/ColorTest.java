package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import net.whg.we.util.Color;

public class ColorTest
{
    @Test
    public void createColor()
    {
        Color color = new Color(1f, 0.75f, 0.5f);

        assertEquals(1f, color.getRed(), 0f);
        assertEquals(0.75f, color.getGreen(), 0f);
        assertEquals(0.5f, color.getBlue(), 0f);
        assertEquals(1f, color.getAlpha(), 0f);
    }

    @Test
    public void createColor_withAlpha()
    {
        Color color = new Color(0.2f, 0.4f, 0.6f, 0.23f);

        assertEquals(0.2f, color.getRed(), 0f);
        assertEquals(0.4f, color.getGreen(), 0f);
        assertEquals(0.6f, color.getBlue(), 0f);
        assertEquals(0.23f, color.getAlpha(), 0f);
    }

    @Test
    public void createColor_outOfBoundsChannel_error()
    {
        float[][] channels = new float[][] {{0.7f, -0.1f, 0.6f}, {-0.24f, -0.1f, 0.26f}, {10.24f, 0.51f, 0.216f},
                {0.24f, 0.1f, 20.26f}, {0.24f, 5.1f, -0.26f}, {0.1f, 0.2f, 0.3f, -1f}, {0.4f, 0.5f, 0.6f, 100f}};

        int captures = 0;
        for (int i = 0; i < channels.length; i++)
        {
            try
            {
                if (channels[i].length == 3)
                    new Color(channels[i][0], channels[i][1], channels[i][2]);
                else
                    new Color(channels[i][0], channels[i][1], channels[i][2], channels[i][3]);

                throw new RuntimeException();
            }
            catch (IllegalArgumentException e)
            {
                captures++;
            }
        }

        assertEquals(channels.length, captures);
    }

    @Test
    public void equals_sameInstance()
    {
        Color a = new Color(0.5f, 0.15f, 0.8f);

        assertEquals(a, a);
        assertEquals(a.hashCode(), a.hashCode());
    }

    @Test
    public void equals_diffInstance()
    {
        Color a = new Color(0.5f, 0.15f, 0.8f);
        Color b = new Color(0.5f, 0.15f, 0.8f);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equals_diffInstance_diffData()
    {
        Color a = new Color(0.5f, 0.15f, 0.8f);
        Color b = new Color(0.2f, 0.3f, 0.4f);

        assertNotEquals(a, b);
        assertNotEquals(a.hashCode(), b.hashCode());
    }
}
