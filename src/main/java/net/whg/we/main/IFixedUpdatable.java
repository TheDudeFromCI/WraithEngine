package net.whg.we.main;

/**
 * A fixed updatable class is triggered to update once for each physics update.
 * This allows for complex actions to occur which must occur on an accurate
 * timestamp, such as AI updates, physics updates, player movement, etc.
 */
public interface IFixedUpdatable
{
    /**
     * Called once on each physics update to update this object.
     */
    void fixedUpdate();
}
