package net.whg.we.main;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import net.whg.we.util.IDisposable;
import net.whg.we.main.Transform3D;

/**
 * A game object is a single entity which exists within the game. A game object
 * is comprised of a set of components which are used to define the object's
 * properties and behavior.
 */
public class GameObject implements IDisposable
{
    private static final String OBJECT_DISPOSED = "Object already disposed!";

    private final List<AbstractBehavior> behaviors = new CopyOnWriteArrayList<>();
    private final Transform3D transform = new Transform3D();
    private final UUID uuid;
    private String name = "New GameObject";
    private boolean markedForRemoval;
    private boolean disposed;
    private Scene scene;

    /**
     * Creates a new empty game object with a randomized UUID.
     */
    public GameObject()
    {
        uuid = UUID.randomUUID();
    }

    /**
     * Gets the UUID of this game object.
     * 
     * @return The UUID.
     */
    public UUID getUUID()
    {
        return uuid;
    }

    /**
     * Sets the name of this game object. This is used primarily for reference
     * purposes.
     * 
     * @param name
     *     - The new name to assign to this game object.
     * @throws IllegalArgumentException
     *     If the name parameter is null.
     */
    public void setName(String name)
    {
        if (isDisposed())
            throw new IllegalStateException(OBJECT_DISPOSED);

        if (name == null)
            throw new IllegalArgumentException("Name cannot be null!");

        this.name = name;
    }

    /**
     * Gets the name of this game object.
     * 
     * @return The name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Adds a new behavior to this game object. This method does nothing if the
     * behavior is null or is already attached to this game object.
     * 
     * @param behavior
     *     - The behavior to add.
     */
    public void addBehavior(AbstractBehavior behavior)
    {
        if (isDisposed())
            throw new IllegalStateException(OBJECT_DISPOSED);

        if (behavior == null)
            return;

        if (behaviors.contains(behavior))
            return;

        behavior.init(this);
        behaviors.add(behavior);
    }

    /**
     * Removes a behavior from this game object. This method does nothing if the
     * behavior is null or is not attached to this game object.
     * 
     * @param behavior
     *     - The behavior to remove.
     */
    public void removeBehavior(AbstractBehavior behavior)
    {
        if (isDisposed())
            throw new IllegalStateException(OBJECT_DISPOSED);

        if (behavior == null)
            return;

        behaviors.remove(behavior);
        behavior.dispose();
    }

    /**
     * Gets and returns the behavior of the given type attached to this game object.
     * Subclasses of the given type are also considered as well. If multiple
     * matching behaviors are present, only the first instance is returned.
     * 
     * @param behaviorType
     *     - The type of behavior to get.
     * @return The behavior with the given superclass, or null if no matching
     *     behavior is found.
     */
    public AbstractBehavior getBehavior(Class<? extends AbstractBehavior> behaviorType)
    {
        for (AbstractBehavior behavior : behaviors)
            if (behaviorType.isAssignableFrom(behavior.getClass()))
                return behavior;

        return null;
    }

    /**
     * Gets a list of all behaviors on this game object.
     * 
     * @return A new list containing all behaviors on this game object.
     */
    public List<AbstractBehavior> getBehaviors()
    {
        return new ArrayList<>(behaviors);
    }

    /**
     * Gets a list of all behaviors attached to this game object of the given class
     * type. All subclasses of the given type are returned as well.
     * 
     * @param behaviorType
     *     - The type of behaviors to get.
     * @return A list of behaviors with the given superclass.
     */
    public List<AbstractBehavior> getBehaviors(Class<? extends AbstractBehavior> behaviorType)
    {
        return behaviors.stream()
                        .filter(behavior -> behaviorType.isAssignableFrom(behavior.getClass()))
                        .collect(Collectors.toList());
    }

    /**
     * Marks this game object for removal.
     * <p>
     * When a game object is marked for removal, it is removed from the scene, along
     * with all child game objects, at the end of the frame.
     */
    public void markForRemoval()
    {
        markedForRemoval = true;
    }

    /**
     * Checks if this game object is marked for removal.
     * <p>
     * When a game object is marked for removal, it is removed from the scene, along
     * with all child game objects, at the end of the frame.
     * 
     * @return True if this game object will be removed at the end of the frame.
     *     False otherwise.
     */
    public boolean isMarkedForRemoval()
    {
        return markedForRemoval;
    }

    /**
     * Gets the number of behaviors attached to this game object.
     * 
     * @return The number of behaviors.
     */
    public int getBehaviorCount()
    {
        return behaviors.size();
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;

        for (AbstractBehavior behavior : behaviors)
            behavior.dispose();
        behaviors.clear();

        scene = null;
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    /**
     * Gets the scene this object currently exists within.
     * 
     * @return The scene this object is in, or null if this object is not in a
     *     scene.
     */
    public Scene getScene()
    {
        return scene;
    }

    /**
     * Sets the scene this object is in.
     * 
     * @param scene
     *     - The new scene this object is in.
     */
    void setScene(Scene scene)
    {
        Scene oldScene = this.scene;
        this.scene = scene;

        for (AbstractBehavior behavior : behaviors)
            behavior.onSceneChange(oldScene, scene);
    }

    /**
     * Gets the transformation object used by this game object.
     * 
     * @return This object's transformation.
     */
    public Transform3D getTransform()
    {
        return transform;
    }
}
