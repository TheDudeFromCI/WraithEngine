package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class PhysicsPipelineAction implements IPipelineAction
{
    private final List<IFixedUpdateable> objects = new CopyOnWriteArrayList<>();

    @Override
    public void run()
    {
        for (IFixedUpdateable obj : objects)
            obj.fixedUpdate();
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
