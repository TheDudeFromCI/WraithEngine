package net.whg.we.external;

import static org.lwjgl.opengl.GL30.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL45;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.rendering.CullingMode;
import net.whg.we.rendering.opengl.GLException;
import net.whg.we.rendering.opengl.IOpenGL;

/**
 * The implementation of the OpenGL api.
 */
public class OpenGLApi implements IOpenGL
{
    private static final Logger logger = LoggerFactory.getLogger(OpenGLApi.class);

    /**
     * This is a debug utility for finding OpenGL errors. This is a bit slow, so
     * only use for debugging purposes.
     * <p>
     * This method can be used by placing various calls to this method throughout
     * newly edited areas with a given state tag. These errors will be logged to the
     * console while running, allowing the developer to narrow down on the
     * problematic area. This method checks for the all OpenGL errors which occured
     * before this method call, meaning it is useful to place it after a set of
     * related actions. These method calls should be removed after debugging.
     * 
     * @param state
     *     - The state tag which should be written to the console.
     */
    public static void checkForErrors(String state)
    {
        int error;
        while ((error = glGetError()) != GL_NO_ERROR)
        {
            String errorName;

            switch (error)
            {
                case GL_INVALID_ENUM:
                    errorName = "Invalid Enum";
                    break;
                case GL_INVALID_VALUE:
                    errorName = "Invalid Value";
                    break;
                case GL_INVALID_OPERATION:
                    errorName = "Invalid Operation";
                    break;
                case GL_STACK_OVERFLOW:
                    errorName = "Stack Overflow";
                    break;
                case GL_STACK_UNDERFLOW:
                    errorName = "Stack Underflow";
                    break;
                case GL_OUT_OF_MEMORY:
                    errorName = "Out of Memory";
                    break;
                case GL_INVALID_FRAMEBUFFER_OPERATION:
                    errorName = "Invalid Framebuffer Operation";
                    break;
                case GL45.GL_CONTEXT_LOST:
                    errorName = "Context Lost";
                    break;
                default:
                    errorName = "Unknown Error";
                    break;
            }

            logger.error("OpenGL Error Detected! {} ({})", errorName, state);
        }
    }

    @Override
    public void init()
    {
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        for (int i = 0; i < 10; i++)
            glEnableVertexAttribArray(i);
    }

    @Override
    public void setDepthTesting(boolean depthTesting)
    {
        if (depthTesting)
            glEnable(GL_DEPTH_TEST);
        else
            glDisable(GL_DEPTH_TEST);
    }

    @Override
    public void setCullingMode(CullingMode mode)
    {
        switch (mode)
        {
            case NONE:
                glDisable(GL_CULL_FACE);
                break;

            case BACK_FACING:
                glEnable(GL_CULL_FACE);
                glCullFace(GL_BACK);
                break;

            case FRONT_FACING:
                glEnable(GL_CULL_FACE);
                glCullFace(GL_FRONT);
                break;
        }
    }

    @Override
    public void setClearColor(float r, float g, float b, float a)
    {
        glClearColor(r, g, b, a);
    }

    @Override
    public void clearScreen(boolean color, boolean depth)
    {
        int mask = 0;

        if (color)
            mask |= GL_COLOR_BUFFER_BIT;

        if (depth)
            mask |= GL_DEPTH_BUFFER_BIT;

        glClear(mask);
    }

    @Override
    public void dispose()
    {
        GL.destroy();
    }

    private int compileShader(String code, int type)
    {
        int id = glCreateShader(type);

        glShaderSource(id, code);
        glCompileShader(id);

        if (glGetShaderi(id, GL_COMPILE_STATUS) != GL_TRUE)
        {
            String logMessage = glGetShaderInfoLog(id);
            throw new GLException("Failed to compiled vertex shader! '" + logMessage + "'");
        }

        return id;
    }

    @Override
    public int createVertexShader(String code)
    {
        return compileShader(code, GL_VERTEX_SHADER);
    }

    @Override
    public int createFragementShader(String code)
    {
        return compileShader(code, GL_FRAGMENT_SHADER);
    }

    @Override
    public int createShaderProgram()
    {
        return glCreateProgram();
    }

    @Override
    public void deleteShaderProgram(int id)
    {
        glDeleteProgram(id);
    }

    @Override
    public void attachShaderProgram(int shaderId, int id)
    {
        glAttachShader(shaderId, id);
    }

    @Override
    public void linkShader(int shaderId)
    {
        glLinkProgram(shaderId);

        if (glGetProgrami(shaderId, GL_LINK_STATUS) != GL_TRUE)
        {
            String logMessage = glGetProgramInfoLog(shaderId);
            throw new GLException("Failed to link shader program! '" + logMessage + "'");
        }
    }

    @Override
    public void deleteShader(int id)
    {
        glDeleteShader(id);
    }

    @Override
    public int getUniformLocation(int shaderId, String uniform)
    {
        return glGetUniformLocation(shaderId, uniform);
    }

    @Override
    public void setUniformMat4(int location, FloatBuffer mat4)
    {
        glUniformMatrix4fv(location, false, mat4);
    }

    @Override
    public void bindShader(int shaderId)
    {
        glUseProgram(shaderId);
    }

    @Override
    public void deleteBuffer(int bufferId)
    {
        glDeleteBuffers(bufferId);
    }

    @Override
    public void deleteVertexArray(int vaoId)
    {
        glDeleteVertexArrays(vaoId);
    }

    @Override
    public void bindVertexArray(int vaoId)
    {
        glBindVertexArray(vaoId);
    }

    @Override
    public void bindBuffer(boolean elementBuffer, int bufferId)
    {
        glBindBuffer(elementBuffer ? GL_ELEMENT_ARRAY_BUFFER : GL_ARRAY_BUFFER, bufferId);
    }

    @Override
    public void drawElements(int indexCount)
    {
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_SHORT, 0);
    }

    @Override
    public void uploadBufferData(FloatBuffer data)
    {
        glBufferData(GL_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    @Override
    public void uploadBufferData(ShortBuffer data)
    {
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, data, GL_STATIC_DRAW);
    }

    @Override
    public int generateBuffer()
    {
        return glGenBuffers();
    }

    @Override
    public int generateVertexArray()
    {
        return glGenVertexArrays();
    }

    @Override
    public void setVertexAttributePointer(int index, int size, int stride, int offset)
    {
        glEnableVertexAttribArray(index);
        glVertexAttribPointer(index, size, GL_FLOAT, false, stride, offset);
    }

    @Override
    public void bindTextureSlot(int slot)
    {
        glActiveTexture(GL_TEXTURE0 + slot);
    }

    @Override
    public void bindTexture(int textureId)
    {
        glBindTexture(GL_TEXTURE_2D, textureId);
    }

    @Override
    public int generateTexture()
    {
        return glGenTextures();
    }

    @Override
    public void deleteTexture(int textureId)
    {
        glDeleteTextures(textureId);
    }

    @Override
    public void setTexture2DClampWrapMode()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP);
    }

    @Override
    public void setTexture2DRepeatWrapMode()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
    }

    @Override
    public void uploadTexture2DDataRGBA8(ByteBuffer pixels, int width, int height)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
    }

    @Override
    public void generateTexture2DMipmaps()
    {
        glGenerateMipmap(GL_TEXTURE_2D);
    }

    @Override
    public void setTexture2DNearestInterpolation()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    @Override
    public void setTexture2DBilinearInterpolation()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    @Override
    public void setTexture2DTrilinearpolation()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }
}
