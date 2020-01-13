package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.List;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameObject;
import net.whg.we.main.Scene;

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

        assertNotEquals(go1.getUUID(), go2.getUUID());
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
        go.addBehavior(behavior);
        go.addBehavior(null);

        assertEquals(1, go.getBehaviorCount());
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

    @Test(expected = IllegalStateException.class)
    public void addBehavior_alreadyDisposed()
    {
        GameObject go = new GameObject();
        go.dispose();

        go.addBehavior(new FakeBehaviorA());
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
    public void getBehavior_longList()
    {
        AbstractBehavior a1 = new FakeBehaviorA();
        AbstractBehavior a2 = new FakeBehaviorA();
        AbstractBehavior a3 = new FakeBehaviorA();
        AbstractBehavior a4 = new FakeBehaviorA();
        AbstractBehavior b = new FakeBehaviorB();

        GameObject go = new GameObject();
        go.addBehavior(a1);
        go.addBehavior(a2);
        go.addBehavior(a3);
        go.addBehavior(b);
        go.addBehavior(a4);

        assertEquals(b, go.getBehavior(FakeBehaviorB.class));
    }

    @Test
    public void getBehavior_notAdded()
    {
        GameObject go = new GameObject();
        assertNull(go.getBehavior(FakeBehaviorA.class));
    }

    @Test
    public void removeBehavior()
    {
        AbstractBehavior behavior = new FakeBehaviorA();

        GameObject go = new GameObject();
        go.addBehavior(behavior);
        go.removeBehavior(behavior);

        go.removeBehavior(behavior);
        go.removeBehavior(null);

        assertEquals(0, go.getBehaviorCount());
    }

    @Test(expected = IllegalStateException.class)
    public void removeBehavior_alreadyDisposed()
    {
        AbstractBehavior behavior = new FakeBehaviorA();
        GameObject go = new GameObject();
        go.addBehavior(behavior);
        go.dispose();

        go.removeBehavior(behavior);
    }

    @Test(expected = IllegalStateException.class)
    public void setName_AlreadyDisposed()
    {
        GameObject go = new GameObject();
        go.dispose();

        go.setName("Steve");
    }

    @Test
    public void dispose_twice()
    {
        int[] calls = new int[1];
        AbstractBehavior behavior = new AbstractBehavior()
        {
            @Override
            protected void onDispose()
            {
                calls[0]++;
            }
        };

        GameObject go = new GameObject();
        go.addBehavior(behavior);

        go.dispose();
        go.dispose();

        assertEquals(1, calls[0]);
    }

    @Test
    public void getTransform()
    {
        assertNotNull(new GameObject().getTransform());
    }

    @Test
    public void setScene()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        assertNull(go.getScene());

        scene.addGameObject(go);

        assertEquals(scene, go.getScene());

        scene.removeGameObject(go);

        assertNull(go.getScene());
    }

    @Test
    public void setScene_sceneChangeEventCalled()
    {
        int[] calls = new int[1];

        AbstractBehavior behavior = new AbstractBehavior()
        {
            @Override
            protected void onSceneChange(Scene oldScene, Scene newScene)
            {
                calls[0]++;
            }
        };

        Scene scene = new Scene();
        GameObject go = new GameObject();
        go.addBehavior(behavior);
        scene.addGameObject(go);
        scene.removeGameObject(go);

        assertEquals(2, calls[0]);
    }
}
