package net.whg.we.rendering;

/**
 * This class contains a set of code which is intended to be sent to the GPU as
 * a shader but has not yet been compiled.
 */
public class RawShaderCode
{
    private final String vertexShader;
    private final String fragmentShader;

    /**
     * Creates a new raw shader code object.
     * 
     * @param vertexShader
     *     - The vertex shader code.
     * @param fragmentShader
     *     - The fragment shader code.
     */
    public RawShaderCode(String vertexShader, String fragmentShader)
    {
        if (vertexShader == null)
            throw new IllegalArgumentException("Vertex Shader cannot be null!");

        if (fragmentShader == null)
            throw new IllegalArgumentException("Fragment Shader cannot be null!");

        this.vertexShader = vertexShader;
        this.fragmentShader = fragmentShader;
    }

    /**
     * Gets the code for the vertex shader.
     * 
     * @return The vertex shader.
     */
    public String getVertexShader()
    {
        return vertexShader;
    }

    /**
     * Gets the code for the fragment shader.
     * 
     * @return The fragment shader.
     */
    public String getFragmentShader()
    {
        return fragmentShader;
    }
}
