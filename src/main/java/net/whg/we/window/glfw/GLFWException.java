package net.whg.we.window.glfw;

/**
 * An exception which is thrown by the GLFW window handler, while attempting to
 * manipulate a window.
 */
public class GLFWException extends RuntimeException
{
    private static final long serialVersionUID = 10293840921L;

    /**
     * Creates a new GLFW Exception with the given error message.
     * 
     * @param message
     *     - The error message.
     */
    public GLFWException(String message)
    {
        super(message);
    }
}
