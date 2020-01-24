package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Test;
import net.whg.we.main.IPipelineAction;
import net.whg.we.main.Scene;
import net.whg.we.main.SceneGameLoop;

public class SceneGameLoopTest
{
    @Test
    public void addRemoveScene()
    {
        SceneGameLoop gameLoop = new SceneGameLoop();
        assertTrue(gameLoop.scenes()
                           .isEmpty());

        Scene scene = mock(Scene.class);
        gameLoop.addScene(scene);
        gameLoop.addScene(scene);
        gameLoop.addScene(null);

        verify(scene, times(1)).addListener(any());

        assertEquals(1, gameLoop.scenes()
                                .size());

        gameLoop.removeScene(scene);
        gameLoop.removeScene(scene);
        gameLoop.removeScene(null);

        verify(scene, times(1)).removeListener(any());

        assertTrue(gameLoop.scenes()
                           .isEmpty());
    }

    @Test
    public void sceneModified_isPipelineAdded()
    {
        Scene scene = new Scene();
        SceneGameLoop gameLoop = new SceneGameLoop();
        gameLoop.addScene(scene);

        IPipelineAction action = mock(IPipelineAction.class);
        scene.addPipelineAction(action);
        assertEquals(1, gameLoop.loopActions()
                                .size());

        scene.removePipelineAction(action);
        assertEquals(0, gameLoop.loopActions()
                                .size());
    }

    @Test
    public void sceneAdded_addExistingActions()
    {
        IPipelineAction action1 = mock(IPipelineAction.class);
        IPipelineAction action2 = mock(IPipelineAction.class);
        IPipelineAction action3 = mock(IPipelineAction.class);

        Scene scene = new Scene();
        scene.addPipelineAction(action1);
        scene.addPipelineAction(action2);
        scene.addPipelineAction(action3);

        SceneGameLoop gameLoop = new SceneGameLoop();
        gameLoop.addScene(scene);

        assertTrue(gameLoop.loopActions()
                           .contains(action1));
        assertTrue(gameLoop.loopActions()
                           .contains(action2));
        assertTrue(gameLoop.loopActions()
                           .contains(action3));
    }

    @Test
    public void sceneRemoved_removeExistingActions()
    {
        Scene scene = new Scene();
        SceneGameLoop gameLoop = new SceneGameLoop();
        gameLoop.addScene(scene);

        IPipelineAction action1 = mock(IPipelineAction.class);
        IPipelineAction action2 = mock(IPipelineAction.class);
        IPipelineAction action3 = mock(IPipelineAction.class);
        scene.addPipelineAction(action1);
        scene.addPipelineAction(action2);
        scene.addPipelineAction(action3);

        gameLoop.removeScene(scene);

        assertFalse(gameLoop.loopActions()
                            .contains(action1));
        assertFalse(gameLoop.loopActions()
                            .contains(action2));
        assertFalse(gameLoop.loopActions()
                            .contains(action3));
    }
}
