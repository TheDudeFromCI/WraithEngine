package net.whg.we.rendering.opengl;

/**
 * This bind states object is used to determine what objects are currently bound
 * in the OpenGL state machine. This object can be used to allow for bindings to
 * only occur as needed without worrying about having to send extra API calls to
 * unbind objects. Binds can be called any number of times, where API calls are
 * only sent to the GPU if they are not already bound.
 */
public class BindStates
{
    private final IOpenGL opengl;
    private int boundShader;
    private int boundVao;
    private int boundBuffer;

    /**
     * Creates a new bind states object.
     * 
     * @param opengl
     *     - The OpenGL instance to send bind requests to.
     */
    BindStates(IOpenGL opengl)
    {
        this.opengl = opengl;
    }

    /**
     * Sets the current shader program to be bound.
     * 
     * @param id
     *     - The shader id to bind, or 0 to unbind everything.
     */
    public void bindShader(int id)
    {
        if (boundShader != id)
        {
            boundShader = id;
            opengl.bindShader(id);
        }
    }

    /**
     * Gets the id of the currently bound shader.
     * 
     * @return The bound shader, or 0 if no shader is currently bound.
     */
    public int getBoundShader()
    {
        return boundShader;
    }

    /**
     * Sets the current vertex array to be bound.
     * 
     * @param id
     *     - The vertex array id to bind, or 0 to unbind everything.
     */
    public void bindVao(int id)
    {
        if (boundVao != id)
        {
            boundVao = id;
            opengl.bindVertexArray(id);
        }
    }

    /**
     * Gets the id of the currently bound vertex array.
     * 
     * @return The bound vertex array, or 0 if no vertex array is currently bound.
     */
    public int getBoundVao()
    {
        return boundVao;
    }

    /**
     * Sets the current buffer object to be bound.
     * 
     * @param elementBuffer
     *     - True if the buffer represents an index buffer, false otherwise.
     * @param id
     *     - The buffer object id to bind, or 0 to unbind everything.
     */
    public void bindBuffer(boolean elementBuffer, int id)
    {
        if (boundBuffer != id)
        {
            boundBuffer = id;
            opengl.bindBuffer(elementBuffer, id);
        }
    }

    /**
     * Gets the id of the currently bound buffer object.
     * 
     * @return The bound buffer object, or 0 if no buffer object is currently bound.
     */
    public int getBoundBuffer()
    {
        return boundBuffer;
    }
}
