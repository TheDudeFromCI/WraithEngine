package net.whg.we.rendering;

import net.whg.we.util.IDisposable;

/**
 * A render resource is any resource created or maintained by the rendering
 * engine, to interact with it.
 */
public interface IRenderResource extends IDisposable
{
    /**
     * Checks if this resource has been created or not. A resource is created after
     * the first call to ant "update" mathod is made. Before creation, render
     * resources cannot be used.
     * 
     * @return Whether or not this resource has been created.
     */
    boolean isCreated();
}
