package net.whg.we.rendering.opengl;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.we.rendering.IShader;
import net.whg.we.rendering.RawShaderCode;

public class GLShader implements IShader
{
    private static final String SHADER_DISPOSED = "Shader already disposed!";
    private static final Logger logger = LoggerFactory.getLogger(GLShader.class);

    private final Map<String, Integer> uniforms = new HashMap<>();
    private final IOpenGL opengl;
    private final BindStates bindStates;
    private int shaderId;
    private boolean disposed;
    private boolean created;

    /**
     * Creates a new OpenGL shader object.
     * 
     * @param bindStates
     *     - The container for binding GPU states.
     * @param opengl
     *     - The OpenGL instance.
     */
    GLShader(BindStates bindStates, IOpenGL opengl)
    {
        this.bindStates = bindStates;
        this.opengl = opengl;
    }

    @Override
    public void bind()
    {
        if (isDisposed())
            throw new IllegalStateException(SHADER_DISPOSED);

        if (!created)
            throw new IllegalStateException("Shader not yet compiled!");

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
        if (!created)
            return;

        if (bindStates.getBoundShader() == shaderId)
            bindStates.bindShader(0);

        opengl.deleteShaderProgram(shaderId);
    }

    @Override
    public void compile(RawShaderCode shaderCode)
    {
        if (isDisposed())
            throw new IllegalStateException(SHADER_DISPOSED);

        if (shaderCode == null)
            throw new IllegalArgumentException("Shader code cannot be null!");

        if (created)
        {
            logger.info("Recompiling shader.");
            destroyShader();
        }
        else
            logger.info("Compiling shader.");

        created = true;

        int vId = opengl.createVertexShader(shaderCode.getVertexShader());
        int fId = opengl.createFragementShader(shaderCode.getFragmentShader());
        shaderId = opengl.createShaderProgram();

        opengl.attachShaderProgram(shaderId, vId);
        opengl.attachShaderProgram(shaderId, fId);
        opengl.linkShader(shaderId);

        opengl.deleteShader(vId);
        opengl.deleteShader(fId);

        logger.debug("Compiled shader with ID: {}", shaderId);
    }

    private int getUniform(String uniform)
    {
        if (uniforms.containsKey(uniform))
            return uniforms.get(uniform);

        int loc = opengl.getUniformLocation(shaderId, uniform);
        uniforms.put(uniform, loc);
        return loc;
    }

    @Override
    public void setUniformMat4(String uniform, FloatBuffer value)
    {
        if (isDisposed())
            throw new IllegalStateException(SHADER_DISPOSED);

        bind();

        int loc = getUniform(uniform);
        opengl.setUniformMat4(loc, value);
    }

    @Override
    public boolean isCreated()
    {
        return created;
    }

    @Override
    public void setUniformInt(String uniform, int value)
    {
        if (isDisposed())
            throw new IllegalStateException(SHADER_DISPOSED);

        bind();

        int loc = getUniform(uniform);
        opengl.setUniformInt(loc, value);
    }
}
