package net.whg.we.main;

import java.util.ArrayList;
import java.util.List;

/**
 * The scene renderer is an object in charge of rendering a scene. This works by
 * keeping a list of all render behaviors active in a scene, and each frame,
 * triggering these to render.
 */
public class SceneRenderer
{
    private final List<RenderBehavior> renderedObjects = new ArrayList<>();

    /**
     * Adds a render behavior to this scene to be rendered. This method does nothing
     * if the behavior is null or is already in this scene renderer.
     * 
     * @param behavior
     *     - The behavior to add.
     */
    void addRenderedObject(RenderBehavior behavior)
    {
        if (behavior == null)
            return;

        if (!renderedObjects.contains(behavior))
            renderedObjects.add(behavior);
    }

    /**
     * Removes a render behavior from this scene. This method does nothing if the
     * behavior is null or is not in this scene renderer.
     * 
     * @param behavior
     *     - The behavior to remove.
     */
    void removeRenderedObject(RenderBehavior behavior)
    {
        if (behavior == null)
            return;

        renderedObjects.remove(behavior);
    }

    /**
     * When called, this method triggers all render behaviors in this scene renderer
     * to be rendered.
     */
    public void render()
    {
        for (RenderBehavior behavior : renderedObjects)
            behavior.render();
    }
}
