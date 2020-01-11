package net.whg.we.rendering.opengl;

import static org.lwjgl.opengl.GL30.*;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.BufferUtils;
import net.whg.we.rendering.IMesh;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;

public class GLMesh implements IMesh
{
    private int vboId;
    private int vaoId;
    private int indexId;
    private int indexCount;
    private boolean disposed;
    private boolean created;

    @Override
    public void dispose()
    {
        if (isDisposed())
            return;

        disposed = true;

        if (created)
        {
            glDeleteBuffers(vboId);
            glDeleteBuffers(indexId);
            glDeleteVertexArrays(vaoId);
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

        glBindVertexArray(vaoId);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
        glDrawElements(GL_TRIANGLES, indexCount, GL_UNSIGNED_SHORT, 0);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glBindVertexArray(0);
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

        indexCount = vertexData.getTriangles().length;

        if (created)
            glBindVertexArray(vaoId);
        else
        {
            vaoId = glGenVertexArrays();
            glBindVertexArray(vaoId);

            vboId = glGenBuffers();
        }

        glBindBuffer(GL_ARRAY_BUFFER, vboId);
        glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

        int stride = vertexData.getVertexByteSize();
        int offset = 0;

        ShaderAttributes attributes = vertexData.getAttributeSizes();
        for (int i = 0; i < attributes.getCount(); i++)
        {
            glVertexAttribPointer(i, attributes.getAttributeSize(i), GL_FLOAT, false, stride, offset);
            glEnableVertexAttribArray(i);
            offset += attributes.getAttributeSize(i) * 4;
        }

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        indexId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);

        glBindVertexArray(0);

        created = true;
    }
}
