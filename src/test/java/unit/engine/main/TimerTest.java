package unit.engine.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.main.ITimeSupplier;
import net.whg.we.main.Timer;

public class TimerTest
{
    @Test(expected = IllegalStateException.class)
    public void mustStartToUpdate()
    {
        new Timer(mock(ITimeSupplier.class)).beginFrame();
    }

    @Test
    public void timerIsRunning()
    {
        Timer timer = new Timer(mock(ITimeSupplier.class));
        assertFalse(timer.isRunning());

        timer.startTimer();
        assertTrue(timer.isRunning());

        timer.stopTimer();
        assertFalse(timer.isRunning());
    }

    @Test
    public void ellapsedTime()
    {
        ITimeSupplier timeSupplier = mock(ITimeSupplier.class);
        when(timeSupplier.nanoTime()).thenReturn(1000000000L)
                                     .thenReturn(2000000000L);

        Timer timer = new Timer(timeSupplier);

        timer.startTimer();
        assertEquals(0f, timer.getElapsedTime(), 0f);

        timer.beginFrame();
        assertEquals(1f, timer.getElapsedTime(), 0.000001f);
    }

    @Test
    public void getFps()
    {
        ITimeSupplier timeSupplier = mock(ITimeSupplier.class);
        when(timeSupplier.nanoTime()).thenReturn(1000000000L)
                                     .thenReturn(1016666666L);

        Timer timer = new Timer(timeSupplier);
        timer.startTimer();

        timer.beginFrame();
        assertEquals(60f, timer.getFps(), 0.001f);
    }

    @Test
    public void getDeltaTime()
    {
        ITimeSupplier timeSupplier = mock(ITimeSupplier.class);
        when(timeSupplier.nanoTime()).thenReturn(1000000000L)
                                     .thenReturn(1100000000L);

        Timer timer = new Timer(timeSupplier);
        timer.startTimer();

        timer.beginFrame();
        assertEquals(0.1f, timer.getDeltaTime(), 0.00001f);
    }

    @Test
    public void defaultPhysicsFrameRate()
    {
        assertEquals(30f, new Timer(mock(ITimeSupplier.class)).getPhysicsFrameRate(), 0f);
    }

    @Test
    public void idealPhysicsFrame()
    {
        ITimeSupplier timeSupplier = mock(ITimeSupplier.class);
        when(timeSupplier.nanoTime()).thenReturn(1000000000L)
                                     .thenReturn(1000000001L)
                                     .thenReturn(1100000000L);

        Timer timer = new Timer(timeSupplier);
        timer.setPhysicsFrameRate(100f);

        timer.startTimer();
        assertEquals(0, timer.getIdealPhysicsFrame());

        timer.beginFrame();
        assertEquals(1, timer.getIdealPhysicsFrame());

        timer.beginFrame();
        assertEquals(11, timer.getIdealPhysicsFrame());
    }

    @Test(expected = IllegalStateException.class)
    public void setPhysicsFrameRate_timerStarted()
    {
        Timer timer = new Timer(mock(ITimeSupplier.class));
        timer.startTimer();
        timer.setPhysicsFrameRate(2f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPhysicsFrameRate_negativeValue()
    {
        Timer timer = new Timer(mock(ITimeSupplier.class));
        timer.setPhysicsFrameRate(-1f);
    }

    @Test
    public void incrementPhysicsFrame()
    {
        Timer timer = new Timer(mock(ITimeSupplier.class));

        timer.startTimer();
        assertEquals(0, timer.getPhysicsFrame());

        timer.incrementPhysicsFrame();
        assertEquals(1, timer.getPhysicsFrame());

        timer.incrementPhysicsFrame();
        assertEquals(2, timer.getPhysicsFrame());
    }

    @Test(expected = IllegalStateException.class)
    public void incrementPhysicsFrame_timerNotStarted()
    {
        new Timer(mock(ITimeSupplier.class)).incrementPhysicsFrame();
    }

    @Test
    public void getRenderFrame()
    {
        Timer timer = new Timer(mock(ITimeSupplier.class));

        timer.startTimer();
        assertEquals(0, timer.getRenderFrame());

        timer.beginFrame();
        assertEquals(1, timer.getRenderFrame());

        timer.beginFrame();
        assertEquals(2, timer.getRenderFrame());
    }
}
