package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameObject;

public class AbstractBehaviorTest
{
    class FakeBehavior extends AbstractBehavior
    {
        int onInitCalled = 0;
        int onDisposeCalled = 0;

        @Override
        protected void onInit()
        {
            onInitCalled++;
        }

        @Override
        protected void onDispose()
        {
            onDisposeCalled++;
        }
    }

    @Test
    public void init_onInitCalled()
    {
        FakeBehavior behavior = new FakeBehavior();

        GameObject go = new GameObject();
        assertEquals(0, behavior.onInitCalled);

        go.addBehavior(behavior);

        assertEquals(1, behavior.onInitCalled);
    }

    @Test(expected = IllegalStateException.class)
    public void init_alreadyInitialized_anotherObject_invalid()
    {
        FakeBehavior behavior = new FakeBehavior();

        GameObject go1 = new GameObject();
        GameObject go2 = new GameObject();

        go1.addBehavior(behavior);
        go2.addBehavior(behavior);
    }

    @Test
    public void init_alreadyInitialized_sameObject_valid()
    {
        FakeBehavior behavior = new FakeBehavior();

        GameObject go = new GameObject();

        go.addBehavior(behavior);
        go.addBehavior(behavior);

        assertEquals(1, behavior.onInitCalled);
    }

    @Test
    public void dispose_onDisposedCalled()
    {
        FakeBehavior behavior = new FakeBehavior();

        GameObject go = new GameObject();

        go.addBehavior(behavior);
        go.removeBehavior(behavior);

        assertEquals(1, behavior.onDisposeCalled);
        assertTrue(behavior.isDisposed());

        behavior.dispose();
        assertEquals(1, behavior.onDisposeCalled);
    }
}
