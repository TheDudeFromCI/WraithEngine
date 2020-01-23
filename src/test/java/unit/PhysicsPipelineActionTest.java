package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IFixedUpdateable;
import net.whg.we.main.PhysicsPipelineAction;

public class PhysicsPipelineActionTest
{
    @Test
    public void ensurePipelinePriority()
    {
        assertEquals(-1000, new PhysicsPipelineAction().getPriority());
    }

    @Test
    public void updateBehaviors()
    {
        PhysicsPipelineAction action = new PhysicsPipelineAction();
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

    class UpdatableAction extends AbstractBehavior implements IFixedUpdateable
    {
        int calls = 0;

        @Override
        public void fixedUpdate()
        {
            calls++;
        }
    }
}
