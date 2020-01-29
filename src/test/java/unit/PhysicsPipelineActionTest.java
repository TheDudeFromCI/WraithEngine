package unit;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IFixedUpdateable;
import net.whg.we.main.PhysicsPipeline;
import net.whg.we.main.PipelineConstants;
import net.whg.we.main.Timer;

public class PhysicsPipelineActionTest
{
    @Test
    public void ensurePipelinePriority()
    {
        assertEquals(PipelineConstants.PHYSICS_UPDATES, new PhysicsPipeline(mock(Timer.class)).getPriority());
    }

    @Test
    public void updateBehaviors()
    {
        Timer timer = mock(Timer.class);
        when(timer.getIdealPhysicsFrame()).thenReturn(1L);
        when(timer.getPhysicsFrame()).thenReturn(0L)
                                     .thenReturn(1L);

        PhysicsPipeline action = new PhysicsPipeline(timer);
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

    @Test
    public void update_twice()
    {
        Timer timer = mock(Timer.class);
        when(timer.getIdealPhysicsFrame()).thenReturn(1L)
                                          .thenReturn(2L);
        when(timer.getPhysicsFrame()).thenReturn(0L)
                                     .thenReturn(1L)
                                     .thenReturn(2L);

        PhysicsPipeline action = new PhysicsPipeline(timer);
        UpdatableAction behavior = new UpdatableAction();
        action.enableBehavior(behavior);

        action.run();
        assertEquals(2, behavior.calls);
    }

    @Test
    public void update_never()
    {
        Timer timer = mock(Timer.class);
        when(timer.getIdealPhysicsFrame()).thenReturn(2L);
        when(timer.getPhysicsFrame()).thenReturn(2L);

        PhysicsPipeline action = new PhysicsPipeline(timer);
        UpdatableAction behavior = new UpdatableAction();
        action.enableBehavior(behavior);

        action.run();
        assertEquals(0, behavior.calls);
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
