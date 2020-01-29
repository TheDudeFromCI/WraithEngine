package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The physics pipeline action is an action in charge of triggering physics
 * updates each frame based on the physics framerate.
 */
public class PhysicsPipeline implements IPipelineAction
{
    private final List<IFixedUpdateable> objects = new CopyOnWriteArrayList<>();
    private final Timer timer;

    /**
     * Creates a new Physics pipeline action.
     * 
     * @param timer
     *     - The timer to being this action to.
     */
    public PhysicsPipeline(Timer timer)
    {
        this.timer = timer;
    }

    @Override
    public void run()
    {
        while (timer.getPhysicsFrame() < timer.getIdealPhysicsFrame())
        {
            timer.incrementPhysicsFrame();

            for (IFixedUpdateable obj : objects)
                obj.fixedUpdate();
        }
    }

    @Override
    public void enableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IFixedUpdateable)
            objects.add((IFixedUpdateable) behavior);
    }

    @Override
    public void disableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IFixedUpdateable)
            objects.remove((IFixedUpdateable) behavior);
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.PHYSICS_UPDATES;
    }
}
