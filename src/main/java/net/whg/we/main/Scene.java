package net.whg.we.main;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A scene is used to represent the current state of the world. It is comprised
 * of a collection of game objects which populate that world.
 */
public class Scene
{
    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private final List<IPipelineAction> pipelineActions = new CopyOnWriteArrayList<>();

    private final List<GameObject> gameObjectsReadOnly = Collections.unmodifiableList(gameObjects);
    private final List<IPipelineAction> pipelineActionsReadOnly = Collections.unmodifiableList(pipelineActions);

    private final List<ISceneListener> listeners = new CopyOnWriteArrayList<>();

    /**
     * Adds a new game object to this scene. This method does nothing if the game
     * object is null or is already in this scene.
     * 
     * @param gameObject
     *     - The gameobject to add.
     */
    public void addGameObject(GameObject gameObject)
    {
        if (gameObject == null)
            return;

        if (gameObjects.contains(gameObject))
            return;

        gameObjects.add(gameObject);
        gameObject.setScene(this);

        for (AbstractBehavior behavior : gameObject.getBehaviors())
            triggerEnableBehavior(behavior);

        for (ISceneListener listener : listeners)
            listener.onGameObjectAdded(gameObject);
    }

    /**
     * Removes a game object from this scene. This method does nothing if the game
     * object is null or is not in this scene.
     * 
     * @param gameObject
     *     - The game object to remove.
     */
    public void removeGameObject(GameObject gameObject)
    {
        if (gameObject == null)
            return;

        if (!gameObjects.contains(gameObject))
            return;

        gameObjects.remove(gameObject);
        gameObject.setScene(null);

        for (AbstractBehavior behavior : gameObject.getBehaviors())
            triggerDisableBehavior(behavior);

        for (ISceneListener listener : listeners)
            listener.onGameObjectRemoved(gameObject);
    }

    /**
     * Adds a new pipeline action to this scene. This will also trigger the
     * enableBehavior method for each behavior within this scene. This method does
     * nothing if the action is null or is already in this scene.
     * 
     * @param action
     *     - The action to add.
     */
    public void addPipelineAction(IPipelineAction action)
    {
        if (action == null)
            return;

        if (pipelineActions.contains(action))
            return;

        pipelineActions.add(action);

        for (GameObject go : gameObjects)
            for (AbstractBehavior behavior : go.getBehaviors())
                action.enableBehavior(behavior);

        for (ISceneListener listener : listeners)
            listener.onPipelineAdded(action);
    }

    /**
     * Removes a pipeline action from this scene. This will also trigger the
     * disableBehavior method for each behavior within this scene. The method does
     * nothing if the action is null or is not in this scene.
     * 
     * @param action
     *     - The action to remove.
     */
    public void removePipelineAction(IPipelineAction action)
    {
        if (action == null)
            return;

        if (!pipelineActions.contains(action))
            return;

        pipelineActions.remove(action);

        for (GameObject go : gameObjects)
            for (AbstractBehavior behavior : go.getBehaviors())
                action.disableBehavior(behavior);

        for (ISceneListener listener : listeners)
            listener.onPipelineRemoved(action);
    }

    /**
     * Gets a read-only list of all game objects in this scene.
     * 
     * @return A list of game objects.
     */
    public List<GameObject> gameObjects()
    {
        return gameObjectsReadOnly;
    }

    /**
     * Gets a read-only list of all pipeline actions in this scene.
     * 
     * @return A list of pipeline actions.
     */
    public List<IPipelineAction> pipelineActions()
    {
        return pipelineActionsReadOnly;
    }

    /**
     * When called, will cause all pipelines in this scene to recieve an
     * enableBehavior event.
     * 
     * @param behavior
     *     - The newly enabled behavior.
     */
    void triggerEnableBehavior(AbstractBehavior behavior)
    {
        for (IPipelineAction action : pipelineActions)
            action.enableBehavior(behavior);
    }

    /**
     * When called, will cause all pipelines in this scene to recieve a
     * disableBehavior event.
     * 
     * @param behavior
     *     - The newly disabled behavior.
     */
    void triggerDisableBehavior(AbstractBehavior behavior)
    {
        for (IPipelineAction action : pipelineActions)
            action.disableBehavior(behavior);
    }

    /**
     * Adds a new listener to this scene. This function does nothing if the listener
     * is null or is already attached to this scene.
     * 
     * @param listener
     *     - The listener to add.
     */
    public void addListener(ISceneListener listener)
    {
        if (listener == null)
            return;

        if (listeners.contains(listener))
            return;

        listeners.add(listener);
    }

    /**
     * Removes a listener from this scene. This function does nothing if the
     * listener is null or is not attached to this scene.
     * 
     * @param listener
     *     - The listener to remove.
     */
    public void removeListener(ISceneListener listener)
    {
        if (listener == null)
            return;

        listeners.remove(listener);
    }
}
