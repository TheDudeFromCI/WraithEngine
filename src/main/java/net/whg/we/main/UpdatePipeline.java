package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UpdatePipeline implements IPipelineAction
{
    private final List<IUpdateable> objects = new CopyOnWriteArrayList<>();

    @Override
    public void run()
    {
        for (IUpdateable obj : objects)
            obj.update();
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
