package net.whg.we.util;

/**
 * An object container is an object which holds a list of objects that can be
 * read externally. This interface is used for containers which want their
 * contents to be read only. This is directed towards objects which avoid acting
 * as full containers.
 */
public interface IObjectContainer<T>
{
    /**
     * Gets the number of objects within this container.
     * 
     * @return The number of objects.
     */
    int getSize();

    /**
     * Gets the object in this container at the specified index.
     * 
     * @param index
     *     - The index of the object.
     * @return The object at the given index.
     */
    T getObjectAt(int index);
}
