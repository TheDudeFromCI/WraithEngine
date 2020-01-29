package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IUpdateable;
import net.whg.we.main.PipelineConstants;
import net.whg.we.main.UpdatePipeline;

public class UpdatePipelineActionTest
{
    @Test
    public void ensurePipelinePriority()
    {
        assertEquals(PipelineConstants.FRAME_UPDATES, new UpdatePipeline().getPriority());
    }

    @Test
    public void updateBehaviors()
    {
        UpdatePipeline action = new UpdatePipeline();
        action.enableBehavior(mock(AbstractBehavior.class)); // To make sure no casting issues occur

        UpdatableAction behavior = new UpdatableAction();
        assertEquals(0, behavior.calls);

        action.enableBehavior(behavior);
        action.run();
        assertEquals(1, behavior.calls);

        action.disableBehavior(behavior);
        action.run();
        assertEquals(1, behavior.calls);
    }

    class UpdatableAction extends AbstractBehavior implements IUpdateable
    {
        int calls = 0;

        @Override
        public void update()
        {
            calls++;
        }
    }
}
