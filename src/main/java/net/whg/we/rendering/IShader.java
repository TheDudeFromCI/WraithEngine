package net.whg.we.rendering;

import java.nio.FloatBuffer;
import net.whg.we.util.IDisposable;

/**
 * A shader is used to determine how an object should be rendered to the screen.
 * It contains driver-specific code to be executed on the graphics card to
 * manipulate vertices into a renderable form. Shaders are also used to apply
 * effects to objects such as textures and lighting.
 */
public interface IShader extends IDisposable
{
    /**
     * When called, this shader is bound to the graphics card, if not already bound,
     * so that future mesh renders will use this shader to render with.
     */
    void bind();

    /**
     * Used to compile or re-compile this shader with a new shader program. As a
     * shader program is comprised of a vertex shader, a geometry shader, and a
     * fragment shader, providing these three components to the shader can be used
     * to compile the shader with.
     * 
     * @param vertShader
     *     - The vertex shader in GLSL code.
     * @param geoShader
     *     - The geometry shader in GLSL code, or null to not use a geometry shader.
     * @param fragShader
     *     - The fragment shader in GLSL code.
     * @throws IllegalArgumentException
     *     If vertShader or fragShader is null.
     */
    void compile(String vertShader, String geoShader, String fragShader);

    /**
     * Assigns a property to this shader in the form of a 4x4 matrix. May also be
     * used to assign an array of matrix values.
     * 
     * @param property
     *     - The property name.
     * @param value
     *     - The matrix values.
     * @throws IllegalArgumentException
     *     If the float buffer has a length which is not an increment of 16.
     */
    void setUniformMat4(String property, FloatBuffer value);
}
