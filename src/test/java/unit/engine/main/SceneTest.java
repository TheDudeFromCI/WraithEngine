package unit.engine.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.GameObject;
import net.whg.we.main.IPipelineAction;
import net.whg.we.main.ISceneListener;
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

        verify(action, never()).disableBehavior(b1);
        verify(action, never()).disableBehavior(b2);
        verify(action, never()).disableBehavior(b3);
        verify(action, never()).disableBehavior(b4);
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

    @Test
    public void addBehavior_existingGameObject()
    {
        GameObject go = new GameObject();
        Scene scene = new Scene();
        scene.addGameObject(go);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        AbstractBehavior b = mock(AbstractBehavior.class);
        go.addBehavior(b);

        verify(action).enableBehavior(b);
        verify(action, never()).disableBehavior(b);
    }

    @Test
    public void removeBehavior_existingGameObject()
    {
        GameObject go = new GameObject();
        Scene scene = new Scene();
        scene.addGameObject(go);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        AbstractBehavior b = mock(AbstractBehavior.class);
        go.addBehavior(b);

        go.removeBehavior(b);

        verify(action).disableBehavior(b);
    }

    @Test
    public void changeScene_transferPipelineBehaviors()
    {
        Scene scene1 = new Scene();
        Scene scene2 = new Scene();

        GameObject go = new GameObject();
        scene1.addGameObject(go);

        AbstractBehavior behavior = mock(AbstractBehavior.class);
        go.addBehavior(behavior);

        IPipelineAction action1 = mock(IPipelineAction.class);
        scene1.addPipelineAction(action1);

        IPipelineAction action2 = mock(IPipelineAction.class);
        scene2.addPipelineAction(action2);

        reset(behavior);
        reset(action1);
        reset(action2);

        scene2.addGameObject(go);

        assertFalse(scene1.gameObjects()
                          .contains(go));
        assertTrue(scene2.gameObjects()
                         .contains(go));

        assertEquals(scene2, go.getScene());

        verify(action1).disableBehavior(behavior);
        verify(action2).enableBehavior(behavior);

        verify(action1, never()).enableBehavior(behavior);
        verify(action2, never()).disableBehavior(behavior);
    }

    @Test
    public void disposeGameObject_removedBehaviors()
    {
        GameObject go = new GameObject();
        Scene scene = new Scene();
        scene.addGameObject(go);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        AbstractBehavior b = mock(AbstractBehavior.class);
        go.addBehavior(b);

        go.dispose();

        verify(action).disableBehavior(b);
    }

    @Test
    public void listener_gameObjectAddedRemoved()
    {
        Scene scene = new Scene();
        ISceneListener listener = mock(ISceneListener.class);
        scene.addListener(listener);

        GameObject go = new GameObject();
        scene.addGameObject(go);
        verify(listener).onGameObjectAdded(go);

        scene.removeGameObject(go);
        verify(listener).onGameObjectRemoved(go);
    }

    @Test
    public void listener_pipelineAddedRemoved()
    {
        Scene scene = new Scene();
        ISceneListener listener = mock(ISceneListener.class);
        scene.addListener(listener);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);
        verify(listener).onPipelineAdded(action);

        scene.removePipelineAction(action);
        verify(listener).onPipelineRemoved(action);
    }

    @Test
    public void gameObjectAddedRemoved_callsPipelineEvent()
    {
        IPipelineAction action = mock(IPipelineAction.class);
        Scene scene = new Scene();
        scene.addPipelineAction(action);

        GameObject go = new GameObject();
        scene.addGameObject(go);
        verify(action).enableGameObject(go);

        scene.removeGameObject(go);
        verify(action).disableGameObject(go);
    }

    @Test
    public void pipelineAdded_enableAllGameObjects()
    {
        Scene scene = new Scene();
        GameObject go1 = new GameObject();
        GameObject go2 = new GameObject();
        GameObject go3 = new GameObject();
        scene.addGameObject(go1);
        scene.addGameObject(go2);
        scene.addGameObject(go3);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        verify(action).enableGameObject(go1);
        verify(action).enableGameObject(go2);
        verify(action).enableGameObject(go3);
    }

    @Test
    public void pipelineRemoved_disableAllGameObjects()
    {
        Scene scene = new Scene();

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);

        GameObject go1 = new GameObject();
        GameObject go2 = new GameObject();
        GameObject go3 = new GameObject();
        scene.addGameObject(go1);
        scene.addGameObject(go2);
        scene.addGameObject(go3);

        scene.removePipelineAction(action);

        verify(action).disableGameObject(go1);
        verify(action).disableGameObject(go2);
        verify(action).disableGameObject(go3);
    }
}
