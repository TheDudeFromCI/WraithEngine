package net.whg.we.rendering;

import java.nio.FloatBuffer;

/**
 * A shader is used to determine how an object should be rendered to the screen.
 * It contains driver-specific code to be executed on the graphics card to
 * manipulate vertices into a renderable form. Shaders are also used to apply
 * effects to objects such as textures and lighting.
 */
public interface IShader extends IRenderResource
{
    /**
     * When called, this shader is bound to the graphics card, if not already bound,
     * so that future mesh renders will use this shader to render with.
     */
    void bind();

    /**
     * Used to compile or re-compile this shader with a new shader program. As a
     * shader program is comprised of a vertex shader and a fragment shader,
     * providing these two components to the shader can be used to compile the
     * shader with. Additional shaders may be added to the pipeline if the
     * underlaying rendering engine supports it.
     * <p>
     * If a shader attributes object is defined, the shader will bind the new layout
     * when creating the shader. This will allow shaders to be initialized to read
     * vertex attribute data in the intended manner. If some attributes are
     * specified in the shader but not in the new layout, or attributes are defined
     * in the layout but not in the shader, unintended behaviors may occur when
     * rendering. If a shader directly requests attributes to be defined with a
     * given layout, that layout value is prioritized over the layout.
     * 
     * @param shaderCode
     *     - The shader code to send to the rendering engine, to compile.
     * @param shaderAttributes
     *     - The new shader attributes layout this shader should use, or null if the
     *     default shader attributes should be layed out automatically.
     * @throws IllegalArgumentException
     *     If shader code object is null.
     */
    void update(RawShaderCode shaderCode, ShaderAttributes shaderAttributes);

    /**
     * Assigns a uniform to this shader in the form of a 4x4 matrix. May also be
     * used to assign an array of matrix values.
     * 
     * @param uniform
     *     - The uniform name.
     * @param value
     *     - The matrix values.
     * @throws IllegalArgumentException
     *     If the float buffer has a length which is not an increment of 16.
     */
    void setUniformMat4(String uniform, FloatBuffer value);

    /**
     * Assigns a uniform to this shader in the form of an int.
     * 
     * @param uniform
     *     - The uniform name.
     * @param value
     *     - The value to assign.
     */
    void setUniformInt(String uniform, int value);
}
