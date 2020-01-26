package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.Timer;
import net.whg.we.main.TimerAction;

public class TimerActionTest
{
    @Test
    public void beginFrameOnRun()
    {
        Timer timer = mock(Timer.class);
        TimerAction action = new TimerAction(timer);

        action.run();

        verify(timer).beginFrame();
    }

    @Test
    public void priorityIs_Negative1000000()
    {
        assertEquals(-1000000, new TimerAction(mock(Timer.class)).getPriority());
    }
}
