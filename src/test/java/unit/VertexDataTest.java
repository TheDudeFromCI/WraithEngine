package unit;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import net.whg.we.rendering.ShaderAttributes;
import net.whg.we.rendering.VertexData;

public class VertexDataTest
{
    @Test
    public void equals_sameInstance()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles = new short[] {0, 1, 2};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertEquals(vertexData, vertexData);
        assertEquals(vertexData.hashCode(), vertexData.hashCode());
    }

    @Test
    public void equals_diffInstance()
    {
        float[] data1 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles1 = new short[] {0, 1, 2};
        ShaderAttributes att1 = new ShaderAttributes();
        att1.addAttribute("pos", 3);

        float[] data2 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles2 = new short[] {0, 1, 2};
        ShaderAttributes att2 = new ShaderAttributes();
        att2.addAttribute("pos", 3);

        VertexData vertexData1 = new VertexData(data1, triangles1, att1);
        VertexData vertexData2 = new VertexData(data2, triangles2, att2);

        assertEquals(vertexData1, vertexData2);
        assertEquals(vertexData1.hashCode(), vertexData2.hashCode());
    }

    @Test
    public void notEquals_diffInstance_diffData()
    {
        float[] data1 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 1};
        short[] triangles1 = new short[] {0, 1, 2};
        ShaderAttributes att1 = new ShaderAttributes();
        att1.addAttribute("pos", 3);

        float[] data2 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles2 = new short[] {0, 1, 2};
        ShaderAttributes att2 = new ShaderAttributes();
        att2.addAttribute("pos", 3);

        VertexData vertexData1 = new VertexData(data1, triangles1, att1);
        VertexData vertexData2 = new VertexData(data2, triangles2, att2);

        assertNotEquals(vertexData1, vertexData2);
        assertNotEquals(vertexData1.hashCode(), vertexData2.hashCode());
    }

    @Test
    public void notEquals_diffInstance_diffTriangleOrder()
    {
        float[] data1 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles1 = new short[] {0, 1, 2};
        ShaderAttributes att1 = new ShaderAttributes();
        att1.addAttribute("pos", 3);

        float[] data2 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles2 = new short[] {0, 2, 1};
        ShaderAttributes att2 = new ShaderAttributes();
        att2.addAttribute("pos", 3);

        VertexData vertexData1 = new VertexData(data1, triangles1, att1);
        VertexData vertexData2 = new VertexData(data2, triangles2, att2);

        assertNotEquals(vertexData1, vertexData2);
        assertNotEquals(vertexData1.hashCode(), vertexData2.hashCode());
    }

    @Test
    public void notEquals_diffInstance_diffAttributes()
    {
        float[] data1 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles1 = new short[] {0, 1, 2};
        ShaderAttributes att1 = new ShaderAttributes();
        att1.addAttribute("pos", 2);
        att1.addAttribute("color", 1);

        float[] data2 = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles2 = new short[] {0, 1, 2};
        ShaderAttributes att2 = new ShaderAttributes();
        att2.addAttribute("pos", 3);

        VertexData vertexData1 = new VertexData(data1, triangles1, att1);
        VertexData vertexData2 = new VertexData(data2, triangles2, att2);

        assertNotEquals(vertexData1, vertexData2);
        assertNotEquals(vertexData1.hashCode(), vertexData2.hashCode());
    }

    @Test
    public void getDataArray()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles = new short[] {0, 1, 2};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertArrayEquals(data, vertexData.getDataArray(), 0f);
    }

    @Test
    public void getTriangleArray()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0};
        short[] triangles = new short[] {0, 1, 2};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertArrayEquals(triangles, vertexData.getTriangles());
    }

    @Test
    public void getVertexSize()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 3, 2, 1, 1, 1, 1, 0, 1, 0, -3, 1, 3, 5, 1, 0, 1, 2, 3, 4};
        short[] triangles = new short[] {0, 1, 2};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);
        att.addAttribute("uv", 2);
        att.addAttribute("normal", 3);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertEquals(8, vertexData.getVertexSize());
    }

    @Test
    public void getVertexByteSize()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1};
        short[] triangles = new short[] {0, 1, 2};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 2);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertEquals(8, vertexData.getVertexByteSize());
    }

    @Test
    public void getTriangleCount()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 2, 3, 7, 8, 9};
        short[] triangles = new short[] {0, 1, 2, 1, 2, 3};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertEquals(2, vertexData.getTriangleCount());
    }

    @Test
    public void getAttributeSizes()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 4, 5, 6, 0, 1, 0, 0, 0, 7, 8, 9, 1};
        short[] triangles = new short[] {0, 1, 2, 1, 2, 3};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        VertexData vertexData = new VertexData(data, triangles, att);

        assertEquals(att, vertexData.getAttributeSizes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_brokenTriangleCount()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1};
        short[] triangles = new short[] {0, 1, 2, 1, 2};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        new VertexData(data, triangles, att);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_noAttributes()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1};
        short[] triangles = new short[] {0, 1, 2};
        ShaderAttributes att = new ShaderAttributes();

        new VertexData(data, triangles, att);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_wrongDataLength()
    {
        float[] data = new float[] {0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1};
        short[] triangles = new short[] {0, 1, 2, 1, 2, 0};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        new VertexData(data, triangles, att);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_triangles_negativeVertex()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1};
        short[] triangles = new short[] {0, -1, 2, 1, 2, 0};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        new VertexData(data, triangles, att);
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_triangles_vertexOutOfBounds()
    {
        float[] data = new float[] {0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1};
        short[] triangles = new short[] {0, 1, 20, 1, 2, 0};
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("pos", 3);

        new VertexData(data, triangles, att);
    }
}
