package net.whg.we.main;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The scene game loop is an auto-managed game loop which handles adding and
 * removing pipeline actions to the game loop through the act of adding and
 * removing scenes. Multiple scenes can be handled simultaneously.
 */
public class SceneGameLoop extends GameLoop
{
    /**
     * A simple listener object which listens for when pipeline actions are added or
     * removed from a scene, and adds or removed the action from the game loop
     * accordingly.
     */
    private class ScenePipelineListener implements ISceneListener
    {
        @Override
        public void onGameObjectAdded(GameObject go)
        {
            // Do nothing
        }

        @Override
        public void onGameObjectRemoved(GameObject go)
        {
            // Do nothing
        }

        @Override
        public void onPipelineAdded(IPipelineAction action)
        {
            addAction(action);
        }

        @Override
        public void onPipelineRemoved(IPipelineAction action)
        {
            removeAction(action);
        }
    }

    private final List<Scene> scenes = new CopyOnWriteArrayList<>();
    private final ScenePipelineListener listener = new ScenePipelineListener();

    private final List<Scene> scenesReadOnly = Collections.unmodifiableList(scenes);

    /**
     * Adds a new scene to be managed by this game loop. This function does nothing
     * if the scene is null or is already managed by this game loop.
     * 
     * @param scene
     *     - The scene to add.
     */
    public void addScene(Scene scene)
    {
        if (scene == null)
            return;

        if (scenes.contains(scene))
            return;

        scenes.add(scene);
        scene.addListener(listener);

        for (IPipelineAction action : scene.pipelineActions())
            addAction(action);
    }

    /**
     * Removes a scene from this game loop. This function does nothing if the scene
     * is null or is not managed by this game loop.
     * 
     * @param scene
     *     - The scene to remove.
     */
    public void removeScene(Scene scene)
    {
        if (scene == null)
            return;

        if (!scenes.contains(scene))
            return;

        scenes.remove(scene);
        scene.removeListener(listener);

        for (IPipelineAction action : scene.pipelineActions())
            removeAction(action);
    }

    /**
     * Gets a read-only list of all the scenes currently being managed by this game
     * loop.
     * 
     * @return A list of scenes.
     */
    public List<Scene> scenes()
    {
        return scenesReadOnly;
    }
}
