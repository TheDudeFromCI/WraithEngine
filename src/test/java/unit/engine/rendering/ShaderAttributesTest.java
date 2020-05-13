package unit.engine.rendering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Assert;
import org.junit.Test;
import net.whg.we.rendering.ShaderAttributes;

public class ShaderAttributesTest
{
    @Test
    public void defaultSize()
    {
        ShaderAttributes attrib = new ShaderAttributes();

        Assert.assertEquals(0, attrib.getCount());
        Assert.assertEquals(0, attrib.getVertexSize());
        Assert.assertEquals(0, attrib.getVertexByteSize());
    }

    @Test
    public void addAttrib()
    {
        ShaderAttributes attrib = new ShaderAttributes();

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv", 2);

        Assert.assertEquals(3, attrib.getCount());
        Assert.assertEquals(8, attrib.getVertexSize());
        Assert.assertEquals(32, attrib.getVertexByteSize());
    }

    @Test
    public void addTooMany()
    {
        ShaderAttributes attrib = new ShaderAttributes(1);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv1", 2);
        attrib.addAttribute("uv2", 2);
        attrib.addAttribute("uv3", 2);
        attrib.addAttribute("uv4", 2);
        attrib.addAttribute("uv5", 2);
        attrib.addAttribute("uv6", 2);
        attrib.addAttribute("uv7", 2);
        attrib.addAttribute("uv8", 2);

        assertEquals(22, attrib.getVertexSize());
    }

    @Test
    public void getSizeOf()
    {
        ShaderAttributes attrib = new ShaderAttributes();

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv", 2);

        Assert.assertEquals(3, attrib.getSizeOf("pos"));
        Assert.assertEquals(3, attrib.getSizeOf("normal"));
        Assert.assertEquals(2, attrib.getSizeOf("uv"));
    }

    @Test
    public void getSizeOf_wrongAttrib()
    {
        ShaderAttributes attrib = new ShaderAttributes();

        Assert.assertEquals(-1, attrib.getSizeOf("pos"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void addLargeAttrib()
    {
        ShaderAttributes attrib = new ShaderAttributes();
        attrib.addAttribute("pos", 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNegativeAttrib()
    {
        ShaderAttributes attrib = new ShaderAttributes();
        attrib.addAttribute("pos", -1);
    }

    @Test
    public void getAttributeName()
    {
        ShaderAttributes attrib = new ShaderAttributes();

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv", 2);

        assertEquals("pos", attrib.getAttributeName(0));
        assertEquals("normal", attrib.getAttributeName(1));
        assertEquals("uv", attrib.getAttributeName(2));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getNegativeAttribName()
    {
        ShaderAttributes attrib = new ShaderAttributes();
        attrib.getAttributeName(-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getAttribName_OverCount_RoomInBuffer()
    {
        ShaderAttributes attrib = new ShaderAttributes(16);
        attrib.getAttributeName(1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getNegativeAttribSize()
    {
        ShaderAttributes attrib = new ShaderAttributes();
        attrib.getAttributeSize(-1);
    }

    @Test
    public void removeAttribute()
    {
        ShaderAttributes attrib = new ShaderAttributes(4);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv1", 2);
        attrib.addAttribute("uv2", 2);

        attrib.removeAttribute(1);

        Assert.assertEquals(3, attrib.getCount());
        Assert.assertEquals(0, attrib.indexOf("pos"));
        Assert.assertEquals(1, attrib.indexOf("uv1"));
        Assert.assertEquals(2, attrib.indexOf("uv2"));
        Assert.assertEquals(-1, attrib.indexOf("normal"));
    }

    @Test
    public void getPositionInVertex()
    {
        ShaderAttributes attrib = new ShaderAttributes(4);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv1", 2);
        attrib.addAttribute("uv2", 2);

        Assert.assertEquals(0, attrib.getPositionInVertex(0));
        Assert.assertEquals(3, attrib.getPositionInVertex(1));
        Assert.assertEquals(6, attrib.getPositionInVertex(2));
        Assert.assertEquals(8, attrib.getPositionInVertex(3));
    }

    @Test
    public void getPositionInVertex_name()
    {
        ShaderAttributes attrib = new ShaderAttributes(4);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv1", 2);
        attrib.addAttribute("uv2", 2);

        Assert.assertEquals(0, attrib.getPositionInVertex("pos"));
        Assert.assertEquals(3, attrib.getPositionInVertex("normal"));
        Assert.assertEquals(6, attrib.getPositionInVertex("uv1"));
        Assert.assertEquals(8, attrib.getPositionInVertex("uv2"));
    }

    @Test
    public void getPositionInVertex_name_doesntExist()
    {
        ShaderAttributes attrib = new ShaderAttributes(4);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);
        attrib.addAttribute("uv1", 2);
        attrib.addAttribute("uv2", 2);

        Assert.assertEquals(-1, attrib.getPositionInVertex("tangent"));
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getPositionInVertex_outOfBounds_low()
    {
        ShaderAttributes attrib = new ShaderAttributes(4);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);

        attrib.getPositionInVertex(-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void getPositionInVertex_outOfBounds_high()
    {
        ShaderAttributes attrib = new ShaderAttributes(4);

        attrib.addAttribute("pos", 3);
        attrib.addAttribute("normal", 3);

        attrib.getPositionInVertex(6);
    }

    @Test
    public void indexedAttributeName()
    {
        assertEquals("uv", ShaderAttributes.getIndexedAttribute("uv", 1));
        assertEquals("uv2", ShaderAttributes.getIndexedAttribute("uv", 2));
        assertEquals("uv3", ShaderAttributes.getIndexedAttribute("uv", 3));
        assertEquals("uv99", ShaderAttributes.getIndexedAttribute("uv", 99));
    }

    @Test(expected = IllegalArgumentException.class)
    public void indexedAttributeName_negativeIndex()
    {
        ShaderAttributes.getIndexedAttribute("color", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void indexedAttributeName_nullAttribute()
    {
        ShaderAttributes.getIndexedAttribute(null, 1);
    }

    @Test
    public void equals_sameInstance()
    {
        ShaderAttributes att = new ShaderAttributes();

        att.addAttribute("apple", 3);
        att.addAttribute("caramel", 2);

        assertEquals(att, att);
    }

    @Test
    public void equals_diffInstance()
    {
        ShaderAttributes att1 = new ShaderAttributes();
        ShaderAttributes att2 = new ShaderAttributes();

        att1.addAttribute("red", 1);
        att1.addAttribute("green", 4);

        att2.addAttribute("red", 1);
        att2.addAttribute("green", 4);

        assertEquals(att1, att2);
    }

    @Test
    public void equals_diffInstance_diffAttributes()
    {
        ShaderAttributes att1 = new ShaderAttributes();
        ShaderAttributes att2 = new ShaderAttributes();

        att1.addAttribute("red", 1);
        att1.addAttribute("green", 1);

        att2.addAttribute("red", 1);

        assertNotEquals(att1, att2);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void removeAttribute_outOfBounds_negative()
    {
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("red", 1);

        att.removeAttribute(-1);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void removeAttribute_outOfBounds_tooHigh()
    {
        ShaderAttributes att = new ShaderAttributes();
        att.addAttribute("red", 1);

        att.removeAttribute(2);
    }
}
