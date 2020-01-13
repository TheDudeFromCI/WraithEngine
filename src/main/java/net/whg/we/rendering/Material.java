package net.whg.we.rendering;

import java.nio.FloatBuffer;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import net.whg.we.rendering.Camera;
import net.whg.we.rendering.IShader;

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
        shader.bind();

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
}
