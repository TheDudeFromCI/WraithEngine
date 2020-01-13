package net.whg.we.rendering;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;

/**
 * Represents a specification of how vertices are formatted, as well as which
 * attributes the information represents, in order to be sent to the shader.
 *
 * @author TheDudeFromCI
 */
public class ShaderAttributes
{
    /**
     * A simple utility method for calculating the name of an attribute in which
     * multiple attributes of the same type are required. Such as: uv, uv2, uv3,
     * etc.
     * <p>
     * Additional attributes, after the first, can also be created by using the
     * format <code>attribute + id</code>, where id is the index of the uv coords.
     * 
     * @param attribute
     *     - The attribute name.
     * @param index
     *     - The index of the attribute.
     * @return The new attribute name for the given index.
     */
    public static String getIndexedAttribute(String attribute, int index)
    {
        if (index < 1)
            throw new IllegalArgumentException("Index must be at least 1!");

        if (attribute == null)
            throw new IllegalArgumentException("Attribute cannot be null!");

        if (index == 1)
            return attribute;

        return attribute + index;
    }

    /**
     * The position attribute.
     */
    public static final String ATTRIB_POSITION = "pos";

    /**
     * The normal attribute.
     */
    public static final String ATTRIB_NORMAL = "normal";

    /**
     * The uv attribute.
     */
    public static final String ATTRIB_UV = "uv";

    /**
     * The color attribute.
     */
    public static final String ATTRIB_COLOR = "color";

    /**
     * The bone indices attribute. Used for pointing to the bones used on the
     * vertex.
     */
    public static final String ATTRIB_BONE_INDICES = "bone_indices";

    /**
     * The bone weights attribute. Used for pointing to the bone weights for each
     * corresponding bone.
     */
    public static final String ATTRIB_BONE_WEIGHTS = "bone_weights";

    /**
     * The tangent attribute.
     */
    public static final String ATTRIB_TANGENT = "tangent";

    /**
     * The bitantent attribute.
     */
    public static final String ATTRIB_BITANGENT = "bitangent";

    private String[] attribNames;
    private int[] attribSizes;
    private int count;
    private int vertexSize;

    /**
     * Creates a new, empty, shader attrbutes object. This uses the initial internal
     * buffer size of 4. Buffers are resized as needed.
     */
    public ShaderAttributes()
    {
        this(4);
    }

    /**
     * Creates a new, empty, shader attrbutes object.
     *
     * @param bufferSize
     *     - The initial size of the internal arrays. This is automatically resized
     *     as needed.
     */
    public ShaderAttributes(int bufferSize)
    {
        attribNames = new String[bufferSize];
        attribSizes = new int[bufferSize];
    }

    /**
     * Gets the number of attributes that have been specified.
     *
     * @return The number of attributes.
     */
    public int getCount()
    {
        return count;
    }

    /**
     * Gets the current size of each vertex, in floats, based on the current
     * attribute sizes.
     *
     * @return The size of each vertex.
     */
    public int getVertexSize()
    {
        return vertexSize;
    }

    /**
     * Gets the name of the attribute at the given index.
     *
     * @param index
     *     - The index of the attribute.
     * @return The name of the attribute.
     */
    public String getAttributeName(int index)
    {
        if (index < 0 || index >= count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + count + ")");

        return attribNames[index];
    }

    /**
     * Gets the size of the attribute at the given index.
     *
     * @param index
     *     - The index of the attribute.
     * @return The size of the attribute.
     */
    public int getAttributeSize(int index)
    {
        if (index < 0 || index >= count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + count + ")");

        return attribSizes[index];
    }

    /**
     * Appends a new attribute to the end of the attribute list. If the internal
     * arraylist does not have the capacity to hold a new attribute, the buffer is
     * increased by 4 slots.
     *
     * @param name
     *     - The name of the new attribute.
     * @param size
     *     - The size of the new attribute. This must be a value between 1 and 4,
     *     inclusive.
     */
    public void addAttribute(String name, int size)
    {
        if (size <= 0)
            throw new IllegalArgumentException("Attribute sizes must be a positive integer!");
        if (size > 4)
            throw new IllegalArgumentException("Attribute sizes must 4 or lower!");

        if (count + 1 >= attribNames.length)
        {
            String[] newNames = new String[attribNames.length + 4];
            int[] newSizes = new int[newNames.length];

            for (int i = 0; i < attribNames.length; i++)
            {
                newNames[i] = attribNames[i];
                newSizes[i] = attribSizes[i];
            }

            attribNames = newNames;
            attribSizes = newSizes;
        }

        attribNames[count] = name;
        attribSizes[count] = size;

        count++;
        vertexSize += size;
    }

    /**
     * Removes the attribute at the given index. All attribute after this are moved
     * down.
     *
     * @param index
     *     - The index of the attribute to remove.
     */
    public void removeAttribute(int index)
    {
        if (index < 0 || index >= count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + count + ")");

        vertexSize -= attribSizes[index];
        for (int i = index; i < count; i++)
        {
            attribNames[i] = attribNames[i + 1];
            attribSizes[i] = attribSizes[i + 1];
        }

        count--;
    }

    /**
     * Gets the size of each vertex in bytes.
     *
     * @return The size of each vertice in bytes.
     * @see {@link #getVertexSize()}
     */
    public int getVertexByteSize()
    {
        return getVertexSize() * 4;
    }

    /**
     * A shorthand method for writing<br>
     * <code>getAttributeSize(indexOf(attrib))</code><br>
     * with error checking in case the attribute is not found.
     *
     * @param attrib
     *     - The name of the attribute.
     * @return The size of the attribute, or -1 if the attribute could not be found.
     */
    public int getSizeOf(String attrib)
    {
        int index = indexOf(attrib);
        if (index == -1)
            return -1;

        return attribSizes[index];
    }

    /**
     * Gets the index of the attribute with the given name. Preforms a search and
     * returns the index of the first attribute found with a matching name.
     *
     * @param attrib
     *     - The name of the attribute.
     * @return The index of the attribute, or -1 if the attribute could not be
     *     found.
     */
    public int indexOf(String attrib)
    {
        for (int i = 0; i < count; i++)
            if (attribNames[i].equals(attrib))
                return i;
        return -1;
    }

    /**
     * A shorthand method for writing<br>
     * <code>getPositionInVertex(indexOf(attrib))</code><br>
     * with error checking in case the attribute is not found.
     *
     * @param attrib
     *     - The name of the attribute.
     * @return The position within the vertex of this attribute, or -1 if the
     *     attribute could not be found.
     */
    public int getPositionInVertex(String attribute)
    {
        int index = indexOf(attribute);
        if (index == -1)
            return -1;

        return getPositionInVertex(index);
    }

    /**
     * Gets the starting position of this attribute within the vertex, based on the
     * sizes of all previous attributes.
     *
     * @param attributeIndex
     *     - The index of the attribute.
     * @return The position within the vertex of this attribute.
     */
    public int getPositionInVertex(int index)
    {
        if (index < 0 || index >= count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + count + ")");

        int t = 0;
        for (int i = 0; i < index; i++)
            t += attribSizes[i];
        return t;
    }

    @Override
    public int hashCode()
    {
        return Arrays.hashCode(attribNames) ^ Arrays.hashCode(attribSizes);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj.getClass() != obj.getClass())
            return false;

        ShaderAttributes o = (ShaderAttributes) obj;
        return count == o.count && Arrays.equals(attribSizes, o.attribSizes)
                && Arrays.equals(attribNames, o.attribNames);
    }
}
