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
    private final int[] boundTextures = new int[24];
    private int boundShader;
    private int boundVao;
    private int boundBuffer;
    private int activeTextureSlot;

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
     * Sets the current shader program to be bound. Does nothing if the shader is
     * already bound.
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
     * Sets the current vertex array to be bound. Does nothing if the vertex array
     * is already bound.
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
     * Sets the current buffer object to be bound. Does nothing if the buffer object
     * is already bound.
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

    /**
     * Binds a texture to the active texture slot. Does nothing the texture is
     * already bound to the given slot.
     * 
     * @param texture
     *     - The ID of the texture to bind, or 0 to unbind the currently bound
     *     texture.
     */
    public void bindTexture(int texture)
    {
        if (boundTextures[activeTextureSlot] != texture)
        {
            boundTextures[activeTextureSlot] = texture;
            opengl.bindTexture(texture);
        }
    }

    /**
     * Gets the ID of the texture currently bound to the active texture slot.
     * 
     * @return The ID of the currently bound texture, or 0 if no texture is bound.
     */
    public int getBoundTexture()
    {
        return boundTextures[activeTextureSlot];
    }

    /**
     * A short hand method for binding a texture slot then binding a texture to that
     * slot.
     * 
     * @param texture
     *     - The ID of the texture to bind.
     * @param slot
     *     - The slot to bind.
     * @see {@link #bindTextureSlot(int)}
     * @see {@link #bindTexture(int)}
     */
    public void bindTexture(int texture, int slot)
    {
        bindTextureSlot(slot);
        bindTexture(texture);
    }

    /**
     * Gets the ID of the texture currently bound to the given texture slot. This
     * method does not change any active binds.
     * 
     * @param slot
     *     - The texture slot to check.
     * @return The ID of the currently bound texture, or 0 if no texture is bound.
     */
    public int getBoundTexture(int slot)
    {
        return boundTextures[slot];
    }

    /**
     * Binds a texture slot. This is used for manipulating an active texture within
     * that slot. Does nothing the texture slot is already bound to the given slot.
     * 
     * @param slot
     *     - The texture slot to bind.
     */
    public void bindTextureSlot(int slot)
    {
        if (activeTextureSlot != slot)
        {
            activeTextureSlot = slot;
            opengl.bindTextureSlot(slot);
        }
    }

    /**
     * Gets the currently bound texture slot.
     * 
     * @return The bound texture slot.
     */
    public int getBoundTextureSlot()
    {
        return activeTextureSlot;
    }
}
