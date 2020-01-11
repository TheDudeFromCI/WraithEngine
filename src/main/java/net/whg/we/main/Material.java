package net.whg.we.main;

import net.whg.we.rendering.IShader;

/**
 * A material is a collection of textures, properties, and a shader which are
 * all used to render a mesh in a specific way to the screen.
 */
public class Material
{
    private final IShader shader;

    /**
     * Creates a new material object.
     * 
     * @param shader
     *     - The shader this material uses to render with.
     * @throws IllegalArgumentException
     *     If the shader is null, or is already disposed.
     */
    public Material(IShader shader)
    {
        if (shader == null)
            throw new IllegalArgumentException("Shader cannot be null!");

        if (shader.isDisposed())
            throw new IllegalArgumentException("Shader already disposed!");

        this.shader = shader;
    }

    /**
     * Gets the shader that is attached to this material.
     * 
     * @return The shader.
     */
    public IShader getShader()
    {
        return shader;
    }

    /**
     * When called, all shaders and textures attached to this material are instantly
     * bound, and all properties for this material are applied to the shader to be
     * used.
     */
    public void bind()
    {
        shader.bind();
    }
}
