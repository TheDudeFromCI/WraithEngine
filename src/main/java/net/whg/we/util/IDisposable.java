package net.whg.we.util;

/**
 * A class which inherits this interface is able to be disposed and have it's
 * memory cleaned up to prevent memory leaks, despite a reference to the top
 * level object still being present. A disposable object cannot be used again
 * once disposed.
 */
public interface IDisposable
{
    /**
     * Disposes this object. Does nothing if the object is already disposed.
     */
    void dispose();

    /**
     * Checks if this object has been disposed or not.
     * 
     * @return True if this object has already been disposed. False otherwise.
     */
    boolean isDisposed();
}
