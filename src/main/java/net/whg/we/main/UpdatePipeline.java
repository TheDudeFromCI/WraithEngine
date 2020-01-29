package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The update pipeline is triggered each frame to prepare a scene to be
 * rendered, or run logic which needs to be executed every frame.
 */
public class UpdatePipeline implements IPipelineAction
{
    private final List<IUpdateable> objects = new CopyOnWriteArrayList<>();
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
        for (IUpdateable obj : objects)
            obj.update(timer);
    }

    @Override
    public void enableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IUpdateable)
            objects.add((IUpdateable) behavior);
    }

    @Override
    public void disableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof IUpdateable)
            objects.remove((IUpdateable) behavior);
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.FRAME_UPDATES;
    }
}
