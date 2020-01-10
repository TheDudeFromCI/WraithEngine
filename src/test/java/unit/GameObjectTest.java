package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameObject;

public class GameObjectTest
{
    public class FakeBehaviorA extends AbstractBehavior
    {}

    public class FakeBehaviorB extends AbstractBehavior
    {}

    @Test
    public void randomUUID()
    {
        GameObject go1 = new GameObject();
        GameObject go2 = new GameObject();

        assertNotEquals(go1, go2);
    }

    @Test
    public void setName()
    {
        GameObject go = new GameObject();
        assertEquals("New GameObject", go.getName());

        go.setName("Apple");
        assertEquals("Apple", go.getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setName_null()
    {
        new GameObject().setName(null);
    }

    @Test
    public void markForRemoval()
    {
        GameObject go = new GameObject();
        assertFalse(go.isMarkedForRemoval());

        go.markForRemoval();
        assertTrue(go.isMarkedForRemoval());
    }

    @Test
    public void addBehavior()
    {
        AbstractBehavior behavior = new FakeBehaviorA();

        GameObject go = new GameObject();
        go.addBehavior(behavior);

        assertEquals(behavior, go.getBehavior(FakeBehaviorA.class));
        assertEquals(go, behavior.getGameObject());
    }

    @Test
    public void addBehavior_TwoBehaviors()
    {
        AbstractBehavior behavior1 = new FakeBehaviorA();
        AbstractBehavior behavior2 = new FakeBehaviorA();

        GameObject go = new GameObject();
        go.addBehavior(behavior1);
        go.addBehavior(behavior2);

        assertEquals(behavior1, go.getBehavior(FakeBehaviorA.class));
    }

    @Test
    public void getBehaviorList()
    {
        AbstractBehavior behavior1 = new FakeBehaviorA();
        AbstractBehavior behavior2 = new FakeBehaviorB();

        GameObject go = new GameObject();
        go.addBehavior(behavior1);
        go.addBehavior(behavior2);

        List<AbstractBehavior> list = go.getBehaviors(AbstractBehavior.class);
        assertEquals(2, list.size());
        assertTrue(list.contains(behavior1));
        assertTrue(list.contains(behavior2));
    }

    @Test
    public void removeBehavior()
    {
        AbstractBehavior behavior = new FakeBehaviorA();

        GameObject go = new GameObject();
        go.addBehavior(behavior);
        go.removeBehavior(behavior);

        assertEquals(0, go.getBehaviorCount());
    }
}
