package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The update pipeline is triggered each frame to prepare a scene to be
 * rendered, or run logic which needs to be executed every frame.
 */
public class UpdatePipeline implements IPipelineAction
{
    private final List<IUpdatable> objects = new CopyOnWriteArrayList<>();
    private final Timer timer;

    /**
     * Creates a new update pipeline object.
     * 
     * @param timer
     *     - The timer associated with this update pipeline.
     */
    public UpdatePipeline(Timer timer)
    {
        this.timer = timer;
    }

    @Override
    public void run()
    {
        for (IUpdatable obj : objects)
            obj.update(timer);
    }

    @Override
    public void enableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IUpdatable)
            objects.add((IUpdatable) behavior);
    }

    @Override
    public void disableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IUpdatable)
            objects.remove((IUpdatable) behavior);
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.FRAME_UPDATES;
    }
}
