package net.whg.we.main;

/**
 * An updatable class is any class which listens for an event which is triggered
 * each time a frame is rendered. This is used to prepare the objects in a scene
 * for rendering, such as animations or camera movements which occur each frame.
 */
public interface IUpdatable
{
    /**
     * Called once each frame to update this object.
     * 
     * @param timer
     *     - The timer associated with the update pipeline. Used to retrieve delta
     *     times.
     */
    void update(Timer timer);
}
