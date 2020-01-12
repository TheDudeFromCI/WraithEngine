package net.whg.we.resource;

import net.whg.we.rendering.VertexData;

public class VertexDataResource implements IResource<VertexData>
{
    private final VertexData vertexData;

    public VertexDataResource(VertexData vertexData)
    {
        this.vertexData = vertexData;
    }

    @Override
    public VertexData getData()
    {
        return vertexData;
    }
}
