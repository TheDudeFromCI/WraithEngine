package net.whg.we.main;

/**
 * A behavior is a component which is attached to a game object to define how it
 * acts and behaves. It contains a set of utility functions which are common for
 * behaviors to implement.
 * <p>
 * A behavior may only be attached to a single game object during it's lifetime.
 */
public abstract class AbstractBehavior
{
    private GameObject gameObject;

    /**
     * Initializes this behavior and links it to a game object.
     * 
     * @param gameObject
     *     - The game object to link this behavior to.
     * @throws IllegalStateException
     *     If this behavior is already attached to another game object.
     */
    void init(GameObject gameObject)
    {
        if (this.gameObject != null)
            throw new IllegalStateException("Behavior already bound to another game object!");

        this.gameObject = gameObject;
    }

    /**
     * Gets the game object this behavior is attached to.
     * 
     * @return The game object, or null if this behavior has not yet been bound to a
     *     game object.
     */
    public GameObject getGameObject()
    {
        return gameObject;
    }
}
