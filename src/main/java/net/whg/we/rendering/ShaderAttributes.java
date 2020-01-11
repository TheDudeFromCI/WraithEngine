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
public class ShaderAttributes implements Externalizable
{
    private static final int FILE_VERSION = 1;

    private String[] _attribNames;
    private int[] _attribSizes;
    private int _count;
    private int _vertexSize;

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
        _attribNames = new String[bufferSize];
        _attribSizes = new int[bufferSize];
    }

    /**
     * Gets the number of attributes that have been specified.
     *
     * @return The number of attributes.
     */
    public int getCount()
    {
        return _count;
    }

    /**
     * Gets the current size of each vertex, in floats, based on the current
     * attribute sizes.
     *
     * @return The size of each vertex.
     */
    public int getVertexSize()
    {
        return _vertexSize;
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
        if (index < 0 || index >= _count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + _count + ")");

        return _attribNames[index];
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
        if (index < 0 || index >= _count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + _count + ")");

        return _attribSizes[index];
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

        if (_count + 1 >= _attribNames.length)
        {
            String[] newNames = new String[_attribNames.length + 4];
            int[] newSizes = new int[newNames.length];

            for (int i = 0; i < _attribNames.length; i++)
            {
                newNames[i] = _attribNames[i];
                newSizes[i] = _attribSizes[i];
            }

            _attribNames = newNames;
            _attribSizes = newSizes;
        }

        _attribNames[_count] = name;
        _attribSizes[_count] = size;

        _count++;
        _vertexSize += size;
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
        if (index < 0 || index >= _count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + _count + ")");

        _vertexSize -= _attribSizes[index];
        for (int i = index; i < _count; i++)
        {
            _attribNames[i] = _attribNames[i + 1];
            _attribSizes[i] = _attribSizes[i + 1];
        }

        _count--;
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

        return _attribSizes[index];
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
        for (int i = 0; i < _count; i++)
            if (_attribNames[i].equals(attrib))
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
        if (index < 0 || index >= _count)
            throw new ArrayIndexOutOfBoundsException("Index " + index + " out of bounds! (Size: " + _count + ")");

        int t = 0;
        for (int i = 0; i < index; i++)
            t += _attribSizes[i];
        return t;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeInt(FILE_VERSION);
        out.writeObject(_attribNames);
        out.writeObject(_attribSizes);
        out.writeInt(_vertexSize);
        out.writeInt(_count);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        int fileVersion = in.readInt();

        switch (fileVersion)
        {
            case 1:
                _attribNames = (String[]) in.readObject();
                _attribSizes = (int[]) in.readObject();
                _vertexSize = in.readInt();
                _count = in.readInt();
                return;

            default:
                throw new IllegalStateException("Unknown file version: " + fileVersion + "!");
        }
    }

    @Override
    public int hashCode()
    {
        return _attribNames.hashCode() ^ _attribSizes.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof ShaderAttributes))
            return false;

        ShaderAttributes o = (ShaderAttributes) obj;
        return _count == o._count && Arrays.equals(_attribSizes, o._attribSizes)
                && Arrays.equals(_attribNames, o._attribNames);
    }
}
