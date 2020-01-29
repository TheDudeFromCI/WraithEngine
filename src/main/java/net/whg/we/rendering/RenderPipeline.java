package net.whg.we.rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.whg.we.main.AbstractBehavior;
import net.whg.we.main.IPipelineAction;
import net.whg.we.main.PipelineConstants;

/**
 * The renderer pipeline action is used to render elements within a scene. By
 * default, all behaviors which extend {@link RenderBehavior} are used.
 */
public class RenderPipeline implements IPipelineAction
{
    private final List<RenderBehavior> renderedObjects = new ArrayList<>();
    private final List<RenderBehavior> renderedObjectsReadOnly = Collections.unmodifiableList(renderedObjects);
    private Camera camera;

    /**
     * Creates a new render pipeline action.
     */
    public RenderPipeline()
    {
        this(null);
    }

    /**
     * Creates a new render pipeline action and initializes it with a camera.
     * 
     * @param camera
     *     - The camera to attach to this render pipeline action.
     */
    public RenderPipeline(Camera camera)
    {
        this.camera = camera;
    }

    @Override
    public void run()
    {
        if (camera == null)
            return;

        for (RenderBehavior behavior : renderedObjects)
            behavior.render(camera);
    }

    @Override
    public void enableBehavior(AbstractBehavior behavior)
    {
        if (behavior instanceof RenderBehavior)
            renderedObjects.add((RenderBehavior) behavior);
    }

    @Override
    public void disableBehavior(AbstractBehavior behavior)
    {
        renderedObjects.remove(behavior);
    }

    /**
     * Sets the camera to use when rendering the scene. If null, the scene is not
     * rendered.
     * 
     * @param camera
     *     - The camera to use.
     */
    public void setCamera(Camera camera)
    {
        this.camera = camera;
    }

    /**
     * Gets the camera used when rendering the scene.
     * 
     * @return The camera being used.
     */
    public Camera getCamera()
    {
        return camera;
    }

    /**
     * Gets a read-only list of render behaviors currently maintained by this
     * action.
     * 
     * @return A list of render behaviors.
     */
    public List<RenderBehavior> renderBehaviors()
    {
        return renderedObjectsReadOnly;
    }

    @Override
    public int getPriority()
    {
        return PipelineConstants.RENDER_SOLIDS;
    }
}
