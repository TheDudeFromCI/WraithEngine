package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameObject;
import net.whg.we.main.IPipelineAction;
import net.whg.we.main.Scene;

public class SceneTest
{
    @Test
    public void addGameObjects()
    {
        Scene scene = new Scene();
        assertTrue(scene.gameObjects()
                        .isEmpty());

        scene.addGameObject(new GameObject());
        scene.addGameObject(new GameObject());
        scene.addGameObject(new GameObject());
        assertEquals(3, scene.gameObjects()
                             .size());
    }

    @Test
    public void addGameObject_Twice_null()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.addGameObject(go);
        scene.addGameObject(null);

        assertEquals(1, scene.gameObjects()
                             .size());
        assertTrue(scene.gameObjects()
                        .contains(go));
    }

    @Test
    public void removeGameObject()
    {
        Scene scene = new Scene();
        GameObject go = new GameObject();

        scene.addGameObject(go);
        scene.removeGameObject(go);

        assertTrue(scene.gameObjects()
                        .isEmpty());
    }

    @Test
    public void addPipelineAction()
    {
        Scene scene = new Scene();
        assertTrue(scene.pipelineActions()
                        .isEmpty());

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);
        scene.addPipelineAction(action);
        scene.addPipelineAction(null);

        assertEquals(1, scene.pipelineActions()
                             .size());
    }

    @Test
    public void removePipelineAction()
    {
        Scene scene = new Scene();
        IPipelineAction action = mock(IPipelineAction.class);

        scene.addPipelineAction(action);
        scene.removePipelineAction(action);

        assertTrue(scene.pipelineActions()
                        .isEmpty());
    }

    @Test
    public void addPipelineAction_enablesBehavior()
    {
        AbstractBehavior b1 = mock(AbstractBehavior.class);
        AbstractBehavior b2 = mock(AbstractBehavior.class);
        AbstractBehavior b3 = mock(AbstractBehavior.class);
        AbstractBehavior b4 = mock(AbstractBehavior.class);

        GameObject go1 = new GameObject();
        go1.addBehavior(b1);
        go1.addBehavior(b2);

        GameObject go2 = new GameObject();
        go2.addBehavior(b3);
        go2.addBehavior(b4);

        Scene scene = new Scene();
        scene.addGameObject(go1);
        scene.addGameObject(go2);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        verify(action).enableBehavior(b1);
        verify(action).enableBehavior(b2);
        verify(action).enableBehavior(b3);
        verify(action).enableBehavior(b4);
    }

    @Test
    public void addPipelineAction_enablesBehavior_behaviorAddedAfter()
    {
        AbstractBehavior b1 = mock(AbstractBehavior.class);
        AbstractBehavior b2 = mock(AbstractBehavior.class);
        AbstractBehavior b3 = mock(AbstractBehavior.class);
        AbstractBehavior b4 = mock(AbstractBehavior.class);

        GameObject go1 = new GameObject();
        go1.addBehavior(b1);
        go1.addBehavior(b2);

        GameObject go2 = new GameObject();
        go2.addBehavior(b3);
        go2.addBehavior(b4);

        Scene scene = new Scene();

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        scene.addGameObject(go1);
        scene.addGameObject(go2);

        verify(action).enableBehavior(b1);
        verify(action).enableBehavior(b2);
        verify(action).enableBehavior(b3);
        verify(action).enableBehavior(b4);
    }

    @Test
    public void removeGameObject_disableBehavior()
    {
        AbstractBehavior b1 = mock(AbstractBehavior.class);
        AbstractBehavior b2 = mock(AbstractBehavior.class);

        GameObject go1 = new GameObject();
        go1.addBehavior(b1);
        go1.addBehavior(b2);

        Scene scene = new Scene();
        scene.addGameObject(go1);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        scene.removeGameObject(go1);

        verify(action).disableBehavior(b1);
        verify(action).disableBehavior(b2);
    }

    @Test
    public void removePipelineAction_disableBehavior()
    {
        AbstractBehavior b1 = mock(AbstractBehavior.class);
        AbstractBehavior b2 = mock(AbstractBehavior.class);
        AbstractBehavior b3 = mock(AbstractBehavior.class);
        AbstractBehavior b4 = mock(AbstractBehavior.class);

        GameObject go1 = new GameObject();
        go1.addBehavior(b1);
        go1.addBehavior(b2);

        GameObject go2 = new GameObject();
        go2.addBehavior(b3);
        go2.addBehavior(b4);

        Scene scene = new Scene();

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        scene.addGameObject(go1);
        scene.addGameObject(go2);

        scene.removePipelineAction(action);

        verify(action).disableBehavior(b1);
        verify(action).disableBehavior(b2);
        verify(action).disableBehavior(b3);
        verify(action).disableBehavior(b4);
    }
}
