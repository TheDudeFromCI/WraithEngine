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
    private final List<GameObject> public_gameObjects = Collections.unmodifiableList(gameObjects);
    private final SceneRenderer renderer = new SceneRenderer();

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

        gameObject.setScene(this);
        if (!gameObjects.contains(gameObject))
            gameObjects.add(gameObject);
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

        gameObject.setScene(null);
        gameObjects.remove(gameObject);
    }

    /**
     * This method iterates over all game objects in the scene and removes all game
     * objects which are currently marked for removal. This is usually called at the
     * end of a frame to remove and clean up game objects.
     */
    public void cullGameObjects()
    {
        for (GameObject gameObject : gameObjects)
            if (gameObject.isMarkedForRemoval())
                removeGameObject(gameObject);
    }

    /**
     * Gets a read-only list of all game objects currently present in this scene.
     * 
     * @return A list of game objects in this scene.
     */
    public List<GameObject> getGameObjects()
    {
        return public_gameObjects;
    }

    /**
     * Gets the object in charge of rendering this scene.
     * 
     * @return The scene renderer.
     */
    public SceneRenderer getRenderer()
    {
        return renderer;
    }
}
