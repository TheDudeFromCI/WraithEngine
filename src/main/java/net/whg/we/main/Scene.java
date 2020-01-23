package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.whg.we.util.IObjectContainer;

/**
 * A scene is used to represent the current state of the world. It is comprised
 * of a collection of game objects which populate that world.
 */
public class Scene implements IObjectContainer<GameObject>
{
    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
    private final List<IPipelineAction> pipelineActions = new CopyOnWriteArrayList<>();

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

        if (gameObject.getScene() != null)
            gameObject.getScene().gameObjects.remove(gameObject);

        gameObjects.add(gameObject);
        gameObject.setScene(this);
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
    }

    @Override
    public int getSize()
    {
        return gameObjects.size();
    }

    @Override
    public GameObject getObjectAt(int index)
    {
        return gameObjects.get(index);
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
    }
}
