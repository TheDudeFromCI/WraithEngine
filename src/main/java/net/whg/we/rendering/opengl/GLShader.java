package net.whg.we.rendering.opengl;

import static org.lwjgl.opengl.GL30.*;
import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL32;
import net.whg.we.rendering.IShader;

public class GLShader implements IShader
{
    private final BindStates bindStates;
    private int shaderId;
    private boolean disposed;
    private boolean created;

    GLShader(BindStates bindStates)
    {
        this.bindStates = bindStates;
    }

    @Override
    public void bind()
    {
        bindStates.bindShader(shaderId);
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;
        destroyShader();
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    /**
     * Destroys the current shader object from the GPU.
     */
    private void destroyShader()
    {
        if (bindStates.getBoundShader() == shaderId)
            bindStates.bindShader(0);

        glDeleteProgram(shaderId);
    }

    @Override
    public void compile(String vertShader, String geoShader, String fragShader)
    {
        if (created)
            destroyShader();

        created = true;

        int vId = glCreateShader(GL_VERTEX_SHADER);
        int gId = -1;
        if (geoShader != null && !geoShader.isEmpty())
            gId = glCreateShader(GL32.GL_GEOMETRY_SHADER);
        int fId = glCreateShader(GL_FRAGMENT_SHADER);

        glShaderSource(vId, vertShader);
        glCompileShader(vId);

        if (glGetShaderi(vId, GL_COMPILE_STATUS) != GL_TRUE)
        {
            String logMessage = glGetShaderInfoLog(vId);
            throw new GLException("Failed to compiled vertex shader! '" + logMessage + "'");
        }

        if (gId != -1)
        {
            glShaderSource(gId, geoShader);
            glCompileShader(gId);

            if (glGetShaderi(gId, GL_COMPILE_STATUS) != GL_TRUE)
            {
                String logMessage = glGetShaderInfoLog(gId);
                throw new GLException("Failed to compiled geometry shader! '" + logMessage + "'");
            }
        }

        glShaderSource(fId, fragShader);
        glCompileShader(fId);

        if (glGetShaderi(fId, GL_COMPILE_STATUS) != GL_TRUE)
        {
            String logMessage = glGetShaderInfoLog(fId);
            throw new GLException("Failed to compiled fragment shader! '" + logMessage + "'");
        }

        shaderId = glCreateProgram();
        glAttachShader(shaderId, vId);
        if (gId != -1)
            glAttachShader(shaderId, gId);
        glAttachShader(shaderId, fId);

        glLinkProgram(shaderId);

        if (glGetProgrami(shaderId, GL_LINK_STATUS) != GL_TRUE)
        {
            String logMessage = glGetProgramInfoLog(shaderId);
            throw new GLException("Failed to link shader program! '" + logMessage + "'");
        }

        glDeleteShader(vId);
        if (gId != -1)
            glDeleteShader(gId);
        glDeleteShader(fId);
    }

    @Override
    public void setUniformMat4(String property, FloatBuffer value)
    {
        bind();

        int loc = glGetUniformLocation(shaderId, property);
        glUniformMatrix4fv(loc, false, value);
    }
}
