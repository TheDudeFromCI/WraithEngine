package net.whg.we.rendering.opengl;

import static org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GL32;
import net.whg.we.rendering.IShader;

public class GLShader implements IShader
{
    private final BindStates bindStates;
    private int _shaderId;
    private boolean disposed;
    private boolean created;

    GLShader(BindStates bindStates)
    {
        this.bindStates = bindStates;
    }

    @Override
    public void bind()
    {
        bindStates.bindShader(_shaderId);
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
        if (bindStates.getBoundShader() == _shaderId)
            bindStates.bindShader(0);

        glDeleteProgram(_shaderId);
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

        _shaderId = glCreateProgram();
        glAttachShader(_shaderId, vId);
        if (gId != -1)
            glAttachShader(_shaderId, gId);
        glAttachShader(_shaderId, fId);

        glLinkProgram(_shaderId);

        if (glGetProgrami(_shaderId, GL_LINK_STATUS) != GL_TRUE)
        {
            String logMessage = glGetProgramInfoLog(_shaderId);
            throw new GLException("Failed to link shader program! '" + logMessage + "'");
        }

        glDeleteShader(vId);
        if (gId != -1)
            glDeleteShader(gId);
        glDeleteShader(fId);
    }
}
