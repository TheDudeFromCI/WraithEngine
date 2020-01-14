package net.whg.we.main;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A scene is used to represent the current state of the world. It is comprised
 * of a collection of game objects which populate that world.
 */
public class Scene implements Iterable<GameObject>
{
    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();
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

        gameObjects.remove(gameObject);
        gameObject.setScene(null);
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
     * Gets the object in charge of rendering this scene.
     * 
     * @return The scene renderer.
     */
    public SceneRenderer getRenderer()
    {
        return renderer;
    }

    /**
     * Gets the number of game objects in this scene.
     * 
     * @return The number of game objects.
     */
    public int countGameObjects()
    {
        return gameObjects.size();
    }

    @Override
    public Iterator<GameObject> iterator()
    {
        return gameObjects.iterator();
    }

    /**
     * Checks if this scene contains the given game object.
     * 
     * @return True if the given game object is in this scene. False otherwise.
     */
    public boolean hasGameObject(GameObject gameObject)
    {
        return gameObjects.contains(gameObject);
    }
}
