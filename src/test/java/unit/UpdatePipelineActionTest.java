package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IUpdateable;
import net.whg.we.main.PipelineConstants;
import net.whg.we.main.Timer;
import net.whg.we.main.UpdatePipeline;

public class UpdatePipelineActionTest
{
    @Test
    public void ensurePipelinePriority()
    {
        assertEquals(PipelineConstants.FRAME_UPDATES, new UpdatePipeline(mock(Timer.class)).getPriority());
    }

    @Test
    public void updateBehaviors()
    {
        Timer timer = mock(Timer.class);
        UpdatePipeline action = new UpdatePipeline(timer);
        action.enableBehavior(mock(AbstractBehavior.class)); // To make sure no casting issues occur

        UpdatableAction behavior = new UpdatableAction();
        assertEquals(0, behavior.calls);

        action.enableBehavior(behavior);
        action.run();
        assertEquals(1, behavior.calls);
        assertTrue(timer == behavior.timer);

        action.disableBehavior(behavior);
        action.run();
        assertEquals(1, behavior.calls);
    }

    class UpdatableAction extends AbstractBehavior implements IUpdateable
    {
        Timer timer;
        int calls = 0;

        @Override
        public void update(Timer timer)
        {
            this.timer = timer;
            calls++;
        }
    }
}
