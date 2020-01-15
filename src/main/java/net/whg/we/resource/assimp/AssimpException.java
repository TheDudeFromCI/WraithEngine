package net.whg.we.resource.assimp;

/**
 * An exception which is thrown while working with the Assimp library.
 */
public class AssimpException extends RuntimeException
{
    private static final long serialVersionUID = 1025315563896250360L;

    /**
     * Creates a new Assimp Exception.
     * 
     * @param message
     *     - The message for this exception.
     */
    public AssimpException(String message)
    {
        super(message);
    }
}
