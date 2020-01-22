package net.whg.we.rendering;

import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

/**
 * A material is a collection of textures, properties, and a shader which are
 * all used to render a mesh in a specific way to the screen.
 */
public class Material
{
    /**
     * The shader uniform for the MVP matrix.
     */
    public static final String UNIFORM_MVP = "mvp";

    private final IShader shader;
    private ITexture[] textures = new ITexture[0];
    private String[] textureUniformNames = new String[0];

    // Temp
    private final FloatBuffer matrixFloatBuffer;
    private final Matrix4f projectionMatrix = new Matrix4f();
    private final Matrix4f viewMatrix = new Matrix4f();
    private final Matrix4f mvpMatrix = new Matrix4f();

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
        matrixFloatBuffer = BufferUtils.createFloatBuffer(16);
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

        for (int i = 0; i < textures.length; i++)
        {
            textures[i].bind(i);
            shader.setUniformInt(textureUniformNames[i], i);
        }
    }

    /**
     * Assigns the model-view-projection matrix to the shader to render the next
     * object with. Binds the shader if not already bound.
     * 
     * @param camera
     *     - The camera which is rendering the scene.
     * @param matrix
     *     - The full transformation matrix of the object being rendered.
     */
    public void setCameraMatrix(Camera camera, Matrix4f matrix)
    {
        projectionMatrix.set(camera.getProjectionMatrix());

        camera.getTransform()
              .getFullMatrix(viewMatrix);
        viewMatrix.invert();

        mvpMatrix.set(projectionMatrix);
        mvpMatrix.mul(viewMatrix);
        mvpMatrix.mul(matrix);
        mvpMatrix.get(matrixFloatBuffer);
        shader.setUniformMat4(Material.UNIFORM_MVP, matrixFloatBuffer);
    }

    /**
     * Sets the array of textures to use for this material. To remove all textures
     * from a material, this value should be assigned an empty array. An array of
     * uniform names must also be applied to bind the textures to the correct
     * uniforms within the shader.
     * 
     * @param textures
     *     - An array of textures, with each texture's index within the array
     *     corresponding to the texture slot it should be bound to.
     * @param textureUniformNames
     *     - An array of uniform names which correspond to each texture with the
     *     same index. These are used to bind the textures to the shader.
     * @throws IllegalArgumentException
     *     If the length textures is greater than 24 elements.
     * @throws IllegalArgumentException
     *     If the length textures is not equal to the length of textureUniformNames.
     */
    public void setTextures(ITexture[] textures, String[] textureUniformNames)
    {
        if (textures.length > 24)
            throw new IllegalArgumentException("A material may contain no more than 24 textures!");

        if (textureUniformNames.length != textures.length)
            throw new IllegalArgumentException("Texture uniform names must be the same length as the textures array!");

        this.textures = textures;
        this.textureUniformNames = textureUniformNames;
    }

    /**
     * Gets the array of textures used by this material. Each texture's index within
     * the array represents which texture slot it is bound to when this material is
     * bound.
     * 
     * @return An array of textures used by this material.
     */
    public ITexture[] getTextures()
    {
        return textures;
    }

    /**
     * Gets the array of texture uniform names used by this material. Each uniform
     * name within the array is the name of the uniform which uses the texture at
     * the same index within this material at the uniform name.
     * 
     * @return An array of uniform names for each texture.
     */
    public String[] getTextureUniformNames()
    {
        return textureUniformNames;
    }
}
