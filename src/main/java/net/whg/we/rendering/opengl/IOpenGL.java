package net.whg.we.rendering.opengl;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import net.whg.we.rendering.CullingMode;

/**
 * This interface is a wrapper for OpenGL to add an extra layer of abstraction.
 */
public interface IOpenGL
{
    /**
     * Initializes OpenGL capabilities.
     */
    void init();

    /**
     * Disposes OpenGL capabilities.
     */
    void dispose();

    /**
     * Sets whether or not depth testing should be enabled.
     */
    void setDepthTesting(boolean depthTesting);

    /**
     * Sets the face culling mode for OpenGL.
     * 
     * @param mode
     *     - The new mode to assign.
     */
    void setCullingMode(CullingMode cullingMode);

    /**
     * Sets the color which is used to clear the screen with.
     * 
     * @param r
     *     - The red channel.
     * @param g
     *     - The green channel.
     * @param b
     *     - The blue channel.
     * @param a
     *     - The alpha channel.
     */
    void setClearColor(float r, float g, float b, float a);

    /**
     * Clears the screen.
     * 
     * @param color
     *     - Whether or not to clear the color channel.
     * @param depth
     *     - Whether or not to clear the depth channel.
     */
    void clearScreen(boolean color, boolean depth);

    /**
     * Creates a new OpenGL vertex shader.
     * 
     * @param code
     *     - The GLSL code to compile the vertex shader with.
     * @return A new vertex shader ID.
     * @throws GLException
     *     If the code could not be compiled.
     */
    int createVertexShader(String code);

    /**
     * Creates a new OpenGL fragment shader.
     * 
     * @param code
     *     - The GLSL code to compile the fragment shader with.
     * @return A new fragment shader ID.
     * @throws GLException
     *     If the code could not be compiled.
     */
    int createFragementShader(String code);

    /**
     * Creates a new shader program.
     * 
     * @return The ID of the newly created shader.
     */
    int createShaderProgram();

    /**
     * Deletes a shader program.
     * 
     * @param id
     *     - The ID of the shader program to delete.
     */
    void deleteShaderProgram(int id);

    /**
     * Attaches a new shader channel to a shader program. This prepares the shader
     * for linking.
     * 
     * @param shaderId
     *     - The shader program to attach the channel to.
     * @param id
     *     - The channel to attach to the shader.
     */
    void attachShaderProgram(int shaderId, int id);

    /**
     * Links together the channels in the shader pipeline, and readies it for use.
     * 
     * @param shaderId
     *     - The shader to link.
     */
    void linkShader(int shaderId);

    /**
     * Deletes a specific shader channel. Not to be confused with
     * {@link #deleteShaderProgram(int)}.
     * 
     * @param id
     *     - The ID of the shader to delete.
     */
    void deleteShader(int id);

    /**
     * Gets the location of a uniform within a shader.
     * 
     * @param shaderId
     *     - The shader to get the uniform from.
     * @param uniform
     *     - The name of the uniform to find.
     * @return The location of the uniform within the shader.
     */
    int getUniformLocation(int shaderId, String uniform);

    /**
     * Sets the value of a uniform as a 4x4 matrix, (or an array of 4x4 matrices).
     * 
     * @param location
     *     - The location of the uniform in the currently bound shader.
     * @param mat4
     *     - The matrix values.
     */
    void setUniformMat4(int location, FloatBuffer mat4);

    /**
     * Binds a shader to render with.
     * 
     * @param shaderId
     *     - The ID of the shader to bind.
     */
    void bindShader(int shaderId);

    /**
     * Deletes a buffer object.
     * 
     * @param bufferId
     *     - The ID of the buffer to delete.
     */
    void deleteBuffer(int bufferId);

    /**
     * Deletes a vertex array object.
     * 
     * @param vaoId
     *     - The ID of the vertex array to delete.
     */
    void deleteVertexArray(int vaoId);

    /**
     * Binds the given vertex array.
     * 
     * @param vaoId
     *     - The ID of the vertex array to bind.
     */
    void bindVertexArray(int vaoId);

    /**
     * Binds the given buffer object.
     * 
     * @param elementBuffer
     *     - True if the buffer represents an index buffer, false otherwise.
     * @param bufferId
     *     - The ID of the buffer object to bind.
     */
    void bindBuffer(boolean elementBuffer, int bufferId);

    /**
     * Draws the currently bound VAO and Index Buffer to the screen.
     * 
     * @param indexCount
     *     - The number of indices to render.
     */
    void drawElements(int indexCount);

    /**
     * Uploads a data buffer to the currently bound buffer object.
     * 
     * @param data
     *     - The data to upload.
     */
    void uploadBufferData(FloatBuffer data);

    /**
     * Uploads a data buffer to the currently bound buffer object.
     * 
     * @param data
     *     - The data to upload.
     */
    void uploadBufferData(ShortBuffer data);

    /**
     * Generates a new buffer object.
     * 
     * @return The ID of the newly created buffer.
     */
    int generateBuffer();

    /**
     * Generates a new vertex array object.
     * 
     * @return The ID of the newly created vertex array.
     */
    int generateVertexArray();

    /**
     * Sets up a pointer for a vertex attribute within a shader.
     * 
     * @param index
     *     - The index of the attribute.
     * @param size
     *     - The size of the attribute.
     * @param stride
     *     - The stride, in bytes, of the attribute.
     * @param offset
     *     - The offset, in bytes, of the attribute.
     */
    void setVertexAttributePointer(int index, int size, int stride, int offset);
}
