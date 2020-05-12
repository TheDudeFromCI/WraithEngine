package net.whg.we.external;

import static org.lwjgl.opengl.GL30.*;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;
import net.whg.we.rendering.CullingMode;
import net.whg.we.rendering.opengl.GLException;
import net.whg.we.rendering.opengl.IOpenGL;
import net.whg.we.util.OutputStreamWrapper;
import net.whg.we.util.OutputStreamWrapper.LogLevel;

/**
 * The implementation of the OpenGL api.
 */
public class OpenGLAPI implements IOpenGL
{
    private boolean debugMode;

    /**
     * Creates a new OpenGL API bridge instance.
     */
    public OpenGLAPI()
    {
        this(false);
    }

    /**
     * Creates a new OpenGL API bridge instance.
     * 
     * @param debugMode
     *     - Whether or not to enable debug mode. Provides useful debugging
     *     messages, but may be slower.
     */
    public OpenGLAPI(boolean debugMode)
    {
        this.debugMode = debugMode;
    }

    @Override
    public void init()
    {
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        if (debugMode)
            GLUtil.setupDebugMessageCallback(new PrintStream(new OutputStreamWrapper(LogLevel.DEBUG, new LoggerAPI())));
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
    public void setTexture2DNearestInterpolation(boolean mipmaps)
    {
        if (mipmaps)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_NEAREST);
        else
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    @Override
    public void setTexture2DBilinearInterpolation(boolean mipmaps)
    {
        if (mipmaps)
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_NEAREST);
        else
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    @Override
    public void setTexture2DTrilinearpolation()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    }

    @Override
    public void setTexture2DNearestSmoothedInterpolation()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    }

    @Override
    public void setUniformInt(int location, int value)
    {
        glUniform1i(location, value);
    }

    @Override
    public void bindAttributeLocation(int shaderId, String attribute, int location)
    {
        glBindAttribLocation(shaderId, location, attribute);
    }
}
