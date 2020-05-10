package net.whg.we.net;

import java.io.Closeable;
import java.io.IOException;

/**
 * A testable wrapper for the Java server socket.
 */
public interface IServerSocket extends Closeable
{
    /**
     * Opens this server socket on the given port.
     * 
     * @param port
     *     - The port to open the server on, or 0 to let the system chose.
     * @throws IOException
     *     If an error occurs while preforming this action.
     */
    void start(int port) throws IOException;

    /**
     * Blocks this thread while waiting for a new client to connect.
     * 
     * @return The newly connected client.
     * @throws IOException
     *     If an error occurs while preforming this action.
     */
    ISocket waitForClient() throws IOException;

    /**
     * Checks whether or not this server socket has been closed.
     * 
     * @return True if the socket is closed or hasn't been opened. False otherwise.
     */
    boolean isClosed();

    /**
     * Gets the local port this socket was started on.
     * 
     * @return The local port.
     * @throws IllegalStateException
     *     If the server socket is closed.
     */
    int getLocalPort();
}
