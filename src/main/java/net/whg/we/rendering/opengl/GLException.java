package net.whg.we.rendering.opengl;

/**
 * A GL Exception represents an exception that occurs when handling OpenGL level
 * operations, such as compiling, binding, or destroying objects.
 */
public class GLException extends RuntimeException
{
    private static final long serialVersionUID = 1213904820398L;

    /**
     * Creates a new GLException with the given message.
     * 
     * @param message
     *     - The message for the exception.
     */
    public GLException(String message)
    {
        super(message);
    }
}
