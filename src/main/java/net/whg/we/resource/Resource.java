package net.whg.we.resource;

/**
 * A resource represents a set of information loaded from a file. This is loaded
 * in some way and contains a data object.
 */
public class Resource
{
    private final Object data;

    /**
     * Creates a new resource object.
     * 
     * @param data
     *     - The data this resource represents.
     */
    public Resource(Object data)
    {
        this.data = data;
    }

    /**
     * Gets the data object for this resource.
     * 
     * @return The data.
     */
    public Object getData()
    {
        return data;
    }
}
