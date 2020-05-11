package net.whg.we.net;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * A testable wrapper for the Java socket.
 */
public interface ISocket extends Closeable
{
    /**
     * Attempts to connect to a server.
     * 
     * @param ip
     *     - The ip to connect to.
     * @param port
     *     - The port to connect to.
     * @throws IOException
     *     If an error occurs while preforming this action.
     */
    void connect(String ip, int port) throws IOException;

    /**
     * Gets the input stream attached to this socket.
     * 
     * @return The input stream.
     * @throws IOException
     *     If an error occurs while preforming this action.
     */
    InputStream getInputStream() throws IOException;

    /**
     * Gets the output stream attached to this socket.
     * 
     * @return The output stream.
     * @throws IOException
     *     If an error occurs while preforming this action.
     */
    OutputStream getOutputStream() throws IOException;

    /**
     * Gets whether or not this socket is open.
     * 
     * @return True if the socket is closed or hasn't been opened yet. False
     *     otherwise.
     */
    boolean isClosed();

    /**
     * Gets the IP of other side of this socket.
     * 
     * @throws IllegalStateException
     *     If the socket is closed.
     */
    String getIP();

    /**
     * Gets the port of the other side of this socket.
     * 
     * @throws IllegalStateException
     *     If the socket is closed.
     */
    int getPort();
}
