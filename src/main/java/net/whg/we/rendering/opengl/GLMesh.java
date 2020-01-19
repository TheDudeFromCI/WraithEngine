package net.whg.we.rendering.opengl;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferUtils;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;

public class GLMesh implements IMesh
{
    private final IOpenGL opengl;
    private final BindStates bindStates;
    private int vboId;
    private int vaoId;
    private int indexId;
    private int indexCount;
    private boolean disposed;
    private boolean created;

    /**
     * Creates a new OpenGL mesh object.
     * 
     * @param bindStates
     *     - The container for binding GPU states.
     * @param opengl
     *     - The OpenGL instance.
     */
    GLMesh(BindStates bindStates, IOpenGL opengl)
    {
        this.bindStates = bindStates;
        this.opengl = opengl;
    }

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;

        if (created)
        {
            if (bindStates.getBoundVao() == vaoId)
                bindStates.bindVao(0);

            if (bindStates.getBoundBuffer() == indexId)
                bindStates.bindBuffer(true, 0);

            opengl.deleteBuffer(vboId);
            opengl.deleteBuffer(indexId);
            opengl.deleteVertexArray(vaoId);
        }
    }

    @Override
    public boolean isDisposed()
    {
        return disposed;
    }

    @Override
    public void render()
    {
        if (isDisposed())
            throw new IllegalStateException("Mesh already disposed!");

        if (!created)
            return;

        bindStates.bindVao(vaoId);
        bindStates.bindBuffer(true, indexId);
        opengl.drawElements(indexCount);
    }

    @Override
    public void update(VertexData vertexData)
    {
        if (isDisposed())
            throw new IllegalStateException("Mesh already disposed!");

        FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertexData.getDataArray().length);
        vertexBuffer.put(vertexData.getDataArray());
        vertexBuffer.flip();

        ShortBuffer indexBuffer = BufferUtils.createShortBuffer(vertexData.getTriangles().length);
        indexBuffer.put(vertexData.getTriangles());
        indexBuffer.flip();

        if (!created)
        {
            vaoId = opengl.generateVertexArray();
            vboId = opengl.generateBuffer();
            indexId = opengl.generateBuffer();
        }

        bindStates.bindVao(vaoId);

        bindStates.bindBuffer(false, vboId);
        opengl.uploadBufferData(vertexBuffer);

        int stride = vertexData.getVertexByteSize();
        int offset = 0;

        ShaderAttributes attributes = vertexData.getAttributeSizes();
        for (int i = 0; i < attributes.getCount(); i++)
        {
            opengl.setVertexAttributePointer(i, attributes.getAttributeSize(i), stride, offset);
            offset += attributes.getAttributeSize(i) * 4;
        }

        bindStates.bindBuffer(true, indexId);
        opengl.uploadBufferData(indexBuffer);

        created = true;
        indexCount = vertexData.getTriangles().length;
    }
}
