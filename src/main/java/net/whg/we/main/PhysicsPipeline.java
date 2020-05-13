package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The physics pipeline action is an action in charge of triggering physics
 * updates each frame based on the physics framerate.
 */
public class PhysicsPipeline implements IPipelineAction
{
    private final List<IFixedUpdatable> objects = new CopyOnWriteArrayList<>();
    private final Timer timer;
    private final float framerate;
    private long physicsFrame;

    /**
     * Creates a new Physics pipeline action.
     * 
     * @param timer
     *     - The timer to being this action to.
     * @param framerate
     *     - The number of physics frames per second.
     */
    public PhysicsPipeline(Timer timer, float framerate)
    {
        if (framerate < 0f)
            throw new IllegalArgumentException("Physics framerate cannot be negative!");

        this.timer = timer;
        this.framerate = framerate;
    }

    @Override
    public void run()
    {
        long idealFrame = (long) (timer.getElapsedTime() * framerate) + 1;
        while (physicsFrame < idealFrame)
        {
            physicsFrame++;

            for (IFixedUpdatable obj : objects)
                obj.fixedUpdate();
        }
    }

    @Override
    public void enableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IFixedUpdatable)
            objects.add((IFixedUpdatable) behavior);
    }

    @Override
    public void disableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IFixedUpdatable)
            objects.remove((IFixedUpdatable) behavior);
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.PHYSICS_UPDATES;
    }

    /**
     * Gets the number of physics frames per second.
     * 
     * @return The number of physics frames which should be run each second.
     */
    public float getFramerate()
    {
        return framerate;
    }
}
