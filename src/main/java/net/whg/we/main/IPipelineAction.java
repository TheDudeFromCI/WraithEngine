package net.whg.we.main;

/**
 * A pipeline action is a large factory object which is used to control how
 * objects within a scene should behave. These actions usually control groups of
 * objects which behave in a specific way within the scene.
 * <p>
 * Within a pipeline action, objects are selected based solely on attached
 * behavors, where a single behavior may be in multiple actions at once, and a
 * game object may have behaviors which interact with different actions.
 */
public interface IPipelineAction extends ILoopAction
{
    /**
     * This event is called each time a new behavior is added to the scene in some
     * way. This could be triggered by a game object being initialized, or by a new
     * behavior being attached to an existing game object. This event is also
     * triggered when a game object switches to the scene this pipeline action
     * exists within.
     * 
     * @param behavior
     *     - The behavior.
     */
    void enableBehavior(AbstractBehavior behavior);

    /**
     * This event is called each time a behavior is removed from the scene in some
     * way. This could be triggered by a game object being destroyed, or by a
     * behavior being removed to an existing game object. This event is also
     * triggered when a game object switches from the scene this pipeline action
     * exists within.
     * 
     * @param behavior
     *     - The behavior.
     */
    void disableBehavior(AbstractBehavior behavior);
}
