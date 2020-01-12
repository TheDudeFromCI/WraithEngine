package net.whg.we.rendering.opengl;

import static org.lwjgl.opengl.GL30.*;

/**
 * This bind states object is used to determine what objects are currently bound
 * in the OpenGL state machine. This object can be used to allow for bindings to
 * only occur as needed without worrying about having to send extra API calls to
 * unbind objects. Binds can be called any number of times, where API calls are
 * only sent to the GPU if they are not already bound.
 */
public class BindStates
{
    private int boundShader;

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
            glUseProgram(id);
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
}
