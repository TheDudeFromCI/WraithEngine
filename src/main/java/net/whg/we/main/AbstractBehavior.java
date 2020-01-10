package net.whg.we.main;

import net.whg.we.util.IDisposable;

/**
 * A behavior is a component which is attached to a game object to define how it
 * acts and behaves. It contains a set of utility functions which are common for
 * behaviors to implement.
 * <p>
 * A behavior may only be attached to a single game object during it's lifetime.
 */
public abstract class AbstractBehavior implements IDisposable
{
    private GameObject gameObject;
    private boolean disposed;

    /**
     * Initializes this behavior and links it to a game object.
     * 
     * @param gameObject
     *     - The game object to link this behavior to.
     * @throws IllegalStateException
     *     If this behavior is already attached to another game object.
     */
    final void init(GameObject gameObject)
    {
        if (this.gameObject != null)
            throw new IllegalStateException("Behavior already bound to another game object!");

        this.gameObject = gameObject;

        onInit();
    }

    /**
     * Gets the game object this behavior is attached to.
     * 
     * @return The game object, or null if this behavior has not yet been bound to a
     *     game object.
     */
    public final GameObject getGameObject()
    {
        return gameObject;
    }

    /**
     * This function is an event which is triggered when this object is first
     * initialized. It can be used to prepare the behavior. The default
     * implementation does nothing, and is desgined to be overriden.
     */
    protected void onInit()
    {}

    @Override
    public final void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        onDispose();
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    /**
     * This event is triggered when a behavior object is destroyed and needs to be
     * cleaned up. This can occur when the behavior is removed from a game object,
     * or the game object is disposed. The default implementation does nothing, and
     * is desgined to be overriden.
     */
    protected void onDispose()
    {}
}
