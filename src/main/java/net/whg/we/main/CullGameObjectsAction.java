package net.whg.we.main;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CullGameObjectsAction implements IPipelineAction
{
    private final List<GameObject> gameObjects = new CopyOnWriteArrayList<>();

    @Override
    public void run()
    {
        for (GameObject go : gameObjects)
            if (go.isMarkedForRemoval())
                go.dispose();
    }

    @Override
    public void enableGameObject(GameObject gameObject)
    {
        gameObjects.add(gameObject);
    }

    @Override
    public void disableGameObject(GameObject gameObject)
    {
        gameObjects.remove(gameObject);
    }

    @Override
    public int getPriority()
    {
        return 40000;
    }
}
